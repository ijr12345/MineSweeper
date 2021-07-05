package v3;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class UI {
    public JFrame prepareFrame(){
        JFrame frame = new JFrame();

        frame.setTitle("Minesweeper");
        //frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        //frame.setBounds(200,200,400,400);
        //frame.setSize(800,800);
        //frame.setMinimumSize(new Dimension(880,900));
        //frame.setMinimumSize(frame.getPreferredSize());
        //frame.pack();

        //frame.setPreferredSize(preferredSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        return frame;
    }

    public JLabel addTitle(){
        JLabel title = new JLabel("M I N E S W E E P E R");
        title.setFont(new FontUIResource("Verdana", Font.BOLD, 30));

        return title;
    }


    public JCheckBox addFlagCheck() {
        JCheckBox flagCheck = new JCheckBox("flag");
        flagCheck.setActionCommand("flag");

        return flagCheck;
        
    }

    public JButton addRestartButton() {
        JButton b = new JButton("restart");
        b.setActionCommand("restart");

        return b;

    }
    

    public JPanel setMenuLayout(JPanel panel, JButton reset, JCheckBox flagging) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(reset);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(flagging);

        return panel;

    }

    public JPanel setGamePlayPanel(JPanel gamePlayPanel, JPanel menuPanel, JPanel buttons) {
        gamePlayPanel.setLayout(new BoxLayout(gamePlayPanel, BoxLayout.LINE_AXIS));
        gamePlayPanel.add(Box.createHorizontalGlue());

        gamePlayPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        gamePlayPanel.add(menuPanel);
        gamePlayPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        gamePlayPanel.add(buttons);
        //gamePlayPanel.add(Box.createRigidArea(new Dimension(0, 0)));

        return gamePlayPanel;

    }

    public JFrame setMainLayout(JFrame frame, JPanel gamePlayPanel, JLabel title) {
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        
        frame.getContentPane().add(title);
        title.setAlignmentX(frame.getAlignmentX());

        frame.add(Box.createRigidArea(new Dimension(5, 5)));
        
        frame.getContentPane().add(gamePlayPanel);
        gamePlayPanel.setAlignmentX(frame.getAlignmentX());
        
        frame.add(Box.createRigidArea(new Dimension(0, 20)));

        return frame;

    }
 
}
