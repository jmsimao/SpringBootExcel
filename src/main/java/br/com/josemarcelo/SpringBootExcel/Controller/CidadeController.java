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
/* endpoints: 
 *  /cidade  					--> Todas as cidades.
 *  /cidade/20					--> Busca pelo id.
 *  /cidade/nome/São Paulo 		--> Busca pelo nome da cidade.
 *  /cidade/parcela/Branca		--> Busca todas as cidades que contém a
 *  								palavra "Branca"
 */
public class CidadeController {

	private List<Cidade> cidade = new ArrayList<Cidade>();
	private static final String nomearquivo = "C:/Stage/Cidades.xlsx";

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
	public Iterable<Cidade> getCidades() {
		if (this.cidade.isEmpty()) {
			throw new NotFoundException("Não há cidades cadastradas!","Base de dados vazia!");
		}
		return this.cidade;
	}
	
	@GetMapping("/{id}")
	public Optional<Cidade> getCidadeById(@PathVariable double id) {
		for (Cidade cidade: this.cidade)  {
			if (cidade.getId() == id) {
				return Optional.of(cidade);
			}
		}
		throw new NotFoundException("Id da cidade não existe!","Id: " + id);
	}
	
	@GetMapping("/nome/{nome}")
	public Optional<Cidade> getCidadeByNome(@PathVariable String nome) {
		for (Cidade cidade: this.cidade) {	
			if (cidade.getCidade().equals(nome)) {
				return Optional.of(cidade);
			}
		}
		throw new NotFoundException("Nome de cidade inválida!","Nome da cidade: " + nome);
	}
	
	@GetMapping("/parcela/{nome}")
	public Iterable<Cidade> getCidadeByNomeParcela(@PathVariable String nome) {
		List<Cidade> parcelaCidade = new ArrayList<Cidade>();
		for (Cidade cidade: this.cidade) {
			if (cidade.getCidade().contains(nome)) {
				parcelaCidade.add(cidade);
			}
		}
		if (parcelaCidade.size() > 0) {
			return parcelaCidade;
		}
		throw new NotFoundException("Não há cidades pela palavra informada!","Parcela: " + nome);
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
