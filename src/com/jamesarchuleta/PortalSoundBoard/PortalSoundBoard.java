package com.jamesarchuleta.PortalSoundBoard;


import com.admob.android.ads.*;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;


public class PortalSoundBoard 
	extends Activity 
	//implements AdListener 
	{
    
    
    Sound Sounds[] = {
    		new Sound(this,R.raw.a_big_party ,R.id.BigParty , "A Big Party")
    		,new Sound(this,R.raw.activated,R.id.Activated , "Activated")
    		,new Sound(this,R.raw.are_you_still_there,R.id.AreYouStillThere , "Are You Still There")
    		,new Sound(this,R.raw.baked_cake,R.id.BakedCake , "Baked Cake")
    		,new Sound(this,R.raw.coming_through,R.id.Coming , "Coming Through")
    		,new Sound(this,R.raw.cube_speak,R.id.Cube_Speaks , "Cube Speak")
    		,new Sound(this,R.raw.excuse_me,R.id.Excuse , "Excuse Me")
    		,new Sound(this,R.raw.if_cube_speaks,R.id.IfCubeSpeaks , "If Cube Speaks")
    		,new Sound(this,R.raw.party_escort_position,R.id.PartyEscort , "Party Escort")
    		,new Sound(this,R.raw.platform_sliding,R.id.Platform , "Platform")
    		,new Sound(this,R.raw.portalgun_shoot_blue1,R.id.GunBlue , "Gun Blue")
    		,new Sound(this,R.raw.portalgun_shoot_red1,R.id.GunYellow , "Gun Yellow")
    		,new Sound(this,R.raw.sorry,R.id.Sorry , "Sorry")
    		,new Sound(this,R.raw.stab,R.id.Stab , "Stab")
    		,new Sound(this,R.raw.stillalive,R.id.StillAlive , "Still Alive")
    		,new Sound(this,R.raw.super_colliding,R.id.SuperCollide , "Super Collide")
    		,new Sound(this,R.raw.who_are_you,R.id.Who , "Who Are You")
    		,new Sound(this,R.raw.you_are_kidding_me,R.id.Kidding , "Kidding")
    		    		
    };
     

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("Portal SoundBoard", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AdView ad = (AdView) findViewById(R.id.ad);
        ad.setAdListener(new SimpleAdListener());
        //ad.setVisibility(0);
                
        AdManager.setTestDevices( new String[] { AdManager.TEST_EMULATOR, "040382670E01C006" } );
        
        Player p = new Player();
        Sound.setPlayer(p);
        
        for (int i = 0; i < Sounds.length; i++){
        	Sounds[i].Initialize();
        }               
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
}