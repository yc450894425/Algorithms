import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

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

    /*
	the smallest one is: 3 * 5 * 7L
key point:
	How to deduplicate?
		Set<Long>
	When to deduplicate?
		Before we insert a new element into minHeap
	How to get all neighbors of one node?
		node * 3, node * 5, node * 7

	Data structure and Initialization:
Set<Long> visited = new HashSet<>();
PriorityQueue<Long> minHeap = new PriorityQueue<>(new Comparator<Long>() {
	@Override
	public int compare(Long n1, Long n2) {
	// compare
}
});
visited.add(3 * 5 * 7L);
minHeap.offer(3 * 5 * 7L);

For each step:
	poll out the min element curr from minHeap
	add {curr * 3, curr * 5, curr * 7} which is not visited yet into minHeap, mark them as visited
Terminate:
	i == k - 1

Return:
	minHeap.poll();
Time Complexity:
	O(log(2k) * k) => O(klogk)
Space Complexity:
	O(2k) for visited
	O(2k) for minHeap
	=> O(k) for total
*/
    public long kth(int k) {
        Set<Long> visited = new HashSet<>();
        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        visited.add(3 * 5 * 7L);
        minHeap.offer(3 * 5 * 7L);
        k--;
        while (k > 0) {
            Long curr = minHeap.poll();
            if (visited.add(curr * 3)) {
                minHeap.offer(curr * 3);
            }
            if (visited.add(curr * 5)) {
                minHeap.offer(curr * 5);
            }
            if (visited.add(curr * 7)) {
                minHeap.offer(curr * 7);
            }
            k--;
        }
        return minHeap.poll();
    }


}
