package com.keyholesoftware.troublemaker.client;

import org.springframework.beans.factory.annotation.Value;

import com.keyholesoftware.troublemaker.client.codeblock.CodeBlock;

public class TroubleMakerClientService {

	@Value("${troublemaker.client.token}")
	private String token;

	@Value("${troublemaker.client.kill:com.keyholesoftware.troublemaker.client.codeblock.KillBlock}")
	private String killClazz;
	@Value("${troublemaker.client.load:com.keyholesoftware.troublemaker.client.codeblock.LoadBlock}")
	private String loadClazz;
	@Value("${troublemaker.client.memory:com.keyholesoftware.troublemaker.client.codeblock.MemoryBlock}")
	private String memoryClazz;
	@Value("${troublemaker.client.exception:com.keyholesoftware.troublemaker.client.codeblock.ExceptionBlock}")
	private String exceptionClazz;

	public void killRequested(String requestToken) {
		this.spawnCodeBlockThread(requestToken, create(this.killClazz));
	}

	public void memoryRequested(String requestToken) {
		this.spawnCodeBlockThread(requestToken, create(this.memoryClazz));
	}

	public void loadRequested(String requestToken) {
		this.spawnCodeBlockThread(requestToken, create(this.loadClazz));
	}

	public void exceptionRequested(String requestToken) {
		this.spawnCodeBlockThread(requestToken, create(this.exceptionClazz));
	}

	private void spawnCodeBlockThread(final String requestToken, final CodeBlock block) {
		if (requestToken == null || this.token == null || !this.token.equals(requestToken)) {
			throw new RuntimeException("TroubleMaker client API denied: Invalid Token.");
		}

		Runnable run = new Runnable() {
			public void run() {
				try {
					block.eval();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		Thread thread = new Thread(run);
		thread.start();
	}

	private CodeBlock create(String clazzName) {
		CodeBlock codeBlock = null;
		try {
			codeBlock = (CodeBlock) Class.forName(clazzName).newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("ERROR Creating Trouble Maker Class " + clazzName + " Make sure it implements khs.trouble.sevlet.CodeBlock and is in classpath...");
		} catch (IllegalAccessException e) {
			throw new RuntimeException("ERROR Creating Trouble Maker Class " + clazzName + " Make sure it extends from khs.trouble.sevlet.BaseCodeBlock and is in classpath...");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ERROR Creating Trouble Maker Class " + clazzName + " Make sure it extends from khs.trouble.sevlet.BaseCodeBlock and is in classpath...");
		} catch (ClassCastException e) {
			throw new RuntimeException("ERROR Creating Trouble Maker Class " + clazzName + " Make sure it extends from khs.trouble.sevlet.BaseCodeBlock and is in classpath...");
		}
		return codeBlock;
	}
}
