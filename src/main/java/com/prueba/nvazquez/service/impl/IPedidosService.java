package com.prueba.nvazquez.service.impl;

import com.prueba.nvazquez.dto.ActualizarEstatusPedido;
import com.prueba.nvazquez.dto.PedidoDTO;
import com.prueba.nvazquez.exception.ProductoNotFoundException;
import com.prueba.nvazquez.exception.ProductoSinStockException;

import java.util.List;

public interface IPedidosService {
    void verificarDisponibilidad(String hawaId) throws ProductoNotFoundException, ProductoSinStockException;
    PedidoDTO crearPedido(PedidoDTO pedidoDTO);
    List<PedidoDTO> obtenerTodosLosPedidos();
    void actualizarEstatusPedidos(List<PedidoDTO> pedidosDTO);
}
