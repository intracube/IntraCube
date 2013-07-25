package org.intracube.api.sprite;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Timer;

import java.util.TimerTask;

import org.intracube.api.elements.ClientElements;
import org.intracube.client.IntraCubeClient;


public class Projectile implements ClientElements {

	//private int numProjs=-1; // -1 indicates unlimited
	//private Rectangle shape = null;
	private Point start;
	private Sprite sprite = null;

	/*public Projectile(Point start, Rectangle shape){
		this.start = start;
		this.shape = shape;
	}*/

	/**
	 * Creates a new Projectile with given starting point and Sprite
	 */
	public Projectile(Point start, Sprite sprite){
		this.start = start;
		this.sprite = sprite;
	}

	/*public void setProjectiles(int amount){
		numProjs = amount;
	}*/

	/*public void addProjectiles(int amount){
		numProjs += amount;
	}*/

	/**
	 * Sets the Projectile's
	 */
	public void setSprite(Sprite sprite){
		this.sprite = sprite;
	}

	public void setPoint(Point point){
		this.start = point;
	}

	/*public void removeProjectiles(int amount){
		numProjs -= amount;
	}*/


	/*public int getProjectiles(){
		return numProjs;
	}*/

	private int velX, velY;
	private Timer timer;

	/**
	 * Continuously moves Projectile at given velocities and speed until Projectile is no longer visible.
	 * @param velX int x velocity
	 * @param velY int y velocity
	 * @param speed int speed to move (in millis)
	 */
	public void move(int velX, int velY, int speed){
		//if (numProjs==-1 || numProjs > 0){
		this.velX = velX;
		this.velY = velY;

		timer = new Timer();
		timer.scheduleAtFixedRate(new LoopTask(), 0, speed);
		//}
	}

	/**
	 * Gets the Projectile's Sprite
	 * @return Sprite
	 */
	public Sprite getSprite(){
		return sprite;
	}

	/**
	 * Gets the starting location of the Projectile
	 * @return Point
	 */
	public Point getLocation(){
		return start;
	}

	/**
	 * Updates the Projectile's Sprite
	 * @param g Graphics to use
	 */
	public void update(Graphics g){
		sprite.update(g);
	}

	private class LoopTask extends TimerTask {
		public void run() {
			start.setLocation(new Point(start.x+velX, start.y+velY));//     

			sprite.setLocation(start);

			int width = sprite.getImage().getWidth(null);
			int height = sprite.getImage().getHeight(null);
			if (!new Rectangle(-50-width,-50-height, new IntraCubeClient().getMainPanel().getWidth()+50+width, new IntraCubeClient().getMainPanel().getHeight()+50+height).contains(sprite.getLocation())){ // 50 pixel gap
				timer.cancel();
				this.cancel();
				timer.purge();
				timer = null;
				sprite = null;
			}

		}
	}
}
