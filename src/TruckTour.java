import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class TruckTour {

    /*
     * Link: https://www.hackerrank.com/challenges/truck-tour/problem
     * Explanation: The important thing to notice is that for each stop, what we care about is the difference between the distance to the next pump and the capacity of the pump.
     * The exact number numbers are not important.
     * So, as a first step we will convert the 2D array into a 1D array, where each entry represents the difference above.
     * Once we have that, we will simply iterate over the array and find, in linear time per pump, if the tour can start at that point.
     * How? We will simply "tour" the cycle, with a variable that will store, at each point, the amount of fuel the truck would have if it started at the original point.
     * If, at any point, we go below 0 - it means that we ran out of fuel and the pump we started at is not good.
     * If we complete the entire cycle without going below 0 - we're golden and found the pump we were looking for.
     */
    static int truckTour(int[][] petrolpumps) {
        int[] deficit = new int[petrolpumps.length];
        for (int i=0; i<deficit.length; i++) {
            deficit[i] = petrolpumps[i][0]-petrolpumps[i][1];
        }
        for (int i=0; i<deficit.length; i++) {
            if (canFinish(deficit, i)) return i;
        }
        return -1;
    }

    static boolean canFinish(int[] deficit, int index) {
        int petrol = 0;
        for (int i=0; i<deficit.length; i++) {
            petrol+= deficit[(i+index)%deficit.length];
            if (petrol <0) return false;
        }
        return true;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(scanner.nextLine().trim());

        int[][] petrolpumps = new int[n][2];

        for (int petrolpumpsRowItr = 0; petrolpumpsRowItr < n; petrolpumpsRowItr++) {
            String[] petrolpumpsRowItems = scanner.nextLine().split(" ");

            for (int petrolpumpsColumnItr = 0; petrolpumpsColumnItr < 2; petrolpumpsColumnItr++) {
                int petrolpumpsItem = Integer.parseInt(petrolpumpsRowItems[petrolpumpsColumnItr].trim());
                petrolpumps[petrolpumpsRowItr][petrolpumpsColumnItr] = petrolpumpsItem;
            }
        }

        int result = truckTour(petrolpumps);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();
    }

}
