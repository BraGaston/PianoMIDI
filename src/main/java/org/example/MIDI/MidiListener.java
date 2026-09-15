package org.example.MIDI;

import org.example.Note.Note;
import org.example.NoteManager.MusicState;
import org.example.Screen.Screen;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

//Clase encargada de escuchar al piano y devolver la informacion emitida
public class MidiListener implements Receiver {
    private final MusicState musicState;
    private final Screen screen; // Agregamos la pantalla de destino

    public MidiListener(MusicState musicState, Screen screen) {
        this.musicState = musicState;
        this.screen = screen;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage sm) {
            int comando = sm.getCommand();
            int valorMidi = sm.getData1();
            int velocidad = sm.getData2();

            if (comando == ShortMessage.NOTE_ON && velocidad > 0) {
                musicState.addRawNote(Note.getNote(valorMidi));
                screen.render(); // 🎬 ¡Ordenamos redibujar la pantalla al instante!
            } else if (comando == ShortMessage.NOTE_OFF || (comando == ShortMessage.NOTE_ON && velocidad == 0)) {
                musicState.removeRawNote(Note.getNote(valorMidi));
                screen.render(); // 🎬 ¡Ordenamos redibujar la pantalla al instante!
            }
        }
    }

    @Override public void close() {}
}
