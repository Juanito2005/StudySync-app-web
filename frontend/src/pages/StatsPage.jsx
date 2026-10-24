import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { api } from "../api/client";

export default function StatsPage() {
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
    if (!stats) return null;

    const summary = [
        {
            label: "Horas planeadas",
            value: stats.totalPlannedHours,
            icon: "📋",
        },
        { label: "Horas reales", value: stats.totalActualHours, icon: "⏱️" },
        {
            label: "Tasa de completado",
            value: Math.round(stats.completionRate * 100) + "%",
            icon: "🎯",
        },
        {
            label: "Racha actual",
            value: stats.currentStreak + " días",
            icon: "🔥",
        },
    ];

    return (
        <div>
            <div className="mb-6">
                <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">
                    Estadísticas
                </h1>
                <p className="text-gray-500 dark:text-gray-400 mt-1">
                    Tu progreso en números
                </p>
            </div>

            {/* Resumen rápido */}
            <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
                {summary.map((c) => (
                    <div
                        key={c.label}
                        className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-5"
                    >
                        <span className="text-2xl">{c.icon}</span>
                        <p className="text-xs text-gray-400 dark:text-gray-500 mt-1 uppercase tracking-wider">
                            {c.label}
                        </p>
                        <p className="text-xl font-bold text-gray-900 dark:text-gray-100 mt-1">
                            {c.value}
                        </p>
                    </div>
                ))}
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                {/* ─── POR MATERIA: barra de progreso sobre lo planeado ─── */}
                <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-gray-100 mb-1">
                        Por materia
                    </h3>
                    <p className="text-xs text-gray-400 dark:text-gray-500 mb-5">
                        Horas reales vs horas planeadas
                    </p>
                    {stats.bySubject?.length ? (
                        <div className="space-y-5">
                            {stats.bySubject.map((s) => {
                                const pct =
                                    s.plannedHours > 0
                                        ? Math.round(
                                              (s.actualHours / s.plannedHours) *
                                                  100,
                                          )
                                        : 0;
                                const over = s.actualHours >= s.plannedHours;
                                return (
                                    <div key={s.subjectId}>
                                        {/* Cabecera: nombre + cifras + badge de porcentaje */}
                                        <div className="flex items-center justify-between mb-1.5">
                                            <div className="flex items-center gap-2 min-w-0">
                                                <div
                                                    className="w-3 h-3 rounded-full shrink-0"
                                                    style={{
                                                        backgroundColor:
                                                            s.colorHEX ||
                                                            "#ccc",
                                                    }}
                                                ></div>
                                                <span className="text-sm font-medium text-gray-900 dark:text-gray-100 truncate">
                                                    {s.subjectName}
                                                </span>
                                            </div>
                                            <div className="flex items-center gap-1.5 text-xs shrink-0 ml-3">
                                                <span
                                                    className={`font-semibold ${over ? "text-green-600 dark:text-green-400" : "text-indigo-600 dark:text-indigo-400"}`}
                                                >
                                                    {s.actualHours}h
                                                </span>
                                                <span className="text-gray-400">
                                                    /
                                                </span>
                                                <span className="text-gray-500 dark:text-gray-400">
                                                    {s.plannedHours}h
                                                </span>
                                                <span
                                                    className={`ml-1 px-1.5 py-0.5 rounded-full text-[10px] font-bold ${
                                                        over
                                                            ? "bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400"
                                                            : pct >= 70
                                                              ? "bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400"
                                                              : "bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400"
                                                    }`}
                                                >
                                                    {pct}%
                                                </span>
                                            </div>
                                        </div>
                                        {/* Barra: el fondo gris es lo planeado; el relleno es lo real */}
                                        <div className="relative h-5 bg-gray-100 dark:bg-gray-700 rounded-full overflow-hidden">
                                            <div
                                                className={`absolute inset-y-0 left-0 rounded-full transition-all duration-500 ${
                                                    over
                                                        ? "bg-green-500 dark:bg-green-600"
                                                        : "bg-indigo-500 dark:bg-indigo-600"
                                                }`}
                                                style={{
                                                    width:
                                                        Math.min(pct, 100) +
                                                        "%",
                                                }}
                                            ></div>
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    ) : (
                        <p className="text-gray-400 dark:text-gray-500 text-sm">
                            No hay datos por materia todavía.
                        </p>
                    )}
                </div>

                {/* ─── ÚLTIMAS SEMANAS: barras lado a lado planeado vs real ─── */}
                <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-gray-100 mb-1">
                        Últimas semanas
                    </h3>
                    <p className="text-xs text-gray-400 dark:text-gray-500 mb-5">
                        Comparación planeado vs real por semana
                    </p>
                    {stats.weekly?.length ? (
                        <div className="space-y-5">
                            {(() => {
                                const maxH = Math.max(
                                    ...stats.weekly.map((w) =>
                                        Math.max(w.plannedHours, w.actualHours),
                                    ),
                                    1,
                                );
                                return stats.weekly.map((w, i) => {
                                    const plannedW = Math.max(
                                        (w.plannedHours / maxH) * 100,
                                        2,
                                    );
                                    const actualW = Math.max(
                                        (w.actualHours / maxH) * 100,
                                        2,
                                    );
                                    const hit = w.actualHours >= w.plannedHours;
                                    return (
                                        <div key={i}>
                                            <span className="text-xs font-medium text-gray-600 dark:text-gray-300 block mb-2">
                                                {w.weekLabel}
                                            </span>
                                            {/* Fila planeado */}
                                            <div className="flex items-center gap-2 mb-1">
                                                <span className="text-[10px] text-gray-400 dark:text-gray-500 w-14 text-right shrink-0">
                                                    Planeado
                                                </span>
                                                <div className="flex-1 h-4 bg-gray-100 dark:bg-gray-700 rounded-full overflow-hidden">
                                                    <div
                                                        className="h-full bg-indigo-100 dark:bg-indigo-900/40 rounded-full transition-all duration-500 flex items-center justify-end pr-1.5"
                                                        style={{
                                                            width:
                                                                plannedW + "%",
                                                        }}
                                                    >
                                                        <span className="text-[10px] font-medium text-indigo-600 dark:text-indigo-400">
                                                            {w.plannedHours}h
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            {/* Fila real */}
                                            <div className="flex items-center gap-2">
                                                <span className="text-[10px] text-indigo-600 dark:text-indigo-400 w-14 text-right shrink-0 font-medium">
                                                    Real
                                                </span>
                                                <div className="flex-1 h-4 bg-gray-100 dark:bg-gray-700 rounded-full overflow-hidden">
                                                    <div
                                                        className={`h-full rounded-full transition-all duration-500 flex items-center justify-end pr-1.5 ${
                                                            hit
                                                                ? "bg-green-500 dark:bg-green-600"
                                                                : "bg-indigo-500 dark:bg-indigo-600"
                                                        }`}
                                                        style={{
                                                            width:
                                                                actualW + "%",
                                                        }}
                                                    >
                                                        <span className="text-[10px] font-medium text-white">
                                                            {w.actualHours}h
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    );
                                });
                            })()}
                        </div>
                    ) : (
                        <p className="text-gray-400 dark:text-gray-500 text-sm">
                            No hay datos semanales todavía.
                        </p>
                    )}
                </div>
            </div>

            {/* Totales */}
            <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mt-8">
                {[
                    { label: "Total planes", value: stats.totalPlannings },
                    {
                        label: "Planes completados",
                        value: stats.completedPlannings,
                    },
                    {
                        label: "Sesiones registradas",
                        value: stats.totalRoutineLogs,
                    },
                    { label: "Días seguidos", value: stats.currentStreak },
                ].map((item) => (
                    <div
                        key={item.label}
                        className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-5 text-center"
                    >
                        <p className="text-2xl font-bold text-gray-900 dark:text-gray-100">
                            {item.value}
                        </p>
                        <p className="text-xs text-gray-400 dark:text-gray-500 mt-1">
                            {item.label}
                        </p>
                    </div>
                ))}
            </div>
        </div>
    );
}
