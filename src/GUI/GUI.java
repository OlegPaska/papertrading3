package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// provides a way to create new graph pop-ups
public class GUI extends JPanel implements ActionListener {

    //this is just for reference
    private JFrame frame;
    private JButton newGraph;

    public GUI() {
        frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0, 400, 300);
        frame.setLayout(null);
        newGraph = new JButton("Graph results");
        newGraph.setBounds(50, 50, 120, 40);
        newGraph.addActionListener(this);
        frame.getContentPane().add(newGraph);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Graph results")) {
            System.out.println("click");
            Basic test = new Basic("TSLA");
        }
    }
}

