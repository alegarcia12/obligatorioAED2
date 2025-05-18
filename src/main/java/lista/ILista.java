package lista;

import java.util.Iterator;

public interface ILista<T> {

    void insertar(T dato);
    void borrar(T dato);
    int largo();
    boolean existe(T dato);
    T recuperar(T dato);
    boolean esVacia();
    boolean esLlena();
    void imprimirDatos();
    Iterator<T> iterator();
}
