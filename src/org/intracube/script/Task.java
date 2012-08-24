package org.intracube.script;

import java.util.Timer;
import java.util.TimerTask;



public abstract class Task {

	public abstract void run();

	public void start(){
		if (tmrMain == null){
			tmrMain = new Timer();
			TimerTask tTask = new LTM(this);

			tmrMain.scheduleAtFixedRate(tTask, 0, 1);
		}
	}

	private class LTM extends TimerTask {

		private Task task;

		public LTM(Task task){
			this.task = task;
		}
		public void run() {
			task.run();
		}
	}
	
	public void terminate(){
		if (tmrMain != null){
			tmrMain.cancel();
			tmrMain = null;
		}
	}

	private Timer tmrMain;

}
