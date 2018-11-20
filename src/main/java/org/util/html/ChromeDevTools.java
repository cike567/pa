package org.util.html;

import java.util.Arrays;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class ChromeDevTools {
	private static Session session = null;
	static {
		Launcher launcher = new Launcher();
		SessionFactory factory = launcher.launch(Arrays.asList("--headless"));
		session = factory.create();
	}

	public static String html(String url) {
		session.navigate(url);
		session.waitDocumentReady();
		// String content = (String) session.getProperty("//body", "outerText");
		String content = session.getContent();
		return content;
	}

}
