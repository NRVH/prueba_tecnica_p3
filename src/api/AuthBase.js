import axios from "axios";

const authApi = axios.create({
    baseURL: "http://localhost:8070/api/auth",
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
});

authApi.interceptors.request.use( (config) => {
    return config;
});

export { authApi };