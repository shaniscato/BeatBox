package com.example.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

class BeatBox(private val assets: AssetManager) {
    //build a list of Sounds
    val sounds: List<Sound>
    var volume = 1.0f
    var streamId = 0
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    init {
        sounds = loadSounds()
    }
    fun play(sound: Sound){
        sound.soundId?.let {
            streamId = soundPool.play(it, volume, volume, 1, 0, 1.0f)
        }

    }
    fun changeVolume(){
        soundPool.setVolume(streamId, volume, volume)
    }


    //unloading sounds from the soundpool
    fun release() {
        soundPool.release()
    }
    //Creating sounds
    private fun loadSounds():List<Sound>{
        val soundNames: Array<String>
        try{
            soundNames = assets.list(SOUNDS_FOLDER)!!
        }
        catch(e: Exception)
        {
            Log.e(TAG, "Cold not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            //loading up all the sounds
            try {
                load(sound)
                sounds.add(sound)
            }
            catch (ioe: IOException){
                Log.e(TAG, "Could not load sound $filename", ioe)
            }
        }
        return sounds
    }
    //Loading sounds into the soundpool
    private fun load(sound: Sound) {
        //loads file into soundpool for playback. keeps track of sound
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}