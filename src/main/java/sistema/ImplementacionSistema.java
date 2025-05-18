package sistema;

import abb.ABB;
import dominio.*;
import interfaz.*;

public class ImplementacionSistema implements Sistema {

    private int maxCiudades;
    private ABB<Ciudad> ciudadesABB;
    private ABB<Viajero> viajerosCedulaABB;
    private ABB<Viajero> viajerosCorreoABB;
    private boolean sistemaInicializado = false;

    @Override
    public Retorno inicializarSistema(int maxCiudades) {
        if (maxCiudades <= 4) {
            return Retorno.error1("Maximo de ciudades debe ser mayor a 4");
        }
        this.maxCiudades = maxCiudades;
        sistemaInicializado = true;
        this.ciudadesABB = new ABB<>();
        this.viajerosCedulaABB = new ABB<>(new ComparadorPorCedula());
        this.viajerosCorreoABB = new ABB<>(new ComparadorPorCorreo());
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
        viajerosCorreoABB.insertar(viajero);
        viajerosCedulaABB.insertar(viajero);
        return Retorno.ok();
    }

    private boolean existeViajeroCorreo(String correo) {
        return viajerosCorreoABB.existe(new Viajero("", "", correo, 0, null));
    }

    private boolean existeViajeroCedula(String cedula) {
        return viajerosCedulaABB.existe(new Viajero(cedula, "", "", 0, null));
    }

    private boolean formatoValidoCorreo(String correo) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
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

        int [] contador = new int[1];
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
        int [] contador = new int[1];
        Viajero viajero = viajerosCorreoABB.existe(new Viajero("", "", correo, 0, null), contador);
        if (viajero == null) {
            return Retorno.error3("El viajero no existe");
        }
        return Retorno.ok(contador[0], viajero.toString());
    }

    @Override
    public Retorno listarViajerosPorCedulaAscendente() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarViajerosPorCedulaDescendente() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarViajerosPorCorreoAscendente() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarViajerosPorCategoria(Categoria unaCategoria) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarViajerosDeUnRangoAscendente(int rango) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarCiudad(String codigo, String nombre) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarVuelo(String codigoCiudadOrigen, String codigoCiudadDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipoDeVuelo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno actualizarVuelo(String codigoCiudadOrigen, String codigoCiudadDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipoDeVuelo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listadoCiudadesCantDeEscalas(String codigoCiudadOrigen, int cantidad) {
        return Retorno.noImplementada();
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
