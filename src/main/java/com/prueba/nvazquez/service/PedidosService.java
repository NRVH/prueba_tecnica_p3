package com.prueba.nvazquez.service;

import com.prueba.nvazquez.dto.PedidoDTO;
import com.prueba.nvazquez.entity.Pedidos;
import com.prueba.nvazquez.entity.Productos;
import com.prueba.nvazquez.exception.ProductoNotFoundException;
import com.prueba.nvazquez.exception.ProductoSinStockException;
import com.prueba.nvazquez.repository.PedidosRepository;
import com.prueba.nvazquez.repository.ProductosRepository;
import com.prueba.nvazquez.service.impl.IPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidosService implements IPedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public void verificarDisponibilidad(String hawaId) throws ProductoNotFoundException, ProductoSinStockException {
        Productos producto = productosRepository.findByHawa(hawaId)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con HAWA: " + hawaId));

        if (producto.getCantidad() <= 0) {
            throw new ProductoSinStockException("Producto sin stock para: " + hawaId);
        }
    }

    //BONUS----------------------------------------------------------------
    //@Transactional si ocurre un error en algún punto durante la ejecución del método,
    // todos los cambios hechos en la base de datos durante esa transacción se revertirán
    @Transactional
    @Override
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        // Primero, se busca el producto
        Productos producto = productosRepository.findByHawa(pedidoDTO.getHawa())
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con HAWA: " + pedidoDTO.getHawa()));

        // Verificar si la cantidad en el pedido no supera la cantidad disponible del producto.
        if (producto.getCantidad() < pedidoDTO.getCantidad()) {
            throw new ProductoSinStockException("Cantidad insuficiente para el pedido con HAWA: " + pedidoDTO.getHawa());
        }

        // Resta la cantidad del pedido a la cantidad del producto y actualiza el producto.
        producto.setCantidad(producto.getCantidad() - pedidoDTO.getCantidad());
        productosRepository.save(producto);

        // Crea el nuevo pedido con la fecha actual y el estado pendiente.
        Pedidos pedido = convertirADominio(pedidoDTO);
        LocalDateTime ahora = LocalDateTime.now();
        pedido.setEstadoPedido("pendiente");
        pedido.setFechaCreacion(ahora);
        pedido.setFechaActualizacion(ahora);
        //Asocia el objeto productos con el objeto pedidos que se está creando o actualizando
        pedido.setProducto(producto);
        pedido = pedidosRepository.save(pedido);

        return convertirADTO(pedido);
    }

    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() {
        List<Pedidos> pedidos = pedidosRepository.findAll();
        return pedidos.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public void actualizarEstatusPedidos(List<PedidoDTO> pedidosDTO) {
        for (PedidoDTO dto : pedidosDTO) {
            Pedidos pedidoExistente = pedidosRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + dto.getId()));
            if (!pedidoExistente.getEstadoPedido().equals(dto.getEstadoPedido())) {
                pedidoExistente.setEstadoPedido(dto.getEstadoPedido());
                pedidoExistente.setFechaActualizacion(LocalDateTime.now());
                pedidosRepository.save(pedidoExistente);
            }
        }
    }

    private Pedidos convertirADominio(PedidoDTO pedidoDTO) {
        Pedidos pedido = new Pedidos();
        pedido.setHawa(pedidoDTO.getHawa());
        pedido.setCantidad(pedidoDTO.getCantidad());
        pedido.setDescuento(pedidoDTO.getDescuento());
        pedido.setNombreCliente(pedidoDTO.getNombreCliente());
        pedido.setDireccion(pedidoDTO.getDireccion());
        pedido.setContacto(pedidoDTO.getContacto());
        pedido.setVendedor(pedidoDTO.getVendedor());
        pedido.setTienda(pedidoDTO.getTienda());
        pedido.setIpUsuario(pedidoDTO.getIpUsuario());
        return pedido;
    }

    private PedidoDTO convertirADTO(Pedidos pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setHawa(pedido.getHawa());
        pedidoDTO.setCantidad(pedido.getCantidad());
        pedidoDTO.setDescuento(pedido.getDescuento());
        pedidoDTO.setNombreCliente(pedido.getNombreCliente());
        pedidoDTO.setDireccion(pedido.getDireccion());
        pedidoDTO.setContacto(pedido.getContacto());
        pedidoDTO.setVendedor(pedido.getVendedor());
        pedidoDTO.setTienda(pedido.getTienda());
        pedidoDTO.setIpUsuario(pedido.getIpUsuario());
        pedidoDTO.setEstadoPedido(pedido.getEstadoPedido());
        if(pedido.getFechaCreacion() != null)
        {
            long minutosDesdeCreacion = ChronoUnit.MINUTES.between(pedido.getFechaCreacion(), LocalDateTime.now());
            pedidoDTO.setCancelable(minutosDesdeCreacion <= 10);
        }
        else
        {
            pedidoDTO.setCancelable(true);
        }


        return pedidoDTO;
    }
}
