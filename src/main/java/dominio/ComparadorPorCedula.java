package dominio;

import java.util.Comparator;

public class ComparadorPorCedula implements Comparator<Viajero> {

    @Override
    public int compare(Viajero v1, Viajero v2) {
        return v1.getCedula().compareTo(v2.getCedula());
    }
}
