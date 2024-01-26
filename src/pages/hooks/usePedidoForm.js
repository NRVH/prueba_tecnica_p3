import {useState} from "react";
import {fetchDataGet, fetchDataPost} from "../../api/ApiQueries.js";
import {showAlert} from "../../utils/Utils.js";
import {useNavigate} from "react-router-dom";

const pedidoInicial = {
    hawa: '',
    cantidad: 0,
    descuento: 0,
    nombreCliente: '',
    direccion: '',
    contacto: '',
    vendedor: '',
    tienda: ''
};
export const usePedidoForm = () => {

    const navigate = useNavigate();
    const [pedido, setPedido] = useState(pedidoInicial);

    const [productoDisponible, setProductoDisponible] = useState(false);
    const [loadingDisponibilidad, setLoadingDisponibilidad] = useState(false);
    const [loadingCrear, setLoadingCrear] = useState(false);

    const handleChange = (e) => {
        setPedido({...pedido, [e.target.name]: e.target.value});
    };

    const verificarDisponibilidad = async () => {
        setProductoDisponible(false);
        setLoadingDisponibilidad(true)
        try
        {
            const respuesta = await fetchDataGet(
                '/disponibilidad',
                " al intentar verificar la disponibilidad del pedido",
                { hawaId: pedido.hawa });

            setProductoDisponible(respuesta.message === "Producto disponible");
        }
        catch (error)
        {
            await showAlert('error', 'Error', error.message);
        }
        finally {
            setLoadingDisponibilidad(false)
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoadingCrear(true)

        const esValido = await validarDatosDelPedido(pedido);
        if (!esValido) {
            return;
        }

        try
        {
            await fetchDataPost("/crear",
                " al intentar crear un pedido",
                pedido,
                {})
            navigate('/confirmacion', { state: { pedido } });
        }
        catch (error)
        {
            await showAlert('error', 'Error', error.message);
        }
        finally {
            setLoadingCrear(false)
        }
    };

    const validarDatosDelPedido = async (pedido) => {
        const camposYErrores = {
            cantidad: "Por favor, ingresa una cantidad válida.",
            descuento: "Por favor, ingresa un descuento válido.",
            nombreCliente: "Por favor, ingresa el nombre del cliente.",
            direccion: "Por favor, ingresa la dirección.",
            contacto: "Por favor, ingresa un contacto.",
            vendedor: "Por favor, ingresa el nombre del vendedor.",
            tienda: "Por favor, selecciona una tienda."
        };

        for (const [campo, mensajeError] of Object.entries(camposYErrores)) {
            const valor = pedido[campo];
            if ((campo === "cantidad" && (!valor || valor <= 0)) ||
                (campo === "descuento" && (valor === null || valor === undefined || isNaN(valor) || valor === 0)) ||
                (!(campo === "cantidad" || campo === "descuento") && !valor)) {
                await showAlert("warning", "Atención", mensajeError);
                return false;
            }
        }
        return true;
    };

    return {
        pedido,
        productoDisponible,
        loadingCrear,
        loadingDisponibilidad,
        handleChange,
        verificarDisponibilidad,
        handleSubmit
    }

}