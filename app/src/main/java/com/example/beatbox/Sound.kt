package com.example.beatbox

import java.security.cert.CertPath

private const val WAV = ".wav"

class Sound(val assetPath: String, var soundId: Int? = null){
    val name = assetPath.split("/").last().removeSuffix(WAV)
}