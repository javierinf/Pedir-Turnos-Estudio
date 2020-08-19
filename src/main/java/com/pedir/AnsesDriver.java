package com.pedir;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AnsesDriver extends ChromeDriver {
	String url = "http://servicioswww.anses.gob.ar/TurnosInternet/g/41c2e3980b924f97878c605198ce248e/Solicitud/IngresoSolicitud?idprest=1";;

	private AnsesDriver(ChromeOptions options) throws Exception {
		super(options);
		this.get("https://www.whatismyip.com/es/");

		this.getUrl();
		
	}
	
	public static AnsesDriver startDriver() {
		ChromeOptions options = AnsesDriver.getOptions();
		AnsesDriver driver = AnsesDriver.tryCreateDriver(options);
		return driver;
	}

	private static ChromeOptions getOptions() {
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--log-level=3",
				"--silent","user-data-dir=selenium","--no-sandbox","excludeSwitches", "enable-automation");
		return options;
	}

	private static AnsesDriver tryCreateDriver(ChromeOptions options) {
		AnsesDriver driver;
		try {
			driver = new AnsesDriver(options);
		} catch (Exception e) {
			driver = tryCreateDriver(options);
		}
		return driver;
	}


	private void getUrl() throws Exception {
		this.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		this.tryGetUrl();

	}

	private void tryGetUrl() throws Exception {
		try {
			this.get(url);
			this.checkHasError();
		} catch (Exception e) {
			this.quitAndTrowException();
		}

	}

	private void quitAndTrowException() throws InterruptedException, Exception {
		this.quit();
		throw new Exception("Not Loaded");
	}

	private void checkHasError() {
		WebDriverWait wait = new WebDriverWait(this, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("CuilTitular")));

	}
	public static void sendKeysSlow(WebElement el, String keysToSend) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (char ch : keysToSend.toCharArray()) {
			
			el.sendKeys(String.valueOf(ch));
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
