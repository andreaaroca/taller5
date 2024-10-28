package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {
	

    private ProductoMenu producto;
    
    @BeforeEach
    public void setUp() {
        
        producto = new ProductoMenu("Producto de Prueba", 10000);
    }

    @Test
    @DisplayName("Verificar que el nombre del producto es correcto")
    public void testGetNombre() {
        assertEquals("Producto de Prueba", producto.getNombre(), "El nombre del producto debería coincidir con el valor inicial.");
    }

    @Test
    @DisplayName("Verificar que el precio del producto es correcto")
    public void testGetPrecio() {
        assertEquals(10000, producto.getPrecio(), "El precio del producto debería coincidir con el valor inicial.");
    }

    @Test
    @DisplayName("Generar texto de factura con formato correcto")
    public void testGenerarTextoFactura() {
        String textoFacturaEsperado = "Producto de Prueba\n            10000\n";
        assertEquals(textoFacturaEsperado, producto.generarTextoFactura(), "El texto de la factura debería tener el formato esperado.");
    }
    
    @Test
    @DisplayName("Verificar creación de producto con precio cero")
    public void testPrecioCero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductoMenu("Producto Precio Cero", 0);
        });
    }

    @Test
    @DisplayName("Verificar creación de producto con precio negativo")
    public void testPrecioNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductoMenu("Producto Precio Negativo", -100);
        });
    }

    @Test
    @DisplayName("Verificar creación de producto sin nombre")
    public void testProductoSinNombre() {
        producto = new ProductoMenu("", 1000);
        assertEquals("", producto.getNombre(), "El nombre del producto debería ser una cadena vacía.");
        assertEquals(1000, producto.getPrecio(), "El precio del producto debería coincidir con el valor inicial.");
    }
}


