import java.util.ArrayList;
import java.util.Map;

public class TermRepository {
    private ArrayList<TermDefinition> termDefinition = new ArrayList<>();
    private GoogleSheetsRepo googleSheetsRepo = new GoogleSheetsRepo();
    private Map<String, String> dataFromCsvFile = googleSheetsRepo.getDataFromCSV();
    private ErrorSearch errorSearch = new ErrorSearch();

    public TermRepository() {
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
    public String allTerms(){
        var terms = dataFromCsvFile.keySet().toString();
        return terms.substring(1, terms.length()-1);
    }

    private String similarWords(String userInput) {
        var message = new StringBuilder("Мы не нашли такого определения. Возможно вы имели ввиду одно из этих?\n\n");

        for (var term : errorSearch.getSimilarTerms(userInput)) {

            message.append(term + ", ");
        }
        return message.toString();
    }

    public String definitionToTerm(String userInput) {
        for (int i = 0; i < termDefinition.size(); i++) {
            if (userInput.equalsIgnoreCase(termDefinition.get(i).getTerm())) {
                return termDefinition.get(i).getTerm() + " - " + termDefinition.get(i).getDefinition(false);
            }
        }
        return similarWords(userInput);
    }
}
