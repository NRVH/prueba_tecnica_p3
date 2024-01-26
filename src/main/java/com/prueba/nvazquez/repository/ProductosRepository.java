package com.prueba.nvazquez.repository;

import com.prueba.nvazquez.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductosRepository extends JpaRepository<Productos, String> {
    Optional<Productos> findByHawa(String hawa);
}