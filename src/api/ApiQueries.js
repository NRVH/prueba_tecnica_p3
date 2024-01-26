import {orderApi} from "./OrdersBase.js";
import {showAlert} from "../utils/Utils.js";
import {authApi} from "./AuthBase.js";

export const getTokenJwt = async () => {
    const credentials = {
        username: "admin",
        password: "123456"
    };
    try {
        const response = await authApi.post('/authenticate', credentials);
        const { jwtToken } = response.data;
        localStorage.setItem('accessToken', jwtToken);
        return jwtToken;
    } catch (error) {
        console.error('Error al obtener el token JWT', error);
        throw new Error('No se pudo renovar el token');
    }
};

export const fetchDataGet = async (url, errorTitle, params) => {
    try {
        const token = localStorage.getItem('accessToken');
        const response = await orderApi.get(url, {
            headers: { Authorization: `Bearer ${token}` },
            params,
        });
        return response.data;

    } catch (error) {
        if (error.response) {
            if(error.response.status === 401 || error.response.status === 403)
            {
                await getTokenJwt();
                return await fetchDataGet(url, errorTitle, params);
            }
            else
            {
                throw new Error(error.response.data.message || "Ocurrió un error desconocido");
            }
        }
        else if (error.message.includes('Network Error'))
        {
            throw new Error("No hay conexión con el servidor");
        }
        else
        {
            throw new Error(error.message);
        }
    }
};


export const fetchDataPost = async (
    url,
    errorTitle,
    data,
    params
) => {
    try {
        const token = localStorage.getItem('accessToken');

        const response = await orderApi.post(url, data,{
            headers: { Authorization: `Bearer ${token}`},
            params,
        });

        await showAlert("success", "Guardado",
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
    } catch (error) {
        if (error.response) {
            if(error.response.status === 401)
            {
                await getTokenJwt();
                return await fetchDataPost(url, errorTitle, data, params);
            }
            else
            {
                throw new Error(error.response.data.message || "Ocurrió un error desconocido");
            }
        }
        else if (error.message.includes('Network Error'))
        {
            throw new Error("No hay conexión con el servidor");
        }
        else
        {
            throw new Error(error.message);
        }
    }
};

export const fetchDataPut = async (
    url,
    errorTitle,
    data,
    params
) => {
    try {
        const token = localStorage.getItem('accessToken');

        const response = await orderApi.put(url, data,{
            headers: { Authorization: `Bearer ${token}`},
            params,
        });

        await showAlert("success", "Guardado",
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
    } catch (error) {
        if (error.response) {
            if(error.response.status === 401)
            {
                await getTokenJwt();
                return await fetchDataPut(url, errorTitle, data, params);
            }
            else
            {
                throw new Error(error.response.data.message || "Ocurrió un error desconocido");
            }
        }
        else if (error.message.includes('Network Error'))
        {
            throw new Error("No hay conexión con el servidor");
        }
        else
        {
            throw new Error(error.message);
        }
    }
};


export const fetchDataPostRet = async (
    url,
    errorTitle,
    data,
    params
) => {
    try {
        const response = await orderApi.post(url, data, {params});
        await showAlert("success", "Guardado",
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
        return response.data.body
    } catch (error) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};