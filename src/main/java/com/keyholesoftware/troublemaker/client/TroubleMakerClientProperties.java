package com.keyholesoftware.troublemaker.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("troublemaker.client")
public class TroubleMakerClientProperties {

	// Token - required
	private String token;

	// CodeBlock class name for each endpoint
	private String kill;
	private String load;
	private String memory;
	private String exception;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getKill() {
		return kill;
	}

	public void setKill(String kill) {
		this.kill = kill;
	}
}
