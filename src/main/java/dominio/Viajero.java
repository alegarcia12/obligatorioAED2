package dominio;

import interfaz.Categoria;

import java.util.Objects;

public class Viajero implements Comparable<Viajero> {

    String cedula;
    int cedulaSanitizada;
    String nombre;
    String correo;
    int edad;
    Categoria categoria;

    public Viajero(String cedula, String nombre, String correo, int edad, Categoria categoria) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.categoria = categoria;
    }

    public Viajero(String cedula, int cedulaSanitizada, String nombre, String correo, int edad, Categoria categoria) {
        this.cedula = cedula;
        this.cedulaSanitizada = cedulaSanitizada;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.categoria = categoria;
    }

    public Viajero(int cedulaSanitizada) {
        this.cedulaSanitizada = cedulaSanitizada;

    }


    public int getCedulaSanitizada() {
        return cedulaSanitizada;
    }

    public void setCedulaSanitizada(int cedulaSanitizada) {
        this.cedulaSanitizada = cedulaSanitizada;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int compareTo(Viajero o) {
        return Integer.compare(this.cedulaSanitizada, o.cedulaSanitizada);
    }

    @Override
    public String toString() {
        return cedula + ";" + nombre + ";" + correo + ";" + edad + ";" + categoria.getTexto();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Viajero viajero = (Viajero) o;
        return cedulaSanitizada == viajero.cedulaSanitizada;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cedulaSanitizada);
    }
}
