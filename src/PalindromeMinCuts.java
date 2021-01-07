public class PalindromeMinCuts {
        public int minCuts(String input) {
            // base cases
            if (input == null || input.length() <= 1) {
                return 0;
            }
            int[] m = new int[input.length() + 1];
            m[0] = 0;
            m[1] = 0;

            for (int i = 2; i < m.length; i++) {
                if (isPalindrome(input, 0, i - 1)) {
                    m[i] = 0;
                    continue;
                }
                m[i] = Integer.MAX_VALUE; // initialized as invalid
                for (int j = 1; j <= i - 1; j++) {
                    if (m[j] != Integer.MAX_VALUE && isPalindrome(input, j, i - 1)) 	{
                        m[i] = Math.min(m[i], m[j] + 1);
                    }
                }
            }
            return m[input.length()];
        }

        private boolean isPalindrome(String string, int s, int e) {
            while (s < e) {
                if (string.charAt(s++) != string.charAt(e--)) {
                    return false;
                }
            }
            return true;
        }
    }
