//package Photophobia;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Rectangle;
//
//public class Wall extends GameObject{
//	Color groundColor = new Color(99, 72, 72);
//	Rectangle wallRect;
//	int formerX, formerY;
//	Player player = new Player(0,0,0,0);
//	public Wall(int a, int b, int c, int d) {
//		super(a, b, c, d);
//	}
//	public void draw(Graphics g) {
//		g.setColor(groundColor);
//		wallRect = new Rectangle(x,y,width,height);
//		g.fillRect(x, y, width, height);
//		player.draw(g);
//		if(wallRect.intersects(player.playerRect)) {
//			player.x=formerX;
//			player.y=formerY;
//		}
//	}
//
//}
