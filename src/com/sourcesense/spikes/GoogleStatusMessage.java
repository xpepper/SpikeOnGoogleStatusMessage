package com.sourcesense.spikes;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

public class GoogleStatusMessage {
	public static void main(String[] args) {
		XMPPConnection connection = new XMPPConnection("gmail.com");
		try {
			connection.connect();
			connection.login("pierodibello@gmail.com", "xxxxx");
			
			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus("Hello Friends!");
			presence.setPriority(24);
			presence.setMode(Presence.Mode.available);
			connection.sendPacket(presence);
//			Thread.sleep(30000); // Sleeps for 30 seconds
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private static String rotate(String input) {
		return input.substring(1) + input.charAt(0);
	}

}
