package com.pedir;


import java.io.IOException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFile extends XSSFWorkbook {

	public ExcelFile() {
		super();
	}

	public ExcelFile(String path) throws IOException {
		super(path);
	}

	public List<ClienteEstudio> createClientListFromExcel() {
		Sheet sheet = this.getSheetAt(0);
		List<ClienteEstudio> listaClientes = new ArrayList<ClienteEstudio>();
		int i = 1;
		for (@SuppressWarnings("unused") Row myrow : sheet) {
			try {
				ClienteEstudio cliente = this.createClientFromRow(i);
				listaClientes.add(cliente);
			} catch (Exception e) {
				
			}
			i++;
		}

		return listaClientes;
	}

	public ClienteEstudio createClientFromRow(int numFila) throws Exception {

		String name = this.getClientName(numFila);
		String cuit = this.getClientCuit(numFila);
		String hasTurno = this.getClientHasTurno(numFila);
		boolean elegibleTo = !name.contentEquals("EMPTY") && !cuit.contentEquals("EMPTY") && !hasTurno.contentEquals("1");
		if (elegibleTo) {
			ClienteEstudio client = new ClienteEstudio(name, cuit, hasTurno);
			return client;
		} else {
			throw new Exception("EMPTY CLIENT ROW");
		}


	}

	public String getClientName(int numFila) {
		return this.ReadCellData(numFila, 0);
		
	}

	public String getClientCuit(int numFila) {
		return this.ReadCellData(numFila, 1);
	}

	public String getClientHasTurno(int numFila) {
		return this.ReadCellData(numFila, 2);
	}

	public String getClientCausante(int numFila) {
		return this.ReadCellData(numFila, 3);
	}

	private String ReadCellData(int vRow, int vColumn) {
		DecimalFormat decimalFormat = new DecimalFormat("0");
		Sheet sheet = this.getSheetAt(0);
		Row row = sheet.getRow(vRow);
		Cell cell = row.getCell(vColumn);

		if (cell == null) {
			return "EMPTY";
		}
		;

		if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
			double value;
			value = cell.getNumericCellValue();
			return decimalFormat.format(value);
		}
		if (cell.getCellTypeEnum().equals(CellType.STRING)) {
			String value;
			value = cell.getStringCellValue();
			return value;
		}

		return "EMPTY";

	}

}
