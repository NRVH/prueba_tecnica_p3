package com.prueba.nvazquez.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "productos")
public class Productos {

    @Id
    @Column(nullable = false)
    private String hawa;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(precision = 10, scale = 2)
    private BigDecimal descuento;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private LocalDateTime fechaActualizacion;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<Pedidos> pedidos;
}
