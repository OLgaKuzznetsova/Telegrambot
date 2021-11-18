import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ErrorSearch {
    private TermRepository termRepository = new TermRepository();
    private final ArrayList<TermDefinition> termDefinition = termRepository.getData();

    private int getLevenshteinDistance(String word1, String word2) {

        int[][] distance = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    distance[i][j] = j;
                } else if (j == 0) {
                    distance[i][j] = i;
                } else {
                    distance[i][j] = min(distance[i - 1][j - 1]
                                    + costOfSubstitution(word1.charAt(i - 1), word2.charAt(j - 1)),
                            distance[i - 1][j] + 1,
                            distance[i][j - 1] + 1);
                }
            }
        }
        return distance[word1.length()][word2.length()];
    }

    private int min(int number1, int number2, int number3) {
        return Math.min(number1, Math.min(number2, number3));
    }

    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public ArrayList<String> getSimilarTerms(String userInput) {

        Map<String, Integer> levenshteinDistanceBetweenTerms = new HashMap<>();
        ArrayList<String> similarTerms = new ArrayList<>();
        int minimalDistance = Integer.MAX_VALUE;
        for (var i = 0; i < termDefinition.size(); i++) {
            var distance = getLevenshteinDistance(userInput.toLowerCase(), termDefinition.get(i).getTerm().toLowerCase());
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
