package com.prueba.nvazquez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Clase DTO para la entidad Pedido.
 * Utiliza anotaciones de Lombok para reducir el código boilerplate.
 */
@Data               // Genera getters, setters, toString, equals y hashCode.
@AllArgsConstructor // Crea un constructor con todos los argumentos.
@NoArgsConstructor  // Crea un constructor sin argumentos.
@ToString           // Genera una implementación del método toString.
public class PedidoDTO {

    private Long id;

    //@NotBlank: Se asegura de que el campo no sea null, no sea una cadena vacía ("") y no esté compuesto solo por espacios en blanco
    @NotBlank(message = "El campo 'hawa' es obligatorio y no puede estar vacío")
    private String hawa;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    @DecimalMin(value = "0", message = "El descuento no puede ser menor que 0")
    private BigDecimal descuento;

    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    private String nombreCliente;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotBlank(message = "El contacto no puede estar vacío")
    private String contacto;

    @NotBlank(message = "El nombre del vendedor no puede estar vacío")
    private String vendedor;

    @NotBlank(message = "El nombre de la tienda no puede estar vacío")
    private String tienda;

    private String estadoPedido;

    private String ipUsuario;

    private boolean cancelable;
}
