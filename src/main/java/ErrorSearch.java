import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ErrorSearch {
    private TermRepository termRepository = new TermRepository();
    private ArrayList<TermDefinition> data = termRepository.getData();
    public  int calculate(String userInput, String term) {
        int[][] dp = new int[userInput.length() + 1][term.length() + 1];

        for (int i = 0; i <= userInput.length(); i++) {
            for (int j = 0; j <= term.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(userInput.charAt(i - 1), term.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }
        return dp[userInput.length()][term.length()];
    }

    private int min(int number1, int number2, int number3){
        int[] numbers = new int[]{number1, number2, number3};
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);}

    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
    public Map<Integer, String> getTheNumberOfTermMismatches(String userInput){
        Map<Integer, String> a = new HashMap<>();
        for (var i = 0; i<data.size(); i++){
            a.put(calculate(userInput, data.get(i).getTerm()), data.get(i).getTerm());
        }
        return a;

    }
}
