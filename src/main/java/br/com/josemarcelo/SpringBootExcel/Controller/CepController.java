package br.com.josemarcelo.SpringBootExcel.Controller;

import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josemarcelo.SpringBootExcel.Model.Cep;
import br.com.josemarcelo.SpringBootExcel.ErrorResponse.*;

import java.text.DecimalFormat;

@RestController
@RequestMapping("/cepsp")
/* endpoints: 
 *  /cepsp/08551100						--> Buscar por um cep.
 *  /cepsp/logradouro/Av. Tiradentes	--> Busca pelo logradouro.
 *  /cepsp/logradouro/parcela/Paulista	--> Retorna os logradouros com a palavra "Paulista".
 *  /cepsp/bairro/Campos				--> Retorna os bairros com a palavra "Campos".
 *  /cepsp/cidade/6386					--> Retorna os CEPs da cidade 6386.
 *  /cepsp/nroceps						--> Retorna a quantidade de CEPs cadastrados.
 */
public class CepController {
	
	private List<Cep> cepsp = new ArrayList<Cep>();
	private static final String nomearq = "C:/Stage/CEPSP.xlsx";
	
	private DecimalFormat df = new DecimalFormat("###,###,###");
	
	public CepController() throws IOException {
		this.populaCep("CEPSP");
	}
	
	private void populaCep(String sheet) throws IOException {
		try {
			FileInputStream arquivo = new FileInputStream(new File(CepController.nomearq));
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet planilha = workbook.getSheet(sheet);
		
			for(Row r: planilha) {
				this.cepsp.add(new Cep(r.getCell(0).toString(),
										r.getCell(1).toString(),
										r.getCell(2).toString(),
										r.getCell(3).toString(),
										r.getCell(4).getNumericCellValue(),
										r.getCell(5).toString())
						);
			}
			
			workbook.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@GetMapping("/{cepNro}")
	public Optional<Cep> getCep(@PathVariable String cepNro) {
		for (Cep cep: this.cepsp) {	
			//System.out.println(c.getCep());
			if (cep.getCep().equals(cepNro)) {
				return Optional.of(cep);
			}
		}
		throw new NotFoundException("CEP não localizado pelo cep informado!","CEP: " + cepNro);
	}
	
	@GetMapping("/logradouro/{logr}")
	public Optional<Cep> getCepByLogradouro(@PathVariable String logr) {
		for (Cep cep: this.cepsp) {	
			if (cep.getLogradouro().equals(logr)) {
				return Optional.of(cep);
			}
		}
		throw new NotFoundException("CEP não localizado pelo nome do logradouro!","Logradouro: " + logr);
	}

	@GetMapping("/logradouro/parcela/{logr}")
	public Iterable<Cep> getCepsByLogradouroParcela(@PathVariable String logr) {
		List<Cep> parcelaCep = new ArrayList<Cep>();
		for (Cep cep: this.cepsp) {
			if (cep.getLogradouro().contains(logr)) {
				parcelaCep.add(cep);
			}
		}
		if (parcelaCep.size() > 0) {
			return parcelaCep;
		}
		throw new NotFoundException("Não há logradouros pela palavra informada!","Parcela: " + logr);
	}
	
	@GetMapping("/bairro/{bairro}") 
	public Iterable<Cep> getCepsByBairroParcela(@PathVariable String bairro) {
		List<Cep> parcelaCep = new ArrayList<>();
		for (Cep cep: this.cepsp) {
			if (cep.getBairro().contains(bairro)) {
				parcelaCep.add(cep);
			}
		}
		if (parcelaCep.size() > 0) {
			return parcelaCep;
		}
		throw new NotFoundException("Não há bairros pela palavra informada!","Parcela: " + bairro);
	}
	
	@GetMapping("/cidade/{cidade}") 
	public Iterable<Cep> getCepsByCidade(@PathVariable double cidade) {
		List<Cep> parcelaCep = new ArrayList<Cep>();
		for (Cep cep: this.cepsp) {
			if (cep.getCidade() == cidade) {
				parcelaCep.add(cep);
			}
		}
		if (parcelaCep.size() > 0) {
			return parcelaCep;
		}
		throw new NotFoundException("Não há cidades cadastradas pelo código!","Cidade: " + cidade);
	}
	
	@GetMapping("/nroceps")
	public String getCepNroCeps() {
		if (this.cepsp.isEmpty()) {
			throw new NotFoundException("Não há CEPs cadastrados!",null);
		}
		return "<h1>CEPs cadastrados: " + df.format(cepsp.size()) + "</h1>";
	}
	
	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<ErrorResponse> handlerNotFoundException(NotFoundException nfe) {
		ErrorResponse errorResponse = new ErrorResponse(
									HttpStatus.NOT_FOUND.value(),
									HttpStatus.NOT_FOUND.toString(),
									nfe.getMessage(),
									nfe.getErroInfo(),
									this.getClass().toString()
									);
		
		return new ResponseEntity<>(errorResponse,
									HttpStatus.NOT_FOUND);
	}
	
}
