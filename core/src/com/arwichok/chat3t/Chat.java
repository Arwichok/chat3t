package com.arwichok.chat3t;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class Chat extends StandartScreen{
	MainController controller;
	TextArea areaMessage;
	TextField sendMessage;
	Table table;
	Stage stage;
	ImageButton sendButton;
	ScrollPane scrollMessage;
	Label label;



	public Chat(MainController game){
		controller = game;
		


		areaMessage = new TextArea("", controller.skin);
		sendMessage = new TextField("", controller.skin);

		sendButton = new ImageButton(controller.skin);

		table = new Table();

		stage = new Stage(controller.viewport);
		Gdx.input.setInputProcessor(stage);

		label = new Label("",controller.skin, "avdira-30", "white");

		controller.setCurrentLabel(label);

		scrollMessage = new ScrollPane(label, controller.skin);
		scrollMessage.layout();


	}

	@Override
	public void show(){
		stage.addActor(table);
		table.setFillParent(true);



		table.add(sendMessage).height(50).prefWidth(999);
		table.add(sendButton).size(50);
		table.row();
		table.add(scrollMessage).prefSize(999).colspan(2);
		// areaMessage.setDisabled(true);
		stage.setKeyboardFocus(sendMessage);


		sendButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				sendMess();

				Gdx.input.setOnscreenKeyboardVisible(false);
			}
		});

		sendMessage.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char c){
				// areaMessage.appendText(c+ " " + (int) c + "\n");
				if((int)c == 13 || (int)c == 10) {
					sendMess();
					Gdx.input.setOnscreenKeyboardVisible(true);

				}
			}
		});

		// sendMessage.addCaptureListener(new TextField.TextFieldClickListener(){
		// 	@Override
		// 	public boolean keyDown (InputEvent event, int character) {
		// 		if (character == Input.Keys.ENTER){
		// 			String mess = sendMessage.getText();
		// 			if(!mess.equals("") && !mess.equals(" ")){
		// 				areaMessage.setText("Name\n   "+mess + "\n" +
		// 					areaMessage.getText());
		// 			}

		// 		}

		// 		return true;
		// 	}
		// });

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
		controller.setMenuScreen(ScreenEnum.MAINMENU);
	}

	public void sendMess(){
		String mess = sendMessage.getText();
		if(!mess.equals("") && !mess.equals(" ")){
			sendMessage.setText("");

			controller.sendMessage(mess);
		}
	}
}
