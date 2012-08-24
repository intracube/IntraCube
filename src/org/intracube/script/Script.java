package org.intracube.script;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;

import org.intracube.api.elements.Calculations;
import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;
import org.intracube.client.IntraCubeClient;
import org.intracube.client.MainDriver;
import org.intracube.script.Task;


public abstract class Script implements ClientElements{ 

	private static boolean sleepStat = false;

	public abstract boolean onStart();
	public abstract int loop();
	public abstract void draw(Graphics2D g);
	public abstract void onFinish();


	public static void sleep(int x){
		try {
			sleepStat = true;
			Thread.sleep(x);
			sleepStat = false;
		} catch (InterruptedException e) {
			log.show(e.getMessage(), Priority.SEVERE);
		}
	}

	/**
	 * sleeps random amount of time
	 * @param max
	 * @param min
	 */
	public static void sleep(int max, int min){
		sleep(Calculations.random(max,min));
	}

	public static boolean isSleeping(){
		return sleepStat;
	}

	public void startTask(Task tsk){
		Task task = tsk;
		if (task==null) return;

		task.start();	
	}

	public void stopTask(Task tsk){
		Task task = tsk;
		if (task==null) return;

		task.terminate();	
	}

	public static boolean isRunning(){
		if (new MainDriver() != null){
			return new MainDriver().isRunning();
		}else{
			return false;
		}
	}

	public static void stopScript(){
		if (new MainDriver() != null){
			new MainDriver().stop();
		}
	}

	public Image getURLImage(String url){
		try {
			Toolkit.getDefaultToolkit();
			Image image = Toolkit.getDefaultToolkit().createImage(new URL(url));
			return image;
		} catch (MalformedURLException e) {
			log.show("Malformed URL.", Priority.SEVERE);
			return null;
		}
	}

	public Dimension getCanvasSize(){
		return new IntraCubeClient().getMainPanel().getSize();
	}

	/*public void log(String message) {
	    new Logger().show(message);
    }*/

}

