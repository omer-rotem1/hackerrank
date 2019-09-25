import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class LargestRectangle {

    /*
     * Link: https://www.hackerrank.com/challenges/largest-rectangle/problem
     * Explanation:
     * Approximation #1: Let's assume, that we iterate over the array once. For every building (entry in the array),
     * we check the maximum rectangle area for which this building is the shortest building among all building participating in the rectangle.
     * In order to do that, for every building i we can look left, to find the largest j<i for which h[k]>=h[i] for every j<=k<=i
     * We can do the same and look for m>i s.t. for every i<=k<=m we have h[m]>=h[i].
     * Once we have that, we can simply compute the h[i]*(m-j+1) and find the largest rectangle area for which i is the smallest building.
     * We iterate over all Is and take the maximum rectangle area. Notice that the largest rectangle must be of this form (length of smallest building in the rectangle * number of building in rectangle)
     * How long does it take?
     * Notice that finding m,j for a specific i takes O(n) time. Given that we do it n times, overall runtime is O(n^2).
     * Surprisingly, This is good enough for this Hackerrank challenge. However, we can do even better!
     * Notice that we can do pre-processing in time O(n), to find m,j as described above in time O(1).
     * How?
     * Create an array Left of length h.length. Iterate over the array h from 0 to length-1. Every time you see a building, pop all buildings in the stack until you reach a building whose height is smaller than the current building's height.
     * Then, assign Left[i] = stack.peek() (or 0, if the stack is empty). Then, push i to the top of the stack.
     * Do the same for Right. Then, whenever we need to want to find the smallest rectangle area for building i, we simply utilize Arrays Right,Left.
     */

    static int[] smallerToLeftArray;
    static int[] smallerToRightArray;

    // Complete the largestRectangle function below.
    static long largestRectangle(int[] h) {
        long max = -1;
        smallerToLeftArray = fillSmallerToLeft(h);
        smallerToRightArray = fillSmallerToRight(h);
        for (int i=0; i<h.length; i++) {
            long maxWithCurrentHouse = largestRectangle(h, i);
            if (maxWithCurrentHouse > max) {
                max = maxWithCurrentHouse;
            }
        }
        return max;
    }

    static int[] fillSmallerToLeft(int[] h) {
        int[] arr = new int[h.length];
        Stack<Integer> stack = new Stack<>();
        for (int i=0; i<h.length; i++) {
            while (!stack.isEmpty() && h[stack.peek()] >= h[i]) {
                stack.pop();
            }
            arr[i] = stack.isEmpty() ? 0 : stack.peek()+1;
            stack.push(i);
        }
        return arr;
    }

    static int[] fillSmallerToRight(int[] h) {
        int[] arr = new int[h.length];
        Stack<Integer> stack = new Stack<>();
        for (int i=h.length-1; i >= 0; i--) {
            while (!stack.isEmpty() && h[stack.peek()] >= h[i]) {
                stack.pop();
            }
            arr[i] = stack.isEmpty() ? h.length-1 : stack.peek()-1;
            stack.push(i);
        }
        return arr;
    }

    static long largestRectangle(int[] h, int index) {
        int smallerToRight = smallerToRightArray[index];
        int smallerToLeft = smallerToLeftArray[index];
        return h[index]*(smallerToRight + 1 - smallerToLeft);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] h = new int[n];

        String[] hItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int hItem = Integer.parseInt(hItems[i]);
            h[i] = hItem;
        }

        long result = largestRectangle(h);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
