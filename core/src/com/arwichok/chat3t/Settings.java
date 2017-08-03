package com.arwichok.chat3t;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Game;
import com.arwichok.chat3t.server.Server;

public class Settings extends StandartScreen{
	MainController controller;
	Stage stage;
	Table table;
	Label label;
	Skin skin;
	TextField hostTextField, portTextField;
	TextButton createServer;


	public Settings(MainController game){
		controller = game;
		skin = controller.skin;

		stage = new Stage(controller.viewport);
		Gdx.input.setInputProcessor(stage);
		table = new Table();
		label = new Label("Settings", skin);
		hostTextField = new TextField("", skin);
		portTextField = new TextField("", skin);
		createServer = new TextButton("Create Server", skin); 


		


	}

	@Override
	public void show(){
		stage.addActor(table);
		table.setFillParent(true);
		// stage.setDebugAll(true);

		label.setAlignment(Align.center);
		label.setFontScale(1f);
		hostTextField.setAlignment(Align.center);
		portTextField.setAlignment(Align.center);
		portTextField.setMaxLength(6);	

		hostTextField.setMessageText("ip");
		portTextField.setMessageText("port");

		hostTextField.setText(controller.storage.getHost());
		portTextField.setText(controller.storage.getPort()+"");

		table.defaults().prefWidth(999).pad(40);
		table.add(label);
		table.row();
		table.add(hostTextField);
		table.row();
		table.add(portTextField);
		table.row();
		table.add(createServer);

		createServer.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				// controller.startServer();
				controller.setMenuScreen(ScreenEnum.SERVER);

			}
		});

		hostTextField.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char c){
				controller.storage.setHost(hostTextField.getText());
				Gdx.input.setOnscreenKeyboardVisible(true);
			}
		});

		portTextField.setTextFieldFilter(new TextField.TextFieldFilter() {
		    @Override
		    public  boolean acceptChar(TextField textField, char c) {
			    if(c >= 48 && c <= 57){
				return true;
			    }
			    return false;
		    }
		});
		portTextField.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char c){
				// if(!(c >= 48 && c <= 57)) return;
				if(Settings.this.portTextField.getText().equals("")) return;
				controller.storage.setPort(Integer.parseInt(Settings.this.portTextField.getText()));
				Gdx.input.setOnscreenKeyboardVisible(true);
			}
		});



	}

	@Override
	public void hide(){
		if(controller.fromArrayToString(hostTextField.getText().split(" ")).equals("")){
			controller.storage.setHost("109.162.9.233");
		}
		if(controller.fromArrayToString(portTextField.getText().split(" ")).equals("")){
			controller.storage.setPort(23098);
		}
	}

	@Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0.3f, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

		
	}
	@Override
	public void backMenu(){
		controller.setMenuScreen(ScreenEnum.LOGIN);
	}
}