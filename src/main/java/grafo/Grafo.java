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


    public void borrarVertice(Vertice v) {
        int pos = obtenerPos(v);
        vertices[pos] = null;

        for (int i = 1; i < aristas.length; i++) {
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
        return aristas[posVinicial][posVfinal];
    }

    public Lista<Vertice> adyacentes(Vertice vertice) {
        int pos = obtenerPos(vertice);
        Lista<Vertice> adyacentes = new Lista<>();
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

    public void bfsConEscalasOriginal(Vertice v, int escala) {
        int posOrigen = obtenerPos(v);
        boolean[] visitados = new boolean[maxVertices];
        Cola<Tupla> cola = new Cola<>();

        visitados[posOrigen] = true;
        cola.encolar(new Tupla(posOrigen, 0)); // Encolar el vértice de origen con escala 0

        while (!cola.esVacia()) {
            Tupla tupla = cola.desencolar();

            if (tupla.getEscala() <= escala) {
                System.out.println("Escala: " + tupla.getEscala() + " en el vértice: " + vertices[tupla.getPos()]);

                System.out.println(vertices[tupla.getPos()] + " + " + tupla.getEscala());

                int pos = tupla.getPos();
                int escalaActual = tupla.getEscala();
                if (escalaActual < escala) { // Verificar si aún se pueden hacer escalas
                    for (int i = 0; i < aristas.length; i++) {
                        if (aristas[pos][i].getExiste() && !visitados[i]) {
                            visitados[i] = true;
                            cola.encolar(new Tupla(i, escalaActual + 1)); // Encolar el vértice adyacente con la nueva escala
                        }
                    }
                }
            }
        }
    }

    public void dijkstra(Vertice vOrigen) {
        int posOrigen = obtenerPos(vOrigen);
        boolean[] visitados = new boolean[maxVertices];
        int[] costos = new int[maxVertices];
        int[] anteriores = new int[maxVertices];

        for (int i = 0; i < maxVertices; i++) {
            costos[i] = Integer.MAX_VALUE; // Inicializar costos a infinito
            anteriores[i] = -1; // No hay anterior al inicio
            visitados[i] = false; // Ningún vértice visitado
        }
        costos[posOrigen] = 0; // El costo al origen es 0

        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerVericesNoVisitadoDeMenorCosto(visitados, costos);

            if (pos != -1) {
                visitados[pos] = true; // Marcamos el vértice como visitado

                for (int i = 0; i < aristas.length; i++) {
                    if (aristas[pos][i].getExiste() && !visitados[i]) {
                        int distanciaNueva = costos[pos] + aristas[pos][i].getPonderacion();
                        if (distanciaNueva < costos[i]) {
                            costos[i] = distanciaNueva; // Actualizamos el costo
                            anteriores[i] = pos; // Actualizamos el anterior
                        }
                    }
                }
            }
        }
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


    public void dijkstraConDestino(Vertice vOrigen, Vertice vDestino) {
        int posOrigen = obtenerPos(vOrigen);
        int posDestino = obtenerPos(vDestino);
        boolean[] visitados = new boolean[maxVertices];
        int[] costos = new int[maxVertices];
        int[] anteriores = new int[maxVertices];

        for (int i = 0; i < maxVertices; i++) {
            costos[i] = Integer.MAX_VALUE; // Inicializar costos a infinito
            anteriores[i] = -1; // No hay anterior al inicio
            visitados[i] = false; // Ningún vértice visitado
        }
        costos[posOrigen] = 0; // El costo al origen es 0

        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerVericesNoVisitadoDeMenorCosto(visitados, costos);

            if (pos != -1) {
                visitados[pos] = true; // Marcamos el vértice como visitado

                for (int i = 0; i < aristas.length; i++) {
                    if (aristas[pos][i].getExiste() && !visitados[i]) {
                        int distanciaNueva = costos[pos] + aristas[pos][i].getPonderacion();
                        if (distanciaNueva < costos[i]) {
                            costos[i] = distanciaNueva; // Actualizamos el costo
                            anteriores[i] = pos; // Actualizamos el anterior
                        }
                    }
                }
            }
        }

        int costoDestino = costos[posDestino];

        String camino = "";
        int posAux = posDestino;
        while (posAux != -1) {
            camino = vertices[posAux] + " -> " + camino;
            posAux = anteriores[posAux];
        }
        System.out.println("Camino desde " + vOrigen + " hasta " + vDestino + ": " + " Camino: " + camino + " con costo: " + costoDestino);
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

    public Vuelo obtenerVuelo(Vertice ciudadOrigen, Vertice ciudadDestino, TipoVueloPermitido tipoVueloPermitido) {
        int posCiudadOrigen = obtenerPos(ciudadOrigen);
        int posCiudadDestino = obtenerPos(ciudadDestino);
        Arista arista = aristas[posCiudadOrigen][posCiudadDestino];

        if (arista.getExiste()) {
            for (Vuelo vuelo : arista.getVuelos()) {
                if (vuelo.getTipoVuelo().equals(tipoVueloPermitido) || tipoVueloPermitido == TipoVueloPermitido.AMBOS) {
                    return vuelo;
                }
            }
        }
        return null; // No se encontró un vuelo válido
    }
}

