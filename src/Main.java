import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Set<String> wordList = loadDictionary("dictionary.txt");
            Scanner scanner = new Scanner(System.in);

            // Input start word dan end word
            System.out.println("Start word:");
            String start = scanner.next().toLowerCase();

            System.out.println("End word:");
            String end = scanner.next().toLowerCase();

            // Pilih algoritma
            System.out.println("Algoritma:");
            System.out.println("1. Uniform Cost Search (UCS)");
            System.out.println("2. Greedy Best First Search");
            System.out.println("3. A*");
            int choice = scanner.nextInt();

            // Mulai hitung waktu
            long startTime = System.currentTimeMillis();

            // Switch case algoritma
            List<String> ladder;
            switch (choice) {
                case 1:
                    ladder = Algorithms.findLadderUCS(start, end, wordList);
                    break;
                case 2:
                    ladder = Algorithms.findLadderGreedyBFS(start, end, wordList);
                    break;
                case 3:
                    ladder = Algorithms.findLadderAStar(start, end, wordList);
                    break;
                default:
                    System.out.println("Tidak valid");
                    return;
            }

            // Selesai hitung waktu
            long endTime = System.currentTimeMillis();

            // Hitung total waktu
            long elapsedTime = endTime - startTime;

            // Tampilkan output
            if (!ladder.isEmpty()) {
                System.out.println("Path: " + ladder);
                System.out.println("Words Traversed: " + ladder.size()); // Print the length of the ladder
            } 
            else {
                System.out.println("Path: []");
            }
            System.out.println("Time: " + elapsedTime + " ms");

            scanner.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found.");
            e.printStackTrace();
        }
    }

    private static Set<String> loadDictionary(String filename) throws FileNotFoundException {
        Set<String> dictionary = new HashSet<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                dictionary.add(scanner.nextLine().trim().toLowerCase());
            }
        }
        return dictionary;
    }
}
