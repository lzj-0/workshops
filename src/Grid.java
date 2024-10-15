public class Grid {

    private Cell[][] eco;

    public Grid(Integer row, Integer col) {
        eco = new Cell[row][col];
    }

    public Cell[][] getEco() {
        return this.eco;
    }

    public void initNeighbours() {
        Integer rowSize = this.eco.length;
        Integer colSize = this.eco[0].length;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (this.eco[i][j] instanceof Cell) {
                    this.eco[i][j].initNeighbour(this, j, i);   // i = row number = y
                }
            }
        }
    }

    public void updateEco() {
        Integer rowSize = this.eco.length;
        Integer colSize = this.eco[0].length;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (this.eco[i][j] instanceof Cell) {
                    this.eco[i][j].survive();
                }
            }
        }
    }

    public void addGeneration() {
        //this.initNeighbours();
        this.updateEco();
        this.initNeighbours();
    }

    public void printGrid() {
        Integer rowSize = this.eco.length;
        Integer colSize = this.eco[0].length;
        for (int i = 0; i < rowSize; i++) {
            String printRow = "";
            for (int j = 0; j < colSize; j++) {
                if (this.eco[i][j] instanceof Cell) {
                    if (this.eco[i][j].getAlive()) {
                        printRow += "* ";
                    } else {
                        printRow += "x ";
                    }
                } else {
                    printRow += ". ";
                }
            }
            System.out.println(printRow);
        }
    }

    public void printNeighboursGrid() {
        Integer rowSize = this.eco.length;
        Integer colSize = this.eco[0].length;
        for (int i = 0; i < rowSize; i++) {
            String printRow = "";
            for (int j = 0; j < colSize; j++) {
                if (this.eco[i][j] instanceof Cell) {
                    printRow += Integer.toString(this.eco[i][j].getNeighbours()) + " ";
                } else {
                    printRow += ". ";
                }
            }
            System.out.println(printRow);
        }
    }



}
