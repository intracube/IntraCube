package org.intracube.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;

public class ClassImport {

	public ArrayList<Class<?>> getClasses(){
		File folder = getScriptSourceDirectory();

		ArrayList<Class<?>> classList = new ArrayList<Class<?>>();

		try {
			if (folder.exists()) {
				ArrayList<File> paths = new ArrayList<File>();
				ArrayList<String> cStrs = new ArrayList<String>();
				paths.add(folder);

				for (File ff : paths.get(0).listFiles()) {
					if (!ff.isDirectory() && ff.getName().endsWith(".class")) {
						cStrs.add(ff.getName().substring(0, ff.getName().indexOf(".class")));
					}
				}

				ClassLoader cl = new URLClassLoader(new URL[]{folder.toURI().toURL()});
				for (String s : cStrs) {
					try {
						classList.add(cl.loadClass(s));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	public File getScriptSourceDirectory(){
		String os = System.getProperty("os.name");
		String docDir = "";
		if (os.toLowerCase().contains("mac")) {
			docDir = System.getProperty("user.home") + File.separator + "Documents";
		}else if (os.toLowerCase().contains("windows")) {
			docDir = System.getProperty("user.home") + File.separator + "My Documents";
		}else if (os.toLowerCase().contains("linux")) {
			docDir = System.getProperty("user.home");
		} else {
			docDir = System.getProperty("user.home");
		}
		return new File(docDir + File.separator + "IntraCube Scripts" + File.separator + "Scripts" + File.separator + "Sources");	
	}
	
	public ArrayList<String> getScriptNames(){
		ArrayList<String> names = new ArrayList<String>();
		
		for (int i=0; i<getClasses().size(); i++){
			names.add(getClasses().get(i).getName());
		}
		
		return names;
	}
	
	public Hashtable<String, Class<?>> getHashtable(){
		ArrayList<Class<?>> classes = getClasses();
		ArrayList<String> names = getScriptNames();
		
		Hashtable<String, Class<?>> table = new Hashtable<String, Class<?>>();
		
		for (int i=0; i<names.size(); i++){
			table.put(names.get(i), classes.get(i));
		}
		
		return table;
	}
}
