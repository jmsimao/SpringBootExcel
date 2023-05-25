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
@RequestMapping("/alunos")
public class AlunoController {
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
	@Value("${aplicacao.nomearquivo}")
	private String nomearq1;
	private static final String nomearq = "C:\\Stage\\Alunos.xlsx";
		//"C:\\Users\\josesimao\\eclipse-workspace2\\SpringBootExcel\\target\\classes\\files\\Alunos.xlsx";
		
	public AlunoController() throws IOException {
		this.populaAlunos();
	}
		
	private void populaAlunos() throws IOException {		
		FileInputStream arquivo = new FileInputStream(new File(AlunoController.nomearq));
		XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		try {
			for(Row row: sheet) {
				alunos.add(new Aluno(
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
	
	@GetMapping
	Iterable<Aluno> getAlunos() {
		return alunos;
	}
	
	@GetMapping("/file")
	String getFile() {
		return nomearq1;
	}
	
}
