import { useState, useEffect, useCallback } from "react";
import { api } from "../api/client";

export default function RoutineLogsPage() {
    const [logs, setLogs] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [editing, setEditing] = useState(null);
    const [filterSubject, setFilterSubject] = useState("");
    const [form, setForm] = useState({
        actualHours: "",
        date: "",
        notes: "",
        subjectId: "",
    });

    const loadData = useCallback(async () => {
        try {
            setLoading(true);
            const [l, s] = await Promise.all([
                api.getRoutineLogs(filterSubject || undefined),
                api.getSubjects(),
            ]);
            setLogs(l);
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
            actualHours: "",
            date: new Date().toISOString().slice(0, 16),
            notes: "",
            subjectId: subjects[0]?.id || "",
        });
        setShowModal(true);
    }
    function openEdit(l) {
        setError("");
        setEditing(l);
        setForm({
            actualHours: l.actualHours || "",
            date: l.date ? l.date.slice(0, 16) : "",
            notes: l.notes || "",
            subjectId: l.subjectId || "",
        });
        setShowModal(true);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        try {
            const payload = {
                actualHours: parseFloat(form.actualHours),
                date: new Date(form.date).toISOString(),
                notes: form.notes,
                subjectId: parseInt(form.subjectId),
            };
            if (editing) await api.updateRoutineLog(editing.id, payload);
            else await api.createRoutineLog(payload);
            setShowModal(false);
            loadData();
        } catch (err) {
            setError(err.message);
        }
    }

    async function handleDelete(id) {
        if (!confirm("¿Eliminar esta sesión?")) return;
        try {
            await api.deleteRoutineLog(id);
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

    if (loading && !logs.length)
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
                        Sesiones de estudio
                    </h1>
                    <p className="text-gray-500 dark:text-gray-400 mt-1">
                        {logs.length} sesión{logs.length !== 1 ? "es" : ""}{" "}
                        registrada{logs.length !== 1 ? "s" : ""}
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
                        + Nueva sesión
                    </button>
                </div>
            </div>

            {error && (
                <div className="mb-4 p-3 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 text-sm rounded-lg">
                    {error}
                </div>
            )}

            {!logs.length ? (
                <div className="text-center py-20 bg-white dark:bg-gray-800 rounded-xl border border-dashed border-gray-300 dark:border-gray-600">
                    <span className="text-4xl">⏱️</span>
                    <p className="text-gray-500 dark:text-gray-400 mt-3">
                        No has registrado sesiones todavía
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
                                        Notas
                                    </th>
                                    <th className="text-right px-4 py-3 font-medium text-gray-600 dark:text-gray-400">
                                        Acciones
                                    </th>
                                </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-100 dark:divide-gray-700">
                                {logs.map((l) => (
                                    <tr
                                        key={l.id}
                                        className="hover:bg-gray-50 dark:hover:bg-gray-700/50"
                                    >
                                        <td className="px-4 py-3 font-medium text-gray-900 dark:text-gray-100">
                                            {l.subjectName}
                                        </td>
                                        <td className="px-4 py-3 text-gray-500 dark:text-gray-400">
                                            {fmt(l.date)}
                                        </td>
                                        <td className="px-4 py-3 text-gray-900 dark:text-gray-100">
                                            {l.actualHours}h
                                        </td>
                                        <td className="px-4 py-3 text-gray-500 dark:text-gray-400 max-w-[200px] truncate">
                                            {l.notes || "—"}
                                        </td>
                                        <td className="px-4 py-3 text-right">
                                            <button
                                                onClick={() => openEdit(l)}
                                                className="text-xs px-2.5 py-1 bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-200 dark:hover:bg-gray-600 mr-1 transition-colors"
                                            >
                                                Editar
                                            </button>
                                            <button
                                                onClick={() =>
                                                    handleDelete(l.id)
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
                            {editing ? "Editar sesión" : "Nueva sesión"}
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
                                    Horas estudiadas
                                </label>
                                <input
                                    type="number"
                                    step="0.5"
                                    min="0.5"
                                    required
                                    value={form.actualHours}
                                    onChange={(e) =>
                                        setForm({
                                            ...form,
                                            actualHours: e.target.value,
                                        })
                                    }
                                    className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                    placeholder="1.5"
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
                            <div>
                                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                    Notas
                                </label>
                                <textarea
                                    value={form.notes}
                                    onChange={(e) =>
                                        setForm({
                                            ...form,
                                            notes: e.target.value,
                                        })
                                    }
                                    rows={3}
                                    className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                    placeholder="¿Qué estudiaste?"
                                />
                            </div>
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
