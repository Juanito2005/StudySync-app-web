import { useState, useEffect } from "react";
import { api } from "../api/client";

const COLORS = [
    "#4F46E5",
    "#059669",
    "#DC2626",
    "#D97706",
    "#7C3AED",
    "#0891B2",
    "#BE185D",
    "#65A30D",
];

export default function SubjectsPage() {
    const [subjects, setSubjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [editing, setEditing] = useState(null);
    const [form, setForm] = useState({ name: "", color: COLORS[0] });

    useEffect(() => {
        loadSubjects();
    }, []);

    async function loadSubjects() {
        try {
            setLoading(true);
            const data = await api.getSubjects();
            setSubjects(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    function openCreate() {
        setError("");
        setEditing(null);
        setForm({ name: "", color: COLORS[0] });
        setShowModal(true);
    }
    function openEdit(s) {
        setError("");
        setEditing(s);
        setForm({ name: s.name, color: s.colorHEX || COLORS[0] });
        setShowModal(true);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        try {
            if (editing)
                await api.updateSubject(editing.id, {
                    name: form.name,
                    color: form.color,
                });
            else
                await api.createSubject({ name: form.name, color: form.color });
            setShowModal(false);
            loadSubjects();
        } catch (err) {
            setError(err.message);
        }
    }

    async function handleDelete(id) {
        if (!confirm("¿Estás seguro de eliminar esta materia?")) return;
        try {
            await api.deleteSubject(id);
            loadSubjects();
        } catch (err) {
            setError(err.message);
        }
    }

    if (loading)
        return (
            <div className="flex justify-center py-20">
                <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-indigo-600"></div>
            </div>
        );

    return (
        <div>
            <div className="flex items-center justify-between mb-6">
                <div>
                    <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">
                        Materias
                    </h1>
                    <p className="text-gray-500 dark:text-gray-400 mt-1">
                        {subjects.length} materia
                        {subjects.length !== 1 ? "s" : ""}
                    </p>
                </div>
                <button
                    onClick={openCreate}
                    className="px-4 py-2 bg-indigo-600 text-white text-sm font-medium rounded-lg hover:bg-indigo-700 transition-colors"
                >
                    + Nueva materia
                </button>
            </div>

            {error && (
                <div className="mb-4 p-3 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 text-sm rounded-lg">
                    {error}
                </div>
            )}

            {subjects.length === 0 ? (
                <div className="text-center py-20 bg-white dark:bg-gray-800 rounded-xl border border-dashed border-gray-300 dark:border-gray-600">
                    <span className="text-4xl">📚</span>
                    <p className="text-gray-500 dark:text-gray-400 mt-3">
                        No tienes materias todavía. ¡Crea la primera!
                    </p>
                </div>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                    {subjects.map((s) => (
                        <div
                            key={s.id}
                            className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-5 hover:shadow-md transition-shadow"
                        >
                            <div className="flex items-center gap-3 mb-3">
                                <div
                                    className="w-4 h-4 rounded-full shrink-0"
                                    style={{
                                        backgroundColor: s.colorHEX || "#ccc",
                                    }}
                                ></div>
                                <h3 className="font-semibold text-gray-900 dark:text-gray-100 truncate">
                                    {s.name}
                                </h3>
                            </div>
                            <div className="flex gap-2">
                                <button
                                    onClick={() => openEdit(s)}
                                    className="text-xs px-2.5 py-1 bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors"
                                >
                                    Editar
                                </button>
                                <button
                                    onClick={() => handleDelete(s.id)}
                                    className="text-xs px-2.5 py-1 bg-red-50 dark:bg-red-900/20 text-red-600 dark:text-red-400 rounded-md hover:bg-red-100 dark:hover:bg-red-900/40 transition-colors"
                                >
                                    Eliminar
                                </button>
                            </div>
                        </div>
                    ))}
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
                            {editing ? "Editar materia" : "Nueva materia"}
                        </h2>
                        {error && (
                            <div className="mb-4 p-2 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 text-sm rounded-lg">
                                {error}
                            </div>
                        )}
                        <form onSubmit={handleSubmit} className="space-y-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                    Nombre
                                </label>
                                <input
                                    type="text"
                                    required
                                    value={form.name}
                                    onChange={(e) =>
                                        setForm({
                                            ...form,
                                            name: e.target.value,
                                        })
                                    }
                                    className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                    placeholder="Ej: Matemáticas"
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                                    Color
                                </label>
                                <div className="flex gap-2 flex-wrap">
                                    {COLORS.map((c) => (
                                        <button
                                            key={c}
                                            type="button"
                                            onClick={() =>
                                                setForm({ ...form, color: c })
                                            }
                                            className={`w-8 h-8 rounded-full border-2 transition-transform ${form.color === c ? "border-gray-900 dark:border-white scale-110" : "border-transparent hover:scale-105"}`}
                                            style={{ backgroundColor: c }}
                                        ></button>
                                    ))}
                                </div>
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
