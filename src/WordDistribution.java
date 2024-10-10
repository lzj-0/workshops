
import java.io.*;
import java.util.*;

public class WordDistribution {

    private HashMap<String, HashMap<String, Integer>> words;

    public WordDistribution() {
        this.words = new HashMap<>();
    }

    public void trainModel(File file, int skipRows) throws IOException {
        //HashMap<String, HashMap<String, Integer>> words = new HashMap<>();

        FileReader fr = new FileReader(file);
        LineNumberReader lnr = new LineNumberReader(fr);
        String line = "";

        for (int i = 0; i < skipRows; i++) {
            line = lnr.readLine();
        }

        String reserve = "";
        boolean hasReserve = false;

        while ((line = lnr.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            line = line.replaceAll("[,\";:?!_]", "").toLowerCase();
            line = line.replace("--", " ");
            line = line.replace(".", " .");
            System.out.println(line);
            String[] line_array = line.split("\\s+");
            if (hasReserve) {
                if (!this.words.containsKey(reserve)) {
                    this.words.put(reserve, new HashMap<String, Integer>());
                }

                if (!this.words.get(reserve).containsKey(line_array[0])) {
                    this.words.get(reserve).put(line_array[0], 0);
                }

                this.words.get(reserve).put(line_array[0], this.words.get(reserve).get(line_array[0]) + 1);
                hasReserve = false;
            }

            for (int i = 0; i < line_array.length - 1; i++) {
                if (!this.words.containsKey(line_array[i])) {
                    this.words.put(line_array[i], new HashMap<String, Integer>());
                }

                if (!this.words.get(line_array[i]).containsKey(line_array[i+1])) {
                    this.words.get(line_array[i]).put(line_array[i+1], 0);
                }
                this.words.get(line_array[i]).put(line_array[i+1], this.words.get(line_array[i]).get(line_array[i+1]) + 1);
            }
            reserve = line_array[line_array.length - 1];
            hasReserve = true;
        }
        System.out.println("Training completed");
        lnr.close();
        fr.close();
    }

    public void printHashMap() {
        for (String word: this.words.keySet()) {
            System.out.printf(">> %s:\n", word);
            HashMap<String, Integer> next = this.words.get(word);
            for (String nextWord: next.keySet()) {
                System.out.printf("\t%s: %d\n", nextWord, next.get(nextWord));
            }
        }
    }

    public String getNextWord(String startWord) {
        int maxCount = -1;
        String nextWordChosen = "";
        if (this.words.containsKey(startWord)) {
            HashMap<String, Integer> next = this.words.get(startWord);
            for (String nextWord: next.keySet()) {
                int count = next.get(nextWord);
                if (count > maxCount) {
                    maxCount = count;
                    nextWordChosen = nextWord;
                }
            }
        }
        return nextWordChosen;

    }

    public String getNextWordRand(String startWord) {
        Integer total = 0;
        String nextWordChosen = "";
        int indexChosen;
        ArrayList<Integer> probArray = new ArrayList<>();
        ArrayList<String> wordArray = new ArrayList<>();
        HashMap<String, Integer> next = this.words.get(startWord);
        for (String word: next.keySet()) {
            total += next.get(word);
            probArray.add(total);
            wordArray.add(word);
        }
        Random rand = new Random();

        int chosenValue = rand.nextInt(total) + 1;
        for (int i = 0; i < probArray.size(); i++) {
            if (chosenValue <= probArray.get(i)) {
                indexChosen = i;
                nextWordChosen = wordArray.get(indexChosen);
                break;
            }
        }
        return nextWordChosen;
    }

    public String predict(String startWord, int numWords) {
        StringBuilder sb = new StringBuilder();
        sb.append(startWord);
        int i = 0;
        while (i < numWords) {
            String nextWord = this.getNextWordRand(startWord);
            if (nextWord.equals(".")) {
                sb.append(nextWord + " ");
            } else {
                sb.append(" " + nextWord);
                i++;                
            }
            startWord = nextWord;
        }
        return sb.toString();
    }


    public static void main(String[] args) throws IOException {

        String filename = args[0];
        int skip = Integer.parseInt(args[1]);

        File f = new File(filename);
        if (!f.exists()) {
            System.out.println("file does not exist");
            System.exit(-1);
        }

        WordDistribution wd = new WordDistribution();
        wd.trainModel(f, skip);
        //wd.printHashMap();

        Console cons = System.console();
        String startWord = cons.readLine("Type a starting word: ").toLowerCase();
        int numWords = Integer.parseInt(cons.readLine("How many words to generate: "));
        System.out.println(wd.predict(startWord, numWords));

        // for (String word: wd.words.get("love").keySet()) {
        //     System.out.printf(">> %s: %d\n", word, wd.words.get("love").get(word));
        // }

    }
}
