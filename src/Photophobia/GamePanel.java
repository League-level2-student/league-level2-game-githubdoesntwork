package Photophobia;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, KeyListener {
	final int menu = 0, game = 1, gameOver = 2, end = 3, instructions = 4, levelCompleted = 5;
	int level = 1;
	int completedX = -50;
	int completedPlayerX = 0;
	int scoreCounting;
	int current = menu;
	Font title;
	Font normal;
	Timer timer;
	GameObject object;
	Player player;
	ArrayList<Lights> lights = new ArrayList<Lights>();
	PowerUp speedBoost;
	Exit exit;
	boolean isPowerUp;
	int rand;
	int x;
	int score;
	ArrayList<Wall> walls = new ArrayList<Wall>();

	public GamePanel() {
		title = new Font("", Font.BOLD, 48);
		normal = new Font("", Font.PLAIN, 20);
		timer = new Timer(1000 / 60, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (current == menu) {
			drawMenu(g);
		} else if (current == game) {
			drawGame(g);
		} else if (current == gameOver) {
			drawGameOver(g);
		} else if (current == end) {
			drawEnd(g);
		} else if (current == instructions) {
			drawInstructions(g);
		} else if (current == levelCompleted) {
			drawLevelCompleted(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		score++;
	}

	public void drawMenu(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 500);
		g.setFont(title);
		g.setColor(Color.yellow);
		g.drawString("Photophobia", 150, 150);
		g.setFont(normal);
		g.drawRect(185, 375, 235, 40);
		g.drawRect(155, 200, 295, 40);
		g.drawString("Press Enter for instructions", 175, 225);
		g.drawString("Press space to start", 210, 400);
	}

	public void drawGame(Graphics g) {
		Color gameColor = new Color(35, 35, 35);
		g.setColor(gameColor);
		g.fillRect(0, 0, 600, 500);
		Color groundColor = new Color(99, 72, 72);
		g.setColor(groundColor);
		g.fillRect(0, 420, 600, 80);
		g.fillRect(0, 0, 600, 80);
		g.fillRect(0, 0, 80, 500);
		g.fillRect(520, 0, 80, 500);
		if (isPowerUp) {
			g.setColor(Color.red);
		} else {
			Color playerColor = new Color(153, 153, 153);
			g.setColor(playerColor);
		}
		player.draw(g);
		exit.draw(g);
		for (Lights light : lights) {
			light.draw(g);
		}
		speedBoost.draw(g);
		for (Wall wall : walls) {
			wall.draw(g);
		}
		for (Lights light : lights) {
			light.update();
		}
		for (Lights light : lights) {
			if (light.getLightType().equals("moving")) {
				if (player.playerRect.intersects(light.movingRect)) {
					intersectsLight();
				}
			} else if (light.getLightType().equals("vert")) {
				if (player.playerRect.intersects(light.vertRect)) {
					intersectsLight();
				}
			} else if (light.getLightType().equals("horiz")) {
				if (player.playerRect.intersects(light.horizRect)) {
					intersectsLight();
				}
			} else if (light.getLightType().equals("vertButton")) {
				if (player.playerRect.intersects(light.buttonRectVert)) {
					intersectsLight();
				}
			} else if (light.getLightType().equals("horizButton")) {
				if (player.playerRect.intersects(light.buttonRectHoriz)) {
					intersectsLight();
				}
			} else if (light.getLightType().equals("blinking")) {
				if (player.playerRect.intersects(light.blinkingRect)) {
					intersectsLight();
				}
			} else if (light.getLightType().equals("other")) {
				if (player.playerRect.intersects(light.otherBlinkingRect)) {
					intersectsLight();
				}
			}
		}

		if (player.playerRect.intersects(speedBoost.rect)) {
			isPowerUp = true;
			speedBoost = new PowerUp(0, 0, 0, 0);
		}

		if (exit.exitRect.intersects(player.playerRect)) {
			completedX = -50;
			completedPlayerX = -440;
			current = levelCompleted;
			int delay = 3000;
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					level++;
					if (level > 4) {
						current = end;
					}else {
						current=game;
					}
					if (level == 1) {
						createLvl1();
					} else if (level == 2) {
						createLvl2();
					} else if (level == 3) {
						createLvl3();
						x = 150;
					} else if (level == 4) {
						createLvl4();
					}
					isPowerUp = false;
				}
			};
			Timer something = new Timer(delay, taskPerformer);
			something.start();
			something.setRepeats(false);

		}
		if (walls != null) {
			for (Wall wall : walls) {
				if (player.futureRect.intersects(wall.wallRect)) {
					player.x = player.formerX;
					player.y = player.formerY;
				}
			}
		}
		for (Lights light : lights) {
			if (light.getLightType() == "vertButton") {
				if (player.playerRect.intersects(light.buttonVert)) {
					light.beamOnVert = false;
				}
			} else if (light.getLightType() == "horizButton") {
				if (player.playerRect.intersects(light.buttonHoriz)) {
					light.beamOnHoriz = false;
				}
			}
		}

	}

	public void drawGameOver(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 500);
		g.setColor(Color.yellow);
		g.setFont(title);
		g.drawString("Game Over", 150, 150);
		g.setFont(normal);
		g.drawString("Press space to restart", 180, 350);
		if (rand == 1) {
			g.drawString("What a tragedy", 215, 250);
		} else if (rand == 0) {
			g.drawString("Try harder than that", 190, 250);
		} else if (rand == 2) {
			g.drawString("Dying is not the objective", 165, 250);
		}
	}

	public void drawEnd(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 500);
		g.setColor(Color.yellow);
		g.setFont(title);
		g.drawString("You Win!", 170, 150);
		g.setFont(normal);
		g.drawString("Score:", 220, 300);
		g.drawString(""+scoreCounting, 290, 300);
		scoreCounting+=8;
		if(scoreCounting>7000-score) {
			timer.stop();
		}
	}

	public void drawLevelCompleted(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 500);
		g.setColor(Color.yellow);
		g.setFont(title);
		g.drawString("Level", 220, 100);
		g.drawString("Completed", completedX, 200);
		g.setColor(new Color(12, 12, 12));
		g.fillRect(completedPlayerX, 330, 70, 70);
		g.setColor(Color.gray);
		g.fillRect(completedPlayerX + 10, 340, 50, 50);
		g.setColor(Color.black);
		g.fillRect(completedPlayerX + 20, 350, 10, 10);
		g.fillRect(completedPlayerX + 50, 350, 10, 10);
		g.fillRect(completedPlayerX + 20, 370, 40, 10);
		completedX += 3;
		completedPlayerX += 6;
		if (completedX > 150) {
			completedX = 150;
		}
		if (walls != null) {
			walls.removeAll(walls);
		}
		lights.removeAll(lights);
	}

	public void drawInstructions(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 500);
		g.setColor(Color.yellow);
		g.setFont(title);
		g.drawString("Instructions", 140, 100);
		g.setFont(normal);
		g.drawString("'Photophobia' is the fear of light. Avoid the light to survive.", 5, 190);
		g.drawString("The red rectangle gives the player a sizable speed boost.", 10, 220);
		g.drawString("Near the end of the level, the black square is the exit,", 5, 250);
		g.drawString("which is triggered upon contact. The yellow represents", 5, 280);
		g.drawString("a light, which kills the player, the orange and green ", 5, 310);
		g.drawString("buttons deactivate horizonal and vertical lights, respectively.", 5, 340);
		g.drawString("Lastly, blinking lights turn on and off once a second.", 5, 370);
		g.drawString("The player can navigate with 'WASD' keys.", 5, 400);
		g.drawString("Press space to return to menu", 150, 430);

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (current == menu && key == KeyEvent.VK_SPACE) {
			level = 1;
			isPowerUp = false;
			player = new Player(288, 238, 10, 10);
			current = game;
			if (walls != null) {
				walls.removeAll(walls);
			}
			lights.removeAll(lights);
			createLvl1();
			System.out.println(current);
		} else if (current == instructions && key == KeyEvent.VK_SPACE) {
			current = menu;
		} else if (current == menu && key == KeyEvent.VK_ENTER) {
			current = instructions;
		} else if (current == gameOver && key == KeyEvent.VK_SPACE) {
			current = menu;
			System.out.println(current);
		} else if (current == end) {
			current = menu;
			System.out.println(current);
		} else if (current == game) {
			System.out.println(player.x + ", " + player.y);
			if (key == KeyEvent.VK_W) {
				player.up = true;
			} else if (key == KeyEvent.VK_ENTER) {
				current++;
			} else if (key == KeyEvent.VK_S) {
				player.down = true;
			} else if (key == KeyEvent.VK_A) {
				player.left = true;
			} else if (key == KeyEvent.VK_D) {
				player.right = true;
			}
			if (isPowerUp) {
				player.speed = 4;
			} else {
				player.speed = 2;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			player.up = false;
			break;
		case KeyEvent.VK_S:
			player.down = false;
			break;
		case KeyEvent.VK_A:
			player.left = false;
			break;
		case KeyEvent.VK_D:
			player.right = false;
			break;
		}
	}

	public void intersectsLight() {
		level = 1;
		rand++;
		if (rand > 2) {
			rand = 0;
		}
		current = gameOver;
	}

	public void createLvl1() {
		player.x = 288;
		player.y = 238;
		exit = new Exit(470, 230, 20, 20);
		lights.add(new Lights(400, 150, 400, 300, "vertBeam", 0, 0, 0, 0, 0));
		speedBoost = new PowerUp(0, 0, 0, 0);
		walls.add(new Wall(0, 0, 200, 200));
	}

	public void createLvl2() {
		player.x = 374;
		player.y = 288;
		exit = new Exit(450, 120, 20, 20);
		lights.add(new Lights(400, 180, 488, 180, "buttonBeamHoriz", 100, 390, 160, 0, 0));
		lights.add(new Lights(80, 240, 80, 320, "movingHoriz", 0, 0, 160, 80, 80));
		speedBoost = new PowerUp(0, 0, 0, 0);
		walls.add(new Wall(300, 80, 100, 100));
		walls.add(new Wall(160, 340, 80, 100));
	}

	public void createLvl3() {
		player.x = 424;
		player.y = 264;
		exit = new Exit(424, 375, 20, 20);
		lights.add(new Lights(220, 200, 350, 200, "blinkingLight", 0, 0, 0, 0, 0));
		lights.add(new Lights(190, 200, 190, 310, "buttonBeamVert", 293, 126, 0, 0, 0));
		lights.add(new Lights(350, 340, 490, 340, "buttonBeamHoriz", 126, 260, 0, 0, 0));
		speedBoost = new PowerUp(0, 0, 0, 0);
		walls.add(new Wall(80, 340, 270, 80));
		walls.add(new Wall(80, 80, 150, 120));
		walls.add(new Wall(370, 80, 150, 120));
	}

	public void createLvl4() {
		player.x = 340;
		player.y = 242;
		exit = new Exit(110, 110, 20, 20);
		lights.add(new Lights(428, 156, 491, 156, "buttonBeamHoriz", 468, 202, 0, 0, 0));
		lights.add(new Lights(430, 156, 430, 222, "otherBlinkingLight", 0, 0, 0, 0, 0));
		lights.add(new Lights(80, 150, 80, 200, "movingHoriz", 0, 0, 158, 80, 0));
		lights.add(new Lights(158, 215, 158, 300, "movingHoriz", 0, 0, 158, 80, 0));
		lights.add(new Lights(80, 320, 133, 320, "blinkingLight", 0, 0, 0, 0, 0));
		speedBoost = new PowerUp(465, 116, 15, 15);
		walls.add(new Wall(213, 80, 215, 80));
		walls.add(new Wall(430, 252, 100, 200));
		walls.add(new Wall(163, 80, 50, 280));
	}

}