package com.romejanic.ddm.update;

public class UpdateStatus {

	// all states
	public final UpdateState state;
	
	// out-of-date only
	public final String latestVersion;
	public final String latestURL;
	public final String[] changelog;
	public final boolean urgent;
	
	// failed only
	public final String error;

	public UpdateStatus() {
		this(UpdateState.UP_TO_DATE, null, null, null, null, false);
	}
	
	public UpdateStatus(String latestVersion, String latestURL, String[] changelog, boolean urgent) {
		this(UpdateState.OUT_OF_DATE, latestVersion, latestURL, changelog, null, urgent);
	}
	
	public UpdateStatus(String error) {
		this(UpdateState.FAILED, null, null, null, error, false);
	}
	
	private UpdateStatus(UpdateState state, String latestVersion, String latestURL, String[] changelog, String error, boolean urgent) {
		this.state = state;
		this.latestVersion = latestVersion;
		this.latestURL = latestURL;
		this.changelog = changelog;
		this.error = error;
		this.urgent = urgent;
	}
	
	public boolean isOutdated() {
		return !didFail() && this.state == UpdateState.OUT_OF_DATE;
	}
	
	public boolean didFail() {
		return this.state == UpdateState.FAILED;
	}
	
}