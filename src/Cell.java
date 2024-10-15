import java.util.*;

public class Cell {
    private Boolean alive;
    private Integer neighbours;

    public Cell() {
        alive = true;
    }
    
    public void initNeighbour(Grid grid, Integer x, Integer y) {
        this.neighbours = 0;
        Integer rowSize = grid.getEco().length;
        Integer colSize = grid.getEco()[0].length;
        Integer left = Collections.max(Arrays.asList(x - 1, 0));    // left/right denotes column number (x)
        Integer top = Collections.max(Arrays.asList(y - 1, 0));
        Integer right = Collections.min(Arrays.asList(x + 1, colSize - 1));
        Integer bottom = Collections.min(Arrays.asList(y + 1, rowSize - 1));
        //System.out.printf("Neighbours for cell (%d, %d)\n", x, y);

        for (int i = top; i <= bottom; i++) { // i = row number = y
            for (int j = left; j <= right; j++) {
                //System.out.printf("Checking row: %d, col: %d\n", i, j);
                if (i == y && j == x) {
                    continue;
                }
                if (grid.getEco()[i][j] instanceof Cell && grid.getEco()[i][j].alive) {
                    //System.out.printf("row: %d, col: %d\n", i, j);
                    this.neighbours++;
                }
            }
        }
    }
    
    public void survive() {
        if (this.alive) {
            if (this.neighbours <= 1 || this.neighbours >= 4) {
                this.alive = false;
            }
        } else {
            if (this.neighbours == 3) {
                this.alive = true;
            }
        }
    }

    public Boolean getAlive() {
        return this.alive;
    }

    public Integer getNeighbours() {
        return this.neighbours;
    }

}
