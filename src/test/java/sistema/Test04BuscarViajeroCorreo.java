
package sistema;

import interfaz.Categoria;
import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test04BuscarViajeroCorreo {
    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(10);
    }

    @Test
    void buscarViajeroOk() {
        s.registrarViajero("1.914.689-5", "Guillermo", "guille@ort.edu.uy", 35, Categoria.ESTANDAR);
        s.registrarViajero("1.914.689-6", "Guillermo", "guille@ort.edu.uy", 35, Categoria.PLATINO);
        s.registrarViajero("1.914.689-7", "Guillermo", "guille@ort.edu.uy", 35, Categoria.FRECUENTE);
        s.registrarViajero("1.914.689-8", "Guillermo", "guille@ort.edu.uy", 35, Categoria.ESTANDAR);
        s.registrarViajero("1.914.689-9", "Guillermo", "guille1@ort.edu.uy", 35, Categoria.ESTANDAR);
        retorno = s.buscarViajeroPorCorreo("guille1@ort.edu.uy");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(2, retorno.getValorInteger());
        assertEquals("1.914.689-9;Guillermo;guille1@ort.edu.uy;35;Estándar", retorno.getValorString());
    }

}
