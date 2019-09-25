import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class GameOfTwoStacks {

    /*
     * Link: https://www.hackerrank.com/challenges/game-of-two-stacks/problem
     * Explanation:
     * The important thing to note is that we the order in which we pop numbers from the stack doesn't matter.
     * Also, note that if we knew how many elements to pop from Stack A, the problem would be trivial: Just keep popping from Stack B until you exceed x.
     * However, we don't know how many elements to pop from stack A. So, here's what we'll do:
     * we'll iterate over all elements in stack A, and assume for each number of elements that this is the last element to pop from Stack A.
     * Now, keep popping from elements from stack B until we exceed x.
     * If A has n elements, and B has m elements, we do O(m) work for every element in A, and therefore a total of O(mn) work.
     * This is not good enough for the large test cases. Can we do better? Yes!
     * Given that in a stack we must pop all elements in order, then we "popping" elements from stack B, we don't have to
     * pop elements one-by-one. Instead, we can modify stack B, so that each element is the running sum of all elements above it (including itself),
     * rather than just the element itself. And now, as all numbers are non-negative, the arrays representing the stacks will be sorted,
     * so we can simply use a modified binary search on stack B to find the largest number of numbers we can pop from B without going over x.
     * Now, for every element in stack A we do O(logm) work, so a total of O(nlog(m) + m) work (bear in mind that in order to transform B into a running-sum array we need to spend O(m)) time).
     * Can we do even better? Yes!
     * We transform A and B to be arrays of the running sum of the original stacks. Now, we initialize two integers, i=0, m=b.length-1 (namely: popping all elements from B without popping any elements from A)
     * As long as a[i] + b[j] does not exceed x, we want to keep popping more elements from A (increase i) - we're not exceeding x, so we want to pop more elements!
     * But if a[i] + b[j] does exceed x, we want to return elements to B (decrease j). We keep track of the maximum i+j achieved in the process, and that's our answer.
     * Runtime: Computing modified A,B - O(m+n).
     * running the process in which we compute maximum i+j - O(m+n) as well. Why? In each step of the process, we either increase i by 1 or decrease j by 1. So, in the worst case scenario,
     * i went up all the way to n, and j all the way down to 0. That gives a total of O(m+n).
     */
    static int twoStacks(int x, int[] a, int[] b)  throws IOException{
        int maxNumbers = 0;
        if (a[0] <= x || b[0] <= x) maxNumbers = 1;
        for (int i=1; i<a.length; i++) {
            a[i] = a[i-1] + a[i];
            if (a[i] > x) a[i] = x+1; // this is needed for the large test cases, as we might get an integer overflow if we're not careful
            if (a[i] <= x && i+1>maxNumbers) maxNumbers = i+1;
        }
        for (int i=1; i<b.length; i++) {
            b[i] = b[i-1] + b[i];
            if (b[i] > x) b[i] = x+1;
            if (b[i] <= x && i+1>maxNumbers) maxNumbers = i+1;
        }
        int i=0, j=b.length-1;
        while (i<a.length || j>0 ) {
            if (a[i] + b[j] <= x) {
                maxNumbers = Math.max(maxNumbers, i+j+2);
                i++;
                if (i >= a.length) break;
            } else {
                j--;
                if (j<0) break;
            }
        }
        return maxNumbers;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int g = Integer.parseInt(scanner.nextLine().trim());

        for (int gItr = 0; gItr < g; gItr++) {
            String[] nmx = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nmx[0].trim());

            int m = Integer.parseInt(nmx[1].trim());

            int x = Integer.parseInt(nmx[2].trim());

            int[] a = new int[n];

            String[] aItems = scanner.nextLine().split(" ");

            for (int aItr = 0; aItr < n; aItr++) {
                int aItem = Integer.parseInt(aItems[aItr].trim());
                a[aItr] = aItem;
            }

            int[] b = new int[m];

            String[] bItems = scanner.nextLine().split(" ");

            for (int bItr = 0; bItr < m; bItr++) {
                int bItem = Integer.parseInt(bItems[bItr].trim());
                b[bItr] = bItem;
            }

            int result = twoStacks(x, a, b);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }
}
