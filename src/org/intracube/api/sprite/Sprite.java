package org.intracube.api.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import org.intracube.api.input.InputResponder;

public class Sprite {

	private Image img;
	private Point location;
	private Animation anim;
	//private Controller cntrl;

	public Sprite(){}

	public Sprite (Image img){
		this(img, 0, 0);
	}

	public Sprite(Image img, int locX, int locY){
		this.img = img;
		this.location = new Point(locX, locY);
	}

	public Sprite(Image img, Point point){
		this.img = img;
		this.location = point;
	}

	public void setImage(Image image){
		this.img = image;
	}

	public void setLocation(Point loc){
		this.location = loc;
	}

	public void setLocation(int x, int y){
		this.location = new Point(x,y);
	}

	/*public void move(Point destination, int speed){

	}*/

	/**
	 * updates sprite image and location (use under the draw(g) method)
	 * @param g
	 */
	public void update(Graphics g){
		g.drawImage(getImage(), getLocation().x, getLocation().y, null);
	}

	public void startAnimation(Animation animation){
		anim = animation;
		if (anim != null){
			anim.start(this);
		}
	}

	public void startAnimation(Animation animation, boolean repeat){
		anim = animation;
		if (anim != null){
			anim.start(this, repeat);
		}
	}

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

	public Image getImage(){
		return img;
	}

	public Point getLocation(){
		return location;
	}

	public Animation getAnimation(){
		return anim;
	}
	
	public boolean isHovered(){
		return new Collision().getBounds(this).contains(new InputResponder().getAbsMousePos());
	}
	
	public boolean isClicked(){
		return (new Collision().getBounds(this).contains(new InputResponder().getAbsMousePos()) && new InputResponder().isMousePress());
	}

	/*public Controller getController(){
		return cntrl;
	}*/

}
