package com.pedir;

import java.io.IOException;
import java.util.List;


public class Start {

	public static void main(String[] args) {

		try {
			ExcelFile wb = new ExcelFile("./Clientes-turnos.xlsx");
			List<ClienteEstudio> clientList = wb.createClientListFromExcel();
			wb.close();
			clientList.forEach((cliente) -> cliente.pedirTurno());

		} catch (IOException e) {
			System.out.println("No se encuentra el archivo Clientes-turnos.xlsx");
		}

	}
}
