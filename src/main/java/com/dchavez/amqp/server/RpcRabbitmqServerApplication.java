package com.dchavez.amqp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

@SpringBootApplication
public class RpcRabbitmqServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcRabbitmqServerApplication.class, args);
	}
	

	@Bean
	public Queue queue() {
		return new Queue("tut.rpc.requests");
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange("tut.rpc");
	}

	@Bean
	public Binding binding(DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with("rpc");
	}

}
