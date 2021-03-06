package com.dchavez.amqp.server.services;

import org.springframework.stereotype.Service;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RpcServer {
	
//	@Autowired
//	private RabbitTemplate template;

	private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
	@RabbitListener(queues = "tut.rpc.requests")
	// @SendTo("tut.rpc.replies") used when the client doesn't set replyTo.
	public int fibonacci(int n) {
		if (n>43)
			throw new AmqpRejectAndDontRequeueException("se rechaza!");
		logger.debug(" [x] Received request for " + n);
		int result = fib(n);
		logger.debug(" [.] Returned " + result);
		return result;
	}

	public int fib(int n) {
		return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
	}
	
}
