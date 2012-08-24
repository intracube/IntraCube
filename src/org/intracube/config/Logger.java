package org.intracube.config;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;


/**
 * Handles logs for IntraGen
 * @author Aaron McClure
 *
 */
public class Logger {

	private JList logText; 
	private ArrayList<Color> backGC;
	private ArrayList<Color> foreGC;
	private DefaultListModel listModel;
	private String message = "", lastMessage = "none";
	private int counter = 1;

	public Logger(){
		if (backGC == null){
			backGC = new ArrayList<Color>();
		}
		if (foreGC == null){
			foreGC = new ArrayList<Color>();
		}
	}

	/**
	 * Shows a string message in the client's log
	 * @param message
	 */
	public void show(String message){
		logText = ClientElements.client.getLog();
		logText.setFocusable(false);
		listModel = ClientElements.client.getListModel();

		backGC.add(Color.LIGHT_GRAY);
		foreGC.add(Color.black);

		lastMessage = message;

		listModel.addElement(formatMsg(message));
		logText.setCellRenderer(new CustomRender(backGC, foreGC));
		counter ++;
		logText.ensureIndexIsVisible(logText.getLastVisibleIndex()+1);
		
		logText.repaint();
	}

	/**
	 * Shows a string message in the client's log with the specified color
	 * @param message
	 * @param color
	 */
	public void show(String message, Color color){	
		logText = ClientElements.client.getLog();
		listModel = ClientElements.client.getListModel();

		backGC.add(Color.LIGHT_GRAY);
		foreGC.add(color);

		lastMessage = message;

		listModel.addElement(formatMsg(message));
		logText.setCellRenderer(new CustomRender(backGC, foreGC));
		counter ++;
		logText.ensureIndexIsVisible(logText.getLastVisibleIndex()+1);
		
		logText.repaint();		
	}

	/**
	 * Shows a string message in the client's log with the specified priority
	 * @param message
	 * @param priority
	 */
	public void show(String message, Priority priority){	
		logText = ClientElements.client.getLog();
		listModel = ClientElements.client.getListModel();

		switch (priority){
		case LOW: backGC.add(Color.white); break;
		case NORMAL: backGC.add(Color.LIGHT_GRAY); break;
		case HIGH: backGC.add(Color.red); break;
		case SEVERE: backGC.add(Color.red); break;
		}

		if (priority.equals(Priority.SEVERE)){
			foreGC.add(Color.white);
		}else{
			foreGC.add(Color.black);
		}

		lastMessage = message;

		listModel.addElement(formatMsg(message));
		logText.setCellRenderer(new CustomRender(backGC, foreGC));
		counter ++;

		logText.ensureIndexIsVisible(logText.getLastVisibleIndex()+1);

		logText.repaint();
	}

	/**
	 * returns the number of logs made (both client and script)
	 * @return
	 */
	public int getNumLogs(){
		return counter;
	}

	/**
	 * returns the last log displayed
	 * @return
	 */
	public String getLastLog(){
		return lastMessage;
	}

	@SuppressWarnings("static-access")
	private String formatMsg(String message){
		String sender = "null";
		Calendar time = Calendar.getInstance();

		this.message = "";
		this.lastMessage = "";

		if (!(new Throwable().getStackTrace()[2].getClassName().toLowerCase().contains("org.intracube.client"))){
			sender = "Script";
		}else{
			sender = "<Client>";
		}
		this.message += String.format("%-10s %-10s %-30s", "[" + time.get(time.HOUR) + ":" + time.get(time.MINUTE) + ":" + time.get(time.SECOND) + "]", sender, message);

		return this.message;
	}

	private class CustomRender extends DefaultListCellRenderer{

		private static final long serialVersionUID = -5707651047493700867L;
		private ArrayList<Color> backC, foreC;

		public CustomRender(ArrayList<Color> back, ArrayList<Color> fore) {
			setOpaque(true);
			this.backC = back;
			this.foreC = fore;
		}

		public Component getListCellRendererComponent(final JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) { // jlist was not final
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			try{
				setForeground(foreC.get(index-1));
				setBackground(backC.get(index-1));
			}catch (Exception ex){
				// ignore exception
			}
			setText(value.toString());
			
			return this;
		}
	}
}
