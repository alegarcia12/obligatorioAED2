package sistema;

import abb.ABB;
import dominio.*;
import grafo.Grafo;
import grafo.Vertice;
import interfaz.*;

public class ImplementacionSistema implements Sistema {

    private int maxCiudades;
    private ABB<Viajero> viajerosCedulaABB;
    private ABB<ViajeroWrapper> viajerosCorreoABB;
    private boolean sistemaInicializado = false;
    private ABB <Viajero> viajerosEstandar;
    private ABB <Viajero> viajerosFrecuentes;
    private ABB <Viajero> viajerosPlatinos;
//    private ABB<Viajero> viajeroRango0;
//    private ABB<Viajero> viajeroRango1;
//    private ABB<Viajero> viajeroRango2;
//    private ABB<Viajero> viajeroRango3;
//    private ABB<Viajero> viajeroRango4;
//    private ABB<Viajero> viajeroRango5;
//    private ABB<Viajero> viajeroRango6;
//    private ABB<Viajero> viajeroRango7;
//    private ABB<Viajero> viajeroRango8;
//    private ABB<Viajero> viajeroRango9;
//    private ABB<Viajero> viajeroRango10;
//    private ABB<Viajero> viajeroRango11;
//    private ABB<Viajero> viajeroRango12;
//    private ABB<Viajero> viajeroRango13;
    private ABB<Viajero>[] viajerosPorRango = new ABB[14];
    private Grafo ciudadesGrafo;

    @Override
    public Retorno inicializarSistema(int maxCiudades) {
        if (maxCiudades <= 4) {
            return Retorno.error1("Maximo de ciudades debe ser mayor a 4");
        }
        this.maxCiudades = maxCiudades;
        sistemaInicializado = true;
        this.viajerosCedulaABB = new ABB<>();
        this.viajerosCorreoABB = new ABB<>();
        this.viajerosEstandar = new ABB<>();
        this.viajerosFrecuentes = new ABB<>();
        this.viajerosPlatinos = new ABB<>();
//        this.viajeroRango0 = new ABB<>();
//        this.viajeroRango1 = new ABB<>();
//        this.viajeroRango2 = new ABB<>();
//        this.viajeroRango3 = new ABB<>();
//        this.viajeroRango4 = new ABB<>();
//        this.viajeroRango5 = new ABB<>();
//        this.viajeroRango6 = new ABB<>();
//        this.viajeroRango7 = new ABB<>();
//        this.viajeroRango8 = new ABB<>();
//        this.viajeroRango9 = new ABB<>();
//        this.viajeroRango10 = new ABB<>();
//        this.viajeroRango11 = new ABB<>();
//        this.viajeroRango12 = new ABB<>();
//        this.viajeroRango13 = new ABB<>();
        for (int i = 0; i < 14; i++) {
            viajerosPorRango[i] = new ABB<>();
        }
        this.ciudadesGrafo = new Grafo(maxCiudades, true);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarViajero(String cedula, String nombre, String correo, int edad, Categoria categoria) {
        if (categoria == null || cedula == null
                || nombre == null || correo == null || cedula.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
            return Retorno.error1("Los campos no pueden estar vacios");
        }
        if (!formatoValidoCedula(cedula)) {
            return Retorno.error2("Formato de cedula incorrecto");
        }
        if (!formatoValidoCorreo(correo)) {
            return Retorno.error3("Formato de correo incorrecto");
        }
        if (edad < 0 || edad > 139) {
            return Retorno.error4("Edad no valida");
        }
        if (existeViajeroCedula(cedula)) {
            return Retorno.error5("El viajero ya existe");
        }
        if (existeViajeroCorreo(correo)) {
            return Retorno.error6("El correo ya existe");
        }

        Viajero viajero = new Viajero(cedula, nombre, correo, edad, categoria);
        viajerosCedulaABB.insertar(viajero);
        ViajeroWrapper viajeroCorreoWrapper = new ViajeroWrapper(viajero);
        viajerosCorreoABB.insertar(viajeroCorreoWrapper);
        insertarSegunCategoria(viajero);
        int rango = obtenerRangoEdad(edad);
        obtenerABBRango(rango).insertar(viajero);
        return Retorno.ok();
    }

    private int obtenerRangoEdad(int edad) {
        return edad /10;
    }

    private void insertarSegunCategoria(Viajero viajero) {
        switch (viajero.getCategoria()) {
            case ESTANDAR:
                viajerosEstandar.insertar(viajero);
                break;
            case FRECUENTE:
                viajerosFrecuentes.insertar(viajero);
                break;
            case PLATINO:
                viajerosPlatinos.insertar(viajero);
                break;
        }
    }

    private boolean existeViajeroCorreo(String correo) {
        ViajeroWrapper viajeroWrapper = new ViajeroWrapper(correo);
        ViajeroWrapper viajeroWrapperEncontrado = viajerosCorreoABB.existe(viajeroWrapper);
        return viajeroWrapperEncontrado != null;
    }

    private boolean existeViajeroCedula(String cedula) {
        Viajero viajero = viajerosCedulaABB.existe(new Viajero(cedula, "", "", 0, null));
        return viajero != null;
    }

    private boolean formatoValidoCorreo(String correo) {
        String regex = "^(?!.*\\.\\.)[\\w.-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-zA-Z]{2,}$";
        return correo.matches(regex);
    }

    private boolean formatoValidoCedula(String cedula) {
        String regex = "^(\\d\\.\\d{3}\\.\\d{3}-\\d|\\d{3}\\.\\d{3}-\\d)$";
        return cedula.matches(regex);
    }

    @Override
    public Retorno buscarViajeroPorCedula(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            return Retorno.error1("Cedula no puede estar vacia");
        }
        if (!formatoValidoCedula(cedula)) {
            return Retorno.error2("Formato de cedula incorrecto");
        }

        int[] contador = new int[1];
        Viajero viajero = viajerosCedulaABB.existe(new Viajero(cedula, "", "", 0, null), contador);
        if (viajero == null) {
            return Retorno.error3("El viajero no existe");
        }
        return Retorno.ok(contador[0], viajero.toString());
    }

    @Override
    public Retorno buscarViajeroPorCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            return Retorno.error1("Correo no puede estar vacio");
        }
        if (!formatoValidoCorreo(correo)) {
            return Retorno.error2("Formato de correo incorrecto");
        }
        int[] contador = new int[1];
        ViajeroWrapper viajeroWrapper = new ViajeroWrapper(correo);
        ViajeroWrapper viajeroWrapperEncontrado = viajerosCorreoABB.existe(viajeroWrapper, contador);
        if (viajeroWrapperEncontrado == null) {
            return Retorno.error3("El viajero no existe");
        }
        return Retorno.ok(contador[0], viajeroWrapperEncontrado.toString());
    }

    @Override
    public Retorno listarViajerosPorCedulaAscendente() {
        String viajerosPorCedula = viajerosCedulaABB.listarAscendente();
        return Retorno.ok(viajerosPorCedula);
    }

    @Override
    public Retorno listarViajerosPorCedulaDescendente() {
        String viajerosPorCedula = viajerosCedulaABB.listarDescendente();
        return Retorno.ok(viajerosPorCedula);
    }

    @Override
    public Retorno listarViajerosPorCorreoAscendente() {
        String viajerosPorCorreo = viajerosCorreoABB.listarAscendente();
        return Retorno.ok(viajerosPorCorreo);
    }

    @Override
    public Retorno listarViajerosPorCategoria(Categoria unaCategoria) {
        switch(unaCategoria){
            case ESTANDAR:
                return Retorno.ok(viajerosEstandar.listarAscendente());
            case FRECUENTE:
                return Retorno.ok(viajerosFrecuentes.listarAscendente());
            case PLATINO:
                return Retorno.ok(viajerosPlatinos.listarAscendente());
            default:
                return Retorno.ok();
        }
    }

    @Override
    public Retorno listarViajerosDeUnRangoAscendente(int rango) {
        if(rango < 0){
            return Retorno.error1("Rango no puede ser menor a 0");
        }
        if(rango > 13){
            return Retorno.error2("Rango no puede ser mayor a 13");
        }
        ABB<Viajero> viajerosRango = obtenerABBRango(rango);
        return Retorno.ok(viajerosRango.listarAscendente());
    }

    private ABB<Viajero> obtenerABBRango(int rango) {
//        switch (rango) {
//            case 0:
//                return viajeroRango0;
//            case 1:
//                return viajeroRango1;
//            case 2:
//                return viajeroRango2;
//            case 3:
//                return viajeroRango3;
//            case 4:
//                return viajeroRango4;
//            case 5:
//                return viajeroRango5;
//            case 6:
//                return viajeroRango6;
//            case 7:
//                return viajeroRango7;
//            case 8:
//                return viajeroRango8;
//            case 9:
//                return viajeroRango9;
//            case 10:
//                return viajeroRango10;
//            case 11:
//                return viajeroRango11;
//            case 12:
//                return viajeroRango12;
//            case 13:
//                return viajeroRango13;
//            default:
//                return null;
//        }
        return viajerosPorRango[rango];
    }

    @Override//agregar vertice
    public Retorno registrarCiudad(String codigo, String nombre) {
        if (ciudadesGrafo.cantVertices() >= maxCiudades) {
            return Retorno.error1("No se pueden registrar mas ciudades");
        }
        if (codigo == null || nombre == null || codigo.isEmpty() || nombre.isEmpty()) {
            return Retorno.error2("Los campos no pueden estar vacios");
        }
        if(existeCiudad(codigo)) {
            return Retorno.error3("La ciudad ya existe");
        }
        Ciudad ciudad = new Ciudad(codigo, nombre);
        Vertice ciudadVertice = new Vertice(ciudad.getCodigo());

        ciudadesGrafo.agregarVertice(ciudadVertice);
        return Retorno.ok();
    }

    private boolean existeCiudad(String codigo) {
        Vertice ciudadVertice = new Vertice(codigo);
        return ciudadesGrafo.existeVertice(ciudadVertice);
    }

    @Override//agregar arista
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino) {
        if (codigoCiudadOrigen == null || codigoCiudadDestino == null || codigoCiudadOrigen.isEmpty() || codigoCiudadDestino.isEmpty()) {
            return Retorno.error1("Los campos no pueden estar vacios");
        }
        if (!existeCiudad(codigoCiudadOrigen)) {
            return Retorno.error2("La ciudad de origen no existe");
        }
        if (!existeCiudad(codigoCiudadDestino)) {
            return Retorno.error3("La ciudad de destino no existe");
        }
        Vertice origen = new Vertice(codigoCiudadOrigen);
        Vertice destino = new Vertice(codigoCiudadDestino);
        if (ciudadesGrafo.obtenerArista(origen, destino) != null) {
            return Retorno.error4("Ya existe una conexion entre las ciudades");
        }
        ciudadesGrafo.agregarArista(origen, destino, new Arista(0, 0, 0, TipoVuelo.NINGUNO));
        return Retorno.ok();

        }
    }

    @Override
    public Retorno registrarVuelo(String codigoCiudadOrigen, String codigoCiudadDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipoDeVuelo) {
        return Retorno.noImplementada();//agregar nueva info de arista
    }

    @Override
    public Retorno actualizarVuelo(String codigoCiudadOrigen, String codigoCiudadDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipoDeVuelo) {
        return Retorno.noImplementada();//modificar info de arista
    }

    @Override
    public Retorno listadoCiudadesCantDeEscalas(String codigoCiudadOrigen, int cantidad) {
        return Retorno.noImplementada(); // recorrida con bfs
    }

    @Override
    public Retorno viajeCostoMinimoMinutos(String codigoCiudadOrigen, String codigoCiudadDestino, TipoVueloPermitido tipoVueloPermitido) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoDolares(String codigoCiudadOrigen, String codigoCiudadDestino, TipoVueloPermitido tipoVueloPermitido) {
        return Retorno.noImplementada();
    }

}
