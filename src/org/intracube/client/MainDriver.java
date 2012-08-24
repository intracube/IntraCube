package org.intracube.client;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;


import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;
import org.intracube.config.ManRead;
import org.intracube.config.SecurityScanner;
import org.intracube.script.Script;


/**
 * IntraCube main driver. DO NOT EDIT
 * @author Aaron McClure
 *
 */
public class MainDriver implements ClientElements {

	public void startScript(Class<?> c){
		Class<?> sClass;
		Script script;
		try {
			sClass = c;
			script = (Script) sClass.newInstance();
			if (new SecurityScanner().isClean(sClass)){
				new GameCanvas(script);
				runScript(sClass, script);
				new PaintLoop().start();
			}else{
				log.show("Security Exception. For Class: " + sClass.getSimpleName(), Priority.SEVERE);
			}
		} catch (InstantiationException e) {
			log.show(e.toString(), Priority.SEVERE);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			log.show(e.toString(), Priority.SEVERE);
			e.printStackTrace();
		} catch (NullPointerException e){ // occurs if class file is deleted from directory
			log.show(e.toString(), Priority.SEVERE);
			e.printStackTrace();
		}
	}

	private void runScript(Class<?> sClass, Script script) {
		Timer tmrMain = new Timer();
		boolean start = false;

		if (!isRunning()){
			new LTM(script, sClass, tmrMain);
			log.show("'" + new ManRead().getScriptName() + "' has started.");
			start = script.onStart();
		}else{
			assert start = false;
		}

		if (start) { 
			TimerTask task = new LTM(script, sClass, tmrMain);

			tmrMain.scheduleAtFixedRate(task, 0, 1);			
		}else{
			Toolkit.getDefaultToolkit().beep();
			log.show("'" + new ManRead().getScriptName() + "' failed to start.", Priority.SEVERE);
			Toolkit.getDefaultToolkit().sync();
			LTM.stop();
			setErr("Error Loading.");
		}
	}

	private static class LTM extends TimerTask {
		private static Script script;
		private static Timer timer = null;
		private static Class<?> sClass;

		public LTM(Script script, Class<?> sClass, Timer timer){
			LTM.script = script;
			LTM.timer = timer;
			LTM.sClass = sClass;
		}
		public void run() {
			try{
				int val = script.loop();

				if (val == -1){
					script.onFinish();
					timer.cancel();
					timer = null;
					log.show("Script stopped.", Priority.SEVERE);
					setErr("Ready.");
				}else{
					Script.sleep(val);
				}
			}catch (Exception ex){
				setErr("Error thrown during execution.");
				log.show("Script error thrown during execution.", Priority.SEVERE);
				log.show(ex.toString(), Priority.SEVERE);
				timer.cancel();
				timer = null;
				
				// closeBackground(); TODO
			}
		}
		public static boolean isRunning(){
			return timer != null;
		}
		public static Script getScript(){
			return script;
		}
		public static Class<?> getScriptClass(){
			return sClass;
		}
		public static void stop(){
			if (timer != null){
				timer.cancel();
				timer = null;
			}
		}
	}
	

	private static void setErr(String message){
		client.setLMsgTxt(message);
	}

	public boolean isRunning(){ 
		return LTM.isRunning();
	}

	public void stop(){
		LTM.stop();
		LTM.script.onFinish();
		setErr("Ready.");
		log.show("Script stopped.", Priority.SEVERE);
	}

	public Script getScript(){
		return LTM.getScript();
	}

	public Class<?> getScriptClass(){
		return LTM.getScriptClass();
	}

}
