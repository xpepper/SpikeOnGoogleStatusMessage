package com.sourcesense.spikes;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;

public class PresenceGoogle extends IQ {

	protected String userName;
	protected String status;
	protected boolean visible;
	protected Presence.Mode mode;

	/**
	 * Override toXML in the case of mode is away Because Google Talk interprets the 'away' status as idle, and idleness
	 * is a per-connection property, you cannot set an 'away' status using this method. To set an idle status message,
	 * send a standard stanza. :
	 */
	@Override
	public String toXML() {
		if (mode.equals(Presence.Mode.away)) {
			Presence p = new Presence(Presence.Type.available);
			p.setMode(mode);
			p.setStatus(status);
			return p.toXML();
		} else {
			return super.toXML();
		}
	}

	/**
	 * Constructor
	 * 
	 * @param userName
	 *            the userName
	 * @param status
	 *            the new status
	 * @param visible
	 *            the visible state > false to be invisble
	 */
	public PresenceGoogle(String userName, String status, boolean visible) {
		super();
		this.userName = userName;
		this.status = status;
		this.visible = visible;
		setType(IQ.Type.SET);

		// I am not sure this code must be test
		// in case of a domain different of gmail.com
		// using gmail.com...
		if (userName.indexOf("@") != -1) {
			setTo(userName + "");
		} else {
			setTo(userName + "@gmail.com");
		}

		mode = Presence.Mode.available;
	}

	/**
	 * Constructor
	 * 
	 * @param userName
	 *            the userName
	 * @param status
	 *            the new status
	 */
	public PresenceGoogle(String userName, String status) {
		this(userName, status, false);
	}

	/**
	 * Return the XML of changing status for google according
	 * http://code.google.com/apis/talk/jep_extensions/shared_status.html
	 */
	@Override
	public String getChildElementXML() {
		StringBuilder buf = new StringBuilder();

		// set query header
		buf.append("");
		// set status
		buf.append("").append(status).append("");
		// set mode
		buf.append("").append(mode).append("");
		// set invisible mode
		if (visible) {
			buf.append("");
		} else {
			buf.append("");
		}
		// close query
		buf.append("");

		return buf.toString();
	}

	public Presence.Mode getMode() {
		return mode;
	}

	public void setMode(Presence.Mode mode) {
		this.mode = mode;
	}

}
