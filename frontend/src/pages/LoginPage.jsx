import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/useAuth";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const { login, isAuthenticated } = useAuth();
    const navigate = useNavigate();

    // Redirigir si ya está autenticado — usando useEffect, NO durante render
    useEffect(() => {
        if (isAuthenticated) navigate("/dashboard", { replace: true });
    }, [isAuthenticated, navigate]);

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setLoading(true);
        try {
            await login(email, password);
            navigate("/dashboard");
        } catch (err) {
            setError(err.message || "Error al iniciar sesión");
        } finally {
            setLoading(false);
        }
    }

    if (isAuthenticated) return null;

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-950 px-4">
            <div className="w-full max-w-md">
                <div className="text-center mb-8">
                    <Link
                        to="/"
                        className="text-3xl font-bold text-indigo-600 dark:text-indigo-400 hover:text-indigo-500"
                    >
                        StudySync
                    </Link>
                    <p className="mt-2 text-gray-600 dark:text-gray-400">
                        Organiza tu estudio, alcanza tus metas
                    </p>
                </div>
                <div className="bg-white dark:bg-gray-800 rounded-xl shadow-sm border border-gray-200 dark:border-gray-700 p-8">
                    <h2 className="text-xl font-semibold text-gray-900 dark:text-gray-100 mb-6">
                        Iniciar sesión
                    </h2>
                    {error && (
                        <div className="mb-4 p-3 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 text-sm rounded-lg">
                            {error}
                        </div>
                    )}
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div>
                            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                Correo electrónico
                            </label>
                            <input
                                type="email"
                                required
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                placeholder="tu@email.com"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                Contraseña
                            </label>
                            <input
                                type="password"
                                required
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                placeholder="••••••••"
                            />
                        </div>
                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full py-2.5 bg-indigo-600 text-white text-sm font-medium rounded-lg hover:bg-indigo-700 disabled:opacity-50 transition-colors"
                        >
                            {loading ? "Iniciando sesión..." : "Iniciar sesión"}
                        </button>
                    </form>
                </div>
                <p className="text-center text-sm text-gray-500 dark:text-gray-400 mt-6">
                    ¿No tienes cuenta?{" "}
                    <Link
                        to="/register"
                        className="text-indigo-600 dark:text-indigo-400 hover:text-indigo-700 font-medium"
                    >
                        Regístrate
                    </Link>
                </p>
            </div>
        </div>
    );
}
