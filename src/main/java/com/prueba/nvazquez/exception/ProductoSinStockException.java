package com.prueba.nvazquez.exception;

public class ProductoSinStockException extends RuntimeException {
    public ProductoSinStockException(String message) {
        super(message);
    }

}
