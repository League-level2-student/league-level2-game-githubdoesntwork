package Photophobia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Exit extends GameObject{
Rectangle exitRect;
	public Exit(int a, int b, int c, int d) {
		super(a, b, c, d);	
		exitRect = new Rectangle(x,y,width,height);
	}
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
	}

}
