package com.demo.videoplayer.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo.videoplayer.R;
import com.demo.videoplayer.base.BaseActivity;
import com.demo.videoplayer.utils.LogUtils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VideoPlayerActivity extends BaseActivity implements OnClickListener,
OnPreparedListener, OnCompletionListener,SurfaceHolder.Callback{
	private Button btn_back;
	
	private SurfaceView sv_videoplayer;
	private SurfaceHolder sv_holder;
	private MediaPlayer videoplayer;
	
	private List<String> videofilelist;
	private static List<Integer> filetimelist = new ArrayList<Integer>();
	private int sumFilesTime;
	private int indexFile = 0;
	private int indexSeekTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videoplayer);
		getElement();
		setListener();
		initData();
	}

	@Override
	protected void getElement() {
		btn_back = (Button) findViewById(R.id.btn_back);
		sv_videoplayer = (SurfaceView) findViewById(R.id.sv_videoplaer);
	}
	
	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
	}
	
	@Override
	protected void initData() {
		videofilelist = new ArrayList<String>();
		videofilelist.add("aa");
		videofilelist.add("bb");
		videofilelist.add("cc");
		
		sumFilesTime = getAllFileTime();
		
		sv_holder = sv_videoplayer.getHolder();
//		sv_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		sv_holder.addCallback((Callback) this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	//play video
	private void playvideo(){
		initIndex();
		releaseMediaPlayer();
		LogUtils.logprint("*************播放定位：****"+indexFile+"***************"+indexSeekTime);
		
		try {
			videoplayer = new MediaPlayer();
			videoplayer.reset();
			videoplayer.setDataSource(getFileURL(getIndexFile()));
			videoplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			videoplayer.setDisplay(sv_holder);
			videoplayer.prepareAsync();
			LogUtils.logprint("**********************is playing...");
		} catch (IllegalArgumentException | SecurityException
				| IllegalStateException | IOException e) {
			//TODO
			LogUtils.logprint("**********************can't play...");
			playvideo();
			e.printStackTrace();
		}
//		LogUtils.logprint("*************该视频文件一共有多少毫秒："+videoplayer.getDuration());
		videoplayer.setOnPreparedListener(this);
		videoplayer.setOnCompletionListener(this);
	}
	
	//release MediaPlayer
	private void releaseMediaPlayer(){
		if(videoplayer != null){
			videoplayer.stop();
			videoplayer.release();
			videoplayer = null;
		}
	}
	
	private void initIndex(){
		int time = (int) ((getCurrentTime() - getReferenceTime()) % sumFilesTime);
		LogUtils.logprint("*************取余*****f:" + time);
		time = time + 1000;
		LogUtils.logprint("*************取余*****B:" + time);
//		for(int i = 0;i < filetimelist.size();i++){
//			if(time > filetimelist.get(i)){
//				LogUtils.logprint("*************time*****:" + time);
//				time = time - filetimelist.get(i);
//				setIndexFile();
//			}else{
//				setPlaySeek(time);
//				return;
//			}
//		}
		if(time>0 && time < filetimelist.get(0)){
			indexFile = 0;
			setPlaySeek(time);
		}else if(time > filetimelist.get(0) && time < filetimelist.get(1)){
			indexFile = 1;
			setPlaySeek(time-filetimelist.get(0));
		}else if(time > filetimelist.get(1) && time < filetimelist.get(2)){
			indexFile = 2;
			setPlaySeek(time-filetimelist.get(0)-filetimelist.get(1));
		}else{
			LogUtils.logprint("*************取余环节出错*****:");
		}
	}
	
	private void setIndexFile(){
		indexFile++;
	}
	
	private int getIndexFile(){
		LogUtils.logprint("*********************getIndexFile:"+indexFile);
		return indexFile;
	}
	
	private void setPlaySeek(int indexSeekTime){
		if(indexSeekTime <2000){
			this.indexSeekTime = 0;
		}else{
			this.indexSeekTime = indexSeekTime - 2000;
		}
	}
	
	private int getPlaySeek(){
		return indexSeekTime;
	}
	
	
	private long getCurrentTime(){
		SimpleDateFormat sdf;
		Date date;
		long currentTime = 0;
		
		sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.format(new Date());
		try {
			date = sdf.parse(sdf.format(new Date()));
			currentTime = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime;
	}
	
	private long getReferenceTime(){
		SimpleDateFormat sdf;
		String indexDay;
		Date date;
		long referenceTime = 0;
		
		sdf = new SimpleDateFormat("yyyyMMdd");
		indexDay = sdf.format(new Date());
		
		sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			date = sdf.parse(indexDay + "000000");
			referenceTime = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return referenceTime;
	}
	
	@SuppressWarnings("finally")
	private int getAllFileTime(){
		/****method one***/
//		MediaPlayer tempMediaPlaper = new MediaPlayer();
//		int sumTime = 0;
//		try {
//			for(int i = 0;i<videofilelist.size();i++){
//				tempMediaPlaper.reset();
//				tempMediaPlaper.setDataSource(getFileURL(i));
//				filetimelist.add(tempMediaPlaper.getDuration());
//				sumTime = sumTime + filetimelist.get(i);
//				//TODO
//				LogUtils.logprint("***********file"+i+":::"+tempMediaPlaper.getDuration());
//			}
////			filetimelist.add(sumTime);
//			
//		} catch (IllegalArgumentException | SecurityException
//				| IllegalStateException | IOException e) {
//			e.printStackTrace();
//			LogUtils.logprint("***********Error:get all files time");
//		}finally{
//			return sumTime;
//		}
		
		/****test***/
		int sumTime = 0;
		filetimelist.add(105000);
		filetimelist.add(7000);
		filetimelist.add(214000);
		sumTime = filetimelist.get(0) + filetimelist.get(1) + filetimelist.get(2);
		return sumTime;
	}
	
	private String getFileURL(int index){
		return "/mnt/sdcard/video/"+videofilelist.get(index)+".mp4";
	}
	
	/**
	 * Method SurfaceHolder.Callback()
	 * ********************************start tag1****************************************
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
//		if(videoplayer.isPlaying()){
//		}
		releaseMediaPlayer();
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder sv_holder) {
		playvideo();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}
	/************************************start tag1**********************************************/
	
	/**
	 *  Method in videoplayer
	 * ***********************************start tag2***********************************************
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		playvideo();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		if(videoplayer != null){
			videoplayer.start();
			videoplayer.seekTo(getPlaySeek());
//			videoplayer.seekTo(100000);
		}
	}
	/**************************************end tag2********************************************/
}
