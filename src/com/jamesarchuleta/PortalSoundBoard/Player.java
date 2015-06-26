package com.jamesarchuleta.PortalSoundBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

public class Player {
	private MediaPlayer mp;
	public Player(){
		mp = new MediaPlayer();
	}
	public void play(Context c, int r) {
		if (mp==null)
			return;
		
		
		if (mp.isPlaying()){
			mp.stop();
			mp.release();
		}else{
		 mp = MediaPlayer.create(c, r);
		 mp.start();
		}
	}
	
	public void stop(){
		if (mp==null)
			return;
		
		mp.stop();
	}
	
	public boolean isPlaying(){
		if (mp==null){
			return false;
		} else{
			return mp.isPlaying();
		}
	}
		
	
	
	
}
	

