import { useState, useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../context/useAuth";

export default function Layout({ children }) {
    const { user, logout } = useAuth();
    const location = useLocation();
    const navigate = useNavigate();
    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [darkMode, setDarkMode] = useState(
        () => localStorage.getItem("studysync_dark") === "true",
    );

    useEffect(() => {
        document.documentElement.classList.toggle("dark", darkMode);
        localStorage.setItem("studysync_dark", darkMode);
    }, [darkMode]);

    const navItems = [
        { path: "/dashboard", label: "Dashboard", icon: "📊" },
        { path: "/subjects", label: "Materias", icon: "📚" },
        { path: "/plannings", label: "Planes", icon: "📋" },
        { path: "/routine-logs", label: "Sesiones", icon: "⏱️" },
        { path: "/stats", label: "Estadísticas", icon: "📈" },
        { path: "/profile", label: "Perfil", icon: "👤" },
    ];

    function handleLogout() {
        logout();
        navigate("/login");
    }

    const linkClass = (active) =>
        `flex items-center gap-3 px-4 py-3 rounded-lg text-sm font-medium transition-colors ${
            active
                ? "bg-indigo-50 text-indigo-700 dark:bg-indigo-900/30 dark:text-indigo-300"
                : "text-gray-600 hover:bg-gray-100 hover:text-gray-900 dark:text-gray-400 dark:hover:bg-gray-800 dark:hover:text-gray-200"
        }`;

    const initials = user?.name?.[0]?.toUpperCase() || "U";
    const avatar = localStorage.getItem("studysync_avatar");

    return (
        <div className="flex h-screen overflow-hidden bg-gray-50 dark:bg-gray-950">
            <aside className="hidden md:flex md:flex-col md:w-64 md:fixed md:inset-y-0 bg-white dark:bg-gray-900 border-r border-gray-200 dark:border-gray-800">
                <div className="flex items-center justify-between h-16 px-6 border-b border-gray-200 dark:border-gray-800">
                    <span className="text-xl font-bold text-indigo-600 dark:text-indigo-400">
                        StudySync
                    </span>
                    <button
                        onClick={() => setDarkMode(!darkMode)}
                        className="p-2 rounded-lg text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                        title={darkMode ? "Modo claro" : "Modo oscuro"}
                    >
                        {darkMode ? "☀️" : "🌙"}
                    </button>
                </div>
                <nav className="flex-1 px-4 py-4 space-y-1 overflow-y-auto">
                    {navItems.map((item) => (
                        <Link
                            key={item.path}
                            to={item.path}
                            className={linkClass(
                                location.pathname === item.path,
                            )}
                        >
                            <span className="text-lg">{item.icon}</span>{" "}
                            {item.label}
                        </Link>
                    ))}
                </nav>
                <div className="p-4 border-t border-gray-200 dark:border-gray-800">
                    <div className="flex items-center gap-3">
                        {avatar ? (
                            <img
                                src={avatar}
                                alt=""
                                className="w-9 h-9 rounded-full object-cover"
                            />
                        ) : (
                            <div className="w-9 h-9 rounded-full bg-indigo-100 dark:bg-indigo-900/50 flex items-center justify-center text-indigo-600 dark:text-indigo-400 font-semibold text-sm">
                                {initials}
                            </div>
                        )}
                        <div className="flex-1 min-w-0">
                            <p className="text-sm font-medium text-gray-900 dark:text-gray-100 truncate">
                                {user?.name || "Usuario"}
                            </p>
                            <p className="text-xs text-gray-500 dark:text-gray-400 truncate">
                                {user?.email || ""}
                            </p>
                        </div>
                    </div>
                    <button
                        onClick={handleLogout}
                        className="mt-3 w-full text-left px-3 py-2 text-sm text-gray-600 dark:text-gray-400 hover:text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20 rounded-lg transition-colors"
                    >
                        Cerrar sesión
                    </button>
                </div>
            </aside>

            {sidebarOpen && (
                <div className="md:hidden fixed inset-0 z-40 flex">
                    <div
                        className="fixed inset-0 bg-black/50"
                        onClick={() => setSidebarOpen(false)}
                    ></div>
                    <div className="relative w-64 bg-white dark:bg-gray-900 shadow-lg">
                        <div className="flex items-center justify-between h-16 px-6 border-b border-gray-200 dark:border-gray-800">
                            <span className="text-xl font-bold text-indigo-600 dark:text-indigo-400">
                                StudySync
                            </span>
                            <button
                                onClick={() => setSidebarOpen(false)}
                                className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-300"
                            >
                                ✕
                            </button>
                        </div>
                        <nav className="px-4 py-4 space-y-1">
                            {navItems.map((item) => (
                                <Link
                                    key={item.path}
                                    to={item.path}
                                    onClick={() => setSidebarOpen(false)}
                                    className={linkClass(
                                        location.pathname === item.path,
                                    )}
                                >
                                    <span className="text-lg">{item.icon}</span>{" "}
                                    {item.label}
                                </Link>
                            ))}
                        </nav>
                        <div className="absolute bottom-0 left-0 right-0 p-4 border-t border-gray-200 dark:border-gray-800 bg-white dark:bg-gray-900">
                            <button
                                onClick={handleLogout}
                                className="w-full text-left px-3 py-2 text-sm text-gray-600 dark:text-gray-400 hover:text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20 rounded-lg transition-colors"
                            >
                                Cerrar sesión
                            </button>
                        </div>
                    </div>
                </div>
            )}

            <div className="flex-1 md:ml-64 flex flex-col min-h-screen">
                <header className="md:hidden sticky top-0 z-30 bg-white dark:bg-gray-900 border-b border-gray-200 dark:border-gray-800 px-4 py-3 flex items-center justify-between">
                    <button
                        onClick={() => setSidebarOpen(true)}
                        className="text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-200"
                    >
                        <svg
                            className="w-6 h-6"
                            fill="none"
                            stroke="currentColor"
                            viewBox="0 0 24 24"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth={2}
                                d="M4 6h16M4 12h16M4 18h16"
                            />
                        </svg>
                    </button>
                    <span className="text-lg font-bold text-indigo-600 dark:text-indigo-400">
                        StudySync
                    </span>
                    <button
                        onClick={() => setDarkMode(!darkMode)}
                        className="p-1 rounded-lg text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                    >
                        {darkMode ? "☀️" : "🌙"}
                    </button>
                </header>
                <main className="flex-1 p-4 md:p-8 overflow-y-auto">
                    {children}
                </main>
            </div>
        </div>
    );
}
