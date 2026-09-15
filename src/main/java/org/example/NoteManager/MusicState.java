package org.example.NoteManager;
import org.example.Note.Note;
import java.util.ArrayList;
import java.util.List;

//Clase puente para el manejo de polifonia (acordes), procesamiento de notas y nexo entre el hardware y pantalla.
public class MusicState {
    private final List<Note> activeNotes = new ArrayList<>();
    // Quitamos el final para poder interactuar con él o usar sus métodos
    private final KeySignature keySignature = new KeySignature();

    public void addRawNote(Note rawNote) {
        Note processedNote = keySignature.processNote(rawNote);
        activeNotes.add(processedNote);
    }

    public void removeRawNote(Note rawNote) {
        activeNotes.removeIf(n -> n.getMidi() == rawNote.getMidi());
    }

    public List<Note> getActiveNotes() {
        return activeNotes;
    }

    // Método fundamental para que el exterior controle la teoría musical
    public KeySignature getKeySignature() {
        return keySignature;
    }
}
