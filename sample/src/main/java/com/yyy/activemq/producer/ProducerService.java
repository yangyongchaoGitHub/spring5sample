package com.yyy.activemq.producer;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

	@Resource(name="jmsTemplate")
	private JmsTemplate template;
	
	public void sendMessage(Destination destination, final String msg) {
		System.out.println("ProduceService sendMessage des " + destination.toString() + " msg " + msg);
		MessageCreator messageCreator = new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(msg);
			}
		};
		
		template.send(destination, messageCreator);
	}
	
	public void sendMessage(final String msg) {
		
		String dst = template.getDefaultDestinationName();
		System.out.println("ProduceService sendMessage dst = " + dst + " msg " + msg);
		MessageCreator messageCreator = new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				return session.createTextMessage(msg);
			}
		};
		
		template.send(messageCreator);
	}
}
