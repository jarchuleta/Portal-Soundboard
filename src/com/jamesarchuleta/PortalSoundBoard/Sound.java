package com.jamesarchuleta.PortalSoundBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;


public class Sound 
implements OnClickListener,OnCreateContextMenuListener, OnMenuItemClickListener{

	static final private int MENU_RINGTONE = Menu.FIRST;
	static final private int MENU_NOTIFICATION = MENU_RINGTONE + 1;
	static final private int MENU_MUSIC = MENU_NOTIFICATION + 1;
	static final private int MENU_SET = MENU_MUSIC + 1;
	
	private Activity mParent;
	private int mResourceId;
	private String mName;
	private int mButtonID;
	
	private static Player mPlayer;
	public static Player getPlayer() { return mPlayer; }
	public static void setPlayer(Player value) {
	    mPlayer = value;
	}
	

	private static boolean mDoSet;
	public static boolean getmDoSet() { return mDoSet; }
	public static void setmDoSet(boolean value) { mDoSet = value;	}
	
	enum Action {Ringtone, Notification, Music};
	private boolean doSet;
	private Action mAction;
	
	Sound(Activity Parent, int RawResId, int ButtonID, String Name){
		mParent = Parent;
		mResourceId = RawResId;
		mName = Name;
		mButtonID = ButtonID;
	}
	public void Initialize() {
		mParent.findViewById(mButtonID).setOnClickListener(this);
		mParent.findViewById(mButtonID).setOnCreateContextMenuListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (mPlayer != null){
			mPlayer.play(mParent.getBaseContext(), mResourceId);
		}
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		 mParent.onCreateContextMenu(menu, v, menuInfo);  
		         menu.setHeaderTitle("Context Menu");
		         menu.add(0, MENU_RINGTONE, 0, R.string.SaveRingtone).setOnMenuItemClickListener(this);
		         menu.add(0, MENU_NOTIFICATION, 0, R.string.SaveNotification).setOnMenuItemClickListener(this);
		         menu.add(0, MENU_MUSIC, 0, R.string.SaveMusic).setOnMenuItemClickListener(this);
		         
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {

		switch (item.getItemId())
		{
		case MENU_RINGTONE:
			mAction = Action.Ringtone;
			break;
		case MENU_NOTIFICATION:
			mAction = Action.Notification;
			break;
		case MENU_MUSIC:
			mAction = Action.Music;
			break;		
		}
		 
		
			String name = mName.toLowerCase().replace(' ', '_');
		return SaveSound(mParent,name, mName, mResourceId);
				
	}
	public boolean SaveSound(Activity Parent, String filename, String Title, int ressound) {
		byte[] buffer = null;
		InputStream fIn = Parent.getBaseContext().getResources().openRawResource(ressound);
		int size = 0;

		try {
			size = fIn.available();
			buffer = new byte[size];
			fIn.read(buffer);
			fIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}

		boolean saved = false;
		
		switch (mAction){
		case Ringtone:
			saved = save(Parent, filename, Title, buffer, "/sdcard/media/audio/ringtones/");
		case Notification:
			saved = save(Parent, filename, Title, buffer, "/sdcard/media/audio/notifications/");
		case Music:
		};
		
		if (saved) {
			return true;
		} else {
			return false;
		}

	}
	public boolean save(Activity Parent, String filename, String Title,
			byte[] buffer, String path) {
		
		 
		 boolean exists = (new File(path)).exists();
		 if (!exists){new File(path).mkdirs();}

		 FileOutputStream save;
		 try {
		  save = new FileOutputStream(path+filename);
		  save.write(buffer);
		  save.flush();
		  save.close();
		 } catch (FileNotFoundException e) {
		  // TODO Auto-generated catch block
		  return false;
		 } catch (IOException e) {
		  // TODO Auto-generated catch block
		  return false;
		 }
		 		 
		 Parent.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+path+filename)));

		 File k = new File(path, filename);

		 ContentValues values = new ContentValues();
		 values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
		 values.put(MediaStore.MediaColumns.TITLE, Title);
		 values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/ogg");
		 values.put(MediaStore.Audio.Media.ARTIST, " ");
		 values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		 values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		 values.put(MediaStore.Audio.Media.IS_ALARM, true);
		 values.put(MediaStore.Audio.Media.IS_MUSIC, true);

		//Insert it into the database
		 Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
		 Uri newUri = Parent.getContentResolver().insert(uri, values);
		 
		 // set the ringtone

		if (doSet == true && mAction == Action.Ringtone
				|| mAction == Action.Notification) {

			int type = RingtoneManager.TYPE_RINGTONE;
			switch (mAction) {
			case Ringtone:
				type = RingtoneManager.TYPE_RINGTONE;
				break;
			case Notification:
				type = RingtoneManager.TYPE_NOTIFICATION;
			}
			
			RingtoneManager.setActualDefaultRingtoneUri(mParent, type, newUri);
		}
		 
		 return true;
	}
		
}
