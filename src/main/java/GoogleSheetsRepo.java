import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.net.URL;
import java.util.Map;


public class GoogleSheetsRepo {
    //String google_sheets_repo_path = System.getenv("GOOGLE_SHEETS_REPO_PATH");
    private Map<String, String> dataFromCSVFile() {
        URL url = null;
        try {
            url = new URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vR_HwF6CXNajSikw_pLaDhXmCNlQiXZlm6lhvQNR5NF0uxInHEVfZ-utVoKnOJEV6bATE8XfebcRpO2/pub?output=csv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        Map<String, String> result = new HashMap();
        try (CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)) {
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
        return dataFromCSVFile();
    }
}
