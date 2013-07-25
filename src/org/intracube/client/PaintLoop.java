package org.intracube.client;

import javax.swing.JPanel;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;

public class PaintLoop extends Thread implements ClientElements{

	private static final MainDriver DRIVER = new MainDriver();
	private static final JPanel CANVAS = new IntraCubeClient().getMainPanel();

	@Override
	public void run() {
		while (DRIVER.isRunning()){
			CANVAS.repaint();
			try {
				Thread.sleep(17);
				Thread.yield();
			} catch (InterruptedException e) {
				log.show("Painting interrupted exception", Priority.SEVERE);
				log.show(e.toString(), Priority.SEVERE);

				e.printStackTrace();
			}
		}
	}
}
