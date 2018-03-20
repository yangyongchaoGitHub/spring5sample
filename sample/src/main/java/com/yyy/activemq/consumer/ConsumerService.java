package com.yyy.activemq.consumer;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

	@Resource(name="jmsTemplate")
	private JmsTemplate template;
	
	public TextMessage receive(Destination destination) {
		TextMessage tm = (TextMessage) template.receive(destination);
		if (tm != null) {
			try {
				System.out.println("ConsumerService tm " + tm.getText() + " des" + destination.toString());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return tm;
	}
}
