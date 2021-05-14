public class Class22GraphSearchAlgorithmsIII {
    /*
	Key point:
how to check if there are common chars?
		int[] set = new int[dict.len];
		set[i] represents the chars in the word dict[i].
		           hgfe dcba
0b0000 ... 0000 0101
|
0b0000 ... 0000 1000
if set[i] & set[j] != 0, word i and j have common chars.

	Data structure:
		int[] set = new int[dict.len];
		int globMax = 0;
	Pre-processing:
		for each word in dict (i):
			for each char in the word (j):
				set[i] |= 1 << (char j - ‘a’);
	For each pair of words (i, j):
		if set[i] & set[j] == 0, update globMax
	Return:
		globMax;
	Time complexity:
		O(n*m) for pre-processing
		O(n-1 + n-2 + ... + 2 + 1) => O(n^2) for iteration
		O(n*m + n^2) total
	Space complexity:
		O(n) for array set
*/
    public int largestProduct(String[] dict) {
        int[] set = new int[dict.length];
        for (int i = 0; i < dict.length; i++) {
            for (int j = 0; j < dict[i].length(); j++) {
                set[i] |= 1 << (dict[i].charAt(j) - 'a');
            }
        }

        int globMax = 0;
        for (int i = 0; i < dict.length; i++) {
            for (int j = i + 1; j < dict.length; j++) {
                if ((set[i] & set[j]) == 0) {
                    globMax = Math.max(globMax, dict[i].length() * dict[j].length());
                }
            }
        }
        return globMax;
    }

}
