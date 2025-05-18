package dominio;

import java.util.Comparator;

public class ComparadorPorCorreo implements Comparator<Viajero> {

    @Override
    public int compare(Viajero o1, Viajero o2) {
        return o1.getCorreo().compareTo(o2.getCorreo());
    }
}
