package org.example.NoteManager;

import org.example.Note.Accidentals;
import org.example.Note.Note;
import org.example.Note.NoteName;

//Clase para el maneno de las alteraciones en las notas y semitonos
public class KeySignature {

    private Accidentals targetAccidental = Accidentals.FLAT;

    public void setTargetAccidental(Accidentals accidental) {
        this.targetAccidental = accidental;
    }

    //Se procesa la nota y se le aplica la alteracion a la nota segun corresponda la configuracion establecida.
    public Note processNote(Note touchedNote) {
        int notaEnOctava = touchedNote.getMidi() % 12;
        int octava = (touchedNote.getMidi() / 12) - 1;

        // Si es una tecla blanca, devolvemos la nota natural
        if (notaEnOctava == 0 || notaEnOctava == 2 || notaEnOctava == 4 ||
                notaEnOctava == 5 || notaEnOctava == 7 || notaEnOctava == 9 || notaEnOctava == 11) {
            return touchedNote;
        }

        // Si la pantalla/clave pide BEMOL, transformamos las teclas negras mapeando al Enum
        if (this.targetAccidental == Accidentals.FLAT) {
            return switch (notaEnOctava) {
                case 1  -> new Note(NoteName.D, Accidentals.FLAT, octava); // C# -> Db
                case 3  -> new Note(NoteName.E, Accidentals.FLAT, octava); // D# -> Eb
                case 6  -> new Note(NoteName.G, Accidentals.FLAT, octava); // F# -> Gb
                case 8  -> new Note(NoteName.A, Accidentals.FLAT, octava); // G# -> Ab
                case 10 -> new Note(NoteName.B, Accidentals.FLAT, octava); // A# -> Bb
                default -> touchedNote;
            };
        }

        // El ultimo caso entonces es una negra por defecto, osea una nota sostenida.
        return touchedNote;
    }
}
