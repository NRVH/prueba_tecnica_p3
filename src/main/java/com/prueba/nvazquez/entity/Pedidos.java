package com.prueba.nvazquez.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hawa;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(precision = 10, scale = 2)
    private BigDecimal descuento;

    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String contacto;

    @Column(nullable = false)
    private String vendedor;

    @Column(nullable = false)
    private String tienda;

    @Column(name = "ip_usuario")
    private String ipUsuario;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "estado_pedido", nullable = false)
    private String estadoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hawa", referencedColumnName = "hawa", insertable = false, updatable = false)
    private Productos producto;

}
