package br.com.josemarcelo.SpringBootExcel.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josemarcelo.SpringBootExcel.ErrorResponse.ErrorResponse;
import br.com.josemarcelo.SpringBootExcel.ErrorResponse.NotFoundException;
import br.com.josemarcelo.SpringBootExcel.Model.UF;

@RestController
@RequestMapping("/uf")
public class UFController {

	private List<UF> uf = new ArrayList<UF>();
	private static final String nomearquivo = "C:/Stage/Cidades.xlsx";
	
	public UFController() throws IOException {
		try {
			this.populaUF("UF");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void populaUF(String sheetName) throws IOException {
		
		try {
			FileInputStream arquivo = new FileInputStream(new File(UFController.nomearquivo));
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheet(sheetName);
				
			for (Row row: sheet) {
				this.uf.add(new UF(
								row.getCell(0).getNumericCellValue(),
								row.getCell(1).toString(),
								row.getCell(2).toString())
								);
			}
			workbook.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@GetMapping
	Iterable<UF> getUF() {
		if (this.uf.isEmpty()) {
			throw new NotFoundException("Não há UFs cadastradas!","Base de dados vazia");
		}
		return this.uf;
	}
	
	@GetMapping("/{sigla}")
	Optional<UF> getUFBySigla(@PathVariable String sigla) {
		for (UF c: this.uf) {	
			if (c.getSigla().equals(sigla)) {
				return Optional.of(c);
			}
		}
		throw new NotFoundException("Sigla inválida da UF!","Sigla informada: " + sigla);
	}
	
	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<ErrorResponse> handlerNotFoundException(NotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse(
										HttpStatus.NOT_FOUND.value(),
										HttpStatus.NOT_FOUND.toString(),
										e.getMessage(),
										e.getErroInfo(),
										this.getClass().getName()
										);
		
		return new ResponseEntity<>(errorResponse,
									HttpStatus.NOT_FOUND
									);
	}
	
	
		
}
