package tictactoe;

import java.util.*;

public class TicTacToe {
    private String[] board;
    private String player;
    private String computer;
    private String state;
    private ArrayList<Integer> occupiedPositions;

    public TicTacToe() {
        this.board = new String[9];
        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = ".";
        }
        this.occupiedPositions = new ArrayList<Integer>();
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    

    public void startGame(String playerSide) {
        this.player = playerSide.toUpperCase();
        if (this.player.equals("X")) {
            this.computer = "O";
        } else {
            this.computer = "X";
        }
        // Random rand = new Random();
        // Boolean playerStarts = rand.nextBoolean();
        // if (playerStarts) {
        //     this.state = "player";
        // } else {
        //     this.state = "computer";
        // }
        this.state = "player";
    }

    public String getState() {
        return state;
    }

    public String printBoard() {
        StringBuilder sb = new StringBuilder();
        //sb.append("%s's turn:\n".formatted(this.state));
        int j = 1;
        sb.append("  Board \t Position\n");
        for (int i = 0; i < board.length; i++) {
            sb.append(" %s ".formatted(board[i]));
            if ((i+1) % 3 == 0) {
                sb.append("\t %d  %d  %d".formatted(j, j+1, j+2));
                sb.append("\n");
                j += 3;
            }
        }
        return sb.toString();
    }

    public Boolean writeBoard(Integer position) {
        String symbol;
        String nextState;
        if (this.occupiedPositions.contains(position)) {
            return false;
        }
        if (this.state.equals("computer")) {
            symbol = this.computer;
            nextState = "player";
        } else {
            symbol = this.player;
            nextState = "computer";
        }
        this.board[position - 1] = symbol;
        this.occupiedPositions.add(position);
        this.state = nextState;
        System.out.println("Writing successful");
        System.out.println(this.occupiedPositions.toString());
        return true;
    }

    public String checkWin() {
        String winner = null;
        String symbol = null;
        if (this.state.equals("computer")) {
            winner = "player";
            symbol = this.player;
        } else {
            winner = "computer";
            symbol = this.computer;
        }
        if (this.board[0].equals(this.board[1]) && this.board[1].equals(this.board[2]) && "XO".contains(this.board[0]) ||
            this.board[3].equals(this.board[4]) && this.board[4].equals(this.board[5]) && "XO".contains(this.board[3]) ||
            this.board[6].equals(this.board[7]) && this.board[7].equals(this.board[8]) && "XO".contains(this.board[6]) ||
            this.board[0].equals(this.board[3]) && this.board[3].equals(this.board[6]) && "XO".contains(this.board[0]) ||
            this.board[1].equals(this.board[4]) && this.board[4].equals(this.board[7]) && "XO".contains(this.board[1]) ||
            this.board[2].equals(this.board[5]) && this.board[5].equals(this.board[8]) && "XO".contains(this.board[2]) ||
            this.board[0].equals(this.board[4]) && this.board[4].equals(this.board[8]) && "XO".contains(this.board[0]) ||
            this.board[2].equals(this.board[4]) && this.board[4].equals(this.board[6]) && "XO".contains(this.board[2])) {
                return "%s (%s) won the game!\n".formatted(winner, symbol);
            } else {
                if (occupiedPositions.size() < 9) {
                    return "Playing";
                }
                return "It is a tie!";
            }
    }

    public String checkWin(String[] currBoard, String state) {
        String winner = null;
        String symbol = null;
        if (state.equals("computer")) {
            winner = "player";
            symbol = this.player;
        } else {
            winner = "computer";
            symbol = this.computer;
        }
        if (currBoard[0].equals(currBoard[1]) && currBoard[1].equals(currBoard[2]) && "XO".contains(currBoard[0]) ||
        currBoard[3].equals(currBoard[4]) && currBoard[4].equals(currBoard[5]) && "XO".contains(currBoard[3]) ||
        currBoard[6].equals(currBoard[7]) && currBoard[7].equals(currBoard[8]) && "XO".contains(currBoard[6]) ||
        currBoard[0].equals(currBoard[3]) && currBoard[3].equals(currBoard[6]) && "XO".contains(currBoard[0]) ||
        currBoard[1].equals(currBoard[4]) && currBoard[4].equals(currBoard[7]) && "XO".contains(currBoard[1]) ||
        currBoard[2].equals(currBoard[5]) && currBoard[5].equals(currBoard[8]) && "XO".contains(currBoard[2]) ||
        currBoard[0].equals(currBoard[4]) && currBoard[4].equals(currBoard[8]) && "XO".contains(currBoard[0]) ||
        currBoard[2].equals(currBoard[4]) && currBoard[4].equals(currBoard[6]) && "XO".contains(currBoard[2])) {
                return "%s (%s) won the game!\n".formatted(winner, symbol);
            } else {
                Integer countEmpty = 0;
                for (int i = 0; i < currBoard.length; i++) {
                    if (currBoard[i].equals(".")) {
                        countEmpty++;
                    }
                }
                if (countEmpty > 0) {
                    return "Playing";
                }
                return "It is a tie!";
            }
    }

    public void computerTurn() {
        Integer maxValue = -1000;
        Integer bestPosition = 0;
        Integer score;
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i].equals(".")) {
                //System.out.println("position: " + i);
                this.board[i] = this.computer;
                score = recurseScoring(this.board, "player");
                //System.out.println(score);
                this.board[i] = ".";
                if (score > maxValue) {
                    maxValue = score;
                    bestPosition = i + 1;
                }
            }
        }
        this.writeBoard(bestPosition);
    }

    public Integer recurseScoring(String[] board, String state) {
        //System.out.println(checkWin(board, state));
        if (!checkWin(board, state).equals("Playing")) {
            //System.out.println(Arrays.toString(board));
            if (checkWin(board, state).equals("It is a tie!")) {
                //System.out.println(0);
                return 0;
            } else if (state.equals("player")) {
                //System.out.println(1);
                return 1;
            } else {
                //System.out.println(-1);
                return -1;
            }
        } else {
            if (state.equals("computer")) {
                //System.out.println("computer");
                Integer computerScore = null;
                Integer maxValue = -1000;
                for (int i = 0; i < board.length; i++) {
                    if (board[i].equals(".")) {
                        board[i] = this.computer;
                        computerScore = recurseScoring(board, "player");
                        board[i] = ".";
                        maxValue = Math.max(computerScore, maxValue);
                    }
                }
                //System.out.println("computerscore: " + computerScore + " min value: " + maxValue);
                return maxValue;
            } else {
                //System.out.println("player");
                Integer playerScore = null;
                Integer minValue = 1000;
                for (int i = 0; i < board.length; i++) {
                    if (board[i].equals(".")) {
                        board[i] = this.player;
                        //System.out.println(Arrays.toString(board));
                        playerScore = recurseScoring(board, "computer");
                        board[i] = ".";
                        minValue = Math.min(playerScore, minValue);
                    }
                }
                //System.out.println("playerscore: " + playerScore + " min value: " + minValue);
                return minValue;
            }
        }
    }

}
