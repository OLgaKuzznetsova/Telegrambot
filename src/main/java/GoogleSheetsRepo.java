import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GoogleSheetsRepo{

    private HashMap<String, String> repository = new HashMap<>();

    public HashMap<String, String>  GoogleSheetsRepo() throws IOException {

        //HttpGet request = new HttpGet("https://docs.google.com/spreadsheets/d/1zUwM2rqsn6TLriLFHqOjzN9OLI5joQcribbLNtgTwts/edit#gid=0");
        try (CSVReader reader = new CSVReader(new FileReader("D:\\Определения_для_бота - Bot (1).csv"))) {
            List<String[]> r = reader.readAll();
            var s = 0;
            for (var i : r) {
                if (s == 0) {
                    s = 1;
                    continue;
                }

                repository.put(i[0], i[1]);
            }

        } catch (CsvException e) {
            e.printStackTrace();
        }
        //System.out.println("");
        //System.out.println(repository);
        //System.out.println("");
        return repository;
    }

}