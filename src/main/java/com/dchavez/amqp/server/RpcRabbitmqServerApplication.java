package com.dchavez.amqp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@SpringBootApplication
public class RpcRabbitmqServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcRabbitmqServerApplication.class, args);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

//	@Bean
//	public Queue queue() {
//		return new Queue("tut.rpc.requests");
//	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange("tut.rpc");
	}

	@Bean
	public Binding binding(DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with("rpc");
	}
	
	@Bean
	public Queue queue() {
	    return QueueBuilder.durable("tut.rpc.requests")
	      //.withArgument("x-message-ttl", 3000)
	      .withArgument("x-dead-letter-exchange", "")
	      .withArgument("x-dead-letter-routing-key", "tut.rpc.requests.dlq")
	      .ttl(3000)
	      .build();
	}
	  
	@Bean
	Queue deadLetterQueue() {
	    return QueueBuilder.durable("tut.rpc.requests.dlq").build();
	}

}
