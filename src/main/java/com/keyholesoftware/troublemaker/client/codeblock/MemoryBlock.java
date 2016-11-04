/*
 * Copyright 2015 Keyhole Software LLC.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.keyholesoftware.troublemaker.client.codeblock;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryBlock extends BaseCodeBlock {

	private static final Logger LOG = LoggerFactory.getLogger(MemoryBlock.class);
	
	public MemoryBlock() {
		this(0);
	}

	public MemoryBlock(long timeout) {
		super(timeout);
	}

	@Override
	public void eval() {
		long memory = Runtime.getRuntime().freeMemory();
		LOG.info("TroubleMaker client: Memory requested.");
		LOG.info("Eating Memory - started at: " + memory);

		List<char[]> buffer = new ArrayList<char[]>();

		while (true) {
			try {
				char[] c = new char[Integer.MAX_VALUE / 4];
				for (int i = 0; i < Integer.MAX_VALUE / 4; i++) {
					c[i] = (char) i;

					if (i % 1000000 == 0) {
						LOG.info("Memory Eaten -" + (Runtime.getRuntime().freeMemory() - memory));
					}
					buffer.add(c);
				}
			} catch (OutOfMemoryError e) {
				long start = System.currentTimeMillis();
				String msg = timeout == 0 ? "NEVER" : "" + (timeout / 60000) + " minute(s)";
				LOG.info("Heap MEMORY limit reached...will stay in low memory condition for " + msg);
				while (true) {
					try {
						long sleep = 1000;
						if (System.currentTimeMillis() - start >= timeout && timeout > 0) {
							buffer = null;
							break;
						}
						Thread.sleep(sleep);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				buffer = null;
				LOG.info("Memory Consumption stopped");

				Thread.currentThread().stop();
			}
		}
	}
}
