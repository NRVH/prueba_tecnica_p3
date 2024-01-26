import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import {ConfirmacionPedido} from "./pages/ConfirmacionPedido.jsx";
import { PedidoForm } from "./pages/PedidoForm.jsx";
import './App.css';
import {PedidosList} from "./pages/PedidosList.jsx";

function App() {
    return (
        <Router>
            <div className="min-h-screen bg-white text-gray-800 w-full">
                <nav className="bg-gray-100 p-4 shadow-md w-full">
                    <div className="flex justify-between items-center w-full">
                        <h1 className="text-xl font-bold text-gray-700">Tiendas Patito</h1>
                        <div className="space-x-4">
                            <Link to="/" className="text-gray-600 hover:text-gray-800">Home</Link>
                            <span className="text-gray-600">|</span>
                            <Link to="/pedido" className="text-gray-600 hover:text-gray-800">Crear Pedido</Link>
                            <span className="text-gray-600">|</span>
                            <Link to="/listado/pedidos" className="text-gray-600 hover:text-gray-800">Listado Pedidos</Link>
                        </div>
                    </div>
                </nav>

                <div className="p-4 w-full">
                    <Routes>
                        <Route path="/" element={
                            <div className="text-center mt-10">
                                <h2 className="text-3xl font-bold text-gray-700">Bienvenidos a Tiendas Patito</h2>
                                <p className="mt-3 text-gray-600">Tu lugar de confianza para pedidos en línea</p>
                            </div>
                        } />
                        <Route path="/pedido" element={<PedidoForm />} />
                        <Route path="/confirmacion" element={<ConfirmacionPedido />} />
                        <Route path="/listado/pedidos" element={<PedidosList />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;