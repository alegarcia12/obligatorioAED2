package cola;

public class Cola<T> implements ICola<T> {

    private NodoCola<T> inicio;
    private NodoCola<T> fin;
    private int cant;

    @Override
    public void encolar(T dato) {
        if (fin == null)
            fin = inicio = new NodoCola<T>(dato);
        else {
            fin.setSig(new NodoCola<T>(dato));
            fin = fin.getSig();
        }
        cant++;
    }

    @Override
    public T desencolar() {
        T dato = inicio.getDato();
        inicio = inicio.getSig();
        if (inicio == null)
            fin = null;
        cant--;
        return dato;
    }

    @Override
    public boolean esVacia() {
        return cant == 0;
    }

    @Override
    public boolean estaLlena() {
        return false;
    }

    @Override
    public int cantElementos() {
        return cant;
    }

    @Override
    public T frente() {
        return inicio.getDato();
    }

    @Override
    public void imprimirDatos() {
        NodoCola<T> aux = inicio;
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

}
