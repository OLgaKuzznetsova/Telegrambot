import java.util.ArrayList;

public class TermRepository {
    private ArrayList<TermDefinition> termDefinition = new ArrayList<>();
    private GoogleSheetsRepo googleSheetsRepo = new GoogleSheetsRepo();

    public TermRepository() {
        var dataFromCsvFile = googleSheetsRepo.getDataFromCSV();
        var keys = dataFromCsvFile.keySet();

        for (var i :  keys)
            addData(i , dataFromCsvFile.get(i));

        for (var line : termDefinition)
            System.out.println(line.getTerm() + " : " + line.getDefinition(false));
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
