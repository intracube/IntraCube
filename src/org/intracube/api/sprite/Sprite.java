package org.intracube.api.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;

import org.intracube.api.input.InputResponder;

public class Sprite {

	private Image img;
	private Point location;
	private Animation anim;
	private int health=0;
	//private Controller cntrl;

	/**
	 * Creates a new Sprite with no values
	 */
	public Sprite(){}

	/**
	 * Creates a new Sprite with given image. Starting location set to (0,0)
	 * @param img Image to set
	 */
	public Sprite (Image img){
		this(img, 0, 0);
	}

	/**
	 * Creates a new Sprite with given image at given location
	 * @param img Image to set
	 * @param locX int x location to set
	 * @param locY int y location to set
	 */
	public Sprite(Image img, int locX, int locY){
		this.img = img;
		this.location = new Point(locX, locY);
	}

	/**
	 * Creates a new Sprite with given image at given location
	 * @param img Image to set
	 * @param point Point to set
	 */
	public Sprite(Image img, Point point){
		this.img = img;
		this.location = point;
	}

	/**
	 * Sets the image of Sprite
	 * @param image Image to set
	 */
	public void setImage(Image image){
		this.img = image;
	}

	/**
	 * Sets the location of Sprite
	 * @param loc Point location
	 */
	public void setLocation(Point loc){
		this.location = loc;
	}

	/**
	 * Sets the location of Sprite
	 * @param x int x location
	 * @param y int y location
	 */
	public void setLocation(int x, int y){
		this.location = new Point(x,y);
	}

	/**
	 * Sets the health of Sprite
	 * @param health int health to set
	 */
	public void setHealth(int health){
		this.health = health;
	}

	/**
	 * Adds health to Sprite
	 * @param amount int amount to add
	 */
	public void addHealth(int amount){
		health += amount;
	}

	/**
	 * Removes health of Sprite
	 * @param amount int amount to remove
	 */
	public void removeHealth(int amount){
		health -= amount;
	}

	/**
	 * Gets the health associated with Sprite
	 * @return int health
	 */
	public int getHealth(){
		return health;
	}

	/**
	 * Fires a given Projectile at a given direction (x and y velocities) and speed
	 * @param proj Projectile to fire
	 * @param velX int velocity of x increase
	 * @param velY int velocity of y increase
	 * @param speed int speed to travel
	 */
	public void fire(Projectile proj,int velX, int velY, int speed){

		Projectile proj2 = new Projectile(proj.getLocation(), new Sprite(proj.getSprite().getImage(), proj.getSprite().getLocation()));
		proj2.move(velX, velY, speed);

		projectiles.add(proj2);
	}

	private LinkedList<Projectile> projectiles = new LinkedList<Projectile>();

	/**
	 * Updates Sprite image, location, and fired Projectiles
	 * Note: Use under draw method
	 * @param g Graphics to use
	 */
	public void update(Graphics g){ 
		try{
			g.drawImage(getImage(), getLocation().x, getLocation().y, null);
			Iterator<Projectile> iterator = projectiles.iterator();
			synchronized (mutex){
				while (iterator.hasNext()){
					Projectile proj = iterator.next();
					Sprite sprite = proj.getSprite();
					if (sprite != null){
					g.drawImage(sprite.getImage(), sprite.getLocation().x, sprite.getLocation().y, null);
					}else{
						iterator.remove();
					}
				}		
			}
		}catch (java.util.ConcurrentModificationException ex){
			// ignore
		}
	}
	private static final Object mutex = new Object();
	
	/**
	 * Gets Projectiles fired for this Sprite
	 * Note: Projectiles are removed when they are no longer visible
	 * @return LinkedList<Projectile> Projectiles fired
	 */
	public LinkedList<Projectile> getProjectiles(){
		return projectiles;
	}

	/**
	 * Starts an Animation for the Sprite
	 * @param animation Animation to use
	 */
	public void startAnimation(Animation animation){
		anim = animation;
		if (anim != null){
			anim.start(this);
		}
	}

	/**
	 * Starts an Animation for the Sprite
	 * @param animation Animation to use
	 * @param repeat boolean - should Animation repeat
	 */
	public void startAnimation(Animation animation, boolean repeat){
		anim = animation;
		if (anim != null){
			anim.start(this, repeat);
		}
	}

	/**
	 * Stops the Sprite's Animation 
	 */
	public void stopAnimation(){
		if (anim != null){
			anim.stop();
			anim = null;
		}
	}

	/*public void setController(Controller cntrl){

	}

	/*public void removeController(){

	}*/

	////// getters //////

	/**
	 * Gets the Sprite's image
	 * @return Image
	 */
	public Image getImage(){
		return img;
	}

	/**
	 * Gets the location of the Sprite
	 * @return Point
	 */
	public Point getLocation(){
		return location;
	}

	/**
	 * Gets the Sprite's Animation
	 * @return Animation
	 */
	public Animation getAnimation(){
		return anim;
	}

	/**
	 * Gets if the Sprite has been hovered by the mouse
	 * @return boolean true if Sprite is hovered, false if Sprite is not hovered
	 */
	public boolean isHovered(){
		return new Collision().getBounds(this).contains(new InputResponder().getAbsMousePos());
	}

	/**
	 * Gets if the Sprite has been clicked by the mouse
	 * @return boolean true if Sprite is clicked, false if Sprite is not clicked
	 */
	public boolean isClicked(){
		return (new Collision().getBounds(this).contains(new InputResponder().getAbsMousePos()) && new InputResponder().isMousePress());
	}

	/*public Controller getController(){
		return cntrl;
	}*/

}
