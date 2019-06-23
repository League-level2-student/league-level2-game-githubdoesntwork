package Photophobia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PowerUp extends GameObject{
	int x, y;
	Rectangle rect; 
	public PowerUp(int a, int b, int c, int d) {
		super(a, b, c, d);
		x=a;
		y=b;
	}
public void update(){
	
}
public void draw(Graphics g) {
	g.setColor(Color.red);
	rect = new Rectangle(x,y,width,height);
	g.fillRect(x, y, width, height);
}
}
