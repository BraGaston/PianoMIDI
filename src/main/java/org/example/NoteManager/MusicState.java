package org.example.NoteManager;
import org.example.Note.Note;
import java.util.ArrayList;
import java.util.List;

public class  MusicState {
    // Lista para soportar acordes (varias notas a la vez)
    private final List<Note> activeNotes = new ArrayList<>();
    private final KeySignature keySignature = new KeySignature();

    public void addRawNote(Note rawNote) {
        // El gestor aplica la alteración (bemol, aumentada, etc.) usando los métodos de Note
        Note processedNote = keySignature.processNote(rawNote);
        activeNotes.add(processedNote);
    }

    public void removeRawNote(Note rawNote) {
        // Lógica para remover la nota cuando se suelta la tecla
        activeNotes.removeIf(n -> n.getMidi() == rawNote.getMidi());
    }

    public List<Note> getActiveNotes() {
        return activeNotes;
    }
}
