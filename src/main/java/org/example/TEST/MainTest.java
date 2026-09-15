package org.example.TEST;

import org.example.MIDI.MidiListener;
import org.example.NoteManager.KeySignature;
import org.example.NoteManager.MusicState;
import org.example.Screen.Screen;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("=== INICIALIZANDO ESCANEO DE DISPOSITIVOS MIDI ===");
        //Obtengo los dispositivos conectados a mi pc
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        MidiDevice pianoFisico = null;

        for(MidiDevice.Info info: infos){
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
               /* System.out.println("Dispositivos: " + "MaxTransmitter: " + device.getMaxTransmitters() + " MaxReciver: " + device.getMaxReceivers() + " - "+ device.getDeviceInfo().getName() +
                        " - " + device.getDeviceInfo().getDescription());*/
                if(device.getMaxTransmitters()!=0 && info.getName().contains("CASIO")){
                    pianoFisico = device;

                }
            } catch (MidiUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
        if(pianoFisico != null){
            System.out.print("Dispositivo encontrado: " + pianoFisico.getDeviceInfo().getName());
            try {
                MusicState musicState = new MusicState();
                Screen screen = new Screen(musicState);
                pianoFisico.open();
                Transmitter transmitter = pianoFisico.getTransmitter();
                MidiListener midiListener = new MidiListener(musicState);
                transmitter.setReceiver(midiListener);

                while (true) {
                    screen.render();
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                }

            } catch (MidiUnavailableException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            System.out.print("No se pudo encontrar dispositivo");
        }
    }
}



