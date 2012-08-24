package org.intracube.api.sprite;

import java.awt.Point;
import java.awt.Rectangle;

import org.intracube.api.elements.ClientElements;

public class Collision implements ClientElements {

	public boolean isCollision(Sprite sprite1, Sprite sprite2){
		return (getBounds(sprite1).intersects(getBounds(sprite2)));
	}

	public boolean isCollisionSide(Sprite sprite){
		return isCollision(sprite, new Point(getBounds(sprite).x, 0)) || // top
		isCollision(sprite, new Point(getBounds(sprite).x, client.getMainPanel().getHeight())) || // bottom
		isCollision(sprite, new Point(0, getBounds(sprite).y)) || // left
		isCollision(sprite, new Point(client.getMainPanel().getWidth(), getBounds(sprite).y));
	}

	public boolean isCollision(Sprite sprite, Point point){
		return getBounds(sprite).contains(point);
	}

	public boolean isCollision(Sprite sprite, Rectangle rect){
		return getBounds(sprite).intersects(rect);
	}

	public Rectangle getBounds(Sprite sprite) {
		return new Rectangle(sprite.getLocation().x, sprite.getLocation().y, sprite.getImage().getWidth(null), sprite.getImage().getHeight(null));
	}
}
