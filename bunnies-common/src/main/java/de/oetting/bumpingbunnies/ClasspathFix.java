package de.oetting.bumpingbunnies;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class ClasspathFix {

	public static void addToClasspathPath(String s) throws Exception {
		File f = new File(s);
		URI u = f.toURI();
		URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> urlClass = URLClassLoader.class;
		Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(urlClassLoader, new Object[] { u.toURL() });
	}

}
