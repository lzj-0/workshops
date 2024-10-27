package baccarat.src.baccarat.server;

import java.io.*;
import java.util.*;


public class BaccaratEngine {

    public void initDecks(int noOfDecks) throws IOException {
        File file = new File("cards.db");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        ArrayList<String> cards = new ArrayList<>();

        for (int i = 0; i < noOfDecks; i++) {
            for (int number = 1; number <= 13; number++) {
                for (int suit = 1; suit <= 4; suit++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(number + "." + suit);
                    cards.add(sb.toString());
                }
            }
        }

        Collections.shuffle(cards);
        cards.forEach(c -> {
            try {
                bw.write(c + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        bw.flush();
        bw.close();
        fw.close();
    }

    public Integer login(String name, Integer funds) throws IOException {
        String filename = "src/baccarat/db/" + name + ".db";
        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();

            FileWriter fw = new FileWriter(f); // have to check for existence of f first, or not it automatically creates new file
            BufferedWriter bw = new BufferedWriter(fw);
    
            bw.write(Integer.toString(funds));
            bw.flush();
            bw.close();
            fw.close();
            System.out.printf("Successfully logged in as new user %s\n", name);
            return funds;
        } else {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            Integer total = Integer.parseInt(br.readLine()) + funds;
            FileWriter fw = new FileWriter(f);  // this line straight away deletes all contents in f, so br.readLine() must come before it
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(Integer.toString(total));
            bw.flush();
            bw.close();
            fw.close();
            br.close();
            fr.close();
            System.out.printf("Successfully logged in as %s\n", name);
            return total;
        }
    }

    public Integer bet(Integer balance, Integer amount) {
        if (amount > balance) {
            return -1;
        } else {
            return amount;
        }
    }

    private Integer drawCard(String deckFile) throws IOException {
        File f = new File(deckFile);
        
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String card = br.readLine();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString());

        bw.flush();
        bw.close();
        fw.close();
        br.close();
        fr.close();
        Integer cardValue = Collections.min(Arrays.asList(Integer.parseInt(card.split("\\.")[0]), 10));
        return cardValue;
    }

    public String deal(String name, String side, Integer betAmount) throws IOException {
        ArrayList<String> player = new ArrayList<>();
        ArrayList<String> banker = new ArrayList<>();

        Integer cardsOnHand = 0;
        Integer sumPlayer = 0;
        Integer sumBanker = 0;


        while (sumPlayer <= 15 && cardsOnHand < 3) {
            Integer card = 0;
            card = this.drawCard("cards.db");
            sumPlayer += card;
            player.add(String.valueOf(card));
            cardsOnHand++;
        }
        cardsOnHand = 0;
        while (sumBanker <= 15 && cardsOnHand < 3) {
            Integer card = 0;
            card = this.drawCard("cards.db");
            sumBanker += card;
            banker.add(String.valueOf(card));
            cardsOnHand++;
        }
        String msg = "P|" + String.join("|", player) + ",B|" + String.join("|", banker);

        // payout
        String filename = "src/baccarat/db/" + name + ".db";
        File f = new File(filename);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        Integer total;

        if (sumPlayer > sumBanker && side.equals("P")) {
            total = Integer.parseInt(br.readLine()) + betAmount;
        } else if (sumBanker > sumPlayer && side.equals("B")) {
            if (sumBanker == 6) {
                total = Integer.parseInt(br.readLine()) + betAmount/2;
            } else {
                total = Integer.parseInt(br.readLine()) + betAmount;
            }
        } else if (sumPlayer == sumBanker) {
            total = Integer.parseInt(br.readLine()) + 8*betAmount;
            // br.close();
            // System.out.println(msg);
            // return "Draw";
        } else {
            total = Integer.parseInt(br.readLine()) - betAmount;
        }

        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(Integer.toString(total));
        bw.flush();
        bw.close();
        fw.close();
        br.close();
        fr.close();

        return msg;
    }
}
