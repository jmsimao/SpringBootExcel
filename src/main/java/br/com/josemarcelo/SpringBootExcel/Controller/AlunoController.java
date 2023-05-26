package br.com.josemarcelo.SpringBootExcel.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josemarcelo.SpringBootExcel.Model.Aluno;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
	@Value("${aplicacao.nomearquivo}")
	private String nomearq1;
	private static final String nomearq = "C:\\Users\\josesimao\\eclipse-workspace2\\SpringBootExcel\\src\\main\\resources\\files\\Alunos.xlsx";
		
	public AlunoController() throws IOException {
		this.populaAlunos();
	}
		
	private void populaAlunos() throws IOException {		
		
		try {
			FileInputStream arquivo = new FileInputStream(new File(AlunoController.nomearq));
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			for(Row row: sheet) {
				this.alunos.add(new Aluno(
								row.getCell(0).toString(),
								row.getCell(1).toString(),
								row.getCell(2).getNumericCellValue(),
								row.getCell(3).getNumericCellValue(),
								row.getCell(4).getNumericCellValue(),
								row.getCell(5).getStringCellValue())
								);
			}
			workbook.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}	
		
	}
	
	@GetMapping("/notas")
	Iterable<Aluno> getAlunos() {
		return this.alunos;
	}
	
	@GetMapping("/arquivo")
	String getFile() {
		return this.nomearq1;
	}
	
}
