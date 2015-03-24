package com.xiaoke.accountsoft.service;

import java.io.IOException;

import com.xiaoke.accountsoft.activity.SettingActivity;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class BackGroundMusicService extends Service {

	private MediaPlayer mediaPlayer;
	private final IBinder binder = new LocalBinder();
	
	
	public void setMusicSource() {
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setLooping(true);
			AssetFileDescriptor fileDescriptor = getApplicationContext().getAssets().openFd("BlankSpace.mp3");
			mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
			mediaPlayer.prepare();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void playMusic() {
		mediaPlayer.start();
	}
	
	public void pauseMusic() {
		mediaPlayer.pause();
	}
	
	public void stopMusic() {
		mediaPlayer.stop();
		mediaPlayer.release();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	public class LocalBinder extends Binder{
		public BackGroundMusicService getService(){
			return BackGroundMusicService.this;
		}
	}
}
