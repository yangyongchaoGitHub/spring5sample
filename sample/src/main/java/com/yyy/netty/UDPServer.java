package com.yyy.netty;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

public class UDPServer {
	private InetSocketAddress address;
	private Channel acceptorChannel;
	
	@Resource
	ServerUDPHandler udpHandler;
	
	public UDPServer(int port) {
		this.address = new InetSocketAddress(port);
	}
	
	@PostConstruct
	public void start() {
		acceptorChannel = ServerUDPChannelFactory.createAcceptorChannel(address, udpHandler);
		System.out.println("start end " + acceptorChannel);
	}
	
	public void stop() {
		if (acceptorChannel != null) {
			acceptorChannel.close().addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	public void restart() {
		stop();
		start();
	}
}
