package com.prueba.nvazquez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data               // Genera getters, setters, toString, equals y hashCode.
@AllArgsConstructor // Crea un constructor con todos los argumentos.
@NoArgsConstructor  // Crea un constructor sin argumentos.
@ToString           // Genera una implementación del método toString.
public class ApiResponse {
    private int status;
    private String message;
}

