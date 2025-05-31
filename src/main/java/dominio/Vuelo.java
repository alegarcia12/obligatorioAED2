package dominio;

import interfaz.TipoVuelo;

import java.util.Objects;

public class Vuelo implements Comparable<Vuelo> {
    private String codigoOriden;
    private String codigoDestino;
    private String codigoDeVuelo;
    private Double combustible;
    private Double minutos;
    private Double costoEnDolares;
    private TipoVuelo tipoVuelo;

    public Vuelo(String codigoOriden, String codigoDestino, String codigoDeVuelo, Double combustible, Double minutos, Double costoEnDolares, TipoVuelo tipoVuelo) {
        this.codigoOriden = codigoOriden;
        this.codigoDestino = codigoDestino;
        this.codigoDeVuelo = codigoDeVuelo;
        this.combustible = combustible;
        this.minutos = minutos;
        this.costoEnDolares = costoEnDolares;
        this.tipoVuelo = tipoVuelo;
    }

    public String getCodigoOriden() {
        return codigoOriden;
    }

    public void setCodigoOriden(String codigoOriden) {
        this.codigoOriden = codigoOriden;
    }

    public String getCodigoDestino() {
        return codigoDestino;
    }

    public void setCodigoDestino(String codigoDestino) {
        this.codigoDestino = codigoDestino;
    }

    public String getCodigoDeVuelo() {
        return codigoDeVuelo;
    }

    public void setCodigoDeVuelo(String codigoDeVuelo) {
        this.codigoDeVuelo = codigoDeVuelo;
    }

    public Double getCombustible() {
        return combustible;
    }

    public void setCombustible(Double combustible) {
        this.combustible = combustible;
    }

    public Double getMinutos() {
        return minutos;
    }

    public void setMinutos(Double minutos) {
        this.minutos = minutos;
    }

    public Double getCostoEnDolares() {
        return costoEnDolares;
    }

    public void setCostoEnDolares(Double costoEnDolares) {
        this.costoEnDolares = costoEnDolares;
    }

    public TipoVuelo getTipoVuelo() {
        return tipoVuelo;
    }

    public void setTipoVuelo(TipoVuelo tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vuelo vuelo = (Vuelo) o;
        return Objects.equals(codigoDeVuelo, vuelo.codigoDeVuelo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigoDeVuelo);
    }

    @Override
    public int compareTo(Vuelo o) {
        return 0;
    }
}
