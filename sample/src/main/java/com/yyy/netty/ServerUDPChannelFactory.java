package com.yyy.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ServerUDPChannelFactory {

	protected static Channel createAcceptorChannel(InetSocketAddress address, final ServerUDPHandler handle) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bs = new Bootstrap();
		
		bs.group(group)
		.channel(NioDatagramChannel.class)
		.option(ChannelOption.SO_REUSEADDR, false)
		.handler(new ChannelInitializer<DatagramChannel>() {

			@Override
			protected void initChannel(DatagramChannel ch) throws Exception {
				final ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("handler", handle);
			}
		});
		
		System.out.println("create udp channel!!!");
		
		try {
			ChannelFuture future = bs.bind(address).sync();
			
			future.awaitUninterruptibly();
			if (future.isSuccess()) {
				return future.channel();
			}
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
