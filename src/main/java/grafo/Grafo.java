package grafo;

import cola.Cola;
import lista.ILista;
import lista.Lista;

public class Grafo {

    private Arista[][] aristas;
    private Vertice[] vertices;
    private int cantidad;
    private final int maxVertices;
    private final boolean dirigido;

    public Grafo(int cantMaxVertices, boolean esDirigido) {
        cantidad = 0;
        vertices = new Vertice[cantMaxVertices];
        aristas = new Arista[cantMaxVertices][cantMaxVertices];
        if (esDirigido) {
            for (int i = 0; i < aristas.length; i++) {
                for (int j = 0; j < aristas.length; j++) {
                    aristas[i][j] = new Arista();
                }
            }
        } else {
            for (int i = 0; i < aristas.length; i++) {
                for (int j = i; j < aristas.length; j++) {
                    Arista arista = new Arista();
                    aristas[i][j] = arista;
                    aristas[j][i] = arista; // Asignar la misma arista en ambas direcciones
                }
            }
        }
        maxVertices = cantMaxVertices;
        dirigido = esDirigido;
    }

    public void agregarVertice(Vertice vertice) {
        if (cantidad < maxVertices) {
            int posLibre = obtenerPosLibre();
            vertices[posLibre] = vertice;
            cantidad++;
        }
    }


    public void borrarVertice(Vertice v) {
        int pos = obtenerPos(v);
        vertices[pos] = null;

        for (int i = 0; i < aristas.length; i++) {
            aristas[pos][i].setExiste(false); //Borro aristas adyacentes
            aristas[i][pos].setExiste(false); //Borro aristas incidentes
        }
        cantidad--;
    }

    public void agregarArista(Vertice vInicio, Vertice vFinal, Arista arista) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        Arista a = aristas[posVinicial][posVfinal];
        a.setExiste(true);
    }

    public void borrarArista(Vertice vInicio, Vertice vFinal) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        aristas[posVinicial][posVfinal].setExiste(false);

    }

    public Arista obtenerArista(Vertice vInicio, Vertice vFinal) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        Arista arista = aristas[posVinicial][posVfinal];
        return (arista != null && arista.getExiste()) ? arista : null;
    }

    public boolean existeArista(Vertice vInicio, Vertice vFinal) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        return aristas[posVinicial][posVfinal].getExiste();
    }

    public ILista<Vertice> adyacentes(Vertice vertice) {
        int pos = obtenerPos(vertice);
        ILista<Vertice> adyacentes = new Lista<>();
        for (int i = 0; i < aristas.length; i++) {
            if (aristas[pos][i].getExiste()) {
                adyacentes.insertar(vertices[i]);
            }
        }
        return adyacentes;
    }

    private int obtenerPos(Vertice v) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] != null && vertices[i].equals(v)) {
                return i;
            }
        }
        return -1; // No se encontró el vértice
    }

    private int obtenerPosLibre() {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                return i;
            }
        }
        return -1; // No hay espacio libre
    }

    //RECORRIDAS ///////////////////////////////////////////

    public void dfs(Vertice v) {
        boolean[] visitados = new boolean[maxVertices];
        int pos = obtenerPos(v);
        dfsRecursivo(pos, visitados);
    }

    private void dfsRecursivo(int pos, boolean[] visitados) {
        System.out.println(vertices[pos]);
        visitados[pos] = true;
        for (int i = 0; i < aristas.length; i++) {
            if (aristas[pos][i].getExiste() && !visitados[i]) {
                dfsRecursivo(i, visitados);
            }
        }
    }

    public void bfs(Vertice v) {
        int posV = obtenerPos(v);
        boolean[] visitados = new boolean[maxVertices];
        Cola<Integer> cola = new Cola<>();
        visitados[posV] = true;
        cola.encolar(posV);

        while (!cola.esVacia()) {
            int pos = cola.desencolar();
            System.out.println(vertices[pos] + " ");
            for (int i = 0; i < aristas.length; i++) {
                if (aristas[pos][i].getExiste() && !visitados[i]) {
                    visitados[i] = true;
                    cola.encolar(i);
                }
            }
        }
    }

    public int cantVertices() {
        return cantidad;
    }

    public boolean existeVertice(Vertice ciudadVertice) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] != null && vertices[i].equals(ciudadVertice)) {
                return true;
            }
        }
        return false;
    }

    /// copilot
//    public void dijkstra(Vertice v) {
//        int posV = obtenerPos(v);
//        boolean[] visitados = new boolean[maxVertices];
//        int[] distancias = new int[maxVertices];
//        Vertice[] predecesores = new Vertice[maxVertices];
//
//        for (int i = 0; i < maxVertices; i++) {
//            distancias[i] = Integer.MAX_VALUE;
//            predecesores[i] = null;
//        }
//        distancias[posV] = 0;
//
//        Cola<Integer> cola = new Cola<>();
//        cola.encolar(posV);
//
//        while (!cola.esVacia()) {
//            int pos = cola.desencolar();
//            visitados[pos] = true;
//
//            for (int i = 0; i < aristas.length; i++) {
//                if (aristas[pos][i].getExiste() && !visitados[i]) {
//                    int nuevaDistancia = distancias[pos] + aristas[pos][i].getPeso();
//                    if (nuevaDistancia < distancias[i]) {
//                        distancias[i] = nuevaDistancia;
//                        predecesores[i] = vertices[pos];
//                        cola.encolar(i);
//                    }
//                }
//            }
//        }

    // Imprimir resultados
//        for (int i = 0; i < maxVertices; i++) {
//            if (distancias[i] != Integer.MAX_VALUE) {
//                System.out.println("Distancia desde " + vertices[posV] + " a " + vertices[i] + ": " + distancias[i]);
//                System.out.print("Ruta: " + vertices[i]);
//                Vertice pred = predecesores[i];
//                while (pred != null) {
//                    System.out.print(" <- " + pred);
//                    pred = predecesores[obtenerPos(pred)];
//                }
//                System.out.println();
//            } else {
//                System.out.println("No hay camino desde " + vertices[posV] + " a " + vertices[i]);
//            }
    // }
    // }


}
