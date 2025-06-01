package lista;

import grafo.Vertice;

import java.util.Iterator;

public class Lista<T extends Comparable<T>> implements ILista<T>, Iterable<T> {

    protected Nodo<T> inicio;
    protected int largo;

    public Lista() {
        this.inicio = null;
        this.largo = 0;
    }

    @Override
    public void insertar(T dato) {
        inicio = new Nodo<T>(dato, inicio);
        largo++;
    }

    @Override
    public void borrar(T dato) {

    }

    @Override
    public int largo() {
        return largo;
    }

    @Override
    public boolean existe(T dato) {
        Nodo<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return true;
            }
            aux = aux.getSig();
        }
        return false;
    }

    @Override
    public T recuperar(T dato) {
        Nodo<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getDato();
            }
            aux = aux.getSig();
        }
        return null;
    }

    @Override
    public boolean esVacia() {
        return largo == 0;
    }

    @Override
    public boolean esLlena() {
        return false;
    }

    @Override
    public void imprimirDatos() {
        Nodo<T> aux = inicio;
        while (aux != null) {
            if (aux.getSig() != null) {
                System.out.print(aux.getDato() + " -> ");
            } else {
                System.out.print(aux.getDato());
            }
            aux = aux.getSig();
        }
        System.out.println();
    }

    public void imprimirDatosV2(Nodo<T> nodo) {
        if (nodo != null) {
            System.out.println(nodo.getDato());
            imprimirDatosV2(nodo.getSig());
        }
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> aux = inicio;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T dato = aux.dato;
                aux = aux.sig;
                return dato;
            }

            @Override
            public void remove() {
            }

        };
    }

    public T recuperar(int pos) {
        if (pos < 0 || pos >= largo) {
            return null;
        }
        Nodo<T> aux = inicio;
        for (int i = 0; i < pos; i++) {
            aux = aux.getSig();
        }
        return aux.getDato();
    }

    public void insertarAlFinal(T dato) {
        if (inicio == null) {
            inicio = new Nodo<>(dato);
        } else {
            Nodo<T> aux = inicio;
            while (aux.getSig() != null) {
                aux = aux.getSig();
            }
            aux.setSig(new Nodo<>(dato));
        }
        largo++;
    }

    public void agregarAlFinal(T elemento) {
        if (esVacia() || elemento.compareTo(inicio.getDato()) < 0) {
            inicio = new Nodo<>(elemento, inicio);
        } else {
            Nodo<T> aux = inicio;
            while (aux.getSig() != null && aux.getSig().getDato().compareTo(elemento) < 0) {
                aux = aux.getSig();
            }
            Nodo<T> nuevo = new Nodo<>(elemento, aux.getSig());
            aux.setSig(nuevo);
        }
        this.largo++;
    }

    public void ordenarLexicograficamentePorCodigo() {

        if (inicio == null || inicio.getSig() == null) return;

        boolean huboCambios;
        do {
            huboCambios = false;
            Nodo<T> actual = inicio;
            while (actual != null && actual.getSig() != null) {
                if (actual.getDato().compareTo(actual.getDato()) > 0) {
                    T temp = actual.getDato();
                    actual.setDato(actual.getSig().getDato());
                    actual.getSig().setDato(temp);
                    huboCambios = true;
                }
                actual = actual.getSig();
            }
        } while (huboCambios);

    }

    class Nodo<T> {
        private T dato;
        private Nodo<T> sig;

        public Nodo(T dato) {
            this.dato = dato;
            this.sig = null;
        }

        public Nodo(T dato, Nodo<T> sig) {
            this.dato = dato;
            this.sig = sig;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public Nodo<T> getSig() {
            return sig;
        }

        public void setSig(Nodo<T> sig) {
            this.sig = sig;
        }

        @Override
        public String toString() {
            return dato.toString();
        }

    }


}
