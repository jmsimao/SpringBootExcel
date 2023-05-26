package br.com.josemarcelo.SpringBootExcel.Controller;

import br.com.josemarcelo.SpringBootExcel.Model.Cidade;

import java.util.List;
import java.util.Optional;

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
public class CidadeController {

	private List<Cidade> cidade = new ArrayList<Cidade>();
	static final String nomearquivo = "C:\\Users\\josesimao\\eclipse-workspace2\\SpringBootExcel\\src\\main\\resources\\files\\Cidades.xlsx";
	
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
		return this.cidade;
	}
	
	@GetMapping("/{nomeCidade}")
	Optional<Cidade> getCidadeByNomeCidade(@PathVariable String nomeCidade) {
		for (Cidade c: this.cidade) {	
			if (c.getCidade().equals(nomeCidade)) {
				return Optional.of(c);
			}
		}
		return Optional.empty();
	}
	
}
