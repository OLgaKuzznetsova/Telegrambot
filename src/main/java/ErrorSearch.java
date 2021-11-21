public class ErrorSearch {
    public int getLevenshteinDistance(String word1, String word2) {

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


}
