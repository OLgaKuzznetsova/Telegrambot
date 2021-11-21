import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
//Java SQL imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class GoogleSheetsRepo {
    String google_sheets_repo_path = System.getenv("GOOGLE_SHEETS_REPO_PATH");
    private Map<String, String> DataFromCSVFile() {
        URL url = null;
        try {
            url = new URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vR_HwF6CXNajSikw_pLaDhXmCNlQiXZlm6lhvQNR5NF0uxInHEVfZ-utVoKnOJEV6bATE8XfebcRpO2/pub?output=csv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        Map<String, String> result = new HashMap();
        try (CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)){
            var dataFromCSVFile = csvParser.getRecords();
            for (var i : dataFromCSVFile) {
                result.put(i.get(0), i.get(1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, String> getDataFromCSV() {
        return DataFromCSVFile();
    }
}
//ссылка на гугл таблицу
//https://docs.google.com/spreadsheets/d/1zUwM2rqsn6TLriLFHqOjzN9OLI5joQcribbLNtgTwts/edit#gid=0

//https://docs.google.com/spreadsheets/d/e/2PACX-1vR_HwF6CXNajSikw_pLaDhXmCNlQiXZlm6lhvQNR5NF0uxInHEVfZ-utVoKnOJEV6bATE8XfebcRpO2/pub?output=csv