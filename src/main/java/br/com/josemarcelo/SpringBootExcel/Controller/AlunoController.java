package br.com.josemarcelo.SpringBootExcel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
/* endpoints: 
 *  /aluno/notas			--> Notas dos alunos.
 *  /aluno/arquivo			--> Teste de saída.
 */
public class AlunoController {
		
	private List<Aluno> alunos = new ArrayList<Aluno>();
	@Value("${aplicacao.nomearquivo}")
	private String nomearq1;
	
	@Autowired
	private String home;
	private static String nomearq = "C:/Stage/Cidades.xlsx";
	
	public AlunoController() throws IOException {
		this.populaAlunos("Alunos");
	}
		
	private void populaAlunos(String sheetName) throws IOException {		
		
		try {
			FileInputStream arquivo = new FileInputStream(new File(AlunoController.nomearq));
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			
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
		return "<h1>" + home + "</h1>";
	}
	
}
