package dominio;

import interfaz.Categoria;

public class Viajero implements Comparable<Viajero> {

    String cedula;
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
        return this.cedula.compareTo(o.cedula);
    }

    @Override
    public String toString() {
        String categoriaTexto;
        switch (categoria) {
            case ESTANDAR -> categoriaTexto = "Estándar";
            case FRECUENTE -> categoriaTexto = "Frecuente";
            case PLATINO -> categoriaTexto = "Platino";
            default -> categoriaTexto = categoria.toString();
        }
        return cedula + ";" + nombre + ";" + correo + ";" + edad + ";" + categoriaTexto;
    }
}
