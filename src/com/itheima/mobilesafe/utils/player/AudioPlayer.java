package com.itheima.mobilesafe.utils.player;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {

	private MediaPlayer mediaPlayer;
	
	private int currentCount;
	
	private int totalPlayCount;
	
	public void play(Context context,int soundResId,int totalPlayCount){
		
		stop();
		currentCount = 0;
		this.totalPlayCount = totalPlayCount;

		currentCount ++;
		mediaPlayer = MediaPlayer.create(context, soundResId);
		mediaPlayer.start();
		
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {

				if(currentCount<AudioPlayer.this.totalPlayCount){
					currentCount ++;
					mediaPlayer.start();
				}else{
					stop();
				}
			}
		});
	}
	
	public void stop(){
		
		if(mediaPlayer != null){
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	
}
