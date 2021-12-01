import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TermRepository {
    ArrayList<TermDefinition> termDefinition = new ArrayList<>();
    private Map<String, String> dataFromCsvFile;
    private ErrorSearch errorSearch;

    public TermRepository(ErrorSearch errorSearch, Map<String, String> dataFromCsvFile) {
        this.errorSearch = errorSearch;
        this.dataFromCsvFile = dataFromCsvFile;
        for (var i : this.dataFromCsvFile.keySet())
            add(i, this.dataFromCsvFile.get(i));
    }

    private void add(String term, String definition) {
        TermDefinition classTermDefinition = new TermDefinition();
        classTermDefinition.setValues(term, definition);
        termDefinition.add(classTermDefinition);
    }

    public String[] getDefinitionToTerm(String userInput) {
        var result = new String[2];
        for (int i = 0; i < termDefinition.size(); i++) {
            if (userInput.equalsIgnoreCase(termDefinition.get(i).getTerm())) {
                result[0] = termDefinition.get(i).getTerm();
                result[1] = termDefinition.get(i).getDefinition(false);
                return result;
            }
        }
        return null;
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
