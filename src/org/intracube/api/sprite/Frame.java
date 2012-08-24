package org.intracube.api.sprite;

import java.awt.Image;

public class Frame {

	private Image image;
	private int delay;

	public Frame(Image image, int delay){ // delay is for how many seconds (in millis) between frame transition
		this.image = image;
		if (delay >= 0){
			this.delay = delay;
		}else{
			this.delay = 0;
		}
	}

	public Frame(Image image){
		this.image = image;
		this.delay = 0;
	}

	public void setImage(Image image){
		this.image = image;
	}

	public void setDelay(int delay){
		if (delay >= 0){
			this.delay = delay;
		}else{
			this.delay = 0;
		}
	}

	public void removeImage(){
		image = null;
	}

	public void removeDelay(){
		delay = 0;
	}


	// gets

	public Image getImage(){
		return image;
	}

	public int getDelay(){
		return delay;
	}

	public int getHeight(){ 
		return image.getHeight(null);
	}

	public int getWidth(){ 
		return image.getWidth(null);
	}

}
