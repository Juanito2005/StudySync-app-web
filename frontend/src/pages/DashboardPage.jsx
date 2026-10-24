import { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { api } from "../api/client";
import { useAuth } from "../context/useAuth";

export default function DashboardPage() {
    const { user } = useAuth();
    const location = useLocation();
    const [stats, setStats] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    async function loadStats() {
        try {
            setLoading(true);
            const data = await api.getStats();
            setStats(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadStats();
    }, [location.key]);

    if (loading)
        return (
            <div className="flex justify-center py-20">
                <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-indigo-600"></div>
            </div>
        );
    if (error)
        return (
            <div className="p-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 rounded-lg">
                {error}
            </div>
        );

    const cards = [
        {
            label: "Horas planeadas",
            value: stats?.totalPlannedHours || 0,
            icon: "📋",
        },
        {
            label: "Horas estudiadas",
            value: stats?.totalActualHours || 0,
            icon: "⏱️",
        },
        {
            label: "Planes completados",
            value: `${stats?.completedPlannings || 0}/${stats?.totalPlannings || 0}`,
            icon: "✅",
        },
        {
            label: "Racha actual",
            value: `${stats?.currentStreak || 0} días`,
            icon: "🔥",
        },
    ];

    return (
        <div>
            <div className="mb-6">
                <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">
                    ¡Hola, {user?.name || "Usuario"}!
                </h1>
                <p className="text-gray-500 dark:text-gray-400 mt-1">
                    Este es tu resumen de estudio
                </p>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
                {cards.map((card) => (
                    <div
                        key={card.label}
                        className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-5 hover:shadow-md transition-shadow"
                    >
                        <div className="flex items-center justify-between mb-3">
                            <span className="text-2xl">{card.icon}</span>
                            <span className="text-xs font-medium text-gray-400 dark:text-gray-500 uppercase tracking-wider">
                                {card.label}
                            </span>
                        </div>
                        <p className="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
                            {card.value}
                        </p>
                    </div>
                ))}
            </div>

            {stats && (
                <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 mb-8">
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-gray-100 mb-3">
                        Progreso general
                    </h3>
                    <div className="flex items-center gap-4">
                        <div className="flex-1 bg-gray-200 dark:bg-gray-700 rounded-full h-3">
                            <div
                                className="bg-indigo-600 h-3 rounded-full transition-all duration-500"
                                style={{
                                    width: `${(stats.completionRate || 0) * 100}%`,
                                }}
                            ></div>
                        </div>
                        <span className="text-sm font-medium text-gray-600 dark:text-gray-400 w-16 text-right">
                            {Math.round((stats.completionRate || 0) * 100)}%
                        </span>
                    </div>
                    <p className="text-xs text-gray-400 dark:text-gray-500 mt-2">
                        {stats.completedPlannings} de {stats.totalPlannings}{" "}
                        planes completados
                    </p>
                </div>
            )}

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
                {[
                    {
                        to: "/subjects",
                        icon: "📚",
                        title: "Materias",
                        desc: "Gestiona tus materias",
                    },
                    {
                        to: "/plannings",
                        icon: "📋",
                        title: "Planes",
                        desc: "Planea tus sesiones",
                    },
                    {
                        to: "/routine-logs",
                        icon: "⏱️",
                        title: "Sesiones",
                        desc: "Registra lo que estudiaste",
                    },
                    {
                        to: "/stats",
                        icon: "📈",
                        title: "Estadísticas",
                        desc: "Mira tu progreso",
                    },
                ].map((item) => (
                    <Link
                        key={item.to}
                        to={item.to}
                        className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-5 hover:border-indigo-300 dark:hover:border-indigo-500 hover:shadow-md transition-all group"
                    >
                        <span className="text-2xl">{item.icon}</span>
                        <h3 className="font-semibold text-gray-900 dark:text-gray-100 mt-2 group-hover:text-indigo-600">
                            {item.title}
                        </h3>
                        <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">
                            {item.desc}
                        </p>
                    </Link>
                ))}
            </div>
        </div>
    );
}
