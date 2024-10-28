package uniandes.dpoo.hamburguesas.tests;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class PedidoTest {
	

    private Pedido pedido;
    private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;
    private Combo combo;

    @BeforeEach
    public void setUp() {
       
        productoBase = new ProductoMenu("Hamburguesa Básica", 8000);
        productoAjustado = new ProductoAjustado(productoBase);
        combo = new Combo("Combo Familiar", 0.9, new ArrayList<>(Arrays.asList(productoBase)));

        pedido = new Pedido("Andrea Aroca", "Calle 127");
        pedido.agregarProducto(productoBase);
        pedido.agregarProducto(productoAjustado);
        pedido.agregarProducto(combo);
    }


    @Test
    @DisplayName("Guardar factura en archivo")
    public void testGuardarFactura() throws FileNotFoundException {
        File archivo = new File("factura.txt");
        
        if (archivo.exists()) {
            archivo.delete();
        }
        
        pedido.guardarFactura(archivo);
        
        assertTrue(archivo.exists(), "El archivo de la factura debería existir.");

        String contenidoFactura = "";
        try {
            contenidoFactura = new String(Files.readAllBytes(archivo.toPath()));
        } catch (IOException e) {
            fail("No se pudo leer el archivo de la factura: " + e.getMessage());
        }
    
        String textoEsperado = "Cliente: Andrea Aroca\n" +
                                "Dirección: Calle 127\n" +
                                "----------------\n";

        for (Producto producto : pedido.getProductos()) {
            textoEsperado += producto.generarTextoFactura();
        }
        
        textoEsperado += "----------------\n" +
                         "Precio Neto:  " + pedido.getPrecioNetoPedido() + "\n" +
                         "IVA:          " + pedido.getPrecioIVAPedido() + "\n" +
                         "Precio Total: " + pedido.getPrecioTotalPedido() + "\n";

        assertEquals(textoEsperado.trim(), contenidoFactura.trim(), "El contenido de la factura guardada no coincide con el esperado.");

        archivo.delete();
    }
    
    @Test
    @DisplayName("Verificar texto de factura")
    public void testGenerarTextoFactura() {
        String nombreCliente = "Andrea Aroca";
        String direccionCliente = "Calle 127";
        int precioBase = productoBase.getPrecio();
        int precioAjustado = productoAjustado.getPrecio();
        int precioCombo = combo.getPrecio();

        int precioNeto = precioBase + precioAjustado + precioCombo;
        int iva = (int) (precioNeto * 0.19);
        int precioTotal = pedido.getPrecioTotalPedido();

        String textoEsperado = "Cliente: " + nombreCliente + "\n" +
                                "Dirección: " + direccionCliente + "\n" +
                                "----------------\n" +
                                "Hamburguesa Básica\n" + 
                                "            " + precioBase + "\n" +
                            
                                productoAjustado.generarTextoFactura() + 
                                "Combo Combo Familiar\n" +
                                " Descuento: " + (0.9 + "\n") + 
                                "            " + precioCombo + "\n" +
                                
                                "----------------\n" +
                                "Precio Neto:  " + precioNeto + "\n" +
                                "IVA:          " + iva + "\n" +
                                "Precio Total: " + precioTotal + "\n";

        assertEquals(textoEsperado.trim(), pedido.generarTextoFactura().trim(), "El texto de la factura no coincide con el esperado.");
    }
    
}


