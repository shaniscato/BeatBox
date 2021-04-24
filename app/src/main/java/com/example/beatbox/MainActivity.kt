package com.example.beatbox

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = BeatBox(assets)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }

        binding.seekBar.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
        object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                beatBox.volume = progress.toFloat() / 100
                beatBox.changeVolume()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }
    private inner class SoundHolder(private val binding: ListItemSoundBinding):
        RecyclerView.ViewHolder(binding.root){
            init {
                binding.viewModel = SoundViewModel(beatBox)
            }
            fun bind(sound: Sound){
                binding.apply {
                    viewModel?.sound = sound
                    executePendingBindings()
                    //forces layout to immediately update the binding data inside recyclerview
                }
            }
        }
    private inner class SoundAdapter(private val sounds: List<Sound>):
        RecyclerView.Adapter<SoundHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
        return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
            }
        //Hooking up the sound list to the main activity
        override fun getItemCount() = sounds.size
        }
}