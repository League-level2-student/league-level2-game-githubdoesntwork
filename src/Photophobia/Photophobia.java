package Photophobia;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Photophobia {
JFrame frame;
GamePanel panel;
public Photophobia() {
	frame = new JFrame();
	panel = new GamePanel();
	config();
}
public static void main(String[] args) {
	Photophobia game = new Photophobia();
}
public void config() {
	frame.add(panel);
	frame.getContentPane().setPreferredSize(new Dimension(600,500));
	frame.setResizable(false);
	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	frame.addKeyListener(panel);
}
}
