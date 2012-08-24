package org.intracube.api.sprite;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class Animation {

	private ArrayList<Frame> frames = new ArrayList<Frame>();
	private boolean running = false, repeat = false;
	private Timer timer;
	Sprite sprite;

	public Animation(Frame...frame){ 
		for (int i=0; i<frame.length; i++){
			this.frames.add(frame[i]);
		}
	}

	public void setFrames(int delay, Frame...frame){ 
		if (this.frames != null){
			this.frames.clear();
		}
		for (int i=0; i<frame.length; i++){
			this.frames.add(frame[i]);
		}
	}

	public void start(Sprite sprite){
		if (!running){
			this.sprite = sprite;

			running = true;
			repeat = false;
		}
	}

	public void start(Sprite sprite, boolean repeat){
		if (!running){
			this.sprite = sprite;

			timer = new Timer();
			TimerTask task = new LoopTask();
			timer.scheduleAtFixedRate(task, 1, 1);

			running = true;
			this.repeat = repeat;

		}
	}

	public void stop(){
		if (running){
			timer.cancel();
			timer = null;

			running = false;
			this.repeat = false;
		}
	}

	private class LoopTask extends TimerTask {
		private int frameIndex;

		public void run() {
			for (int i=0; i<frames.size(); i++){
				int frameDelay = frames.get(i).getDelay();
				if (frameDelay < 30){
					frameDelay = 30; // 150
				}
				frameIndex = i;
				try {
					Thread.currentThread();
					Thread.sleep(frameDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				sprite.setImage(frames.get(frameIndex).getImage());

				if (!repeat){
					if (timer != null){
						timer.cancel();
					}
				}
			}
			//timer.cancel();
		}
		public int getFrameIndex(){
			return frameIndex;
		}
	}

	// getters 

	public Frame[] getFrames(){
		Frame[] rFrames = new Frame[this.frames.size()];

		for (int i=0; i<rFrames.length; i++){
			rFrames[i] = this.frames.get(i);
		}
		return rFrames;
	}

	public Frame getFrame(int index){
		return (frames.get(index));
	}

	public Frame getCurrentFrame(){ // only returns if animation is running
		if (running){
			return frames.get(new LoopTask().getFrameIndex());
		}else{
			return null;
		}
	}

	public int getCurrentFrameIndex(){ // only returns if animation is running
		if (running){
			return new LoopTask().getFrameIndex();
		}else{
			return -1;
		}
	}

	public boolean isRunning(){
		return running;
	}
}
