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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josemarcelo.SpringBootExcel.Model.UF;

@RestController
@RequestMapping("/uf")
public class UFController {

	private List<UF> uf = new ArrayList<UF>();
	private static final String nomearquivo = "M:\\eclipse-workspace2\\SpringBootExcel\\src\\main\\resources\\files\\UF.xlsx";
	
	public UFController() throws IOException {
		this.populaUF();
	}

	private void populaUF() throws IOException {
		
		try {
			FileInputStream arquivo = new FileInputStream(new File(UFController.nomearquivo));
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheetAt(0);
				
			for (Row row: sheet) {
				uf.add(new UF(
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
	Iterable<UF> getUFS() {
		return uf;
	}
	
	@GetMapping("/{sigla}")
	Optional<UF> getUFBySigla(@PathVariable String sigla) {
		for (UF c: uf) {	
			if (c.getSigla().equals(sigla)) {
				return Optional.of(c);
			}
		}
		return Optional.empty();
	}
	
}
