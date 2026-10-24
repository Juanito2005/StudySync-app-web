import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";

// https://vite.dev/config/
export default defineConfig({
    plugins: [react(), tailwindcss()],
    server: {
        host: true, // Permite conexiones desde otros dispositivos en la red
        port: 3000,
        // Proxy para redirigir /api al backend Spring Boot en el puerto 8080
        // Así evitamos problemas de CORS en desarrollo
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
            },
        },
    },
});
