package abb;

import lista.ILista;
import lista.Lista;

import java.util.Comparator;

public class ABB<T extends Comparable<T>> {

    private NodoABB<T> raiz;

    private final Comparator<T> comparador;

    public ABB() {
        this.comparador = null;
    }

    public ABB(Comparator<T> comparador) {
        this.comparador = comparador;
    }

    private int comparar(T dato1, T dato2) {
        if (this.comparador == null) {
            return dato1.compareTo(dato2);
        }
        return this.comparador.compare(dato1, dato2);
    }

    public void insertar(T dato) {
        if (raiz == null) {
            raiz = new NodoABB<>(dato);
        } else {
            insertar(raiz, dato);
        }
    }

    private void insertar(NodoABB<T> nodo, T dato) {
        if (comparar(dato, nodo.dato) < 0) {
            if (nodo.izq == null) {
                nodo.izq = new NodoABB<>(dato);
            } else {
                insertar(nodo.izq, dato);
            }
        } else {
            if (nodo.der == null) {
                nodo.der = new NodoABB<>(dato);
            } else {
                insertar(nodo.der, dato);
            }
        }
    }


    public boolean pertenece(T dato) {
        return pertenece(raiz, dato);
    }

    private boolean pertenece(NodoABB<T> nodo, T dato) {
        if (nodo != null) {
            if (dato.equals(nodo.dato)) {
                return true;
            } else if (comparar(dato, nodo.dato) < 0) {
                return pertenece(nodo.izq, dato);
            } else {
                return pertenece(nodo.der, dato);
            }
        } else {
            return false;
        }
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoABB<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.der == null && nodo.izq == null) {
            return 0;
        }
        return 1 + Math.max(altura(nodo.izq), altura(nodo.der));
    }

    //EXISTE VIAJERO NORMAL
    public boolean existe(T viajero) {
        return existeRec(raiz, viajero);
    }

    private boolean existeRec(NodoABB<T> nodo, T viajero) {
        if (nodo == null) {
            return false;
        }
        if (comparar(viajero, nodo.dato) == 0) {
            return true;
        } else if (comparar(viajero, nodo.dato) < 0) {
            return existeRec(nodo.izq, viajero);
        } else {
            return existeRec(nodo.der, viajero);
        }
    }

    //EXISTE VIAJERO CON CONTADOR
    public T existe(T viajero, int[] contador) {
        return existeConContador(raiz, viajero, contador);
    }

    private T existeConContador(NodoABB<T> nodo, T viajero, int[] contador) {
        if (nodo == null) {
            return null;
        }

        contador[0]++;

        if (comparar(viajero, nodo.dato) == 0) {
            return nodo.dato;
        } else if (comparar(viajero, nodo.dato) < 0) {
            return existeConContador(nodo.izq, viajero, contador);
        } else {
            return existeConContador(nodo.der, viajero, contador);
        }
    }

    //CLASE DE NODO
    private class NodoABB<Q> {
        private Q dato;
        private NodoABB<Q> izq;
        private NodoABB<Q> der;

        public NodoABB(Q dato) {
            this.dato = dato;
        }

        @Override
        public String toString() {
            return "{" + dato +
                    '}';
        }
    }


}



