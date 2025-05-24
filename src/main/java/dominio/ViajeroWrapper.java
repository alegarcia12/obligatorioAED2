package dominio;

import java.util.Objects;

public class ViajeroWrapper implements Comparable<ViajeroWrapper> {


    private final Viajero viajero;

    public ViajeroWrapper(Viajero viajero) {
        this.viajero = viajero;
    }

    public ViajeroWrapper(String correo) {
        this.viajero = new Viajero("", "", correo, 0, null);
    }

    public Viajero getViajero() {
        return viajero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViajeroWrapper that = (ViajeroWrapper) o;
        return viajero.getCorreo().equals(that.viajero.getCorreo());
    }

    @Override
    public int compareTo(ViajeroWrapper o) {
        return viajero.getCorreo().compareTo(o.viajero.getCorreo());
    }

    @Override
    public String toString() {
        return viajero.toString();
    }
}
