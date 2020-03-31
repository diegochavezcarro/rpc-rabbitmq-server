package com.dchavez.amqp.server.services;

import org.springframework.stereotype.Service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RpcServer {

	private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
	@RabbitListener(queues = "tut.rpc.requests")
	// @SendTo("tut.rpc.replies") used when the client doesn't set replyTo.
	public int fibonacci(int n) {
		logger.debug(" [x] Received request for " + n);
		int result = fib(n);
		logger.debug(" [.] Returned " + result);
		return result;
	}

	public int fib(int n) {
		return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
	}
	
}
