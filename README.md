# 📚 StudySync

**Gestor de rutinas y progreso de estudio** — aplicación web full-stack que ayuda a los estudiantes a planificar sus sesiones de estudio, registrar el tiempo real dedicado y visualizar su progreso con métricas claras.

Proyecto de Fin de Grado (TFG) — Modalidad: **Desarrollo de Procesos**

---

## ✨ Características

- 🔐 **Registro e inicio de sesión** con autenticación JWT (stateless) y contraseñas hasheadas con BCrypt
- 📚 **CRUD completo de materias** con colores personalizados y unicidad por usuario
- 📋 **Planes de estudio** con estados (Creado, Completado, Eliminado) y filtrado por materia
- ⏱️ **Registro de sesiones reales** con notas y comparación planeado vs real
- 📊 **Panel de estadísticas**: horas totales, tasa de completado, desglose por materia, tendencia semanal y racha de estudio
- 👤 **Perfil de usuario** con avatar, edición de datos y eliminación de cuenta
- 🌙 **Modo oscuro** persistente
- 📱 **Diseño responsive** (escritorio y móvil)
- 🏠 **Landing page** pública con SEO optimizado (Open Graph, Twitter Cards, JSON-LD)
- ✅ **23 pruebas automatizadas** (unitarias + integración)

## 🛠️ Stack tecnológico

| Capa                 | Tecnología                                                      |
| -------------------- | --------------------------------------------------------------- |
| **Backend**          | Java 21 · Spring Boot 3.5.4 · Spring Security · Spring Data JPA |
| **Frontend**         | React 19 · Vite · Tailwind CSS v4 · React Router v7             |
| **Base de datos**    | MySQL 8.0 (Docker)                                              |
| **ORM**              | Hibernate 6.6 / JPA                                             |
| **Autenticación**    | JWT (jjwt 0.12.5) · BCrypt                                      |
| **Validación**       | Jakarta Bean Validation                                         |
| **Testing**          | JUnit Jupiter 5.10 · Mockito 5.2 · MockMvc                      |
| **Containerización** | Docker Compose                                                  |

## 🚀 Instalación y ejecución

### Requisitos previos

- Java 21 (JDK)
- Node.js 18+ y npm
- Docker / Docker Compose (o Orbstack en macOS)

### 1. Base de datos (MySQL)

```bash
docker compose up -d
```

### 2. Backend (puerto 8080)

```bash
./mvnw spring-boot:run
```

### 3. Frontend (puerto 3000)

```bash
cd frontend
npm install
npm run dev
```

La aplicación estará disponible en **http://localhost:3000**. El proxy de Vite redirige automáticamente `/api/*` al backend.

### Usuarios de prueba

| Email                   | Contraseña |
| ----------------------- | ---------- |
| `juanito@studysync.com` | `123456`   |
| `maria@studysync.com`   | `123456`   |

> ⚠️ Si usas `spring.jpa.hibernate.ddl-auto=create-drop`, las tablas se borran al reiniciar. Para desarrollo continuo usa `update`.

## 🧪 Tests

```bash
./mvnw test
```

**Resultado:** 23 pruebas, 0 fallos

| Tipo                  | Cantidad | Alcance                                                        |
| --------------------- | -------- | -------------------------------------------------------------- |
| Unitarias (JWT)       | 4        | Creación, validación, expiración y manipulación de tokens      |
| Integración (MockMvc) | 18       | PlanningController, RoutineLogController, StatisticsController |
| Contexto              | 1        | Carga de la aplicación Spring Boot                             |

## 📁 Estructura del proyecto

```
studysync/
├── docker-compose.yml          # MySQL 8.0 en Docker
├── pom.xml                     # Configuración Maven
├── StudySync API/              # Requests de Bruno (API client)
├── src/
│   ├── main/java/dev/juanito/studysync/
│   │   ├── controller/         # Controladores REST (6)
│   │   ├── dto/                # DTOs de entrada/salida (15)
│   │   ├── exception/          # Excepciones personalizadas (7)
│   │   ├── handler/            # GlobalExceptionHandler
│   │   ├── model/              # Entidades JPA (4)
│   │   ├── repository/         # Repositorios Spring Data (4)
│   │   ├── service/            # Interfaces + implementaciones
│   │   └── security/           # JWT, filtros y configuración
│   └── test/java/...           # 23 pruebas automatizadas
└── frontend/
    ├── src/api/                # Cliente HTTP con JWT
    ├── src/context/            # AuthContext + useAuth
    ├── src/components/         # Layout con sidebar
    └── src/pages/              # 9 páginas (landing, CRUD, stats...)
```

## 🔌 API Endpoints

| Método         | Endpoint                 | Auth | Descripción            |
| -------------- | ------------------------ | ---- | ---------------------- |
| POST           | `/api/users/register`    | ❌   | Registrar usuario      |
| POST           | `/api/auth/login`        | ❌   | Login (devuelve JWT)   |
| GET/PUT/DELETE | `/api/users/{id}`        | ✅   | Perfil del usuario     |
| GET/POST       | `/api/subjects`          | ✅   | Listar/crear materias  |
| GET/PUT/DELETE | `/api/subjects/{id}`     | ✅   | Gestión de materia     |
| GET/POST       | `/api/plannings`         | ✅   | Listar/crear planes    |
| GET/PUT/DELETE | `/api/plannings/{id}`    | ✅   | Gestión de plan        |
| GET/POST       | `/api/routine-logs`      | ✅   | Listar/crear sesiones  |
| GET/PUT/DELETE | `/api/routine-logs/{id}` | ✅   | Gestión de sesión      |
| GET            | `/api/stats`             | ✅   | Estadísticas completas |

## 📖 Documentación

- **[ARCHITECTURE.md](ARCHITECTURE.md)** — Arquitectura y visión general del proyecto
- **[TFG.md](TFG.md)** — Memoria completa del proyecto (10 apartados)

## 📝 Licencia

Proyecto académico creado por **Juan Camilo García Cuevas** — Proyecto de Fin de Grado, curso 2025–2026.
