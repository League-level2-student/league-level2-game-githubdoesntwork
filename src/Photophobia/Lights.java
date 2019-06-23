package Photophobia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Lights extends GameObject {
	int firstX, firstY, secondX, secondY, buttonX, buttonY;
	String type;
	Rectangle horizRect, vertRect, movingRect, buttonRectVert, buttonHoriz, buttonVert, buttonRectHoriz, blinkingRect,
			otherBlinkingRect;
	boolean right = true, on = true;
	boolean beamOnVert = true, beamOnHoriz = true;
	int movingMax, movingMin;
	Color vertButtonColor = new Color(93, 255, 0);
	Color horizButtonColor = new Color(255, 182, 0);
	int thing;

	public Lights(int firstX, int firstY, int secondX, int secondY, String type, int buttonX, int buttonY,
			int movingMax, int movingMin, int thing) {
		super(firstX, firstY, secondX, secondY);
		this.firstX = firstX;
		this.firstY = firstY;
		this.secondX = secondX;
		this.secondY = secondY;
		this.type = type;
		this.buttonX = buttonX;
		this.buttonY = buttonY;
		this.movingMax = movingMax;
		this.movingMin = movingMin;
		this.thing=thing;
		x = secondX;
		if (type.equals("blinkingLight") || type.equals("otherBlinkingLight")) {
			int delay = 1000;
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (on) {
						on = false;
					} else {
						on = true;
					}
				}
			};
			Timer something = new Timer(delay, taskPerformer);
			something.start();
		}
	}

	public void draw(Graphics g) {
		if (type == "horizBeam") {
			g.setColor(Color.yellow);
			horizRect = new Rectangle(firstX + 5, firstY + 5, secondX - firstX + 15, 5);
			g.fillRect(firstX + 5, firstY + 5, secondX - firstX + 15, 5);
			g.setColor(Color.DARK_GRAY);
			g.fillOval(firstX, firstY, 15, 15);
			g.fillOval(secondX + 15, secondY, 15, 15);
		} else if (type == "vertBeam") {
			g.setColor(Color.yellow);
			vertRect = new Rectangle(firstX + 5, firstY + 5, 5, secondY - firstY + 15);
			g.fillRect(firstX + 5, firstY + 5, 5, secondY - firstY + 15);
			g.setColor(Color.DARK_GRAY);
			g.fillOval(firstX, firstY, 15, 15);
			g.fillOval(secondX, secondY + 15, 15, 15);
		} else if (type == "movingHoriz") {
			g.setColor(Color.yellow);
			movingRect = new Rectangle(x + 3, firstY + 5, 7, secondY - firstY + 15);
			g.fillRect(x + 3, firstY + 5, 7, secondY - firstY + 15);
			g.setColor(Color.DARK_GRAY);
			g.fillOval(x - 1, firstY, 15, 15);
		} else if (type == "buttonBeamVert") {
			if (beamOnVert) {
				g.setColor(Color.yellow);
				buttonRectVert = new Rectangle(firstX + 5, firstY + 5, 5, secondY - firstY + 15);
				g.fillRect(firstX + 5, firstY + 5, 5, secondY - firstY + 15);
			} else {
				buttonRectVert = new Rectangle(0, 0, 0, 0);
			}

			g.setColor(Color.DARK_GRAY);
			g.fillOval(firstX, firstY, 15, 15);
			g.fillOval(secondX, secondY + 15, 15, 15);
			g.setColor(vertButtonColor);
			g.fillRect(buttonX, buttonY, 15, 15);
			buttonVert = new Rectangle(buttonX, buttonY, 15, 15);
		} else if (type == "buttonBeamHoriz") {
			if (beamOnHoriz) {
				g.setColor(Color.yellow);
				buttonRectHoriz = new Rectangle(firstX + 5, firstY + 5, secondX - firstX + 15, 5);
				g.fillRect(firstX + 5, firstY + 5, secondX - firstX + 15, 5);
			} else {
				buttonRectHoriz = new Rectangle(0, 0, 0, 0);
			}

			g.setColor(Color.DARK_GRAY);
			g.fillOval(firstX, firstY, 15, 15);
			g.fillOval(secondX + 15, secondY, 15, 15);
			g.setColor(horizButtonColor);
			g.fillRect(buttonX, buttonY, 15, 15);
			buttonHoriz = new Rectangle(buttonX, buttonY, 15, 15);
		} else if (type == "blinkingLight") {
			if(on) {
				g.setColor(Color.yellow);
				g.fillRect(firstX + 5, firstY + 5, secondX - firstX + 15, 5);
			blinkingRect = new Rectangle(firstX + 5, firstY + 5, secondX - firstX + 15, 5);
			}else {
				blinkingRect = new Rectangle(0,0,0,0);
			}
			g.setColor(Color.DARK_GRAY);
			g.fillOval(firstX, firstY, 15, 15);
			g.fillOval(secondX + 15, secondY, 15, 15);
		}else if (type == "otherBlinkingLight") {
			if (on) {
				g.setColor(Color.yellow);
				otherBlinkingRect = new Rectangle(firstX + 5, firstY + 5, 5, secondY - firstY + 15);
				g.fillRect(firstX + 5, firstY + 5, 5, secondY - firstY + 15);
			} else {
				otherBlinkingRect = new Rectangle(0, 0, 0, 0);
			}
			g.setColor(Color.DARK_GRAY);
			g.fillOval(firstX, firstY, 15, 15);
			g.fillOval(secondX, secondY + 15, 15, 15);
		}
	}

	public void update() {
		if (right) {
			x += 2;
		} else {
			x -= 2;
		}
		if (x > movingMax) {
			right = false;
		} else if (x < movingMin) {
			right = true;
		}
	}

	public String getLightType() {
		if (type == "horizBeam") {
			return "horiz";
		}
		if (type == "vertBeam") {
			return "vert";
		}
		if (type == "movingHoriz") {
			return "moving";
		}
		if (type == "buttonBeamVert") {
			return "vertButton";
		}
		if (type == "buttonBeamHoriz") {
			return "horizButton";
		}
		if (type == "blinkingLight") {
			return "blinking";
		}
		if(type=="otherBlinkingLight") {
			return "other";
		}

		return null;
	}

}
