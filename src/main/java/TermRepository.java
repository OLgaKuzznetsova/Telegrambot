import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TermRepository {
    ArrayList<TermDefinition> termDefinition = new ArrayList<>();
    private ErrorSearch errorSearch;

    public TermRepository(ErrorSearch errorSearch, Map<String, String> dataFromCsvFile) {
        this.errorSearch = errorSearch;
        for (var i : dataFromCsvFile.keySet())
            add(i, dataFromCsvFile.get(i));
    }

    private void add(String term, String definition) {
        TermDefinition classTermDefinition = new TermDefinition();
        classTermDefinition.setValues(term, definition);
        termDefinition.add(classTermDefinition);
    }

    public ArrayList<String> getTerms() {
        ArrayList<String> terms = new ArrayList<>();
        for (var termAndDefinition : termDefinition)
            terms.add(termAndDefinition.getTerm());
        return terms;
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
