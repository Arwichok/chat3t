package com.arwichok.chat3t;


import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Storage implements Disposable{
	private Skin mainSkin;
	private MainController controller;

	private String host = "127.0.0.1";
	private String localhost = "localhost";
	private int port = 23098;


	public Storage(MainController game){
		controller = game;
		mainSkin = new Skin(Gdx.files.internal("res/default/uiskin.json"));
		mainSkin.getFont("default-font").getData().markupEnabled = true;
		// mainSkin = new Skin(Gdx.files.internal("res/skin.json"), 
		// 	new TextureAtlas(Gdx.files.internal("res/aqua.atlas")));
	}

	public Skin getSkin(){
		return mainSkin;
	}

	
	public void setPort(int p){
		port = p;
	}
	public void setHost(String h){
		host = h;
	}

	public int getPort(){
		return port;
	}

	public String getHost(){
		return host;
	}

	@Override
	public void dispose(){
		mainSkin.dispose();
	}

	


}