package grafo;

import dominio.Vuelo;
import lista.Lista;

public class Arista {
    private boolean existe;
    private Lista<Vuelo> vuelos;

    public Arista() {
        this.existe = false;
        this.vuelos = new Lista<>();
    }

    public boolean getExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public Lista<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(Lista<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public boolean existeVuelo(String codigoVuelo) {
        for (Vuelo v : vuelos) {
            if (v.getCodigoDeVuelo().equals(codigoVuelo)) {
                return true;
            }
        }
        return false;
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.insertar(vuelo);
    }
}
