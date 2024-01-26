import axios from "axios";

const orderApi = axios.create({
    baseURL: "http://localhost:8070/pedidos",
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
});

orderApi.interceptors.request.use( (config) => {
    return config;
});

export { orderApi };