
//USING THIS ONE

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class newGame implements ActionListener {
    int row_count = 30;
    int col_count = 16;
    int total_mine_count = 99;
    
    JFrame frame;
    JPanel mainPanel = new JPanel();
    JButton[] addButtons = new JButton[row_count*col_count];
    Cell[][] cells = new Cell[row_count][col_count];
    Logic2 game = new Logic2(col_count, row_count, total_mine_count);

    int currentMineCount = 0;

    boolean firstTurn = true;


    newGame(){
    
        uiSetup();
 
    }

    //sets basic gui 
    public void uiSetup() {
        UI ui = new UI();
        
        //JFrame settings
        frame = ui.prepareFrame();

        //add title
        JLabel title = ui.addTitle();

        //add flag check to main panel
        JCheckBox flagCheck = ui.addFlagCheck();
        flagCheck.addActionListener(this);

        //add restart button
        JButton restart = ui.addRestartButton();
        restart.addActionListener(this);

        //add game play buttons
        JPanel gameButtons = addButtonGrid();
        gameButtons.setPreferredSize(new Dimension(800, 800));

        //menu panel layout
        JPanel menuPanel = new JPanel();
        frame.add(menuPanel);
        menuPanel = ui.setMenuLayout(menuPanel, restart, flagCheck);

        //game play layout
        JPanel gamePlayPanel = new JPanel();
        gamePlayPanel = ui.setGamePlayPanel(gamePlayPanel, menuPanel, gameButtons);


        // overall program layout
        frame = ui.setMainLayout(frame, gamePlayPanel, title);

        frame.pack();
        frame.setVisible(true);

    }

    //makes grid of buttons for play
    public JPanel addButtonGrid(){

        JPanel panel = new JPanel(new GridLayout(row_count,col_count));

        for (int i = 0; i < row_count; i++) {
            for (int j = 0; j < col_count; j++) {

                cells[i][j] = new Cell(i, j);
                cells[i][j].getPreferredSize();
                cells[i][j].addActionListener(this);
                panel.add(cells[i][j]);

            }
        }
        
        frame.getContentPane().add(panel);

        return panel;
          
    }

    //button 'node'
    private class Cell extends JButton {
        private int row;
        private int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        } 

        int getRow() {
            return row;
        }

        int getCol() {
            return col;
        }
    }


    //@Override
    public void actionPerformed(ActionEvent e) {
        
        //setting status of flagging status 
        if ("flag".equals(e.getActionCommand())) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if (checkBox.isSelected()) {
                game.setflaggingMode(true);
            }
            else {
                game.setflaggingMode(false);
            }
        }
        else if ("restart".equals(e.getActionCommand())) {
            new newGame();
        }

        ///BUTTONS
        else {

            Cell btn = (Cell) e.getSource();
            int row = btn.getRow();
            int col = btn.getCol();
        
            //flag a mine
            if (game.getflaggingMode()) {
                btn.setText("X");
                //btn.getPreferredSize();
                currentMineCount++;
                if (checkWin()) {
                    winGame();
                    endGame();
                }

            }
            else {
                //unflag a mine
                if (btn.getText().equals("X")) {
                    btn.setText("");
                    currentMineCount--;
                }
                else {

                    //if first turn, generate game logic 
                    //this prevents first turn choice from being a mine
                    if (firstTurn == true) {
                        firstTurn = false;
                        game.generateMineMap(btn.getRow(), btn.getCol());
                    }

                    //check win
                    if (checkWin()) {
                        winGame();
                        endGame();
                    }
                    
                    //game ends when mine is clicked
                    //cells[0][0].setText(String.valueOf(row));
                    if (game.mineMap[row][col] == -1) {
                        endGame();
                    }

                    //reveal # of mines square is touching
                    else if (game.mineMap[row][col] != 0 ) {
                        btn.setText(String.valueOf(game.mineMap[row][col]));
                        btn.setEnabled(false);
                    }

                    //reveal surrounding squares when it is touching no mines
                    if (game.mineMap[row][col] == 0) {
                        revealSurrounding(row, col);
                    }
                }
            }
        }
        

        frame.pack();
    }

    public void endGame() {
        for (Cell[] i: cells) {
            for (Cell j: i) {
                //show mines
                if (game.mineMap[j.getRow()][j.getCol()] == -1) {
                    //leave flagged mines unchanged
                    if(j.getText().equals("X")) {}
                    else {
                        j.setText("*");
                        j.setEnabled(false);
                    }
                    j.setBackground(Color.red);
                }
                else {
                    if(j.getText().equals("X")) {
                        j.setText("#");
                    }

                }

                //disable all buttons
                if (j.isEnabled()) {
                    j.setEnabled(false);
                }
            }
        }
    }


    public void revealSurrounding(int row, int col) {

        //base cases
        if (!game.validBounds(row, col)) {
            return;
        }
        if (!cells[row][col].isEnabled()) {
            return;
        }
        if (game.mineMap[row][col] != 0) {
            cells[row][col].setText(String.valueOf(game.mineMap[row][col]));
            cells[row][col].setEnabled(false);
            return;
        }

        //skip over flagged squares. keep flaged squares covered
        if (cells[row][col].getText().equals("X")) {
        }
        else {
            cells[row][col].setText("");
            cells[row][col].setEnabled(false);

    
            //recursive calls
            revealSurrounding(row - 1, col - 1);
            revealSurrounding(row - 1, col);
            revealSurrounding(row - 1, col + 1);
            revealSurrounding(row, col - 1);
            revealSurrounding(row, col + 1);
            revealSurrounding(row + 1, col - 1);
            revealSurrounding(row + 1, col);
            revealSurrounding(row + 1, col + 1);
        
        }
    }

    public boolean checkWin() {
        if (currentMineCount == game.mine_count) {
            for (Mine2 mine: game.mineInfo) {
                //all mines must be flagged
                if (!cells[mine.getRow()][mine.getCol()].getText().equals("X")) {
                    return false;
                }
            }
            return true;
        }
        
        return false;
        
    }

    public void winGame() {
        JPanel test = new JPanel();
        JLabel winMessage = new JLabel("You win!");
        test.add(winMessage);
        frame.add(test);
        //cells[0][0].setText("yo");
        frame.pack();

    }
}

public class MineSweeper2 {
    public static void main(String[] args)
    {
        new newGame();
    }
}



