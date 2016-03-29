public class SumIntegers {
    private static double sumOfIntegerUptoN(Integer n) {
        Double sum = (double) 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        return sum;
    }

    private static double printSum(Double n) {
        System.out.println("Sum: " + n);
        return n;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        printSum(sumOfIntegerUptoN(100000000));
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start));
    }
}
