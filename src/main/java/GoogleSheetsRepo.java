import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.net.URL;
import java.util.Map;


public class GoogleSheetsRepo {
    String google_sheets_repo_url;

    public GoogleSheetsRepo(String google_sheets_repo_url) {
        this.google_sheets_repo_url = google_sheets_repo_url;
    }

    private Map<String, String> dataFromCSVFile() {
        URL url = null;
        try {
            url = new URL(google_sheets_repo_url);
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
