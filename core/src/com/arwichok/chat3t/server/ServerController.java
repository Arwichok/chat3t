package com.arwichok.chat3t.server;



import com.badlogic.gdx.utils.Json;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.ServerSocket;
import com.arwichok.chat3t.MainController;
import com.arwichok.chat3t.Person;

public class ServerController implements Disposable{

	MainController controller;
	ServerSocket serverSocket;
	Socket socket;
	Server screen;
	Array<SocketThread> array;
	SocketHints socketHints;
	ServerSocketHints serverSocketHints;

	
	public ServerController(MainController game, Server scr){
		controller = game;
		screen = scr;
		array = new Array<SocketThread>(100);
		socketHints = new SocketHints();
		// socketHints.connectTimeout = 100000;
		serverSocketHints = new ServerSocketHints();
		serverSocketHints.acceptTimeout = 10000000;
		// serverSocketHints.performancePrefConnectionTime = 100000;

		newServer();
	}

	public void newServer(){

		if(serverSocket!=null){
			return;
		}

		serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP,
						controller.storage.getPort(), 
						serverSocketHints);

		controller.log("Server Start on port: " + controller.storage.getPort());

		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					while(true){

						Socket tmpSocket = ServerController.this.serverSocket.accept(socketHints);
						ServerController.this.addSocket(tmpSocket);
					}
				}catch(GdxRuntimeException except){
					controller.log("ServerController newServer Anon run" + except);
					ServerController.this.dispose();
				}
			}
		}).start();

	}

	@Override
	public void dispose(){
		if(serverSocket!=null)
			serverSocket.dispose();
	}

	private synchronized void addSocket(Socket socket){
		SocketThread tmpThread = new SocketThread(socket);
		tmpThread.start();
		array.add(tmpThread);
		updateCountUsers();
	}

	public class SocketThread extends Thread{
		Socket socket;
		BufferedReader in;
		OutputStream out;

		public SocketThread(Socket sock){
			socket = sock;	
		}
		@Override
		public void run(){
			try{
				in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "utf-8"));
				
				out = socket.getOutputStream();

				while(true){
					String tmpstr = in.readLine();
					if(tmpstr==null) {
						deleteSocketThread(this);
						break;
					}
					acceptMessage(tmpstr);
				}
			}catch(IOException ex){
				controller.log("SocketThread run: " + ex);
			}
		}

		public void toWrite(String str){
			try{
				out.write(new String(str+"\n").getBytes("utf-8"));
			}catch(IOException ex){
				controller.log("SocketThread toWrite: " + ex);
			}
		}
	}

	synchronized void acceptMessage(String str){
		

		Person person = new Json().fromJson(Person.class, str);
		String send;

		if(person.getMessage().equals("")){
			send = person.getName()+" came";
		}else{
			send = person.getName() + ": " + person.getMessage();
		}
		
		
		writeToAllUsers(send);
		screen.appendMessage(send);

	}
	void writeToAllUsers(String str){
		for(SocketThread st : array){
			st.toWrite(str);
		}
	}

	private void updateCountUsers(){
		controller.log(""+array.size);
		screen.setCountUsers(array.size);
	}
	private void deleteSocketThread(SocketThread st){
		array.removeValue(st, true);
		updateCountUsers();
	}
}