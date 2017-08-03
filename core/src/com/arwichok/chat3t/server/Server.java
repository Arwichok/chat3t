package com.arwichok.chat3t.server;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.ScreenAdapter;
import com.arwichok.chat3t.MainController;
import com.arwichok.chat3t.StandartScreen;
import com.arwichok.chat3t.ScreenEnum;


public class Server extends StandartScreen{
	MainController controller;
	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton startStopServerButton;
	Label countUsers;
	Label serverMessage;
	private ScrollPane serverScrollPane;
	ServerController serverController;
	private boolean startStopValue;

	public Server(MainController game){
		controller = game;

		skin = controller.skin;

		startStopValue = false;

		stage = new Stage(controller.viewport);
		Gdx.input.setInputProcessor(stage);
		table = new Table();
		countUsers = new Label("0\n user ", skin);
		startStopServerButton = new TextButton("Start/Stop \n Server", skin);
		serverMessage = new Label("", skin, "avdira-30", "white");
		serverScrollPane = new ScrollPane(serverMessage, controller.skin);



	}

	@Override
	public void show(){
		stage.addActor(table);
		table.setFillParent(true);
		// stage.setDebugAll(true);
		countUsers.setAlignment(Align.center);



		table.add(serverScrollPane).prefWidth(999).prefHeight(999).colspan(2);
		table.row();
		table.add(startStopServerButton).prefWidth(999);
		table.add(countUsers);

		startStopServerButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(serverController==null || !startStopValue){
					startServer();
				}else if(startStopValue){
					// stopServer();
					Server.this.restartServer();
				}
				// startStopServerButton.setDisabled(true);
			}
		});


	}

	@Override
	public void render(float delta){
		stage.act(delta);
		Gdx.gl.glClearColor(0, 0.3f, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
 
	}

	@Override
	public void backMenu(){
		controller.setMenuScreen(ScreenEnum.SETTINGS);
	}

	public void hide(){
		// dispose();
	}

	@Override
	public void dispose(){
		stopServer();
	}

	void startServer(){
		startStopServerButton.setText("Restart \nserver");
		startStopValue = true;
		serverController = new ServerController(controller, this);
	}

	void stopServer(){
		startStopServerButton.setText("Start \nserver");
		startStopValue = false;
		serverController.dispose();
	}

	public void appendMessage(String str){
		serverMessage.setText(str + "\n" + 
			serverMessage.getText());
	}

	public void setCountUsers(int i){
		countUsers.setText(i+"\nusers");
	}

	public void restartServer(){
		serverController.dispose();
		serverController = new ServerController(controller, this);
	}
}