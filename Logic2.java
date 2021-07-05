package v3;



import java.util.Random;
import java.util.ArrayList;

public class Logic2 {
    int max_col;
    int max_row;
    int mine_count;
    boolean flagMode = false;
    

    //plots all mines (-1) and surrounding values 
    int[][] mineMap;

    //each 'mine' item contains row/col info
    ArrayList <Mine2> mineInfo = new ArrayList<>();
    
    public Logic2(int tot_col, int tot_row, int tot_mines) {
        max_col = tot_col;
        max_row = tot_row;
        mine_count = tot_mines;
        mineMap = new int[max_row][max_col];
    }
    

    //args: row and col of first game choice button. prevents first turn from choosing mine
    public void generateMineMap(int firstRow, int firstCol) {
        
        Mine2 newMine;
        
        //plot mines
        for (int i = 0; i < mine_count; i++) {
            Random random = new Random();
            int row = random.nextInt(max_row);
            int col = random.nextInt(max_col);
            
            //unique mines in game. not on first choice square
            while (mineMap[row][col] == -1 || (firstCol == col && firstRow == row) ) {
                row = random.nextInt(max_row);
                col = random.nextInt(max_col);
            }

            mineMap[row][col] = -1;

            //adds new mine info to array of mines
            newMine = new Mine2(row, col);
            mineInfo.add(newMine);
  
        }
        

        //for each mine plot surrounding values 
        for (Mine2 mine: mineInfo) {

            //traverse surrounding 8 squares
            for (int i = mine.getRow() - 1; i < mine.getRow() + 2; i++) {
                for (int j = mine.getCol() - 1; j < mine.getCol() + 2; j++) {
                    
                    //skips over mines 
                    if (isMine(i, j)) {
                        continue;
                    }

                    //updates count of # of mines touching specific square
                    if (validBounds(i, j)) {
                        mineMap[i][j]++;
                    }
                }
            }

        }

        //showMineMap();
    }

    //determine if row/col position contains mine
    public boolean isMine(int row, int col) {
        for (Mine2 mine: mineInfo) {
            if (mine.getRow() == row) {
                if (mine.getCol() == col) {
                    return true;
                }
            }
        }
        return false;
    }

    //determines if row/col position is in grid bounds
    public boolean validBounds(int row, int col) {
        if (row < 0 || col < 0 || row > max_row - 1 || col > max_col - 1) {
            return false;
        } 
        return true;
    }

    //prints mineMap to console
    public void showMineMap() {
        for (int i = 0; i < max_row; i++) {
            for (int j = 0; j < max_col; j++) {
                System.out.printf("%3d", mineMap[i][j]);
            }
            System.out.println();
        }
    }

    //change mine flagging state
    public void setflaggingMode(Boolean state) {
        flagMode = state;
    }

    //get flagging state
    public boolean getflaggingMode() {
        return flagMode;

    }
    
}

