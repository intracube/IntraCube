package org.intracube.config;

import java.lang.annotation.Annotation;

import org.intracube.api.manifest.ScriptManifest;
import org.intracube.client.MainDriver;

/**
 * 
 * @author Aaron McClure
 * IntraCube
 *
 */
public class ManRead {

	public ScriptManifest getAnnotation(Class<?> sClass){
		Annotation[] annotations = sClass.getAnnotations();

		for(Annotation annotation : annotations){
			if(annotation instanceof ScriptManifest){
				return (ScriptManifest) annotation;
			}
		}
		return null;
	}

	private Class<?> getDrivC(){
		return new MainDriver().getScriptClass();
	}

	public String getScriptName(){
		if (getAnnotation(getDrivC()).name() != null){
			return getAnnotation(getDrivC()).name();
		}else{
			return "null";
		}
	}

	public String[] getAuthors(){
		return getAnnotation(getDrivC()).authors();
	}

	public double getVersion(){
		return getAnnotation(getDrivC()).version();
	}

	public String getDescription(){
		return getAnnotation(getDrivC()).description();
	}

	public String getWebsite(){
		return getAnnotation(getDrivC()).website();
	}
}
