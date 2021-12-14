import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.net.URL;
import java.util.Map;


public class GoogleSheetsRepo {
    URL google_sheets_repo_url;

    public GoogleSheetsRepo(URL google_sheets_repo_url) {
        this.google_sheets_repo_url = google_sheets_repo_url;
    }

    public Map<String, String> getDataFromCSVFile() {
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        Map<String, String> result = new HashMap();
        try (CSVParser csvParser = CSVParser.parse(google_sheets_repo_url, StandardCharsets.UTF_8, csvFormat)) {
            var records = csvParser.getRecords();
            for (var record : records) {
                result.put(record.get(0), record.get(1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return result;
    }
}
