package org.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

import org.util.io.Files;
import org.util.io.Stream;

public class Jar {

	public URL[] classpath() throws IOException {
		URL url = Jar.class.getResource(MF);
		String root = url.getFile().replace(MF, "");
		System.out.println(root);
		List<String> lines = Stream.readLine(url.openStream());
		boolean FLAG = false;
		StringBuffer sb = new StringBuffer();
		for (String line : lines) {
			if (line.startsWith(CLASS_PATH)) {
				FLAG = true;
				sb.append(line.substring(CLASS_PATH.length()).trim());
				continue;
			}
			if (FLAG) {
				if (line.indexOf(":") == -1) {
					sb.append(line.trim());
				} else {
					FLAG = false;
					break;
				}
			}
		}
		if ("".equals(sb.toString())) {
			return new URL[0];
		}

		String[] names = sb.toString().split(" ");
		URL[] urls = new URL[names.length];
		for (int i = 0; i < names.length; i++) {
			url = new URL(String.format("jar:%s/%s", root, names[i]));// "jar:" +
			File file = new File(names[i]);
			if (!file.exists()) {
				Files.create(names[i]);
			}
			Stream.save(url.openStream(), file);
			urls[i] = file.toURL();
		}

		return urls;
	}

	public static Object run(String className, String method, Object... args) throws Exception {
		System.out.println(className);
		Class clas = Class.forName(className);
		Object obj = clas.newInstance();
		return method(obj, method, args);
	}

	public static Object method(Object obj, String method, Object... args) throws Exception {
		Class[] clas = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			Class c = args[i].getClass();
			Class p = c.getSuperclass();
			clas[i] = p != Object.class ? p : c;
		}
		Method add = obj.getClass().getDeclaredMethod(method, clas);
		add.setAccessible(true);
		return add.invoke(obj, args);
	}

	private final String MF = "/META-INF/MANIFEST.MF";

	private final String CLASS_PATH = "Class-Path:";

	// private final Logger log = LoggerFactory.getLogger(this.getClass());

}
