import java.util.ArrayList;

public class TermRepository {
    private ArrayList<TermDefinition> termDefinition = new ArrayList<>();
    private GoogleSheetsRepo googleSheetsRepo = new GoogleSheetsRepo();

    public TermRepository() {
        var dataFromCsvFile = googleSheetsRepo.getDataFromCSV();
        var s = 0;
        for (var line : dataFromCsvFile) {
            if (s == 0) {
                s = 1;
                continue;
            }
            addData(line[0], line[1]);
        }
    }

    private void addData(String term, String definition) {
        TermDefinition classTermDefinition = new TermDefinition();
        classTermDefinition.setValues(term, definition);
        termDefinition.add(classTermDefinition);
    }

    public ArrayList<TermDefinition> getData() {
        return termDefinition;
    }
}
