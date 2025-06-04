package grafo;

import cola.Cola;
import dominio.Vuelo;
import interfaz.TipoVueloPermitido;
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

    public void agregarArista(Vertice vInicio, Vertice vFinal, Arista arista) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        Arista a = aristas[posVinicial][posVfinal];
        a.setExiste(true);
    }

    public Arista obtenerArista(Vertice vInicio, Vertice vFinal) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        return aristas[posVinicial][posVfinal];
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

    public boolean existeArista(Vertice vInicio, Vertice vFinal) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        return aristas[posVinicial][posVfinal].getExiste();
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

    //RECORRIDAS ///////////////////////////////////////////

    public Lista<Vertice> bfsConEscalas(Vertice v, int cant) {
        int posV = obtenerPos(v);
        boolean[] visitados = new boolean[maxVertices];
        int[] niveles = new int[maxVertices];
        Cola<Integer> cola = new Cola<>();
        Lista<Vertice> result = new Lista<>();

        visitados[posV] = true;
        niveles[posV] = 0;
        cola.encolar(posV);

        while (!cola.esVacia()) {
            int pos = cola.desencolar();

            if (niveles[pos] <= cant) {
                result.agregarAlFinal(vertices[pos]);
            }

            for (int i = 0; i < maxVertices; i++) {
                if (aristas[pos][i] != null &&
                        aristas[pos][i].getExiste() &&
                        !visitados[i] &&
                        aristas[pos][i].getVuelos().largo() > 0) {

                    visitados[i] = true;
                    niveles[i] = niveles[pos] + 1;

                    if (niveles[i] <= cant) {
                        cola.encolar(i);
                    }
                }
            }
        }

        return result;
    }

    public String dijkstraConDestinoYCostoMinutos(Vertice vOrigen, Vertice vDestino, TipoVueloPermitido tipoVueloPermitido, double[] costoTotal) {
        int posOrigen = obtenerPos(vOrigen);
        int posDestino = obtenerPos(vDestino);

        boolean[] visitados = new boolean[maxVertices];
        double[] costos = new double[maxVertices];
        int[] anteriores = new int[maxVertices];

        for (int i = 0; i < maxVertices; i++) {
            costos[i] = Double.MAX_VALUE;
            anteriores[i] = -1;
        }
        costos[posOrigen] = 0;

        for (int i = 0; i < cantidad; i++) {
            int posActual = obtenerVericesNoVisitadoDeMenorCostoDouble(visitados, costos);

            if (posActual != -1) {

                visitados[posActual] = true;

                for (int j = 0; j < cantidad; j++) {
                    if (!visitados[j]) {
                        Arista arista = aristas[posActual][j];
                        if (arista != null && arista.getExiste()) {
                            for (Vuelo vuelo : arista.getVuelos()) {
                                if (tipoVueloPermitido == TipoVueloPermitido.AMBOS
                                        || vuelo.getTipoVuelo().getTexto().equals(tipoVueloPermitido.getTexto())) {
                                    double nuevoCosto = costos[posActual] + vuelo.getMinutos();
                                    if (nuevoCosto < costos[j]) {
                                        costos[j] = nuevoCosto;
                                        anteriores[j] = posActual;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (costos[posDestino] == Double.MAX_VALUE) {
            return null; // no hay camino
        }

        // reconstrucción del camino
        StringBuilder camino = new StringBuilder();
        int pos = posDestino;
        while (pos != -1) {
            camino.insert(0, vertices[pos].getId() + ";" + vertices[pos].getNombre() + "|");
            pos = anteriores[pos];
        }

        if (camino.length() > 0) camino.setLength(camino.length() - 1);// Quita la ultima barra "|"

        costoTotal[0] = costos[posDestino];// asignar el costo total al primer elemento del array
        return camino.toString();
    }


    public String dijkstraConDestinoYCostoDolares(Vertice vOrigen, Vertice vDestino, TipoVueloPermitido tipoVueloPermitido, double[] costoTotal) {
        int posOrigen = obtenerPos(vOrigen);
        int posDestino = obtenerPos(vDestino);

        boolean[] visitados = new boolean[maxVertices];
        double[] costos = new double[maxVertices];
        int[] anteriores = new int[maxVertices];

        for (int i = 0; i < maxVertices; i++) {
            costos[i] = Double.MAX_VALUE;
            anteriores[i] = -1;
        }
        costos[posOrigen] = 0;

        for (int i = 0; i < cantidad; i++) {
            int posActual = obtenerVericesNoVisitadoDeMenorCostoDouble(visitados, costos);

            if (posActual != -1) {

                visitados[posActual] = true;

                for (int j = 0; j < cantidad; j++) {
                    if (!visitados[j]) {
                        Arista arista = aristas[posActual][j];
                        if (arista != null && arista.getExiste()) {
                            for (Vuelo vuelo : arista.getVuelos()) {
                                if (tipoVueloPermitido == TipoVueloPermitido.AMBOS
                                        || vuelo.getTipoVuelo().getTexto().equals(tipoVueloPermitido.getTexto())) {
                                    double nuevoCosto = costos[posActual] + vuelo.getCostoEnDolares();
                                    if (nuevoCosto < costos[j]) {
                                        costos[j] = nuevoCosto;
                                        anteriores[j] = posActual;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        if (costos[posDestino] == Double.MAX_VALUE) {
            return null; // no hay camino
        }

        // reconstrucción del camino
        StringBuilder camino = new StringBuilder();
        int pos = posDestino;
        while (pos != -1) {
            camino.insert(0, vertices[pos].getId() + ";" + vertices[pos].getNombre() + "|");
            pos = anteriores[pos];
        }

        if (camino.length() > 0) camino.setLength(camino.length() - 1); // Quita la ultima barra "|"

        costoTotal[0] = costos[posDestino]; // asignar el costo total al primer elemento del array
        return camino.toString();
    }

    private int obtenerVericesNoVisitadoDeMenorCostoDouble(boolean[] visitados, double[] costos) {
        int posMin = -1;
        double min = Double.MAX_VALUE;

        for (int i = 0; i < maxVertices; i++) {
            if (!visitados[i] && costos[i] < min) {
                min = costos[i];
                posMin = i;
            }
        }
        return posMin;
    }

    private int obtenerVericesNoVisitadoDeMenorCosto(boolean[] visitados, int[] costos) {
        int posMin = -1;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < maxVertices; i++) {
            if (!visitados[i] && costos[i] < min) {
                min = costos[i];
                posMin = i;
            }
        }
        return posMin;
    }

    public Vertice obtenerVertice(String codigoCiudadOrigen) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] != null && vertices[i].getId().equals(codigoCiudadOrigen)) {
                return vertices[i];
            }
        }
        return null; // No se encontró el vértice
    }
}

