package org.example.NoteManager;

import org.example.Note.Note;

/*Esta clase sera la encargada de agregar las alteraciones a las notas segun
 la clave de la partitura o desde la screen
 */
public class KeySignature {
    Note note;

    public void setNote(Note touchedNote) {
        this.note = touchedNote;

    }

    public Note getNote(){
        return note;
    }


    public Note processNote(Note touchedNote) {
        return touchedNote;
    }
}
