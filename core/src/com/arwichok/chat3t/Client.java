package com.arwichok.chat3t;


import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.lang.Exception;
import java.lang.Thread;
import java.net.SocketTimeoutException;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.Gdx;

public class Client implements Disposable {
	private MainController controller;
	private Socket socket;
	String host = "localhost";
	int port = 8080;
	OutputStream out;
	BufferedReader in;

	public Client(MainController game){
		controller = game;
		
	}

	private class ClientInputThread extends Thread{
		BufferedReader in;
		Client client;

		ClientInputThread(Client client){
			this.client = client;
			in = client.in;
		}
		@Override
		public void run(){
			try{
				while(true){
					String tmpstr = in.readLine();
					if(tmpstr == null) {
						controller.log("ClientInputThread null");
						break;
					}
					client.acceptMessage(tmpstr);
				}
			}catch(IOException e){
				controller.log("Client ClientInputThread run: " + e);
				client.dispose();
				client.serverStoped();
			}
		}
	}

	public void newConnect(){

		host = controller.storage.getHost();
		port = controller.storage.getPort();

		try{

			socket = Gdx.net.newClientSocket(Net.Protocol.TCP, 
				host, 
				port, 
				new SocketHints());

			

			in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "utf-8"));
			out = socket.getOutputStream();

			ClientInputThread cit = new ClientInputThread(this);
			cit.start();

			if(socket.isConnected()) controller.serverConnected();

			controller.log("client after connect and in/out");

		}catch(GdxRuntimeException except){
			controller.serverNotFound();
			controller.log("Client newConnect: " + except);
		}catch(IOException e){
			controller.log("Client newConnect:" + e);

		}

		controller.log("client after catch");
	}

	@Override
	public void dispose(){
		if(socket != null)
			socket.dispose();
	}

	public void sendLogin(String name){
		controller.log(name);
	}

	private void writeToServer(String str){
		try{
			out.write(new String(str+"\n").getBytes("utf-8"));
		}catch(IOException e){
			controller.log("Client writeToServer" + e);
		}
	}

	synchronized private void acceptMessage(String str){
		controller.acceptMessage(str);
	}

	public void sendMessage(String str){
		writeToServer(str);
	}

	public void serverStoped(){
		String strServerStoped = "[#ff0000]ServerStoped[]";
		this.acceptMessage(strServerStoped);
		controller.log(strServerStoped);
	}
}