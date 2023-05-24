package br.com.josemarcelo.SpringBootExcel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private static final String nomearq = "C:\\Users\\josesimao\\eclipse-workspace2\\SpringBootExcel\\src\\main\\resources\\files\\Alunos.xlsx";

	public AlunoController() throws Exception {
		this.populaAlunos();
	}
		
	private void populaAlunos() throws Exception {
		
		FileInputStream arquivo = new FileInputStream(new File(AlunoController.nomearq));
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet planilha = workbook.getSheetAt(0);
			
			for(Row p: planilha) {
				alunos.add(new Aluno(
						p.getCell(0).toString(),
						p.getCell(1).toString(),
						p.getCell(2).getNumericCellValue(),
						p.getCell(3).getNumericCellValue(),
						p.getCell(4).getNumericCellValue(),
						p.getCell(5).getStringCellValue())
						);
			}
		} finally {
			
		}
	
	}
	
	@GetMapping
	Iterable<Aluno> getAlunos() {
		return alunos;
	}
	
}
