package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.intracube.api.manifest.ScriptManifest;
import org.intracube.api.sprite.Projectile;
import org.intracube.api.sprite.Sprite;
import org.intracube.script.Script;
import org.intracube.script.Task;

@ScriptManifest(
		authors = {"Aaron McClure"},
		version = 1.0,
		description = "Alien Shooter Game",
		name = "Aliens"
)
public class Aliens extends Script {

	private Sprite ship = new Sprite(getURLImage("http://i1256.photobucket.com/albums/ii500/a_mcclure/spaceship_zpse282797f.png"), this.getCanvasSize().width/2, this.getCanvasSize().height-110);
	private Sprite alien = new Sprite(getURLImage("http://i1256.photobucket.com/albums/ii500/a_mcclure/alien_zps29eee4a0.png"), new Point(-100,-100)); // start off screen
	private Sprite bullet = new Sprite(getURLImage("http://i1256.photobucket.com/albums/ii500/a_mcclure/AlienResources/bullet_zpse86aa4cc.png"), new Point(0,0));
	private Projectile projectiles = new Projectile(new Point(0,0), bullet);
	private ArrayList<Sprite> aliens = new ArrayList<Sprite>();
	private boolean over=false;
	@Override
	public boolean init() {
		log.show("Welcome to Aliens!!", Color.green);
		enemies = Integer.parseInt(JOptionPane.showInputDialog("How many aliens do you want to fight?"));
		for (int i=0; i<enemies; i++){
			aliens.add(new Sprite(alien.getImage(), alien.getLocation()));
		}
		new UserInput().start();
		return true;
	}

	@Override
	public int loop() {
		if (!over){
			if (!sendEnemy()){
				Script.stopScript();
			}
			if (lives <= 0){
				log.show("You lose!");
				over = true;
			}
			if (enemies <= 0){
				over = true;
				log.show("You win!");
			}
		}
		return 550;
	}

	private boolean sendEnemy() {
		int x = calc.random(5, 660);
		for (int i=0; i<aliens.size(); i++){
			if (aliens.get(i).getLocation().y < 5){
				aliens.get(i).setLocation(x, 0);
				new EnemyMover(aliens.get(i)).start();
				return true;
			}
		}
		return false;
	}

	public class EnemyMover extends Task{

		private Sprite sprite;
		public EnemyMover(Sprite sprite){
			this.sprite = sprite;
		}
		@Override
		public void run() {
			if ((sprite.getLocation().y + 8) <= 700){
				sprite.setLocation(sprite.getLocation().x, sprite.getLocation().y+8);
			}else{
				aliens.remove(sprite);
				enemies--;
				this.terminate();
			}
			if (collision.isCollision(ship, sprite)){
				lives--;
				aliens.remove(sprite);
				enemies--;
				this.terminate();
			}
			LinkedList<Projectile> proj = ship.getProjectiles();
			for (int i=0; i<proj.size(); i++){
				if (proj.get(i).getSprite() != null && collision.isCollision(proj.get(i).getSprite(), sprite)){
					score+=50;
					aliens.remove(sprite);
					enemies--;
					this.terminate();
				}
			}
			sleep(30);
		}

	}

	public class UserInput extends Task{
		@Override
		public void run() {
			checkInput();
			sleep(40);
		}
	}

	private void checkInput(){
		if (input.isKeyPressed("f")){
			projectiles.setPoint(new Point(ship.getLocation().x+44, ship.getLocation().y+10));
			ship.fire(projectiles, 0, -5, 10);
		}
		if (input.isKeyPressed("a")){
			if (ship.getLocation().x - 20 >= 0){
				ship.setLocation(ship.getLocation().x-20, ship.getLocation().y);
			}
		}
		if (input.isKeyPressed("d")){
			if (ship.getLocation().x + 20 <= 650){
				ship.setLocation(ship.getLocation().x+20, ship.getLocation().y);
			}
		}
	}

	private int score=0, enemies=0, lives=3;

	@Override
	public void draw(Graphics2D g) {
		if (!over){
			g.setColor(Color.GREEN);
			g.setFont(new Font("sansserif", Font.PLAIN, 18));
			g.drawString("Score: " + score, 0, 18);
			g.drawString("Enemies remaining: " + enemies, 0, 40);
			g.drawString("Lives: " + lives, 640, 20);

			ship.update(g);
			for (int i=0; i<aliens.size(); i++){
				aliens.get(i).update(g);
			}
		}else{
			g.setColor(Color.green);
			g.setFont(new Font("sansserif", Font.PLAIN, 48));
			g.drawString("Game Over!", 200, 225);
		}
	}

	@Override
	public void onFinish() {
		log.show("Thanks for playing :)", Color.blue);

	}

}
