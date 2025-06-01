package grafo;

import dominio.Vuelo;
import interfaz.TipoVuelo;
import lista.Lista;

public class Arista {
    private boolean existe;
    private Lista<Vuelo> vuelos;
    private int ponderacion;

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

    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
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

    public void actulizarVuelo(String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipoDeVuelo) {
        for (Vuelo v : vuelos) {
            if (v.getCodigoDeVuelo().equals(codigoDeVuelo)) {
                v.setCombustible(combustible);
                v.setMinutos(minutos);
                v.setCostoEnDolares(costoEnDolares);
                v.setTipoVuelo(tipoDeVuelo);
                return;
            }
        }
    }
}
