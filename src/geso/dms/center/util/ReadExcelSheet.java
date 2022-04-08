package geso.dms.center.util;
import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelSheet {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void read() throws IOException  {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns(); j++) {				
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
/*					if (cell.getType() == CellType.LABEL) {
						System.out.println("I got a label "
								+ cell.getContents());
					}

					if (cell.getType() == CellType.NUMBER) {
						System.out.println("I got a number "
								+ cell.getContents());
					}*/
					System.out.print(cell.getContents() + "  ");					

				}
				System.out.println(" ");
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		ReadExcelSheet test = new ReadExcelSheet();
		test.setInputFile("d:\\phanboKM.xls");
		test.read();
	}

}
