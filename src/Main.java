import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            List<Integer> result = narcissisticNumber(m, n);
            if (result.size() == 0) {
                System.out.println("no");
            } else {
                for (int i = 0; i < result.size(); i++) {
                    System.out.print(result.get(i));
                    if (i != result.size() - 1) {
                        System.out.print(" ");
                    }
                }
            }
        }
    }

    private static double squareSum(double n, int m) {
        if (m == 0) {
            return 0.0;
        }
        double result = 0;
        for (int i = 0; i < m; i++) {
            result += n;
            n = Math.sqrt(n);
        }
        return result;
    }

    private static List<Integer> narcissisticNumber(int m, int n) {
        List<Integer> result = new ArrayList<>();
        while (m <= n) {
            if (isNarcissisticNumber(m)) {
                result.add(m);
            }
            m++;
        }
        return result;
    }

    private static boolean isNarcissisticNumber(int m) {
        int num = m;
        int hundred = num / 100;
        num %= 100;
        int ten = num / 10;
        num %= 10;
        int base = num;
        return Math.pow(hundred, 3) + Math.pow(ten, 3) + Math.pow(base, 3) == m;
    }

}
