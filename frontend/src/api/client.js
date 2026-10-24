// Cliente HTTP para comunicarse con la API de StudySync.
const API_BASE = "/api";

function getToken() {
    return localStorage.getItem("studysync_token");
}

export function setToken(token) {
    localStorage.setItem("studysync_token", token);
}

export function clearToken() {
    localStorage.removeItem("studysync_token");
    localStorage.removeItem("studysync_user");
}

async function request(endpoint, options = {}) {
    const token = getToken();
    const headers = { ...options.headers };

    if (options.body) headers["Content-Type"] = "application/json";
    if (token) headers["Authorization"] = `Bearer ${token}`;
    delete options.headers;

    const method = (options.method || "GET").toUpperCase();
    const response = await fetch(`${API_BASE}${endpoint}`, {
        ...options,
        headers,
        cache: "no-store",
    });

    if (response.status === 401 && token) {
        if (method !== "GET") {
            clearToken();
            window.location.href = "/login";
        }
        throw new Error("Sesión expirada. Inicia sesión de nuevo.");
    }

    if (response.status === 204) return null;

    if (!response.ok) {
        const errorText = await response.text();
        let errorMessage;
        try {
            const errorJson = JSON.parse(errorText);
            errorMessage =
                typeof errorJson === "object"
                    ? Object.values(errorJson)
                          .filter((v) => v != null && v !== "")
                          .join(", ")
                    : errorText;
        } catch {
            errorMessage =
                errorText || `Error del servidor (${response.status})`;
        }
        throw new Error(errorMessage);
    }

    return response.json();
}

export const api = {
    login: (email, password) =>
        request("/auth/login", {
            method: "POST",
            body: JSON.stringify({ email, password }),
        }),
    register: (name, email, password) =>
        request("/users/register", {
            method: "POST",
            body: JSON.stringify({ name, email, password }),
        }),
    getUser: (id) => request(`/users/${id}`),
    updateUser: (id, data) =>
        request(`/users/update/${id}`, {
            method: "PUT",
            body: JSON.stringify(data),
        }),
    deleteUser: (id) => request(`/users/${id}`, { method: "DELETE" }),
    getSubjects: () => request("/subjects"),
    getSubject: (id) => request(`/subjects/${id}`),
    createSubject: (data) =>
        request("/subjects", { method: "POST", body: JSON.stringify(data) }),
    updateSubject: (id, data) =>
        request(`/subjects/${id}`, {
            method: "PUT",
            body: JSON.stringify(data),
        }),
    deleteSubject: (id) => request(`/subjects/${id}`, { method: "DELETE" }),
    getPlannings: (subjectId) =>
        request(`/plannings${subjectId ? "?subjectId=" + subjectId : ""}`),
    getPlanning: (id) => request(`/plannings/${id}`),
    createPlanning: (data) =>
        request("/plannings", { method: "POST", body: JSON.stringify(data) }),
    updatePlanning: (id, data) =>
        request(`/plannings/${id}`, {
            method: "PUT",
            body: JSON.stringify(data),
        }),
    deletePlanning: (id) => request(`/plannings/${id}`, { method: "DELETE" }),
    getRoutineLogs: (subjectId) =>
        request(`/routine-logs${subjectId ? "?subjectId=" + subjectId : ""}`),
    getRoutineLog: (id) => request(`/routine-logs/${id}`),
    createRoutineLog: (data) =>
        request("/routine-logs", {
            method: "POST",
            body: JSON.stringify(data),
        }),
    updateRoutineLog: (id, data) =>
        request(`/routine-logs/${id}`, {
            method: "PUT",
            body: JSON.stringify(data),
        }),
    deleteRoutineLog: (id) =>
        request(`/routine-logs/${id}`, { method: "DELETE" }),
    getStats: () => request("/stats"),
};
