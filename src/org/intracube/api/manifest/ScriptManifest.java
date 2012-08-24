package org.intracube.api.manifest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface ScriptManifest {

	String name() default "ScriptName";
	
	String[] authors() default "author(s)";

	double version() default 1.0;

	String description() default "description";	

	String website() default "website";
}
