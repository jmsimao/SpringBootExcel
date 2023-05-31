package br.com.josemarcelo.SpringBootExcel.Controller;

import br.com.josemarcelo.SpringBootExcel.ErrorResponse.ErrorResponse;
import br.com.josemarcelo.SpringBootExcel.ErrorResponse.NotFoundException;


import br.com.josemarcelo.SpringBootExcel.Model.Cidade;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.io.File;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	private List<Cidade> cidade = new ArrayList<Cidade>();
	static final String nomearquivo = "C:/Stage/Cidades.xlsx";

	public CidadeController() throws IOException {
		try {
			this.populaCidade("Cidade");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void populaCidade(String sheetName) throws IOException {
		
		try {
			FileInputStream arquivo = new FileInputStream(new File(CidadeController.nomearquivo));
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheet(sheetName);
				
			for (Row row: sheet) {
				this.cidade.add(new Cidade(
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
	Iterable<Cidade> getCidades() {
		if (!this.cidade.isEmpty()) {
			return this.cidade;
		}
		throw new NotFoundException("Não há cidades cadastradas!", null, null);
	}
	
	@GetMapping("/{nomeCidade}")
	Optional<Cidade> getCidadeByNomeCidade(@PathVariable String nomeCidade) {
		for (Cidade c: this.cidade) {	
			if (c.getCidade().equals(nomeCidade)) {
				return Optional.of(c);
			}
		}
		throw new NotFoundException("Cidade \"" + nomeCidade + "\" não existe!","Informe uma cidade cadastrada!", this.getClass().toString());
	}
	
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
    	ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
    										HttpStatus.NOT_FOUND.toString(),
    										e.getMessage(),
    										e.getErroInfo(),
    										e.getNomeClasse()
    										);
    	return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
	
}
