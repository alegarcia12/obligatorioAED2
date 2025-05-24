package grafo;

import lista.ILista;
import lista.Lista;

public class GrafoEnteros {

    private int[][] aristas;
    private boolean[] vertices;
    private int cantidad;
    private final int maxVertices;
    private final boolean dirigido;

    public GrafoEnteros(int cantMaxVertices, boolean esDirigido) {
        cantidad = 0;
        vertices = new boolean[cantMaxVertices + 1];
        aristas = new int[cantMaxVertices + 1][cantMaxVertices + 1];
        maxVertices = cantMaxVertices;
        dirigido = esDirigido;
    }

    public void agregarVertice(int vertice) {
        vertices[vertice] = true;
        cantidad++;
    }

    public void borrarVertice(int vertice) {
        vertices[vertice] = false;

        for (int i = 1; i < aristas.length; i++) {
            aristas[vertice][i] = 0; //Borro aristas adyacentes
            aristas[i][vertice] = 0; //Borro aristas incidentes
        }
        cantidad--;
    }

    public void agregarArista(int vInicio, int vFinal, int peso) {
        aristas[vInicio][vFinal] = peso;
        if (!dirigido) {
            aristas[vFinal][vInicio] = peso;
        }
    }

    public void borrarArista(int vInicio, int vFinal) {
        aristas[vInicio][vFinal] = 0;
        if (!dirigido) {
            aristas[vFinal][vInicio] = 0;
        }
    }

    public int obtenerArista(int vInicio, int vFinal) {
        return aristas[vInicio][vFinal];
    }

    public ILista<Integer> adyacentes(int vertice) {
        ILista<Integer> adyacentes = new Lista<>();
        for (int i = 0; i < aristas.length; i++) {
            if (aristas[vertice][i] > 0) {
                adyacentes.insertar(i);
            }
        }
        return adyacentes;
    }

}
