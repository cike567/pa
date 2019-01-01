package org.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

import org.util.io.Files;
import org.util.io.Stream;

public class Jar extends URLClassLoader {

	public Jar() throws IOException, InterruptedException {
		this(classpath(), Thread.currentThread().getContextClassLoader());// Thread.currentThread().getContextClassLoader()
	}

	public Jar(URL[] urls, ClassLoader parent) throws InterruptedException, IOException {
		super(urls, parent);
		load(urls);
	}

	public static URL[] classpath() throws IOException {
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

			url = new URL("jar:" + root + "/" + names[i]);
			File file = new File(names[i]);
			if (!file.exists()) {
				Files.create(names[i]);
			}
			Stream.save(url.openStream(), file);
			urls[i] = file.toURL();
		}

		return urls;
	}

	/*
	 * public void load() throws IOException { //
	 * JarFile.registerUrlProtocolHandler(); URL[] urls = classpath();
	 * Arrays.asList(urls).stream().forEach((url) -> { try { method(this, "addURL",
	 * url); } catch (Exception e) { e.printStackTrace(); } });
	 * 
	 * }
	 */

	public void load(URL[] urls) {
		Arrays.asList(urls).stream().forEach((url) -> {
			try {
				System.out.println(String.format("addURL(%s)", url));
				method(this, "addURL", url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		try {
			Class clas = this.findClass("javax/servlet/Servlet".replaceAll("/", "."));
			System.out.println(clas);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void addURL(URL url) {
		// TODO Auto-generated method stub
		super.addURL(url);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.findClass(name);
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
			clas[i] = args[i].getClass();
		}
		Method add = obj.getClass().getDeclaredMethod(method, clas);
		add.setAccessible(true);
		return add.invoke(obj, args);
	}

	private final static String MF = "/META-INF/MANIFEST.MF";

	private final static String CLASS_PATH = "Class-Path:";

	// private final Logger log = LoggerFactory.getLogger(this.getClass());

}
