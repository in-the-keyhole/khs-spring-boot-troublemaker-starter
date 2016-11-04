# khs-spring-boot-troublemaker-starter
A Spring Boot starter for autoconfiguration of the TroubleMaker Client.

This autoconfiguration can be used to configure clients of the [Trouble Maker](https://github.com/in-the-keyhole/khs-trouble-maker) framework used to randomly produce server trouble issues.

Installation
------------
Add this dependency to your pom.xml:

	<dependency>
		<groupId>com.keyholesoftware</groupId>
		<artifactId>khs-spring-boot-troublemaker-starter</artifactId>
		<version>1.0.0</version>
	</dependency>	

Annotate your Spring Boot main class:

	@SpringBootApplication
	@EnableTroubleMaker
	public class MySpringBootApp {

		public static void main(String[] args) {
			SpringApplication.run(MySpringBootApp.class, args);
		}
	}

Configure an access token:

using properties:

	troublemaker.client.token=abc123

or yaml:

	troublemaker:
		client:
			token: abc123

Actions
-------	
By default, Trouble Maker actions will be performed by calling the following APIs. A matching access token is required to be provided in the `token` request header. The Trouble Maker [dashboard](https://github.com/in-the-keyhole/khs-trouble-maker) will invoke these APIs. 

`http://<server>/trouble/kill` - Kills the service with a System.exit() command. 

`http://<server>/trouble/memory` - Executes a thread that fills up heap memory and keeps it there for the timeout period.

`http://<server>/trouble/load` - Spawns specified number of threads the block for the the timeout period.

`http://<server>/trouble/exception` - Throws an exception to validate exception handling behavior of a service.


Defining Custom Actions Code Blocks
-----------------------------------
If you want to apply your own trouble actions and override the supplied defaults, you can create a class that extends from this supplied abstract class, as shown below. 

	package com.mycode;
  
	public class MyKillCodeBlock extends BaseCodeBlock {	
	
		public KillBlock() {
	  		super();
		}
    
		@Override
		public void eval() {
			// Do stuff to kill this process...like..
			System.exit(-1)
		}	
	}

Then you will need to register the custom block. The example below shows how a custom load action is registered:
 
using properties:

	troublemaker.client.kill=com.mycode.MyKillCodeBlock
  
or yaml:

	troublemaker:
		client:
			kill: com.mycode.MyKillCodeBlock
  
The complete list of operation names that can be overridden are:
* kill
* load
* memory
* exception 
