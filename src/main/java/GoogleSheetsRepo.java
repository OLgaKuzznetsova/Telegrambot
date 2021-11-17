import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GoogleSheetsRepo {
    private List<String[]> DataFromCSVFile() {
        try (CSVReader reader = new CSVReader(new FileReader("D:\\Определения_для_бота.csv"))) {
            List<String[]> dataFromCSVFile = reader.readAll();
            return dataFromCSVFile;
        } catch (CsvException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String[]> getDataFromCSV() {
        return DataFromCSVFile();
    }
}
//ссылка на гугл таблицу
//https://docs.google.com/spreadsheets/d/1zUwM2rqsn6TLriLFHqOjzN9OLI5joQcribbLNtgTwts/edit#gid=0