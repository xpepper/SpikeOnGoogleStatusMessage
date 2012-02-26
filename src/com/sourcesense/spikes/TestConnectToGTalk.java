package com.sourcesense.spikes;

import java.util.HashSet;
import java.util.Set;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestConnectToGTalk {

	private static final String USERNAME = "teststatusmessage@gmail.com";
	private static final String PASSWORD = "TestStatusMessage1234";

	private XMPPConnection connection;

	@Before
	public void createConnection() throws Exception {
		// XMPPConnection.DEBUG_ENABLED = true;
		connection = new XMPPConnection("gmail.com");
		connection.connect();
	}

	@Test (timeout=10000)
	public void testConnection() throws Exception {
		assertFalse(connection.isAuthenticated());

		connection.login(USERNAME, PASSWORD);

		assertTrue(connection.isConnected());
		assertTrue(connection.isAuthenticated());
		assertTrue(connection.isSecureConnection());
	}

	@Test (timeout=10000)
	public void testStatusMessage() throws Exception {
		connection.login(USERNAME, PASSWORD);
//		connection.addPacketListener(new LoggingPacketLister(), new AlwaysAcceptingPacketFilter());
		EchoingPacketLister packetLister = new EchoingPacketLister();
		connection.addPacketWriterListener(packetLister, new AlwaysAcceptingPacketFilter());
		
		Presence presence = new Presence(Presence.Type.available);
		presence.setMode(Presence.Mode.available);
		presence.setStatus("Hello Friends!");
		presence.setPriority(24);
		
		connection.sendPacket(presence);
		
		Thread.sleep(2000);
		
		assertFalse(packetLister.allReceivedPackets().isEmpty());
		System.out.println(presence);
		System.out.println(packetLister.allReceivedPackets());
		
		Presence expectedPresence = new Presence(Presence.Type.available);
		expectedPresence.setMode(Presence.Mode.available);
		expectedPresence.setStatus("Hello Friends!");
		assertTrue(packetLister.allReceivedPackets().contains(expectedPresence));
	}

	@After
	public void disconnect() {
		if (connection != null)
			connection.disconnect();
	}
	
	private final class EchoingPacketLister implements PacketListener {
		
		private Set<Packet> receivedPacketes;

		public EchoingPacketLister() {
			receivedPacketes = new HashSet<Packet>();
		}
		
		public void processPacket(Packet aPacket) {
			receivedPacketes.add(aPacket);
		}
		
		public Set<Packet> allReceivedPackets()
		{
			return receivedPacketes;
		}
	}
	
	private final class LoggingPacketLister implements PacketListener {
		public void processPacket(Packet aPacket) {
			System.out.println(aPacket.toXML());

		}
	}

	private final class AlwaysAcceptingPacketFilter implements PacketFilter {
		public boolean accept(Packet aPacket) {
			return true;
		}
	}
}
