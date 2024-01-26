package com.prueba.nvazquez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarEstatusPedido {
    private Long id;
    private String nuevoEstado;
}
