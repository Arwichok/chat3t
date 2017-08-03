package com.arwichok.chat3t;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.arwichok.chat3t.server.ServerController;
import com.arwichok.chat3t.server.Server;

public class MainController extends Game{

	public OrthographicCamera camera;
	public ScreenViewport viewport;
	public Storage storage;
	public Skin skin;
	private StandartScreen settingsScreen, serverScreen;
	private LightLogin loginScreen;
	public Client client;
	private ServerController server;
	private StandartScreen currentScreen;
	Label currentChatLabel;
	private String login;
	



	@Override
	public void create () {



		Gdx.input.setCatchBackKey(true);

		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);

		storage = new Storage(this);
		client = new Client(this);

		skin = storage.getSkin();

		setMenuScreen(ScreenEnum.LOGIN);
	}

	@Override
	public void render () {
		super.render();

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ||
			Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
			if(this.getStandartScreen() != null){
				this.getStandartScreen().backMenu();
			}
		}
	}

	@Override
	public void resize(int windowWidth, int windowHeight){
		camera.setToOrtho(false, windowWidth, windowHeight);
		camera.viewportWidth = windowWidth;
		camera.viewportHeight = windowHeight;
		currentScreen.resize(windowWidth, windowHeight);
		camera.update();
	}

	@Override
	public void dispose () {
		skin.dispose();
		client.dispose();
		storage.dispose();
	}

	public boolean isNamePasswordTrue(String name, String pass){
		return false;
	}

	public void sendLogin(String name, LightLogin loginScreen){
		// startClientConnect();
		// client.newConnect(mainHosting, mainPort);
		// client.sendLogin(name);
		login = name;
		this.loginScreen = loginScreen;
		client.newConnect();

	}

	public synchronized void log(Object str){
		Gdx.app.log("chat3t", str.toString());
	}

	public StandartScreen getStandartScreen(){
		return currentScreen;
	}

	public void setMenuScreen(ScreenEnum menu){

		switch(menu){
			case LOGIN:
				currentScreen = new LightLogin(this);
				break;
			case SERVER:
				currentScreen = new Server(this);
				break;
			case SETTINGS:
				currentScreen = new Settings(this);
				break;
			case MAINMENU:
				currentScreen = new MainMenu(this);
				break;
			case CHAT:
				currentScreen = new Chat(this);
		}
		this.setScreen(currentScreen);
	}

	public void sendMessage(String mess){
		Person person = new Person();
		person.setType("Chat");
		person.setName(login);
		person.setMessage(mess);

		client.sendMessage(new Json().toJson(person));
	}

	public void setCurrentLabel(Label l){
		currentChatLabel = l;
	}

	public void acceptMessage(String str){
		currentChatLabel.setText(str+"\n"+
			currentChatLabel.getText());
	}
	public void serverNotFound(){
		loginScreen.serverNotFound();
	}
	public String fromArrayToString(String[] arr){
		String tmp = "";
		for(String s : arr){
			tmp+=s;
		}
		return tmp;
	}

	public void serverConnected(){
		log("serverConnected");
		setMenuScreen(ScreenEnum.MAINMENU);
		// sendMessage("");
	}

}
