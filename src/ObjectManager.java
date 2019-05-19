import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener{
Rocketship rocket;
ArrayList<Projectile> projectiles=new ArrayList<Projectile>();
ArrayList<Alien> aliens=new ArrayList<Alien>();
Random r = new Random();
int score=0;
public ObjectManager(Rocketship r) {
	rocket = r;
}
public void addProjectile(Projectile p) {
	projectiles.add(p);
}
public void addAlien() {
	aliens.add(new Alien(r.nextInt(LeagueInvaders.WIDTH),0,50,50));
}
public void update() {
	rocket.update();
	for (int i = 0; i < aliens.size(); i++) {
		aliens.get(i).update();
		if(aliens.get(i).x>LeagueInvaders.WIDTH||aliens.get(i).x<0||aliens.get(i).y>LeagueInvaders.HEIGHT) {
			aliens.get(i).isActive=false;
		}
	}
	for (int i = 0; i < projectiles.size(); i++) {
		projectiles.get(i).update();
		if(projectiles.get(i).y<0) {
			projectiles.get(i).isActive=false;
		}
	}
	if(rocket.isActive) {
	checkCollision();
	purgeObjects();
	}
}
public int getScore() {
	return score;
}
public void draw(Graphics g) {
	rocket.draw(g);
	for (int i = 0; i < aliens.size(); i++) {
		aliens.get(i).draw(g);
	}
	for (int i = 0; i < projectiles.size(); i++) {
		projectiles.get(i).draw(g);
	}
}
public void purgeObjects() {
	for (int i = 0; i < aliens.size(); i++) {
		if(!aliens.get(i).isActive) {
			aliens.remove(i);
		}
	}
	for (int i = 0; i < projectiles.size(); i++) {
		if(!projectiles.get(i).isActive) {
			projectiles.remove(i);
		}
	}
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	addAlien();
}
public void checkCollision() {
	for (int i = 0; i < aliens.size(); i++) {
		if(aliens.get(i).collisionBox.intersects(rocket.collisionBox)) {
			rocket.isActive=false;
			aliens.get(i).isActive=false;
		}
		for (int j = 0; j < projectiles.size(); j++) {
			if(aliens.get(i).collisionBox.intersects(projectiles.get(j).collisionBox)) {
				aliens.get(i).isActive=false;
				projectiles.get(j).isActive=false;
				score++;
			}
		}
	}
}
}


















