package org.example.Note;

public class Note {

    private final NoteName name;
    private final int octaves;
    private Accidentals accidental;

    // Constructor principal
    public Note(NoteName name, Accidentals accidental, int octaves) {
        this.name = name;
        this.accidental = accidental;
        this.octaves = octaves;
    }

    // Metodo para manejar las alteraciones en los semitonos
    public void setAccidental(Accidentals accidental){
        this.accidental = accidental;
    }

    // Me devuelve la nota desde un valor/codigo midi
    public static Note getNote(int midiValue) {
        if (midiValue < 0 || midiValue > 127) {
            throw new IllegalArgumentException("El número MIDI debe estar entre 0 y 127");
        }

        int notaEnOctava = midiValue % 12;
        int octava = (midiValue / 12) - 1;

        //Generamos la nota natural para las teclas blancas y por defecto sostenidas para las negras
        return switch (notaEnOctava) {
            case 0  -> new Note(NoteName.C, Accidentals.NATURAL, octava);
            case 1  -> new Note(NoteName.C, Accidentals.SHARP, octava); // C# por defecto
            case 2  -> new Note(NoteName.D, Accidentals.NATURAL, octava);
            case 3  -> new Note(NoteName.D, Accidentals.SHARP, octava); // D# por defecto
            case 4  -> new Note(NoteName.E, Accidentals.NATURAL, octava);
            case 5  -> new Note(NoteName.F, Accidentals.NATURAL, octava);
            case 6  -> new Note(NoteName.F, Accidentals.SHARP, octava); // F# por defecto
            case 7  -> new Note(NoteName.G, Accidentals.NATURAL, octava);
            case 8  -> new Note(NoteName.G, Accidentals.SHARP, octava); // G# por defecto
            case 9  -> new Note(NoteName.A, Accidentals.NATURAL, octava);
            case 10 -> new Note(NoteName.A, Accidentals.SHARP, octava); // A# por defecto
            case 11 -> new Note(NoteName.B, Accidentals.NATURAL, octava);
            default -> throw new IllegalStateException("Error matemático");
        };
    }

    // Convertimos el Enum a un valor numérico para la fórmula matemática
    private int getAccidentalNumericValue() {
        return switch (this.accidental) {
            case DOUBLE_FLAT -> -2;
            case FLAT        -> -1;
            case NATURAL     -> 0;
            case SHARP       -> 1;
            case DOUBLE_SHARP-> 2;
        };
    }

    public int getMidi() {
        int valorBase = switch (this.name) {
            case C -> 0;  case D -> 2;  case E -> 4;
            case F -> 5;  case G -> 7;  case A -> 9;  case B -> 11;
        };

        // Sumamos el valor numérico dinámico del enum
        return (this.octaves + 1) * 12 + valorBase + getAccidentalNumericValue();
    }

    @Override
    public String toString() {
        String simboloAlteracion = switch (this.accidental) {
            case DOUBLE_FLAT  -> "bb";
            case FLAT         -> "b";
            case SHARP        -> "#";
            case DOUBLE_SHARP -> "x";
            default           -> "";
        };
        return name.name() + simboloAlteracion + octaves + " (MIDI: " + getMidi() + ")";
    }

    public NoteName getName() {
        return this.name;
    }

}
