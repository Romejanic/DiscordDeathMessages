package com.romejanic.ddm.update;

public class UpdateStatus {

	// all states
	public final UpdateState state;
	
	// out-of-date only
	public final String latestVersion;
	public final String latestURL;
	public final String[] changelog;
	
	// failed only
	public final String error;

	public UpdateStatus() {
		this(UpdateState.UP_TO_DATE, null, null, null, null);
	}
	
	public UpdateStatus(String latestVersion, String latestURL, String[] changelog) {
		this(UpdateState.OUT_OF_DATE, latestVersion, latestURL, changelog, null);
	}
	
	public UpdateStatus(String error) {
		this(UpdateState.FAILED, null, null, null, error);
	}
	
	private UpdateStatus(UpdateState state, String latestVersion, String latestURL, String[] changelog, String error) {
		this.state = state;
		this.latestVersion = latestVersion;
		this.latestURL = latestURL;
		this.changelog = changelog;
		this.error = error;
	}
	
	public boolean isOutdated() {
		return !didFail() && this.state == UpdateState.OUT_OF_DATE;
	}
	
	public boolean didFail() {
		return this.state == UpdateState.FAILED;
	}
	
}