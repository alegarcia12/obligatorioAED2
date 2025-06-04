package grafo;

import java.util.Objects;

public class Vertice implements Comparable<Vertice>{
    private String id;
    private String nombre;

    public Vertice(String id) {
        this.id = id;
    }

    public Vertice(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Objects.equals(id, vertice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id + ";" + nombre;
    }


    @Override
    public int compareTo(Vertice o) {
        if (o == null || getClass() != o.getClass()) return 0;
        Vertice otraCiudad = (Vertice) o;
        return this.id.compareTo(otraCiudad.id);
    }
}
