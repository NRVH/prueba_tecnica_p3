package com.prueba.nvazquez.controller;

import com.prueba.nvazquez.dto.ActualizarEstatusPedido;
import com.prueba.nvazquez.dto.ApiResponse;
import com.prueba.nvazquez.dto.PedidoDTO;
import com.prueba.nvazquez.exception.ProductoNotFoundException;
import com.prueba.nvazquez.exception.ProductoSinStockException;
import com.prueba.nvazquez.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private Validator validator;

    @GetMapping("/disponibilidad")
    public ResponseEntity<ApiResponse> verificarDisponibilidad(@RequestParam String hawaId) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            pedidosService.verificarDisponibilidad(hawaId);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Producto disponible");
            return ResponseEntity.ok().body(apiResponse);
        }
        catch (ProductoNotFoundException e)
        {
            apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
            apiResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        catch (ProductoSinStockException e)
        {
            apiResponse.setStatus(HttpStatus.CONFLICT.value());
            apiResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }
        catch (Exception e)
        {
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setMessage("Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Crear un nuevo pedido
    @PostMapping("/crear")
    public ResponseEntity<Object> crearPedido(@RequestBody PedidoDTO pedidoDTO, HttpServletRequest request) {
        if (!isValido(pedidoDTO)) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("error", "Validación fallida");
            errorDetails.put("message", "Datos de pedido no válidos");
            return ResponseEntity.badRequest().body(errorDetails);
        }

        try {
            String ipUsuario = request.getRemoteAddr();
            pedidoDTO.setIpUsuario(ipUsuario);
            PedidoDTO nuevoPedido = pedidosService.crearPedido(pedidoDTO);
            return ResponseEntity.ok(nuevoPedido);
        } catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/obtener")
    public ResponseEntity<Object> obtenerTodosLosPedidos() {
        try {
            List<PedidoDTO> pedidos = pedidosService.obtenerTodosLosPedidos();
            Thread.sleep(1000);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Actualizar un pedido
    @PutMapping("/actualizar/estatus/pedidos")
    public ResponseEntity<Object> actualizarEstadoPedido(@RequestBody List<PedidoDTO> pedidosDTO) {

        ApiResponse apiResponse = new ApiResponse();
        try
        {
            pedidosService.actualizarEstatusPedidos(pedidosDTO);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Pedidos actualizados correctamente");
            Thread.sleep(1000);
            return ResponseEntity.ok().body(apiResponse);
        }
        catch (IllegalArgumentException e)
        {
            apiResponse.setStatus(HttpStatus.FORBIDDEN.value());
            apiResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
        }
        catch (EntityNotFoundException e)
        {
            apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
            apiResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        catch (Exception e)
        {
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    public boolean isValido(PedidoDTO pedidoDTO) {
        Set<ConstraintViolation<PedidoDTO>> violations = validator.validate(pedidoDTO);
        return violations.isEmpty();
    }
}
