package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.*;
import uniandes.dpoo.hamburguesas.mundo.*;

import java.io.File;
import java.io.IOException;

public class RestauranteTest {
	private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante();
        File archivoIngredientes = new File("data/ingredientes.txt");
        File archivoMenu = new File("data/menu.txt");
        File archivoCombos = new File("data/combos.txt");
        
        try {
            restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        } catch (IOException | HamburguesaException e) {
            fail("Error al cargar información del restaurante: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Iniciar un pedido correctamente")
    public void testIniciarPedido() {
        assertDoesNotThrow(() -> {
            restaurante.iniciarPedido("Andrea Aroca", "Calle 127");
        });
        
        assertNotNull(restaurante.getPedidoEnCurso(), "El pedido en curso debería estar inicializado.");
        assertEquals("Andrea Aroca", restaurante.getPedidoEnCurso().getNombreCliente(), "El nombre del cliente debe ser correcto.");
    }

    @Test
    @DisplayName("Cerrar y guardar un pedido exitosamente")
    public void testCerrarYGuardarPedido() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
        restaurante.iniciarPedido("Andrea Aroca", "Calle 127");

        ProductoMenu producto = restaurante.getMenuBase().get(0); 
        restaurante.getPedidoEnCurso().agregarProducto(producto); 

        assertEquals(1, restaurante.getPedidos().size(), "El número de pedidos cerrados debería ser 1.");
    }

    @Test
    @DisplayName("Intentar iniciar un pedido cuando ya hay uno en curso")
    public void testIniciarPedidoYaEnCurso() {
        assertDoesNotThrow(() -> {
            restaurante.iniciarPedido("Andrea Aroca", "Calle 127");
        });

        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Juan Pérez", "Calle 456");
        });
    }

    @Test
    @DisplayName("Intentar cerrar un pedido sin iniciar")
    public void testCerrarSinPedidoEnCurso() {
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
    }
}