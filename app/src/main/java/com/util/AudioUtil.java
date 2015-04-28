package com.util;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Vibrator;

import com.psb.R;
import com.psb.core.AppContext;

public class AudioUtil {
	private static AudioUtil util = null;

	private final static SoundPool sp = new SoundPool(5,
			AudioManager.STREAM_MUSIC, 0);

	public static final Integer PUSH_SOUND = 1;
	private static Map<Integer, Integer> sounds = null;
	private final static AudioManager am = (AudioManager) AppContext
			.getInstance().getSystemService(Context.AUDIO_SERVICE);// 实例化AudioManager
	private final static Vibrator vibrator = (Vibrator) AppContext
			.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
	private final static float audioMaxVolumn = am
			.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	private static long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启

	private AudioUtil() {

	}

	public static AudioUtil getInstance() {
		if (util == null) {
			util = new AudioUtil();
		}
		return util;
	}

	public static AudioUtil getInstance(AudioManager am) {

		if (util == null) {
			util = new AudioUtil();
		}
		util.mAudioManager = am;
		return util;
	}

	AudioManager mAudioManager;
	private OnAudioFocusChangeListener mAudioFocusChangeListener = new OnAudioFocusChangeListener() {

		@Override
		public void onAudioFocusChange(int focusChange) {
			if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

			}
		}
	};

	public void requestAudioFocus() {
		if (mAudioManager == null) {
			mAudioManager = (AudioManager) AppContext.getInstance()
					.getSystemService(Context.AUDIO_SERVICE);
		}
		if (mAudioManager != null) {
				mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
						AudioManager.STREAM_MUSIC,
						AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
		}
	}

	public void loseAudioFocus() {
		if (mAudioManager != null) {
			mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
			mAudioManager = null;
		}
	}

	@SuppressLint("UseSparseArrays")
	public static void initSounds() {
		if (null == sounds) {
			sounds = new HashMap<Integer, Integer>();
			sounds.put(PUSH_SOUND,
					sp.load(AppContext.getInstance(), R.raw.push, 1));
		}
	}


	@SuppressLint("UseSparseArrays")
	public static void recvMsg(int sound, boolean needReadSetting) {
		if (null == sounds) {
			initSounds();
		}
		switch (am.getRingerMode()) {
		case AudioManager.RINGER_MODE_NORMAL:
			// 普通
			float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			float volumnRatio = volumnCurrent / audioMaxVolumn;
			if (needReadSetting) {
					sp.play(sounds.get(sound), volumnRatio, volumnRatio, 1, 0,
							1f);
					vibrator.vibrate(pattern, -1);
			} else {
				sp.play(sounds.get(sound), volumnRatio, volumnRatio, 1, 0, 1f);
			}

			break;
		case AudioManager.RINGER_MODE_SILENT:
			// 静音无震动
			break;
		case AudioManager.RINGER_MODE_VIBRATE:
			// 震动 重复两次上面的pattern 如果只想震动一次，index设为-1

				vibrator.vibrate(pattern, -1);
			break;
		}
	}
}
