package org.intracube.config;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;

/**
 * 
 * @author Aaron McClure
 *
 */
public class SecurityScanner implements ClientElements {

	public boolean isClean(Class<?> sClass){
		if (getClassString(sClass).toLowerCase().contains("java/lang/reflect") || getClassString(sClass).toLowerCase().contains("java/lang/runtime")){
			return false;
		}else if (getClassString(sClass).equals("null")){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isClean(Class<?> sClass, String...search){
		String terms[] = search;
		String classString = getClassString(sClass);
		for (int i=0; i<terms.length; i++){
			if (classString.toLowerCase().contains(terms[i])){
				return false;
			}
		}
		return true;	
	}
	
	public String getClassString(Class<?> sClass){
		try{
			String cName = sClass.getName();
			String cPath = cName.replace('.', '/') + ".class";
			InputStream stream = sClass.getClassLoader().getResourceAsStream(cPath);

			ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
			int next = stream.read();
			while (next > -1) {
				baoStream.write(next);
				next = stream.read();
			}
			baoStream.flush();
			
			byte[] clsByte = baoStream.toByteArray();
			String strClassCon = new String(clsByte);

			return strClassCon;
		}catch (Exception ex){
			log.show("Unable to complete security check.", Priority.SEVERE);
			log.show("Exception: " + ex.getLocalizedMessage(), Priority.SEVERE);
			return "null";
		}
	}
}