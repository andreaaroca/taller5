package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;

public class ProductoAjustadoTest {

    private ProductoAjustado productoAjustado;
    private ProductoMenu productoBase;
    

    @BeforeEach
    public void setUp() {
        productoBase = new ProductoMenu("Hamburguesa Básica", 8000);
        productoAjustado = new ProductoAjustado(productoBase);
        
       
    }

    @Test
    @DisplayName("Verificar el nombre del producto ajustado")
    public void testGetNombre() {
        assertEquals("Hamburguesa Básica", productoAjustado.getNombre(), "El nombre del producto ajustado debe coincidir con el del producto base.");
    }

    @Test
    @DisplayName("Verificar el precio del producto sin modificaciones")
    public void testPrecioSinModificaciones() {
        assertEquals(8000, productoAjustado.getPrecio(), "El precio del producto sin modificaciones debería ser 8000.");
    }
    
    @Test
    @DisplayName("Verificar el precio del producto con ingredientes adicionales")
    public void testPrecioConIngredientesAdicionales() {
        Ingrediente quesoExtra = new Ingrediente("Queso Extra", 1000);
        productoAjustado.agregados.add(quesoExtra);
        
        int precioEsperado = 8000 + quesoExtra.getCostoAdicional();
        assertEquals(precioEsperado, productoAjustado.getPrecio(), "El precio del producto ajustado debería ser igual al precio base más el costo de los ingredientes adicionales.");
    }
    
    @DisplayName("Verificar el texto de factura para producto ajustado con ingredientes adicionales y eliminados")
    public void testGenerarTextoFactura() {
        productoAjustado.agregados.add(new Ingrediente("Queso Extra", 1000));
        productoAjustado.eliminados.add(new Ingrediente("Cebolla", 0));

        String textoEsperado = "Hamburguesa Básica\n" + 
                                "    +Queso Extra" + 
                                "                1000\n" + 
                                "    -Cebolla";
   
        textoEsperado += "\n            " + productoAjustado.getPrecio() + "\n"; 

        assertEquals(textoEsperado, productoAjustado.generarTextoFactura(), "El texto de factura no coincide con la implementación actual.");
    }
}


