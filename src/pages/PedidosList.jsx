import {useEffect, useState} from "react";
import {fetchDataGet, fetchDataPut} from "../api/ApiQueries.js";
import {ButtonContent} from "../components/ButtonContent.jsx";
import {showAlert} from "../utils/Utils.js";
import {MoonLoader} from "react-spinners";

export function PedidosList() {

    const [pedidos, setPedidos] = useState([]);
    const [loadingPedidos, setLoadingPedidos] = useState(false);
    const [loadingModificarPedidos, setLoadingModificarPedidos] = useState(false);


    useEffect(() => {
        if(!pedidos || pedidos.length === 0) {
            cargarPedidos().then();
        }
    }, [pedidos]);

    const cargarPedidos = async () => {
        setLoadingPedidos(true)
        try
        {
            const respuesta = await fetchDataGet(
                '/obtener',
                " al obtener todos los pedidos",
                {});

            setPedidos(respuesta)
        }
        catch (error) {
            await showAlert('error', 'Error', error.message);
        }
        finally {
            setLoadingPedidos(false)
        }
    };

    const cambiarEstatusPedido = (idPedido, nuevoEstatus) => {
        // Crea una copia de los pedidos actuales
        const pedidosActualizados = pedidos.map((pedido) => {
            // Encuentra el pedido con el ID correspondiente y actualiza su estatus
            if (pedido.id === idPedido) {
                return { ...pedido, estadoPedido: nuevoEstatus };
            }
            // Deja los demás pedidos como están
            return pedido;
        });

        // Establece el nuevo estado de pedidos
        setPedidos(pedidosActualizados);
    };

    const handleCambiarEstatus = async (e) => {
        e.preventDefault();
        setLoadingModificarPedidos(true)
        try
        {
            await fetchDataPut(
                '/actualizar/estatus/pedidos',
                " al modificar todos los pedidos",
                 pedidos,
                {});

            setPedidos([])
        }
        catch (error) {
            await showAlert('error', 'Error', error.message);
        }
        finally {
            setLoadingModificarPedidos(false)
        }

    }

    if (loadingPedidos || !setPedidos.length) {
        return (
            <div className="mt-24 flex justify-center items-center h-full">
                {loadingPedidos ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }


    return (
        <div>
            <div className="text-center mt-10">
                <h2 className="text-3xl text-gray-700">Lista de pedidos</h2>
            </div>

            {pedidos && pedidos.length > 0 ? (
                <>
                    <table className="w-full text-left mt-14">
                        <thead>
                        <tr className="border-b">
                            <th className="p-4">HAWA</th>
                            <th className="p-4">Cliente</th>
                            <th className="p-4">Cantidad</th>
                            <th className="p-4">Descuento</th>
                            <th className="p-4">Direccion</th>
                            <th className="p-4">Vendedor</th>
                            <th className="p-4">Tienda</th>
                            <th className="p-4">Estatus</th>
                        </tr>
                        </thead>
                        <tbody>
                        {pedidos.map((pedido) => (
                            <tr className="border-b hover:bg-gray-100" key={pedido.id}>
                                <td className="p-4">{pedido.hawa}</td>
                                <td className="p-4">{pedido.nombreCliente}</td>
                                <td className="p-4">{pedido.cantidad}</td>
                                <td className="p-4">{pedido.descuento}</td>
                                <td className="p-4">{pedido.direccion}</td>
                                <td className="p-4">{pedido.vendedor}</td>
                                <td className="p-4">{pedido.tienda}</td>
                                <td className="p-4">
                                    <select
                                        className="border border-gray-300 rounded p-2 bg-white"
                                        value={pedido.estadoPedido}
                                        onChange={(e) =>
                                            cambiarEstatusPedido(pedido.id, e.target.value)}>
                                        <option value="pendiente">Pendiente</option>
                                        <option value="entregado">Entregado</option>
                                        {pedido.cancelable && <option value="cancelado">Cancelado</option>}
                                    </select>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <div className="mt-6 flex justify-center">
                        <button
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                            onClick={handleCambiarEstatus}
                        >
                            <ButtonContent name="Modificar Pedidos" loading={loadingModificarPedidos}/>
                        </button>
                    </div>
                </>
            ) : (
                <div className="text-center mt-10">
                    <p>No hay pedidos para mostrar.</p>
                </div>
            )}
        </div>
    );
}