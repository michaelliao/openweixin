package com.itranswarp.wxapi.sample;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Run embedded jetty.
 * 
 * @author michael
 */
public class App {

	public static void main(String[] args) throws Exception {
		final int port = parsePort(2016, args);
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar("src/main/webapp");
		Server server = new Server(port);
		server.setHandler(webapp);
		server.start();
		System.out.println("Server started at port " + port + "...");
		server.join();
	}

	static int parsePort(int defaultPort, String[] args) {
		for (String arg : args) {
			if (arg.startsWith("--port=")) {
				return Integer.parseInt(arg.substring(7));
			}
		}
		return defaultPort;
	}
}
