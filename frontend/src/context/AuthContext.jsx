import { useState } from "react";
import { api, setToken, clearToken } from "../api/client";
import { AuthContext } from "./useAuth";

export function AuthProvider({ children }) {
    const [user, setUser] = useState(() => {
        try {
            const saved = localStorage.getItem("studysync_user");
            return saved ? JSON.parse(saved) : null;
        } catch {
            return null;
        }
    });

    const [token, setTokenState] = useState(() =>
        localStorage.getItem("studysync_token"),
    );

    async function login(email, password) {
        const data = await api.login(email, password);
        setToken(data.token);
        setTokenState(data.token);
        const userData = {
            id: data.userId,
            email: data.email,
            name: data.name,
        };
        localStorage.setItem("studysync_user", JSON.stringify(userData));
        setUser(userData);
        return data;
    }

    async function register(name, email, password) {
        await api.register(name, email, password);
        return login(email, password);
    }

    function logout() {
        clearToken();
        setTokenState(null);
        setUser(null);
    }

    const value = {
        user,
        token,
        isAuthenticated: !!token,
        login,
        register,
        logout,
    };

    return (
        <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
    );
}
