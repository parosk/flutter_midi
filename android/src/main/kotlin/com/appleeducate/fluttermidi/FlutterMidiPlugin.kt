package com.appleeducate.fluttermidi

import cn.sherlock.com.sun.media.sound.SF2Soundbank
import cn.sherlock.com.sun.media.sound.SoftSynthesizer
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.PluginRegistry.Registrar
import jp.kshoji.javax.sound.midi.InvalidMidiDataException
import jp.kshoji.javax.sound.midi.MidiUnavailableException
import jp.kshoji.javax.sound.midi.Receiver
import jp.kshoji.javax.sound.midi.ShortMessage
import java.io.File
import java.io.IOException


/** FlutterMidiPlugin  */
class FlutterMidiPlugin : MethodCallHandler {
//    private var synth: SoftSynthesizer? = null
//    private var recv: Receiver? = null

    private val nativeLibJNI = NativeLibJNI()

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method == "prepare_midi") {
            try {
                val _path = call.argument<String>("path")
                val _file = File(_path)
                //val sf = SF2Soundbank(_file)
//                synth = SoftSynthesizer()
//                synth!!.open()
//                synth!!.loadAllInstruments(sf)
//                synth!!.channels[0].programChange(0)
//                synth!!.channels[1].programChange(1)
//                recv = synth!!.receiver

                System.loadLibrary("fluidsynth")
                nativeLibJNI.init(_file.absolutePath)





            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: MidiUnavailableException) {
                e.printStackTrace()
            }
        } else if (call.method == "change_sound") {
            try {
                val _path = call.argument<String>("path")
                val _file = File(_path)
//                val sf = SF2Soundbank(_file)
//                synth = SoftSynthesizer()
//                synth!!.open()
//                synth!!.loadAllInstruments(sf)
//                synth!!.channels[0].programChange(0)
//                synth!!.channels[1].programChange(1)


                nativeLibJNI.programChange(0,0)
                nativeLibJNI.programChange(1,1)
                //recv = synth!!.receiver
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: MidiUnavailableException) {
                e.printStackTrace()
            }
        } else if (call.method == "play_midi_note") {
            val _note = call.argument<Int>("note")!!
            try {
//                val msg = ShortMessage()
//                msg.setMessage(ShortMessage.NOTE_ON, 0, _note, 127)
//                recv!!.send(msg, -1)

                nativeLibJNI.noteOn(_note,127)


            } catch (e: InvalidMidiDataException) {
                e.printStackTrace()
            }
        } else if (call.method == "stop_midi_note") {
            val _note = call.argument<Int>("note")!!
            try {
//                val msg = ShortMessage()
//                msg.setMessage(ShortMessage.NOTE_OFF, 0, _note, 127)
//                recv!!.send(msg, -1)

                nativeLibJNI.noteOff(_note)
            } catch (e: InvalidMidiDataException) {
                e.printStackTrace()
            }
        } else {
        }
    }

    companion object {
        /** Plugin registration.  */
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "flutter_midi")
            channel.setMethodCallHandler(FlutterMidiPlugin())
        }
    }
}