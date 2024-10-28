package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import java.util.ArrayList;

public class ComboTest {
	

    private Combo combo;
    private ArrayList<ProductoMenu> itemsCombo;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    public void setUp() {
        itemsCombo = new ArrayList<>();
        
        producto1 = new ProductoMenu("Hamburguesa", 8000);
        producto2 = new ProductoMenu("Papas Fritas", 3000);
        
        itemsCombo.add(producto1);
        itemsCombo.add(producto2);
        
        combo = new Combo("Combo 1", 0.8, itemsCombo); // digamos que tiene el 20% de descuento
    }

    @Test
    @DisplayName("Verificar el nombre del combo")
    public void testGetNombre() {
        assertEquals("Combo 1", combo.getNombre(), "El nombre del combo debe coincidir con el esperado.");
    }

    @Test
    @DisplayName("Verificar el precio del combo con descuento")
    public void testGetPrecio() {
        assertEquals(8800, combo.getPrecio(), "El precio del combo con descuento debe ser 8800.");
    }

    @Test
    @DisplayName("Verificar el texto de la factura del combo")
    public void testGenerarTextoFactura() {
        String textoEsperado = "Combo Combo 1\n" +
                                " Descuento: 0.8\n" +
                                "            8800\n";

        assertEquals(textoEsperado, combo.generarTextoFactura(), "El texto de la factura no coincide con el esperado.");
    }
}


