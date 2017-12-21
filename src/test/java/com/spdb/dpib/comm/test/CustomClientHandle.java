package com.spdb.dpib.comm.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class CustomClientHandle {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("127.0.0.1",8848);
		socket.setSoTimeout(3 * 60 * 1000);
		InputStream input = socket.getInputStream();
		OutputStream output = socket.getOutputStream();
		byte[] testByte = new byte[5];
		testByte[0]='1';
		testByte[1]='2';
		testByte[2]='3';
		testByte[3]='4';
		output.write(testByte);
	
		byte[] testByte2 = new byte[2];
		input.read(testByte2);
		System.out.println("0="+testByte2[0]+"1="+testByte2[1]);
		socket.close();
	}
}
