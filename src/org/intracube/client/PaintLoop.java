package org.intracube.client;

import javax.swing.JPanel;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;

public class PaintLoop extends Thread implements ClientElements{
	
	private MainDriver driver = new MainDriver();
	private JPanel canvas = new IntraCubeClient().getMainPanel();
	
	@Override
	public void run() {
		 while (driver.isRunning()){
			canvas.repaint();
			try {
				Thread.sleep(100);
				Thread.yield();
				
			} catch (InterruptedException e) {
				log.show("Painting interrupted exception", Priority.SEVERE);
				log.show(e.toString(), Priority.SEVERE);
				
				e.printStackTrace();
			}
		}
	}

}
