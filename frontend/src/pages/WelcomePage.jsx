import { useState } from "react";
import { Link } from "react-router-dom";

const features = [
    {
        icon: "📋",
        title: "Planificación de rutinas",
        desc: "Crea rutinas personalizadas por materia, asigna horas y fechas. Todo organizado en un solo lugar.",
    },
    {
        icon: "⏱️",
        title: "Registro de sesiones",
        desc: "Marca sesiones reales con fecha, duración y materia. Ideal para medir cumplimiento frente al plan.",
    },
    {
        icon: "📈",
        title: "Dashboard de progreso",
        desc: "Visualizaciones sencillas: tiempo total por materia, gráfico semanal y porcentaje de cumplimiento.",
    },
    {
        icon: "📤",
        title: "Exportación de datos",
        desc: "Exporta planes y sesiones en CSV. Importa plantillas de rutinas.",
    },
    {
        icon: "🔒",
        title: "Seguridad y privacidad",
        desc: "Autenticación JWT. Datos en MySQL. La memoria documenta prácticas de anonimización.",
    },
    {
        icon: "🤖",
        title: "Métricas inteligentes",
        desc: "Tablas de progreso y resúmenes: rachas, comparativas por materia y más.",
    },
];

const faqs = [
    {
        q: "¿Qué es StudySync?",
        a: "StudySync es una aplicación web para planificar rutinas de estudio, registrar sesiones y visualizar el progreso mediante métricas sencillas.",
    },
    {
        q: "¿Por qué StudySync es gratis?",
        a: "StudySync fue creado por amor a la educación y la programación. No cobramos, pero aceptamos donaciones.",
    },
    {
        q: "¿Qué datos guarda StudySync?",
        a: "Guardamos datos de usuario (correo), materias, rutinas y registros de sesiones. Se aplican prácticas de anonimización.",
    },
    {
        q: "¿Cómo exporto mis datos?",
        a: "El MVP incluye exportación en CSV para planes y sesiones. La memoria describe el procedimiento técnico.",
    },
];

const comparison = [
    [
        "Objetivo principal",
        "Rutinas de estudio con análisis por materia",
        "Tareas genéricas sin foco en estudio",
    ],
    [
        "Efectividad medible",
        "Métricas, rachas, tasa de completado, CSV",
        "Sin métricas específicas de estudio",
    ],
    [
        "Personalización",
        "Rutinas por asignatura, horarios flexibles",
        "Plantillas básicas sin programación",
    ],
    [
        "Evidencia técnica",
        "Repositorio, tests, documentación de despliegue",
        "Sin evidencia técnica para un TFG",
    ],
    [
        "Privacidad",
        "Datos en MySQL, anonimización documentada",
        "Muchas apps recopilan datos sin control",
    ],
];

export default function WelcomePage() {
    const [faqOpen, setFaqOpen] = useState(null);

    return (
        <div className="min-h-screen bg-gray-950 text-gray-200">
            {/* ─── NAVBAR ─── */}
            <nav className="sticky top-0 z-50 bg-gray-900/95 backdrop-blur border-b border-gray-800">
                <div className="max-w-6xl mx-auto px-4 py-3 flex items-center justify-between flex-wrap gap-3">
                    <a
                        href="#inicio"
                        className="text-xl font-bold text-cyan-400"
                    >
                        StudySync
                    </a>
                    <div className="flex items-center gap-3 flex-wrap text-sm">
                        <a
                            href="#sobre"
                            className="text-gray-400 hover:text-white transition-colors"
                        >
                            Sobre StudySync
                        </a>
                        <a
                            href="#caracteristicas"
                            className="text-gray-400 hover:text-white transition-colors hidden sm:inline"
                        >
                            Características
                        </a>
                        <a
                            href="#FAQ"
                            className="text-gray-400 hover:text-white transition-colors hidden sm:inline"
                        >
                            FAQ
                        </a>
                        <Link
                            to="/login"
                            className="px-3 py-1.5 border border-cyan-400 text-cyan-400 rounded-lg hover:bg-cyan-400/10 transition-colors"
                        >
                            Iniciar sesión
                        </Link>
                        <Link
                            to="/register"
                            className="px-3 py-1.5 bg-cyan-500 text-black font-medium rounded-lg hover:bg-cyan-400 transition-colors"
                        >
                            Registrarse
                        </Link>
                    </div>
                </div>
            </nav>

            {/* ─── HERO ─── */}
            <section
                id="inicio"
                className="max-w-4xl mx-auto px-4 py-24 md:py-32 text-center"
            >
                <h1 className="text-4xl md:text-6xl font-bold mb-4">
                    StudySync
                </h1>
                <p className="text-xl text-gray-300 mb-2">
                    Gestor de rutinas y seguimiento del progreso de estudio
                </p>
                <p className="text-gray-500 mb-8 max-w-xl mx-auto">
                    Crea planes, registra sesiones y visualiza tu progreso de
                    forma simple y efectiva.
                </p>
                <div className="flex justify-center gap-4 flex-wrap">
                    <Link
                        to="/register"
                        className="px-8 py-3 bg-cyan-500 text-black font-bold rounded-xl hover:bg-cyan-400 transition-colors text-lg"
                    >
                        Pruébalo gratis
                    </Link>
                    <Link
                        to="/login"
                        className="px-8 py-3 border border-gray-500 text-gray-300 rounded-xl hover:bg-gray-800 transition-colors text-lg"
                    >
                        Ya tengo cuenta
                    </Link>
                </div>
            </section>

            {/* ─── COMPARATIVA ─── */}
            <section id="sobre" className="max-w-5xl mx-auto px-4 py-16">
                <h2 className="text-2xl font-bold mb-2">
                    Comparativa: StudySync vs otras apps
                </h2>
                <p className="text-gray-500 mb-6">
                    Criterios clave para evaluar StudySync frente a aplicaciones
                    genéricas de productividad.
                </p>
                <div className="overflow-x-auto rounded-xl border border-gray-800">
                    <table className="w-full text-sm">
                        <thead className="bg-gray-800 text-gray-300">
                            <tr>
                                <th className="text-left px-4 py-3 font-medium">
                                    Criterio
                                </th>
                                <th className="text-left px-4 py-3 font-medium text-cyan-400">
                                    StudySync
                                </th>
                                <th className="text-left px-4 py-3 font-medium text-gray-500">
                                    Otras apps
                                </th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-800">
                            {comparison.map((row, i) => (
                                <tr
                                    key={i}
                                    className="hover:bg-gray-800/50 transition-colors"
                                >
                                    <td className="px-4 py-3 font-medium text-gray-300">
                                        {row[0]}
                                    </td>
                                    <td className="px-4 py-3">{row[1]}</td>
                                    <td className="px-4 py-3 text-gray-500">
                                        {row[2]}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>

            {/* ─── CARACTERÍSTICAS ─── */}
            <section
                id="caracteristicas"
                className="max-w-5xl mx-auto px-4 py-16"
            >
                <h2 className="text-2xl font-bold mb-6">
                    Características principales
                </h2>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    {features.map((f, i) => (
                        <div
                            key={i}
                            className="bg-gray-800 rounded-xl p-6 hover:bg-gray-750 transition-colors border border-gray-700"
                        >
                            <span className="text-3xl mb-3 block">
                                {f.icon}
                            </span>
                            <h3 className="text-lg font-bold text-cyan-400 mb-2">
                                {f.title}
                            </h3>
                            <p className="text-gray-400 text-sm leading-relaxed">
                                {f.desc}
                            </p>
                        </div>
                    ))}
                </div>
            </section>

            {/* ─── FAQ ─── */}
            <section id="FAQ" className="max-w-3xl mx-auto px-4 py-16">
                <h2 className="text-2xl font-bold mb-6">
                    Preguntas Frecuentes
                </h2>
                <div className="space-y-3">
                    {faqs.map((faq, i) => (
                        <details
                            key={i}
                            className="bg-gray-800 rounded-xl border border-gray-700 overflow-hidden"
                            open={faqOpen === i}
                            onToggle={(e) =>
                                setFaqOpen(e.target.open ? i : null)
                            }
                        >
                            <summary className="px-5 py-4 cursor-pointer text-cyan-400 font-semibold hover:text-cyan-300 transition-colors select-none">
                                {faq.q}
                            </summary>
                            <p className="px-5 pb-4 text-gray-400 text-sm leading-relaxed">
                                {faq.a}
                            </p>
                        </details>
                    ))}
                </div>
            </section>

            {/* ─── CONTACTO ─── */}
            <section id="contacto" className="max-w-5xl mx-auto px-4 py-16">
                <h2 className="text-2xl font-bold mb-2">Contacto</h2>
                <p className="text-gray-500 mb-6">
                    ¿Tienes dudas, sugerencias para el TFG o quieres colaborar?
                    Escríbenos.
                </p>
                <div className="grid grid-cols-1 lg:grid-cols-5 gap-6">
                    <form
                        className="lg:col-span-3 bg-gray-800 rounded-xl p-6 border border-gray-700 space-y-4"
                        onSubmit={(e) => {
                            e.preventDefault();
                            window.location.href =
                                "mailto:studysync.contacto@example.com";
                        }}
                    >
                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-1">
                                Nombre
                            </label>
                            <input
                                type="text"
                                required
                                className="w-full px-3 py-2 bg-gray-900 border border-gray-600 rounded-lg text-white text-sm focus:outline-none focus:ring-2 focus:ring-cyan-500"
                                placeholder="Tu nombre"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-1">
                                Correo electrónico
                            </label>
                            <input
                                type="email"
                                required
                                className="w-full px-3 py-2 bg-gray-900 border border-gray-600 rounded-lg text-white text-sm focus:outline-none focus:ring-2 focus:ring-cyan-500"
                                placeholder="tu@ejemplo.com"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-1">
                                Mensaje
                            </label>
                            <textarea
                                rows="4"
                                required
                                className="w-full px-3 py-2 bg-gray-900 border border-gray-600 rounded-lg text-white text-sm focus:outline-none focus:ring-2 focus:ring-cyan-500"
                                placeholder="Escribe aquí tu mensaje..."
                            ></textarea>
                        </div>
                        <button
                            type="submit"
                            className="px-6 py-2.5 bg-cyan-500 text-black font-bold rounded-lg hover:bg-cyan-400 transition-colors"
                        >
                            Enviar mensaje
                        </button>
                        <p className="text-xs text-gray-500">
                            Al enviar aceptas que almacenemos tus datos para
                            responder.
                        </p>
                    </form>
                    <aside className="lg:col-span-2 bg-gray-800 rounded-xl p-6 border border-gray-700">
                        <h3 className="text-lg font-bold text-cyan-400 mb-4">
                            Canales de contacto
                        </h3>
                        <div className="space-y-3 text-sm">
                            <div className="flex justify-between">
                                <span className="text-gray-400">Soporte</span>
                                <a
                                    href="mailto:studysync.contacto@example.com"
                                    className="text-cyan-400 hover:underline"
                                >
                                    studysync.contacto@example.com
                                </a>
                            </div>
                            <div className="flex justify-between">
                                <span className="text-gray-400">GitHub</span>
                                <a
                                    href="https://github.com/Juanito2005"
                                    target="_blank"
                                    className="text-cyan-400 hover:underline"
                                    rel="noreferrer"
                                >
                                    github.com/Juanito2005
                                </a>
                            </div>
                            <div className="flex justify-between">
                                <span className="text-gray-400">Horario</span>
                                <span>Lu-Vi 09:00–18:00 CET</span>
                            </div>
                        </div>
                    </aside>
                </div>
            </section>

            {/* ─── FOOTER ─── */}
            <footer className="border-t border-gray-800 py-8 text-center text-sm text-gray-500">
                <div className="max-w-5xl mx-auto px-4">
                    <p className="mb-3">
                        © 2026 StudySync. Proyecto de TFG creado por Juan Camilo
                        García Cuevas.
                    </p>
                    <div className="flex justify-center gap-4">
                        <a
                            href="https://github.com/Juanito2005"
                            target="_blank"
                            className="text-cyan-400 hover:underline"
                            rel="noreferrer"
                        >
                            GitHub
                        </a>
                        <a
                            href="https://linkedin.com/in/juan-camilo-garcia-cuevas-917a7a288"
                            target="_blank"
                            className="text-cyan-400 hover:underline"
                            rel="noreferrer"
                        >
                            LinkedIn
                        </a>
                    </div>
                </div>
            </footer>
        </div>
    );
}
