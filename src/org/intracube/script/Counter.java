package org.intracube.script;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;

public class Counter extends Thread implements ClientElements{

	private boolean flag=false;
	private boolean tick=false;
	private int interval;
	
	public Counter(int interval){
		this.interval = interval;
	}
	
	public void kill(){
		flag = true;
	}
	
	public boolean isTick(){
		return tick;
	}
		
	@Override
	public void run(){
		while (!flag){
			try {
				tick=false;
				sleep(interval);
				tick=true;
			} catch (InterruptedException e) {
				log.show("Counter Thread interrupted.", Priority.SEVERE);
				flag=true;
			}
		}
	}
}
