import { createContext, useContext } from "react";

// Creamos el contexto aquí para que AuthContext.jsx solo exporte componentes
// (React Fast Refresh requiere que un archivo exporte SOLO componentes o SOLO no-componentes)
export const AuthContext = createContext(null);

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth debe usarse dentro de un AuthProvider");
    }
    return context;
}
