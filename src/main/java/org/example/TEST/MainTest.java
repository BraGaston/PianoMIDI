package org.example.TEST;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.MIDI.MidiListener;
import org.example.NoteManager.MusicState;
import org.example.Screen.Screen;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;

public class MainTest extends Application {

    private static MusicState musicState;
    private static Screen screen;

    public static void main(String[] args) {
        // Inicializamos nuestro estado y pantalla antes de arrancar la interfaz
        musicState = new MusicState();
        screen = new Screen(musicState);

        // Arrancamos el entorno MIDI en un hilo separado para que no bloquee la app gráfica
        new Thread(MainTest::initMidi).start();

        // Lanza la ventana gráfica de JavaFX (Llamará automáticamente al método start)
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        screen.start(primaryStage);
    }

    private static void initMidi() {
        System.out.println("=== INICIALIZANDO ESCANEO DE DISPOSITIVOS MIDI ===");
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        MidiDevice pianoFisico = null;

        for (MidiDevice.Info info : infos) {
            try {
                if (info.getName().contains("CASIO")) {
                    MidiDevice device = MidiSystem.getMidiDevice(info);
                    if (device.getMaxTransmitters() != 0) {
                        pianoFisico = device;
                        break;
                    }
                }
            } catch (MidiUnavailableException e) {
                System.err.println("Error escaneando: " + info.getName());
            }
        }

        if (pianoFisico != null) {
            try {
                pianoFisico.open();
                Transmitter transmitter = pianoFisico.getTransmitter();
                // Pasamos la pantalla al listener para poder refrescarla al instante
                MidiListener midiListener = new MidiListener(musicState, screen);
                transmitter.setReceiver(midiListener);
                System.out.println("🎹 Piano conectado con éxito.");
            } catch (MidiUnavailableException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No se encontró el dispositivo CASIO.");
        }
    }
}
