package com.meditatenowBadHabits.api;

import java.util.ArrayList;

import com.meditatenowBadHabits.R;

public class MNAudioManager {
	
	private static MNAudioManager _instance;
	private ArrayList<MNAudioInfo> _audios = new ArrayList<MNAudioInfo>();

	public static MNAudioManager getInstance() {
		if(_instance == null)
			_instance = new MNAudioManager();
		return _instance;
	}
	
	public MNAudioManager() {
		addAudio(R.raw.one_introduction, (2*60 + 15)*1000 + 500, "1. Introduction.mp3", "Settle...");
		addAudio(R.raw.two_bad_habits_instructions, (5*60 + 3)*1000 + 500, "2. Bad Habits Instructions.mp3", "Focus...");
		addAudio(R.raw.three_bad_habits_lesson, (1*60 + 23)*1000 + 500, "3. Bad Habits Lesson.mp3", "Breathe...");
	}
	
	private MNAudioInfo addAudio(int resourceId, int duration, String name, String title) {
		MNAudioInfo info = new MNAudioInfo(resourceId, duration, name, title);
		_audios.add(info);
		return info;
	}
	
	public int getAudiosCount() {
		return _audios.size();
	}
	
	public MNAudioInfo getAudioInfo(int index) {
		return _audios.get(index);
	}
	
	public int indexOfAudio(MNAudioInfo audio) {
		return _audios.indexOf(audio);
	}
	
	private boolean isAudioAllowed(MNAudioInfo audio) {
		int index = this.indexOfAudio(audio);
		MNSettingManager sett = MNSettingManager.getInstance();
		if(index == 0 && !sett.isPlayIntro())
			return false;
		if(index == 1 && !sett.isPlayInstructions())
			return false;
		return true;
	}
	
	public MNAudioInfo getFirstAudio() {
		for(MNAudioInfo audio : _audios)
			if(this.isAudioAllowed(audio))
				return audio;
		return null;
	}
	
	public MNAudioInfo getNextAudio(MNAudioInfo prevAudio) {
		if(prevAudio == null)
			return this.getFirstAudio();
		int prevIndex = this.indexOfAudio(prevAudio);
		for(int i = prevIndex + 1; i < _audios.size(); i++)
			if(this.isAudioAllowed(_audios.get(i)))
				return _audios.get(i);
		return null;
	}
}
