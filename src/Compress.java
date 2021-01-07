// CompressStringII
// C: input: String; output: String
// A: input not null; ‘a’ - ‘z’
// R: high level: we traverse the input twice.
// 	Step 1: For the first traversal, traverse from left to right, we compress all letters that appear more than 1 time (not including 1) and record the number of letters that appear only 1 time;
// 	Step 2: For the second traversal, we traverse from right to left, and compress letters appear only 1 time;
// 	DS: inplace, String input -> char[] array
//	Assume the length is large enough to fit the compressed letters?
//	input->”student       “, output->”s1t1u1d1e1n1t1”
// 	For step 1:
//		fast: letters on the left side of f (not including f) are
//		processed letters.
//		slow: letters on the left of s (not including s) are
//		processed letters should be kept;
//		counter: count the letters appear 1 time;
//
// 		for each step:
//			start = f, move f forward till array[f] != array[start]
//			if (f - start) == 1: copy array[start] to
//			array[s], s++, counter++;
//			if (f - start) > 1: copy array[start] to array[s++],
//			copy digit at array[s], then move s forward.
//		termination: fast >= array.length;
//
//	For step 2:
//		we create a new char[] result,
//			result.length = s + counter;
//		we use a f and s
//		f = s - 1;
//		s = result.length - 1;
//		if (array[f] is a digit):
//			copy array[f] to result[s],
//			then s--, f--, repeat it till array[f] is a letter,
//			then copy array[f--] to result[s--]
//		if (array[f] is a letter):
//			result[s--] = ‘1’,
//			then result[s--] = array[f--];
//		termination: fast < 0;
// 	Time: worst case is all letters appear only once, O(n)
//	Space: O(n) because we create a char[] array;
public class Compress {
    public String compress(String input) {
        // corner cases
        if (input == null || input.length() == 0) {
            return input;
        }
        char[] array = input.toCharArray();
        int s = 0;
        int f = 0;
        int counter = 0;
        while (f < array.length) {
            int start = f;
            while (f < array.length && array[f] == array[start]) {
                f++;
            }
            int occur = f - start;
            array[s++] = array[start];
            if (occur == 1) {
                counter++;
            } else {
                int len = copy(array, s, occur);
                s += len;
            }
        }
        char[] result = new char[s + counter];
        f = s - 1;
        s = result.length - 1;
        while (f >= 0) {
            if (!isDigit(array[f])) {
                result[s--] = '1';
                result[s--] = array[f--];
                continue;
            }
            while (f > 0 && isDigit(array[f])) {
                result[s--] = array[f--];
            }
            result[s--] = array[f--];
        }
        return new String(result);
    }

    private int copy(char[] array, int index, int digit) {
        int number = digit;
        int len = 1;
        while (number / 10 > 0) {
            len++;
            number /= 10;
        }
        for (int i = len - 1; i >= 0; i--) {
            array[index + i] = (char)(digit % 10 + '0');
            digit /= 10;
        }
        return len;
    }

    private boolean isDigit(char ele) {
        if (ele >= '0' && ele <= '9') {
            return true;
        }
        return false;
    }
}
