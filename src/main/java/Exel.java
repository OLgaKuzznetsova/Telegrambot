import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class Exel {
    public static String readFromExcel() throws IOException {
        String fileName = ""; //твой имя файла
        // "C:\\Users\\user\\Desktop\\ООП\\Telegrambot\\data.xlsx"
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(fileName));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("data");
        XSSFRow row = myExcelSheet.getRow(0);
        if(row.getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING) {
            String name = row.getCell(0).getStringCellValue();
            System.out.println("ячейка A1 : " + name);
        }
        return "";
    }
}
