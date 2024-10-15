
import java.util.*;
import java.io.*;

public class GoL {
    public static void main(String[] args) throws IOException {
        
        String filename = args[0];
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("file does not exist");
            System.exit(-1);
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        Integer gridRow = -1;
        Integer gridCol = -1;
        Integer startX = -1;
        Integer startY = -1;
        

        while (true) {
            String[] input = br.readLine().split(" ");
            if (input[0].equals("#")) {
                continue;
            } else if (input[0].equals("GRID")) {
                gridRow = Integer.parseInt(input[1]);
                gridCol = Integer.parseInt(input[2]);
            } else if (input[0].equals("START")) {
                startX = Integer.parseInt(input[1]);
                startY = Integer.parseInt(input[2]);
            } else if (input[0].equals("DATA")) {
                break;
            }
        }

        Grid grid = new Grid(gridRow, gridCol);
        Integer curX = startX;
        Integer curY = startY;

        while ((line = br.readLine()) != null) {
            String[] input = line.split("");
            for (int i = 0; i < input.length; i++) {
                if (input[i].equals("*")) {
                    grid.getEco()[curY][curX + i] = new Cell();
                }
            }
            curY++;
        }
        grid.initNeighbours();

        System.out.println("Initial ecosystem:");
        grid.printGrid();
        for (int i = 0; i < 5; i++) {
            System.out.printf("Iteration %d:\n", i + 1);
            grid.addGeneration();
            grid.printGrid();
            // grid.printNeighboursGrid();
        }

    }
}
