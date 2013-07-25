package org.intracube.api.environment;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.input.Direction;

public class IntraBackground implements ClientElements {

	private Image image;
	private Point location = new Point(0,0);

	public IntraBackground(Image img){
		this.image = img;
	}
	
	public IntraBackground(Image img, Point start){
		this.image = img;
		this.location = start;
	}

	public Image getImage(){
		return image;
	}

	public Point getTopLeft(){
		return location;
	}
	
	public Point getBottomRight(){
		return new Point(location.x+client.getMainPanel().getWidth(), location.y+client.getMainPanel().getHeight());
	}

	/**
	 * Moves background in given direction (use only: UP, DOWN, LEFT, RIGHT)
	 * @param dir
	 * @param amount
	 */
	public void move(Direction dir, int amount){
		if (dir == Direction.UP){
			location = new Point(location.x, location.y+amount);
		}else if (dir == Direction.DOWN){
			location = new Point(location.x, location.y-amount);
		}else if (dir == Direction.LEFT){
			location = new Point(location.x+amount, location.y);
		}else if (dir == Direction.RIGHT){
			location = new Point(location.x-amount, location.y);
		}
	}

	/**
	 * updates background with supplied graphics
	 * @param g Graphics to use
	 */
	public void update(Graphics2D g){
		g.drawImage(image, location.x, location.y, null);
	}
}
