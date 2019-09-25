import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class BalancedBrackets {

    static String YES="YES";
    static String NO="NO";

    // Complete the isBalanced function below.
    static String isBalanced(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = bracketsMap();
        for (int i=0; i < s.length(); i++) {
            if (map.keySet().contains(s.charAt(i))) { // if the current character is an opening parenthesis
                stack.push(s.charAt(i));
            } else if (stack.isEmpty()) return NO; // we are seeing a non-opening paranthesis without corresponding opening paranthesis
            else if (map.get(stack.pop()) != s.charAt(i)) return NO; // current closing paranthesis does not match the
        }
        if (stack.isEmpty()) return YES;
        return NO; // opening parantheses with no corresponding closing parantheses


    }

    static Map<Character, Character> bracketsMap() {
        Map<Character, Character> map = new HashMap<>();
        map.put('[', ']');
        map.put('{', '}');
        map.put('(', ')');
        return map;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String s = scanner.nextLine();

            String result = isBalanced(s);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
