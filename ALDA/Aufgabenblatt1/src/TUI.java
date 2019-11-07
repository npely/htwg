import javax.swing.*;
import java.io.*;
import java.util.*;

public class TUI {

    private static Dictionary<String, String> dic;

    public static void main (String[] args) throws Exception {

        System.out.println("Welcome to Dictionary TUI");

        Scanner scanner = new Scanner(System.in);
        do {
            String input = scanner.nextLine();
            commands(input);
        } while(true);

    }

    private static void commands(String command) throws Exception {

        String args[] = command.split(" ");

        switch (args[0]) {
            case "create":
                    create(args);
                break;
            case "read":
                read(Integer.parseInt(args[1]));
            case "p":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    print();
                break;
            case "s":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    search(args);
                break;
            case "i":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    insert(args);
                break;
            case "r":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    remove(args);
                break;
            case "t":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    test(Integer.parseInt(args[1]));
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }

    private static void create(String[] args) {
        System.out.println("Creating new Dictionary");
        if (args[1].equals("HashDictionary"))
            dic = new HashDictionary(3);

        else if (args[1].equals("Binary"))
            dic = new BinaryTreeDictionary<>();

        else
            dic = new SortedArrayDictionary();
    }

    private static void print() {
        for (Dictionary.Entry<String, String> v : dic)
            System.out.println(v.getKey() + ": " + v.getValue());
    }

    private static void read(int n) throws IOException {

        int counter = 0;

        File selectedFile = null;
        String line;

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/niklas13/Programme/htwg/ALDA"));
        int rv = chooser.showOpenDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else
            return;

        FileReader in;
        long start = 0;
        long stop = 0;
        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            start = System.nanoTime();
            while ((line = br.readLine()) != null && counter < n)
            {
                String[] words = line.split(" ");
                dic.insert(words[0], words[1]);
                counter++;
            }
            stop = System.nanoTime();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long diff = stop - start;
        System.out.println("Read took " + (diff / 1000000) + "ms");

    }

    private static void search(String[] args) {
        try {
            System.out.printf(dic.search(args[1]));
        } catch (NullPointerException e) {
            System.err.println("Wort wurde nicht gefunden!");
        }

    }

    private static void insert(String[] args) {
        System.out.printf("Adding %s: %s to the Dictionary\n", args[1], args[2]);
        dic.insert(args[1], args[2]);
    }

    private static void remove(String[] args) {
        System.out.printf("Removing %s from Dictionary\n", args[1]);
        dic.remove(args[1]);
    }

    private static void test(int n) {
        int counter = 0;

        File selectedFile = null;
        String line;
        List<String> germanWords = new LinkedList<>();
        List<String> englishWords = new LinkedList<>();

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/niklas13/Programme/htwg/ALDA"));
        int rv = chooser.showOpenDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else
            return;

        FileReader in;
        long startG = 0;
        long stopG = 0;
        long startE = 0;
        long stopE = 0;
        long timeG = 0;
        long timeE = 0;
        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            while ((line = br.readLine()) != null && counter < n)
            {
                String[] words = line.split(" ");
                germanWords.add(words[0]);
                englishWords.add(words[1]);
                dic.insert(words[0], words[1]);
                counter++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListIterator<String> itG = germanWords.listIterator();

        while (itG.hasNext() == true) {
            startG = System.nanoTime();
            dic.search(itG.next());
            stopG = System.nanoTime();
            timeG += (stopG - startG);
        }

        System.out.printf("Search time for german words: " + (timeG / 1000000) + "ms\n");

        ListIterator<String> itE = englishWords.listIterator();

        while (itE.hasNext() == true) {
            startE = System.nanoTime();
            dic.search(itE.next());
            stopE = System.nanoTime();
            timeE += (stopE - startE);
        }

        System.out.printf("Search time for english words: " + (timeE / 1000000) + "ms\n");

    }
}
