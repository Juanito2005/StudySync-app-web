import { useState, useEffect, useCallback } from "react";
import { api } from "../api/client";

const S = { CREATED: "Creado", COMPLETED: "Completado", DELETED: "Cancelado" };
const SC = {
    CREATED: "bg-blue-100 dark:bg-blue-900/40 text-blue-700 dark:text-blue-300",
    COMPLETED:
        "bg-green-100 dark:bg-green-900/40 text-green-700 dark:text-green-300",
    DELETED: "bg-gray-100 dark:bg-gray-700 text-gray-500 dark:text-gray-400",
};

export default function PlanningsPage() {
    const [plannings, setPlannings] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [editing, setEditing] = useState(null);
    const [filterSubject, setFilterSubject] = useState("");
    const [form, setForm] = useState({
        plannedHours: "",
        date: "",
        subjectId: "",
        status: "CREATED",
    });

    const loadData = useCallback(async () => {
        try {
            setLoading(true);
            const [p, s] = await Promise.all([
                api.getPlannings(filterSubject || undefined),
                api.getSubjects(),
            ]);
            setPlannings(p);
            setSubjects(s);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }, [filterSubject]);

    useEffect(() => {
        loadData();
    }, [loadData]);

    function openCreate() {
        setError("");
        setEditing(null);
        setForm({
            plannedHours: "",
            date: new Date().toISOString().slice(0, 16),
            subjectId: subjects[0]?.id || "",
            status: "CREATED",
        });
        setShowModal(true);
    }
    function openEdit(p) {
        setError("");
        setEditing(p);
        setForm({
            plannedHours: p.plannedHours || "",
            date: p.date ? p.date.slice(0, 16) : "",
            subjectId: p.subjectId || "",
            status: p.status || "CREATED",
        });
        setShowModal(true);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        try {
            const payload = {
                plannedHours: parseFloat(form.plannedHours),
                date: new Date(form.date).toISOString(),
                subjectId: parseInt(form.subjectId),
                status: form.status,
            };
            if (editing) await api.updatePlanning(editing.id, payload);
            else await api.createPlanning(payload);
            setShowModal(false);
            loadData();
        } catch (err) {
            setError(err.message);
        }
    }

    async function handleDelete(id) {
        if (!confirm("¿Eliminar este plan?")) return;
        try {
            await api.deletePlanning(id);
            loadData();
        } catch (err) {
            setError(err.message);
        }
    }

    const fmt = (d) =>
        d
            ? new Date(d).toLocaleDateString("es-ES", {
                  day: "numeric",
                  month: "short",
                  year: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
              })
            : "";

    if (loading && !plannings.length)
        return (
            <div className="flex justify-center py-20">
                <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-indigo-600"></div>
            </div>
        );

    return (
        <div>
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6">
                <div>
                    <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">
                        Planes de estudio
                    </h1>
                    <p className="text-gray-500 dark:text-gray-400 mt-1">
                        {plannings.length} plan
                        {plannings.length !== 1 ? "es" : ""}
                    </p>
                </div>
                <div className="flex gap-3">
                    <select
                        value={filterSubject}
                        onChange={(e) => setFilterSubject(e.target.value)}
                        className="px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                    >
                        <option value="">Todas las materias</option>
                        {subjects.map((s) => (
                            <option key={s.id} value={s.id}>
                                {s.name}
                            </option>
                        ))}
                    </select>
                    <button
                        onClick={openCreate}
                        className="px-4 py-2 bg-indigo-600 text-white text-sm font-medium rounded-lg hover:bg-indigo-700 transition-colors whitespace-nowrap"
                    >
                        + Nuevo plan
                    </button>
                </div>
            </div>

            {error && (
                <div className="mb-4 p-3 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 text-sm rounded-lg">
                    {error}
                </div>
            )}

            {!plannings.length ? (
                <div className="text-center py-20 bg-white dark:bg-gray-800 rounded-xl border border-dashed border-gray-300 dark:border-gray-600">
                    <span className="text-4xl">📋</span>
                    <p className="text-gray-500 dark:text-gray-400 mt-3">
                        No hay planes todavía. ¡Crea el primero!
                    </p>
                </div>
            ) : (
                <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 overflow-hidden">
                    <div className="overflow-x-auto">
                        <table className="w-full text-sm">
                            <thead className="bg-gray-50 dark:bg-gray-900/50 border-b border-gray-200 dark:border-gray-700">
                                <tr>
                                    <th className="text-left px-4 py-3 font-medium text-gray-600 dark:text-gray-400">
                                        Materia
                                    </th>
                                    <th className="text-left px-4 py-3 font-medium text-gray-600 dark:text-gray-400">
                                        Fecha
                                    </th>
                                    <th className="text-left px-4 py-3 font-medium text-gray-600 dark:text-gray-400">
                                        Horas
                                    </th>
                                    <th className="text-left px-4 py-3 font-medium text-gray-600 dark:text-gray-400">
                                        Estado
                                    </th>
                                    <th className="text-right px-4 py-3 font-medium text-gray-600 dark:text-gray-400">
                                        Acciones
                                    </th>
                                </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-100 dark:divide-gray-700">
                                {plannings.map((p) => (
                                    <tr
                                        key={p.id}
                                        className="hover:bg-gray-50 dark:hover:bg-gray-700/50"
                                    >
                                        <td className="px-4 py-3 font-medium text-gray-900 dark:text-gray-100">
                                            {p.subjectName}
                                        </td>
                                        <td className="px-4 py-3 text-gray-500 dark:text-gray-400">
                                            {fmt(p.date)}
                                        </td>
                                        <td className="px-4 py-3 text-gray-900 dark:text-gray-100">
                                            {p.plannedHours}h
                                        </td>
                                        <td className="px-4 py-3">
                                            <span
                                                className={`inline-block px-2 py-0.5 rounded-full text-xs font-medium ${SC[p.status] || ""}`}
                                            >
                                                {S[p.status] || p.status}
                                            </span>
                                        </td>
                                        <td className="px-4 py-3 text-right">
                                            <button
                                                onClick={() => openEdit(p)}
                                                className="text-xs px-2.5 py-1 bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-200 dark:hover:bg-gray-600 mr-1 transition-colors"
                                            >
                                                Editar
                                            </button>
                                            <button
                                                onClick={() =>
                                                    handleDelete(p.id)
                                                }
                                                className="text-xs px-2.5 py-1 bg-red-50 dark:bg-red-900/20 text-red-600 dark:text-red-400 rounded-md hover:bg-red-100 dark:hover:bg-red-900/40 transition-colors"
                                            >
                                                Eliminar
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}

            {showModal && (
                <div
                    className="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
                    onClick={() => setShowModal(false)}
                >
                    <div
                        className="bg-white dark:bg-gray-800 rounded-xl shadow-xl p-6 w-full max-w-md mx-4"
                        onClick={(e) => e.stopPropagation()}
                    >
                        <h2 className="text-lg font-semibold text-gray-900 dark:text-gray-100 mb-4">
                            {editing ? "Editar plan" : "Nuevo plan"}
                        </h2>
                        {error && (
                            <div className="mb-4 p-2 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 text-sm rounded-lg">
                                {error}
                            </div>
                        )}
                        <form onSubmit={handleSubmit} className="space-y-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                    Materia
                                </label>
                                <select
                                    value={form.subjectId}
                                    onChange={(e) =>
                                        setForm({
                                            ...form,
                                            subjectId: e.target.value,
                                        })
                                    }
                                    required
                                    className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                >
                                    <option value="">Selecciona materia</option>
                                    {subjects.map((s) => (
                                        <option key={s.id} value={s.id}>
                                            {s.name}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                    Horas planeadas
                                </label>
                                <input
                                    type="number"
                                    step="0.5"
                                    min="0.5"
                                    required
                                    value={form.plannedHours}
                                    onChange={(e) =>
                                        setForm({
                                            ...form,
                                            plannedHours: e.target.value,
                                        })
                                    }
                                    className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                    placeholder="2.5"
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                    Fecha y hora
                                </label>
                                <input
                                    type="datetime-local"
                                    required
                                    value={form.date}
                                    onChange={(e) =>
                                        setForm({
                                            ...form,
                                            date: e.target.value,
                                        })
                                    }
                                    className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                />
                            </div>
                            {editing && (
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                        Estado
                                    </label>
                                    <select
                                        value={form.status}
                                        onChange={(e) =>
                                            setForm({
                                                ...form,
                                                status: e.target.value,
                                            })
                                        }
                                        className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                    >
                                        {[
                                            "CREATED",
                                            "COMPLETED",
                                            "DELETED",
                                        ].map((s) => (
                                            <option key={s} value={s}>
                                                {S[s]}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            )}
                            <div className="flex gap-3 pt-2">
                                <button
                                    type="button"
                                    onClick={() => setShowModal(false)}
                                    className="flex-1 py-2 border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 text-sm rounded-lg hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors"
                                >
                                    Cancelar
                                </button>
                                <button
                                    type="submit"
                                    className="flex-1 py-2 bg-indigo-600 text-white text-sm rounded-lg hover:bg-indigo-700 transition-colors"
                                >
                                    {editing ? "Guardar" : "Crear"}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}
