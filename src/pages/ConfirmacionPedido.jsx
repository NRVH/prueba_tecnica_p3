import {useLocation} from "react-router-dom";

export const ConfirmacionPedido = () => {

    const location = useLocation();
    const pedido = location.state?.pedido;

    return (
        <div className="container mx-auto p-4">
            <h2 className="text-center text-green-600 text-2xl font-bold mb-4">PEDIDO CONFIRMADO!</h2>
            <div className="bg-white p-4 rounded shadow">
                <h3 className="text-lg font-semibold mb-4">Detalles del Pedido:</h3>
                <p><strong>ID HAWA:</strong> {pedido.hawa}</p>
                <p><strong>Cantidad:</strong> {pedido.cantidad}</p>
                <p><strong>Descuento:</strong> {pedido.descuento}%</p>
                <p><strong>Nombre del Cliente:</strong> {pedido.nombreCliente}</p>
                <p><strong>Dirección:</strong> {pedido.direccion}</p>
                <p><strong>Contacto:</strong> {pedido.contacto}</p>
                <p><strong>Vendedor:</strong> {pedido.vendedor}</p>
                <p><strong>Tienda:</strong> {pedido.tienda}</p>
            </div>
        </div>
    );
};