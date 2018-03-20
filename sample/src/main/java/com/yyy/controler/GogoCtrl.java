package com.yyy.controler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.aspectj.weaver.reflect.ReflectionBasedResolvedMemberImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yyy.activemq.consumer.ConsumerService;
import com.yyy.activemq.producer.ProducerService;
import com.yyy.model.TestModel;
import com.yyy.model.domain.User;
import com.yyy.service.UserService;

/**
 * 
 * @author Administrator
 * 这里也可以注解为 @RestController 但是就不能返回指定的显示界面只能返回内容
 * 若用 @Controller 可以返回指定的配置界面  同时在function加上@ResponseBody就可以返回内容
 */
@Controller
public class GogoCtrl {

	private static final String defName = "no good , %s!";
	private final AtomicLong id = new AtomicLong();
	
	@Autowired
	UserService userService;

	@Resource(name = "demoQueueDestination")
	private Destination demoQueueDestination;

	@Resource(name = "producerService")
	private ProducerService producer;

	@Resource(name = "consumerService")
	private ConsumerService consumer;
	
	@Resource(name = "mqtt")
	private MqttPahoMessageHandler mqttss;

	@RequestMapping("/test")
	@ResponseBody
	public User test(@RequestParam(value="name", defaultValue="yyyyyyy") String name) {
		User user = new User();
		user.setUserName("first");
		userService.addUser(user);
		
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		return user;
	}
	
	@RequestMapping("/getAll")
	public List<User> getAll() {
		return userService.getAllUser();
	}
	
	@RequestMapping("/page")
	public String jump() {
		return "result";
	}
	
	@RequestMapping(value="/producer", method = RequestMethod.GET)
	@ResponseBody
	public String producer() {
		System.out.println("ctrl producer " );
		return "producer";
	}
	
	@RequestMapping(value="/send")
	@ResponseBody
	public String producer(@RequestParam(value="msg", defaultValue="y") String msg) {
		System.out.println("ctrl send " );
		producer.sendMessage(msg);
		return "send end : " + msg;
	}
	
	@RequestMapping(value="/receive")
	@ResponseBody
	public String receiver() throws JMSException {
		System.out.println("ctrl receiver ");
		TextMessage message = consumer.receive(demoQueueDestination);
		
		if (message == null) {
			return "queue is empty";
		} else {
			return message.getText();
		}
	}
	
	
	public void jmsManager() throws IOException {
		System.out.println("--------------jms manager");
		
		JMXServiceURL url = new JMXServiceURL("");

		JMXConnector connector = JMXConnectorFactory.connect(url);
		connector.connect();
		MBeanServerConnection connection = connector.getMBeanServerConnection();
	}
	
	@RequestMapping(value="/mqtt/send")
	@ResponseBody
	public void sendMqtt() {
		Message<String> message = MessageBuilder.withPayload("999999999999999999999").setHeader(MqttHeaders.TOPIC, "robot_server").build();
		mqttss.handleMessage(message);
		System.out.println("mqtt send end");
	}
}
