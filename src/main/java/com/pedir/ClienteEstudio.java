package com.pedir;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ClienteEstudio {
	String cuit;
	String name;
	String hasTurno;
	Abogado abogado;
	AnsesDriver driver;
	
	public ClienteEstudio(String name, String cuit, String hasTurno) {
		this.cuit = cuit;
		this.name = name;
		this.hasTurno = hasTurno;
		this.abogado = new Abogado();
	}

	@Override
	public String toString() {
		return "ClienteEstudio [cuit=" + cuit + ", name=" + name + ", hasTurno=" + hasTurno + "]";
	}

	public void pedirTurno(){
		// TODO Auto-generated method stub
		this.driver = AnsesDriver.startDriver(); // con el form visible
		fillFirstForm();
		finishPedirTurno();
		this.driver.close();
	}

	private void fillFirstForm() {
		try {
			tryFillFirstForm();
		} catch (Exception e) {
			this.driver.get(this.driver.url);
			fillFirstForm();
		}
	}
	
	private void tryFillFirstForm() throws Exception {
		Thread.sleep(500);
		WebElement titularBox = this.driver.findElement(By.name("CuilTitular"));
		WebElement apoderadoBox = this.driver.findElement(By.name("CuilApoderado"));
		AnsesDriver.sendKeysSlow(titularBox, this.cuit);
		AnsesDriver.sendKeysSlow(apoderadoBox, this.abogado.cuit);
		apoderadoBox.sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("Ntelefono")));
	}
	
	private void finishPedirTurno() {
		try {
			fillSecondForm();
			selectUdai();
			selectTime();
			confirmClick();
		} catch (Exception e) {
			this.driver.get(this.driver.url);
			finishPedirTurno();
		}
	}
	private void fillSecondForm() throws InterruptedException {
		Thread.sleep(500);
		AnsesDriver.sendKeysSlow(driver.findElement(By.name("Ntelefono")), this.abogado.phone);
		AnsesDriver.sendKeysSlow(driver.findElement(By.name("Email")), this.abogado.email);
		AnsesDriver.sendKeysSlow(driver.findElement(By.name("ConfirmacionEmail")), this.abogado.email);
		driver.findElement(By.name("ConfirmacionEmail")).sendKeys(Keys.ENTER);
	}

	
	public void selectUdai() throws InterruptedException {
		Thread.sleep(500);
		List<WebElement> myElements = this.driver.findElements(By.cssSelector("#udai > div"));
		for (WebElement e : myElements) {
			if (!e.getText().toLowerCase().contains("tres de febrero")) {
				e.click();
				System.out.println("UDAI Seleccionada");
				break;
			}
		}
	}
	
	public void selectTime() throws InterruptedException {
		Thread.sleep(500);
		new Actions(this.driver).sendKeys(Keys.TAB).perform();
		new Actions(this.driver).sendKeys(Keys.ENTER).perform();
		new Actions(this.driver).sendKeys(Keys.TAB).perform();
		new Actions(this.driver).sendKeys(Keys.TAB).perform();
		new Actions(this.driver).sendKeys(Keys.ENTER).perform();
	}
	

	public void confirmClick() throws InterruptedException {
		try {
			WebElement botonContinuar = this.driver.findElement(By.name("button"));
			botonContinuar.click();
		} catch (Exception e) {
			Thread.sleep(200);
			confirmClick();
		}
	}

	
}

