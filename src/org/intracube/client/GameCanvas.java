package org.intracube.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.intracube.api.elements.ClientElements;
import org.intracube.script.Script;

public class GameCanvas extends JPanel implements ClientElements {

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
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.fillRect(0,0,script.getCanvasSize().width, script.getCanvasSize().height);
		script.draw(g2);
	}

}
