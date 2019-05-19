import javax.swing.*;

import java.awt.Dimension;
import java.util.*;
public class LeagueInvaders {
	GamePanel panel;
	JFrame frame;
	public static final int WIDTH=500, HEIGHT=800;
	public static void main(String[] args) {
LeagueInvaders game = new LeagueInvaders();
	}
	public LeagueInvaders() {
		panel = new GamePanel();
		frame = new JFrame();
//		frame.addKeyListener(panel);
		setup();
	}
	public void setup() {
		frame.add(panel);
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addKeyListener(panel);
	}
}
