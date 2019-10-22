import java.io.*;
import java.util.*;

public class TUI {

    private static final Scanner EINGABE = new Scanner(System.in);

    public static void main (String[] args) {
        Dictionary dic = null;
        String in;

        while (EINGABE.hasNext()) {
            in = EINGABE.next();

            if (in == "create") {
                if (EINGABE.next().equals("HashDictionary"))
                    dic = create(1);
                else
                    dic = create(0);
            }
            else if (in == "read") {

            }
            else if (in == "p") {
                print(dic);
            }
            else if (in == "s") {
                search(EINGABE.next(), dic);
            }
            else if (in == "i") {
                insert(EINGABE.next(), EINGABE.next(), dic);
            }
            else if (in == "r") {
                remove(EINGABE.next(), dic);
            }
            else if (in == "exit") {
                System.exit(0);
            }
        }
    }

    private static Dictionary<String, String> create(int x) {
        System.out.println("Creating new Dictionary");
        if (x == 1)
            return new HashDictionary(3);
        else
            return new SortedArrayDictionary();
    }

    private static void print(Dictionary<String, String> dic) {
        for (Dictionary.Entry<String, String> v : dic) {
            System.out.println(v.getKey() + ": " + v.getValue());
        }
    }

    private static void search(String key, Dictionary<String, String> dic) {
        System.out.printf("Found: " + key + "\n");
        dic.search(key);
    }

    private static void insert(String key, String value, Dictionary<String, String> dic) {
        System.out.printf("Adding %s: %s to the Dictionary\n", key, value);
        dic.insert(key, value);
    }

    private static void remove(String key, Dictionary<String, String> dic) {
        System.out.printf("Removing %s from Dictionary\n", key);
        dic.remove(key);
    }
}
