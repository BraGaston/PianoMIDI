package org.example.Screen;

import org.example.NoteManager.MusicState;

public class Screen {
    private final MusicState musicState;

    public Screen(MusicState musicState) {
        this.musicState = musicState;
    }

    // Este método es controlado por Screen (por ejemplo, en su bucle de repintado)
    public void render() {
        if (!musicState.getActiveNotes().isEmpty()) {
            System.out.println("\n [Pantalla] Dibujando notas actuales modificadas:");
            musicState.getActiveNotes().forEach(note -> System.out.println(" -> " + note));
        }
    }
}
