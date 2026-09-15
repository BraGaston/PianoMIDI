package org.example.Screen;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Note.Accidentals;
import org.example.NoteManager.MusicState;

public class Screen {
    private final MusicState musicState;
    private Label notesLabel; // Componente visual para mostrar las notas

    public Screen(MusicState musicState) {
        this.musicState = musicState;
    }

    // JavaFX llamará a este método para construir la interfaz gráfica
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Piano MIDI Visualizer");

        // 1. Componente que mostrará las notas que estás tocando
        notesLabel = new Label("Presiona teclas en tu piano...");
        notesLabel.setStyle("-font-size: 24px; -font-weight: bold;");

        // 2. Botones para cambiar el Enum de alteración en tiempo real
        Button sharpButton = new Button("Usar Sostenidos (#)");
        Button flatButton = new Button("Usar Bemoles (b)");

        // Acciones de los botones usando tus ENUMS
        sharpButton.setOnAction(e -> musicState.getKeySignature().setTargetAccidental(Accidentals.SHARP));
        flatButton.setOnAction(e -> musicState.getKeySignature().setTargetAccidental(Accidentals.FLAT));

        // 3. Organización de los componentes en la pantalla (Layout)
        HBox buttonLayout = new HBox(15, sharpButton, flatButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(30, notesLabel, buttonLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-background-color: #f0f0f0;");

        // 4. Crear la escena y mostrar la ventana
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Este método reemplaza tu antiguo render() por consola
    public void render() {
        // Platform.runLater es OBLIGATORIO en JavaFX cuando actualizas la pantalla
        // desde hilos externos (como el hilo asíncrono de tu MidiListener)
        Platform.runLater(() -> {
            if (notesLabel != null) {
                if (musicState.getActiveNotes().isEmpty()) {
                    notesLabel.setText("Ninguna nota presionada");
                } else {
                    // Juntamos todas las notas activas en un solo texto
                    StringBuilder sb = new StringBuilder("Notas: ");
                    musicState.getActiveNotes().forEach(note -> sb.append(note.toString()).append(" "));
                    notesLabel.setText(sb.toString());
                }
            }
        });
    }
}
