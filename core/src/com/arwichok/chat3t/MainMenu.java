package com.arwichok.chat3t;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Game;

public class MainMenu extends StandartScreen{
	MainController controller;
	private Stage stage;
	private Table table;
	private Skin skin;
	private TextButton chatButton, gameButton;

	public MainMenu(MainController game){
		controller = game;

		skin = controller.skin;
		stage = new Stage(controller.viewport);
		Gdx.input.setInputProcessor(stage);
		table = new Table();

		chatButton = new TextButton("Chat", skin);
		gameButton = new TextButton("Game", skin);

	}

	@Override
	public void show(){
		stage.addActor(table);
		table.setFillParent(true);


		table.defaults().prefWidth(999);
		table.add(chatButton);
		table.row();
		// table.add(gameButton);

		chatButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				controller.setMenuScreen(ScreenEnum.CHAT);
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
	public void backMenu(){
		controller.setMenuScreen(ScreenEnum.LOGIN);
	}
}