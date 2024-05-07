import java.util.*;

public class Algorithms {

    // UCS (Uniform Cost Search)
    public static List<String> findLadderUCS(String start, String end, Set<String> wordList) {
        Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>(); // Initialize parent map
    
        queue.offer(new Node(start, 0));
        visited.add(start);
    
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            String currentWord = current.getWord();
            if (currentWord.equals(end)) {
                return constructPath(start, end, parent);
            }
    
            List<String> neighbors = getNeighbors(currentWord, wordList);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, currentWord);
                    queue.offer(new Node(neighbor, current.getCost() + 1));
                }
            }
        }
        List<String> path = constructPath(start, end, parent);
        return path;
    }

    // Greedy Best First Search
    public static List<String> findLadderGreedyBFS(String start, String end, Set<String> wordList) {
        Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getHeuristic));
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        queue.offer(new Node(start, calculateHeuristic(start, end)));
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            String currentWord = current.getWord();
            if (currentWord.equals(end)) {
                return constructPath(start, end, parent);
            }

            List<String> neighbors = getNeighbors(currentWord, wordList);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, currentWord);
                    queue.offer(new Node(neighbor, calculateHeuristic(neighbor, end)));
                }
            }
        }
        List<String> path = constructPath(start, end, parent);
        return path;
    }

    // A* (A-star)
    public static List<String> findLadderAStar(String start, String end, Set<String> wordList) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        Map<String, Integer> gScore = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();

        openSet.offer(new Node(start, 0, calculateHeuristic(start, end)));
        gScore.put(start, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            String currentWord = current.getWord();
            if (currentWord.equals(end)) {
                return reconstructPath(cameFrom, currentWord);
            }

            List<String> neighbors = getNeighbors(currentWord, wordList);
            for (String neighbor : neighbors) {
                int tentativeGScore = gScore.getOrDefault(currentWord, Integer.MAX_VALUE) + 1;
                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, currentWord);
                    gScore.put(neighbor, tentativeGScore);
                    openSet.offer(new Node(neighbor, tentativeGScore, calculateHeuristic(neighbor, end)));
                }
            }
        }
        List<String> path = reconstructPath(cameFrom, end);
    return path;
    }

    private static int calculateHeuristic(String current, String end) {
        int heuristic = 0;
        for (int i = 0; i < current.length(); i++) {
            if (current.charAt(i) != end.charAt(i)) {
                heuristic++;
            }
        }
        return heuristic;
    }

    private static List<String> constructPath(String start, String end, Map<String, String> parent) {
        List<String> path = new ArrayList<>();
        String current = end;
        while (!current.equals(start)) {
            path.add(0, current);
            current = parent.get(current);
        }
        path.add(0, start);
        return path;
    }

    private static List<String> getNeighbors(String word, Set<String> wordList) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char originalChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (wordList.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            chars[i] = originalChar;
        }
        return neighbors;
    }

    private static class Node {
        private String word;
        private int cost;
        private int heuristic;

        public Node(String word, int cost) {
            this.word = word;
            this.cost = cost;
        }

        public Node(String word, int cost, int heuristic) {
            this.word = word;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        public String getWord() {
            return word;
        }

        public int getCost() {
            return cost;
        }

        public int getHeuristic() {
            return heuristic;
        }

        public int getF() {
            return cost + heuristic;
        }
    }
    private static List<String> reconstructPath(Map<String, String> cameFrom, String currentWord) {
        List<String> path = new ArrayList<>();
        while (currentWord != null) {
            path.add(0, currentWord);
            currentWord = cameFrom.get(currentWord);
        }
        return path;
    }
}
