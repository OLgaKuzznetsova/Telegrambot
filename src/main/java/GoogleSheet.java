import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleSheet {

    private static Sheets sheetsService;
    private String SPREADSHEET_ID = "1zUwM2rqsn6TLriLFHqOjzN9OLI5joQcribbLNtgTwts/edit#gid=0";
    private Map<String, String> dictionary = new HashMap<>();

    public void getInformation() throws IOException, GeneralSecurityException{
        sheetsService = SheetsServiceUtil.getSheetsService();
        String range = "Лист1!A2:B3";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();
        for (List row : values) {
            String term = row.get(0).toString();
            String definition = row.get(1).toString();
            dictionary.put(term, definition);
        }

    System.out.println(values);
    }


}
