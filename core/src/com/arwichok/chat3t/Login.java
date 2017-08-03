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

public class Login extends ScreenAdapter{
	private MainController controller;
	private Stage loginStage;
	private Table table;
	private Label helloLabel;
	private ScreenViewport viewport;
	private TextField nameTextField, passTextField;
	private Skin skin;
	private TextButton loginButton, regButton;
	private CheckBox showPassCheckBox;


	public Login(MainController cont){
		controller = cont;
		skin = controller.skin;
		viewport = new ScreenViewport();
		loginStage = new Stage(controller.viewport);
		Gdx.input.setInputProcessor(loginStage);
		table = new Table();
		helloLabel = new Label("Hello", skin);

		nameTextField = new TextField("", skin);
		passTextField = new TextField("", skin);

		loginButton = new TextButton("Login", skin);
		regButton = new TextButton("Reg", skin);

		showPassCheckBox = new CheckBox("",skin);

		

	}

	@Override
	public void show(){
		Gdx.app.log("chat3t", "Login.show()");

		loginStage.setDebugAll(true);
		loginStage.addActor(table);

		table.setFillParent(true);
		// table.setDebug(true);
		// table.add(helloLabel);


		nameTextField.setMessageText(" Name...");
		passTextField.setMessageText(" Password...");
		passTextField.setPasswordCharacter('*');
		passTextField.setPasswordMode(true);
		showPassCheckBox.setChecked(true);



		table.defaults().prefWidth(999).padLeft(20).padRight(20).padBottom(20);
		table.add(nameTextField).colspan(3);
		table.row();
		table.add(passTextField).colspan(3);
		table.row();
		table.add(loginButton);
		table.add(regButton);
		table.add(showPassCheckBox).width(100);
		table.padBottom(100f);



		showPassCheckBox.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				passTextField.setPasswordMode(showPassCheckBox.isChecked());
			}
		});

		loginButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				controller.isNamePasswordTrue(nameTextField.getText(), passTextField.getText());
			}
		});













	}

	@Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0.3f, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		loginStage.draw();
	}
	@Override
	public void resize(int screenWidth, int screenHeight){
		table.setSize(screenWidth, screenHeight);
		loginStage.getViewport().setScreenSize(screenWidth, screenHeight);
		Gdx.app.log("chat3t", "Login.resize()");
	}

	@Override
	public void dispose(){

	}
}