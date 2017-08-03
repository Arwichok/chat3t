package com.arwichok.chat3t;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class LightLogin extends StandartScreen{
	private MainController controller;
	private Stage stage;
	private Table table;
	private Label helloLabel;
	private TextField nameTextField, passTextField;
	private Skin skin;
	private TextButton loginButton, settingButton;
	private CheckBox showPassCheckBox;
	private Label errorMessageLabel;


	public LightLogin(MainController cont){
		controller = cont;
		skin = controller.skin;
		stage = new Stage(controller.viewport);
		Gdx.input.setInputProcessor(stage);
		table = new Table();

		nameTextField = new TextField("", skin);

		loginButton = new TextButton("Login", skin);

		settingButton = new TextButton("Settings", skin);

		errorMessageLabel = new Label("", skin);

	}

	@Override
	public void show(){

		controller.client.dispose();

		// stage.setDebugAll(true);
		stage.addActor(table);

		table.setFillParent(true);
		// table.setDebug(true);
		// table.add(helloLabel);


		nameTextField.setMessageText(" Name...");
		nameTextField.setMaxLength(100);
		errorMessageLabel.setAlignment(Align.center);
		loginButton.setDisabled(true);


		table.defaults().prefWidth(999).padLeft(20).padRight(20).padBottom(20);
		table.add(errorMessageLabel);
		table.row();
		table.add(nameTextField);
		table.row();
		table.add(loginButton);
		table.row();
		table.add(settingButton);

		loginButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				errorMessageLabel.setText("");
				checkName();
			}
		});

		settingButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				
				// controller.setMenuScreen("Settings");
				controller.setMenuScreen(ScreenEnum.SETTINGS);
			}
		});

	}

	@Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0.3f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int screenWidth, int screenHeight){
		table.setSize(screenWidth, screenHeight);
		stage.getViewport().setScreenSize(screenWidth, screenHeight);
	}

	public void serverNotFound(){
		errorMessageLabel.setText("[#ff0000]Server not found");
	}

	@Override
	public void backMenu(){
		Gdx.app.exit();
	}

	private void checkName(){
		if(!controller.fromArrayToString(nameTextField.getText().split(" ")).equals("")){
			controller.sendLogin(nameTextField.getText(), LightLogin.this);
		}else{
			errorMessageLabel.setText("Please write name");
		}
	}
}