package Photophobia;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends GameObject {
	int speed;
	boolean up = false, down = false, left = false, right = false;
	Rectangle playerRect, futureRect;
	int formerX, formerY;

	public Player(int a, int b, int c, int d) {
		super(a, b, c, d);
	}

	public void draw(Graphics g) {
		formerX = x;
		formerY = y;
		if (up) {
			y -= speed;
			if (y < 80) {
				y = 80;
			}
		}
		if (down) {
			y += speed;
			if (y + 10 > 420) {
				y = 410;
			}
		}
		if (left) {
			x -= speed;
			if (x < 80) {
				x = 80;
			}
		}
		if (right) {
			x += speed;
			if (x + 10 > 520) {
				x = 510;
			}
		}
		playerRect = new Rectangle(x, y, width, height);
		futureRect = new Rectangle(x - speed, y - speed, width + (2 * speed), height + (2 * speed));
		g.fillRect(x, y, width, height);
	}

}
