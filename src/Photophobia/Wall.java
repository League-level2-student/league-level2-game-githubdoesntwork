package Photophobia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends GameObject{
	Color groundColor = new Color(99, 72, 72);
	Rectangle wallRect;
	public Wall(int a, int b, int c, int d) {
		super(a, b, c, d);
		wallRect = new Rectangle(x,y,width,height);
	}

	public void draw(Graphics g) {
		g.setColor(groundColor);
		g.fillRect(x, y, width, height);
	}
	public void update() {
		
	}

}
