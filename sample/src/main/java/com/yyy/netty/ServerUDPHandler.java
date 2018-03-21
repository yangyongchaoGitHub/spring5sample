package com.yyy.netty;

import org.springframework.beans.factory.annotation.Autowired;

import com.yyy.model.domain.User;
import com.yyy.service.UserService;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

public class ServerUDPHandler extends ChannelInboundHandlerAdapter {

	@Autowired
	UserService userService;

	public ServerUDPHandler() {
		super();
		// System.out.println("ServerUDPHandler create");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof DatagramPacket) {
			DatagramPacket packet = (DatagramPacket) msg;
			ByteBuf bbf = packet.content();
			
			System.out.println("ServerUDPHandler channelRead ctx.channel " + bbf.readableBytes());
			User user = new User();
			user.setUserName(bbf.readableBytes() + " " + System.currentTimeMillis());
			userService.addUser(user);
		}

		super.channelRead(ctx, msg);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("ServerUDPHandler exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}
}
