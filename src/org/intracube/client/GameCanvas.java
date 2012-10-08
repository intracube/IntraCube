package org.intracube.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import org.intracube.script.Script;

public class GameCanvas extends JPanel {

	private static final long serialVersionUID = 3298864204965508279L;

	
	private static Script script;

	public GameCanvas() {
		
	}

    public GameCanvas(Script game) {
		GameCanvas.script = game;
		requestFocus();
    }

    @Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0,0,10000,10000);
		script.draw((Graphics2D)g);
	}
}
