package com.prueba.nvazquez.repository;

import com.prueba.nvazquez.entity.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {

}

