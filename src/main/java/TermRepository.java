import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TermRepository {
    private ArrayList<TermDefinition> termDefinition;
    private Map<String, String> dataFromCsvFile;
    private ErrorSearch errorSearch;

    public TermRepository(ErrorSearch errorSearch, ArrayList<TermDefinition> termDefinition, Map<String, String> dataFromCsvFile) {
        this.errorSearch = errorSearch;
        this.termDefinition = termDefinition;
        this.dataFromCsvFile = dataFromCsvFile;
        for (var i : this.dataFromCsvFile.keySet())
            addData(i, this.dataFromCsvFile.get(i));
    }

    private void addData(String term, String definition) {
        TermDefinition classTermDefinition = new TermDefinition();
        classTermDefinition.setValues(term, definition);
        termDefinition.add(classTermDefinition);
    }

    public String getAllTerms() {
        var terms = dataFromCsvFile.keySet().toString();
        return terms.substring(1, terms.length() - 1);
    }

    private String gatSimilarWords(String userInput) {
        var message = new StringBuilder("Мы не нашли такого определения. Возможно вы имели ввиду одно из этих?\n\n");

        for (var term : getSimilarTerms(userInput)) {

            message.append(term + ", ");
        }
        return message.toString();
    }

    public String gatDefinitionToTerm(String userInput) {
        for (int i = 0; i < termDefinition.size(); i++) {
            if (userInput.equalsIgnoreCase(termDefinition.get(i).getTerm())) {
                return termDefinition.get(i).getTerm() + " - " + termDefinition.get(i).getDefinition(false);
            }
        }
        return gatSimilarWords(userInput);
    }

    public ArrayList<String> getSimilarTerms(String userInput) {

        Map<String, Integer> levenshteinDistanceBetweenTerms = new HashMap<>();
        ArrayList<String> similarTerms = new ArrayList<>();
        int minimalDistance = Integer.MAX_VALUE;
        for (var i = 0; i < termDefinition.size(); i++) {
            var distance = errorSearch.getLevenshteinDistance(userInput.toLowerCase(), termDefinition.get(i).getTerm().toLowerCase());
            levenshteinDistanceBetweenTerms.put(termDefinition.get(i).getTerm().toLowerCase(), distance);
            if (distance < minimalDistance) {
                minimalDistance = distance;
            }
        }
        for (var key : levenshteinDistanceBetweenTerms.keySet()) {
            if (levenshteinDistanceBetweenTerms.get(key) == minimalDistance) {
                similarTerms.add(key);
            }
        }
        return similarTerms;
    }
}
