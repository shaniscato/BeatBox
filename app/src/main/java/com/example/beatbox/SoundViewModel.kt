package com.example.beatbox

import android.databinding.BaseObservable
import android.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable(){
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }
    @get:Bindable
    val title: String?
        get() = sound?.name

}