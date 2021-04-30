package com.example.beatbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_color_chooser.*
import kotlinx.android.synthetic.main.fragment_color_chooser.view.*

class ColorChooserFragment: DialogFragment() {
    private val TAG = "ColorChooserFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_color_chooser, container, false)

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }
        rootView.acceptButton.setOnClickListener{
            val selected = themeChoiceRadioGroup.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selected)
            var themeText = radio.text.toString()
            val theme = when(radio){
                rootView.defaultThemeButton -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                rootView.lightThemeButton -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_YES
            }
            AppCompatDelegate.setDefaultNightMode(theme)
            Toast.makeText(context,"Theme: $themeText", Toast.LENGTH_LONG).show()
//

            dismiss()
        }
        return rootView
    }

}