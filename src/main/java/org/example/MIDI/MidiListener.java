package org.example.MIDI;

import org.example.Note.Note;
import org.example.NoteManager.MusicState;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MidiListener implements Receiver {
    private final MusicState musicState;

    public MidiListener(MusicState musicState) {
        this.musicState = musicState;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage sm) {

            int comando = sm.getCommand();
            int valorMidi = sm.getData1(); // Valor de nota
            int velocidad = sm.getData2(); // La fuerza con la que se tocó (0 a 127)

            // Se presiona una tecla
            if (comando == ShortMessage.NOTE_ON && velocidad > 0) {
                musicState.addRawNote(Note.getNote(valorMidi));

                // Se suelta la tecla
            } else if (comando == ShortMessage.NOTE_OFF || (comando == ShortMessage.NOTE_ON && velocidad == 0)) {
                musicState.removeRawNote(Note.getNote(valorMidi));
            }
        }
    }

    @Override
    public void close() {
        System.out.println("Canal MIDI cerrado de forma segura.");
    }
}
