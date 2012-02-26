package com.sourcesense.spikes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class SetStatusMessage {
	public static XMPPConnection getConn() throws XMPPException {
		String login = "sandeepxxxxgiri@gmail.com";
		String pass = "xxxx";

		return getConnection(login, pass);
	}

	private static XMPPConnection getConnection(String login, String pass) throws XMPPException {
		XMPPConnection connection = new XMPPConnection("gmail.com");
		connection.connect();
		connection.login(login, pass);
		
		Presence presence = new Presence(Presence.Type.available);
		presence.setStatus("Hello Friends!");
		presence.setPriority(24);
		presence.setMode(Presence.Mode.available);
		
		connection.sendPacket(presence);
		return connection;
	}

	public static void setStatus(XMPPConnection connection, String msg) {
//		PresenceGoogle pg = new PresenceGoogle("pierodibello", msg, true);
//		connection.sendPacket(pg);
	}

	public static void setStatus(String login, String pass, String msg) throws XMPPException {
		XMPPConnection connection = getConnection(login, pass);
		setStatus(connection, msg);
	}

	private static String readStdIn() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		StringBuilder input = new StringBuilder();
		String str;
		while ((str = br.readLine()) != null) {
			input.append(str);
		}
		return input.toString();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, XMPPException {
		String login;
		String pass;

		if (args.length == 2) {
			login = args[0];
			pass = args[1];
		} else if (args.length == 0) {
			String userHome = System.getProperty("user.home");
			if (userHome == null) {
				throw new IllegalStateException("user.home==null");
			}
			Properties props = new Properties();
			props.load(new FileInputStream(userHome + "/.setstatus.properties"));
			login = props.getProperty("login");
			pass = props.getProperty("password");
		} else {
			System.out
					.println("Usage: \n 1. setStatusMessage \n\t Sets the status message using the login and password.\n 2. setStatusMessage \n\t Reads login and password from .setstatus.properties");
			return;
		}
//		String quote = readStdIn();
		setStatus(login, pass, "quote");
	}
}
