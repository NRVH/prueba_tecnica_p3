import {usePedidoForm} from "./hooks/usePedidoForm.js";
import {ButtonContent} from "../components/ButtonContent.jsx";

export const PedidoForm = () => {

    const {
        pedido,
        productoDisponible,
        loadingCrear,
        loadingDisponibilidad,
        handleChange,
        verificarDisponibilidad,
        handleSubmit
    } = usePedidoForm()


    return (
        <div className="container mx-auto p-4">
            <form onSubmit={handleSubmit} className="grid grid-cols- gap-6">
                <div className="flex items-center">
                    <label htmlFor="hawa" className="w-1/2">ID HAWA:</label>
                    <input type="text" name="hawa" value={pedido.hawa} onChange={handleChange}
                           className="border p-2 w-full"/>
                    <button type="button" onClick={verificarDisponibilidad}
                            className="ml-4 px-4 py-2 border rounded bg-blue-500 text-white">
                        <ButtonContent name="Verificar Disponibilidad" loading={loadingDisponibilidad}/>
                    </button>
                </div>

                {productoDisponible && (
                    <>
                        <div className="mt-8 grid grid-cols-4 gap-4 mr-44">
                            <label htmlFor="cantidad" className="col-span-1 text-right">Cantidad:</label>
                            <input type="number" name="cantidad" value={pedido.cantidad} onChange={handleChange}
                                   className="col-span-3 border p-2"/>
                        </div>
                        <div className="grid grid-cols-4 gap-4 mr-44">
                            <label htmlFor="descuento" className="col-span-1 text-right">Descuento:</label>
                            <input type="number" name="descuento" value={pedido.descuento} onChange={handleChange}
                                   className="col-span-3 border p-2"/>
                        </div>
                        <div className="grid grid-cols-4 gap-4 mr-44">
                            <label htmlFor="nombreCliente" className="col-span-1 text-right">Nombre del Cliente:</label>
                            <input type="text" name="nombreCliente" value={pedido.nombreCliente} onChange={handleChange}
                                   className="col-span-3 border p-2"/>
                        </div>
                        <div className="grid grid-cols-4 gap-4 mr-44">
                            <label htmlFor="direccion" className="col-span-1 text-right">Dirección:</label>
                            <input type="text" name="direccion" value={pedido.direccion} onChange={handleChange}
                                   className="col-span-3 border p-2"/>
                        </div>
                        <div className="grid grid-cols-4 gap-4 mr-44">
                            <label htmlFor="contacto" className="col-span-1 text-right">Contacto:</label>
                            <input type="text" name="contacto" value={pedido.contacto} onChange={handleChange}
                                   className="col-span-3 border p-2"/>
                        </div>
                        <div className="grid grid-cols-4 gap-4 mr-44">
                            <label htmlFor="vendedor" className="col-span-1 text-right">Vendedor:</label>
                            <input type="text" name="vendedor" value={pedido.vendedor} onChange={handleChange}
                                   className="col-span-3 border p-2"/>
                        </div>
                        <div className="grid grid-cols-4 gap-4 mr-44">
                            <label htmlFor="tienda" className="col-span-1 text-right">Tienda:</label>
                            <input type="text" name="tienda" value={pedido.tienda} onChange={handleChange}
                                   className="col-span-3 border p-2"/>
                        </div>

                        <div className="flex justify-center">
                            <button type="submit"
                                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                                <ButtonContent name="Enviar Pedido" loading={loadingCrear}/>
                            </button>
                        </div>
                    </>
                )}
            </form>
        </div>
    );
};