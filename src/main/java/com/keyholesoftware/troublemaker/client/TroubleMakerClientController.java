package com.keyholesoftware.troublemaker.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trouble")
public class TroubleMakerClientController {

	private static final Logger LOG = LoggerFactory.getLogger(TroubleMakerClientController.class);

	@Autowired
	private TroubleMakerClientService service;

	public TroubleMakerClientController() {
		super();
	}

	@RequestMapping(value = "/kill", method = RequestMethod.GET)
	public void kill(@RequestHeader("token") String requestToken) {
		LOG.info("/kill requested by the TroubleMaker");
		service.killRequested(requestToken);
	}

	@RequestMapping(value = "/memory", method = RequestMethod.GET)
	public void memory(@RequestHeader("token") String requestToken) {
		LOG.info("/memory requested by the TroubleMaker");
		service.memoryRequested(requestToken);
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public void load(@RequestHeader("token") String requestToken) {
		LOG.info("/load requested by the TroubleMaker");
		service.loadRequested(requestToken);
	}

	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	public void exception(@RequestHeader("token") String requestToken) {
		LOG.info("/exception requested by the TroubleMaker");
		service.exceptionRequested(requestToken);
	}
}
