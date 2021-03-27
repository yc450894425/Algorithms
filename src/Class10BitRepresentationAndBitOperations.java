public class Class10BitRepresentationAndBitOperations {
    //    solution 1: count number of 1s
//    solution 2: 1000 & 0111 must be 0
    public boolean isPowerOfTwo(int number) {
        return countOnes(number) == 1;
//        return number > 0 && (number & (number - 1)) == 0;
    }

    private int countOnes(int number) {
        int count = 0;
        while (number > 0) {
            count += number & 1;
            number >>>= 1;
        }
        return count;
    }

    //    solution 1: a xor b, then count 1s.
//    solution 2: right shifting a and b, then compare their lowest digit.
    public int diffBits(int a, int b) {
        // corner cases
//        int count = 0;
//        for (int c = a ^ b; c != 0; c >>>= 1) {
//            count += c & 1;
//        }
//        return count;
        int count = 0;
        a ^= b;
        while (a != 0) {
            count++;
            // eliminate the rightest 1 in a;
            a &= a - 1;
        }
        return count;
    }

    //    data structure:
//        an int array
//        each bit of int represents one char, 1 => exists, 0 => not exist
//        there are 256 chars, 0 - 255, need 256 bits
//        length of array = 256 / 32 = 8;
//
//    for each step:
//        if the corresponding bit is 1, return false;
//	    if not 1, set it to 1;
//    terminate: traverse the whole word
//
//    time: O(n) in worst case, where n is length of word
//    space: O(256) => O(1) in worst case
    public boolean allUnique(String word) {
        // corner cases
        if (word == null || word.isEmpty()) {
            return true;
        }
        int[] chars = new int[8];
        char[] array = word.toCharArray();
        for (char cur : array) {
            int row = cur / 32;
            int col = cur % 32;
            if ((chars[row] >> col & 1) == 1) {
                return false;
            }
            chars[row] |= 1 << col;
        }
        return true;
    }

    //two pointers:
//    x = 1 xxx 0
//        i     j
//    i = 63
//    j = 0
//    swap bi and bj
//
//for each step:
//    case 1: bi == bj, then return;
//	case 2: bi != bj, then x ^= 1 << i, x ^= 1 << j;
//	        1 xxx 0
//    xor   1 000 0
//          -------
//	        0 xxx 0
//    xor   0 000 1
//          -------
//	        0 xxx 1
//
//terminate: i >= j
    public long reverseBits(long n) {
        int i = 0;
        int j = 31;
        while (i < j) {
            n = swap(n, i++, j--);
        }
        return n;
    }

    private long swap(long n, int i, int j) {
        if ((n >> i & 1L) == (n >> j & 1L)) {
            return n;
        }
        n ^= 1L << i;
//        actually only when j == 31, L in 1L makes difference;
        n ^= 1L << j;
        return n;
    }

    public long reverseBitsI(long n) {
        n = ((n & 0xFFFF0000) >>> 16) | ((n & 0x0000FFFF) << 16);
        n = ((n & 0xFF00FF00) >>> 8) | ((n & 0x00FF00FF) << 8);
        n = ((n & 0xF0F0F0F0) >>> 4) | ((n & 0x0F0F0F0F) << 4);
        n = ((n & 0xCCCCCCCC) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xAAAAAAAA) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }

    //    Group all bits in number by 4:
//            0110 1100 .... 0101
//              6	   C  ....	 5
//            Ox6C...5
//
//    data structure:
//    char[] chars = {'0', ..., 'F'};
//	StringBuilder sb = new StringBuilder(0x);
//
//    for each step:
//            right shift the number by 28/24/20/.../0
//            then get the last 4 digits by &15
//            e.g. rs by 24
//
//            0110 1100 .... 0101
//            28   24        0
//
//            1100 => 12 => C
//            check if its leading 0
//            if not, add C to sb
//
//    time: O(32/4) => O(1)
//    space: O(16) for char[], O(10) for sb => O(1)
    public String hex(int number) {
        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder("0x");
        boolean leading = true;
        for (int i = 28; i >= 0; i -= 4) {
            int cur = number >> i & 15;
            if (i == 0 || !(cur == 0 && leading)){
//            if (i == 0 || cur != 0 || sb.length() > 2) {
                sb.append(chars[cur]);
                leading = false;
            }
        }
        return sb.toString();
    }
}
