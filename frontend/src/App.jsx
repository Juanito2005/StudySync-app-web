import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "./context/useAuth";
import { AuthProvider } from "./context/AuthContext";
import Layout from "./components/Layout";
import WelcomePage from "./pages/WelcomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import DashboardPage from "./pages/DashboardPage";
import SubjectsPage from "./pages/SubjectsPage";
import PlanningsPage from "./pages/PlanningsPage";
import RoutineLogsPage from "./pages/RoutineLogsPage";
import StatsPage from "./pages/StatsPage";
import ProfilePage from "./pages/ProfilePage";

function ProtectedRoute({ children }) {
    const { isAuthenticated } = useAuth();
    if (!isAuthenticated) return <Navigate to="/login" replace />;
    return <Layout>{children}</Layout>;
}

export default function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <Routes>
                    {/* Públicas */}
                    <Route path="/" element={<WelcomePage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />

                    {/* Protegidas */}
                    <Route
                        path="/dashboard"
                        element={
                            <ProtectedRoute>
                                <DashboardPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/subjects"
                        element={
                            <ProtectedRoute>
                                <SubjectsPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/plannings"
                        element={
                            <ProtectedRoute>
                                <PlanningsPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/routine-logs"
                        element={
                            <ProtectedRoute>
                                <RoutineLogsPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/stats"
                        element={
                            <ProtectedRoute>
                                <StatsPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/profile"
                        element={
                            <ProtectedRoute>
                                <ProfilePage />
                            </ProtectedRoute>
                        }
                    />

                    <Route path="*" element={<Navigate to="/" replace />} />
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    );
}
