# StudySync

## Gestor de rutinas y progreso de estudio

---

**Alumno:** Juan Camilo García Cuevas

**Ciclo Formativo:** DAM — 2º Curso

**Tutor/a del proyecto:** Elam

**Modalidad:** Desarrollo de Procesos

**Curso académico:** 2025–2026

**Fecha de entrega:** 31/05/2026

---

> Proyecto de Fin de Grado presentado ante el tribunal evaluador del centro
> Explora x Ucademy

---

## Índice

1. **Portada**
2. **Índice**
3. **Resumen**
4. **Introducción**
    - 4.1 Contextualización del proyecto
    - 4.2 Justificación de la modalidad
    - 4.3 Objetivos generales y específicos
    - 4.4 Alcance
5. **Marco teórico / Fundamentación**
    - 5.1 Tecnologías de desarrollo web modernas
    - 5.2 Arquitectura cliente-servidor y APIs REST
    - 5.3 Autenticación y seguridad con JWT
    - 5.4 Bases de datos relacionales y ORM
    - 5.5 Metodología de desarrollo
    - 5.6 Legislación aplicable (RGPD, LOPDGDD)
6. **Desarrollo del proyecto**
    - 6.1 Fase 1 — Análisis y diseño
        - 6.1.1 Requisitos funcionales y no funcionales
        - 6.1.2 Modelo de datos y diagrama entidad-relación
        - 6.1.3 Diseño de la API REST
        - 6.1.4 Diseño de la interfaz de usuario (wireframes)
    - 6.2 Fase 2 — Implementación del backend
        - 6.2.1 Configuración del entorno (Spring Boot, MySQL, Docker)
        - 6.2.2 Implementación de entidades JPA y repositorios
        - 6.2.3 Capa de servicio y lógica de negocio
        - 6.2.4 Controladores REST y DTOs
        - 6.2.5 Seguridad: autenticación JWT y autorización
        - 6.2.6 Manejo de excepciones y validaciones
    - 6.3 Fase 3 — Implementación del frontend
        - 6.3.1 Configuración del entorno (React, Vite, Tailwind CSS)
        - 6.3.2 Arquitectura de componentes y enrutamiento
        - 6.3.3 Cliente HTTP y gestión del token JWT
        - 6.3.4 Páginas principales: landing, dashboard, CRUD, estadísticas
        - 6.3.5 Diseño responsive y modo oscuro
    - 6.4 Fase 4 — Pruebas y validación
        - 6.4.1 Pruebas unitarias del backend (JWT)
        - 6.4.2 Pruebas de integración de controladores (MockMvc)
        - 6.4.3 Pruebas funcionales de la API
        - 6.4.4 Pruebas de interfaz y experiencia de usuario
7. **Resultados y evaluación**
    - 7.1 Funcionalidades implementadas frente al MVP planificado
    - 7.2 Cobertura de pruebas
    - 7.3 Capturas de la aplicación en funcionamiento
    - 7.4 Valoración crítica del trabajo
8. **Conclusiones**
    - 8.1 Reflexión final
    - 8.2 Aprendizajes técnicos y personales
    - 8.3 Propuestas de mejora y líneas de continuidad
9. **Bibliografía**
10. **Anexos**
    - 10.1 Enlace al repositorio de código fuente
    - 10.2 Manual de instalación y despliegue
    - 10.3 Esquema completo de la base de datos
    - 10.4 Capturas de pantalla adicionales
    - 10.5 Listado completo de endpoints de la API

---

## 3. Resumen

StudySync es una aplicación web full-stack diseñada para la gestión integral de rutinas de estudio. El proyecto nace de la necesidad de ofrecer a los estudiantes una herramienta centralizada donde planificar sesiones, registrar el tiempo real dedicado y visualizar el progreso mediante métricas claras, superando las limitaciones de las aplicaciones genéricas de productividad.

El sistema se ha desarrollado siguiendo una arquitectura cliente-servidor con una API REST como núcleo de comunicación. El backend, implementado en Java 21 con Spring Boot 3.5, expone 23 endpoints protegidos mediante autenticación JWT sin estado y persiste los datos en MySQL 8.0 a través de Hibernate/JPA. El frontend consiste en una Single Page Application construida con React 19, Vite y Tailwind CSS v4, que ofrece una interfaz responsive con modo oscuro, dashboard de estadísticas, landing page SEO y operaciones CRUD completas para usuarios, materias, planes de estudio y registros de sesiones.

El desarrollo se ha estructurado en cuatro fases: análisis y diseño del modelo de datos y la API, implementación del backend con cobertura de pruebas unitarias y de integración (23 pruebas con JUnit y MockMvc), construcción del frontend con gestión de estado y enrutamiento, y validación funcional completa del sistema. El entorno de desarrollo se orquesta mediante Docker Compose para la base de datos y Vite para el servidor de desarrollo frontal.

El resultado es un producto funcional que cumple el alcance mínimo viable definido inicialmente y sirve como evidencia de las competencias adquiridas en el ciclo formativo en áreas como desarrollo backend con Java, diseño de APIs REST, seguridad con JWT, desarrollo frontend con React y pruebas automatizadas.

---

## 4. Introducción

### 4.1 Contextualización del proyecto

En el entorno académico actual, los estudiantes disponen de múltiples herramientas digitales para organizar su tiempo: calendarios, listas de tareas, temporizadores Pomodoro y aplicaciones de notas. Sin embargo, ninguna de ellas está diseñada específicamente para el flujo de trabajo del estudio: planificar _cuánto_ y _cuándo_ se va a estudiar cada materia, registrar _cuánto_ se estudió realmente y comparar ambas cifras para evaluar la efectividad.

Esta brecha funcional es especialmente relevante en la Formación Profesional, donde la carga de trabajo práctico exige una organización meticulosa. Un estudiante que dedica 10 horas semanales a programación y 5 a inglés necesita saber no solo cuánto planeó, sino cuánto cumplió realmente, en qué materias se desvía y qué semanas son más productivas.

StudySync nace para cubrir esta necesidad: una aplicación web que centraliza la planificación, el registro y el análisis del estudio en un único lugar, ofreciendo métricas accionables como la tasa de completado, la racha de días consecutivos y el desglose por materia y semana.

### 4.2 Justificación de la modalidad

La modalidad seleccionada para este Proyecto de Fin de Grado es **Desarrollo de Procesos**, conforme a la clasificación establecida por el centro educativo. Esta elección se fundamenta en que el trabajo consiste en el diseño, implementación y validación de un proceso completo de desarrollo de software: desde la captura de requisitos y el modelado de datos, pasando por la codificación del backend y frontend, hasta las pruebas automatizadas y la documentación técnica.

A diferencia de una memoria bibliográfica o un estudio de viabilidad, este proyecto produce un artefacto funcional y tangible —una aplicación web operativa— cuyo valor reside en el propio proceso de construcción y en las decisiones técnicas documentadas a lo largo del mismo.

### 4.3 Objetivos generales y específicos

**Objetivo general**

Desarrollar una aplicación web full-stack que permita a los estudiantes planificar rutinas de estudio, registrar sesiones reales y visualizar su progreso mediante estadísticas, aplicando una arquitectura cliente-servidor con autenticación segura y persistencia en base de datos relacional.

**Objetivos específicos**

1. Diseñar e implementar una API REST con Spring Boot que gestione las entidades Usuario, Materia, Plan de Estudio (Planning) y Registro de Sesión (RoutineLog), aplicando el patrón DTO y validaciones Jakarta.
2. Implementar un sistema de autenticación sin estado basado en JSON Web Tokens (JWT) con Spring Security, garantizando que cada usuario solo acceda a sus propios datos.
3. Modelar una base de datos relacional en MySQL con JPA/Hibernate que refleje las relaciones uno-a-muchos y uno-a-uno entre las entidades del dominio.
4. Construir una Single Page Application con React y Tailwind CSS que consuma la API REST, ofreciendo una interfaz responsive, modo oscuro y una landing page informativa.
5. Desarrollar un panel de estadísticas que calcule y visualice horas totales, tasa de completado, desglose por materia, tendencia semanal y racha de estudio.
6. Implementar pruebas unitarias y de integración que validen el correcto funcionamiento de los componentes críticos del sistema.
7. Documentar el proceso de desarrollo, las decisiones de arquitectura y los resultados obtenidos como parte del presente informe.

### 4.4 Alcance

El proyecto se acota a un **Producto Mínimo Viable (MVP)** que incluye:

- Registro e inicio de sesión de usuarios con JWT.
- CRUD completo de materias, planes de estudio y registros de sesiones, con filtrado por materia y verificación de propiedad.
- Panel de estadísticas con resumen general, desglose por materia y tendencia semanal.
- Interfaz responsive con diseño adaptado a escritorio y móvil, incluyendo modo oscuro.
- Landing page pública con información del producto, sección de características, comparativa, FAQ y formulario de contacto.
- Perfil de usuario con edición de datos, cambio de avatar y eliminación de cuenta.
- 23 pruebas automatizadas entre unitarias (JWT) y de integración de controladores (MockMvc).

Quedan fuera del alcance del MVP, aunque se mencionan como líneas de mejora: notificaciones por correo, recuperación de contraseña, exportación real a CSV, despliegue en producción con HTTPS, tests end-to-end y panel de administración.

---

## 5. Marco teórico / Fundamentación

### 5.1 Tecnologías de desarrollo web modernas

El desarrollo de StudySync se apoya en un conjunto de tecnologías consolidadas en la industria del desarrollo web. En el lado del servidor, **Java 21** (Oracle, 2023) constituye la plataforma base sobre la que se ejecuta el backend. Java es uno de los lenguajes más utilizados en entornos empresariales debido a su madurez, rendimiento y ecosistema de bibliotecas. La versión 21 es una versión LTS (Long Term Support) que introduce mejoras como Virtual Threads y Pattern Matching.

Sobre Java se asienta **Spring Boot 3.5.4**, un framework que simplifica radicalmente la creación de aplicaciones autocontenidas. Spring Boot sigue el paradigma de convención sobre configuración: el desarrollador solo necesita especificar aquello que se desvía del comportamiento por defecto. Proporciona integración nativa con Spring Web (controladores REST), Spring Security (autenticación y autorización), Spring Data JPA (acceso a datos) y un servidor Tomcat embebido que elimina la necesidad de desplegar archivos WAR en un servidor externo.

En el lado del cliente, **React 19** (Meta, 2024) es una biblioteca de JavaScript para construir interfaces de usuario basadas en componentes reutilizables. React utiliza un Virtual DOM que optimiza las actualizaciones del DOM real, mejorando el rendimiento. Junto con **Vite** como herramienta de construcción, se consigue un entorno de desarrollo con recarga instantánea (Hot Module Replacement) y builds de producción optimizados mediante tree-shaking.

Para el estilado se emplea **Tailwind CSS v4**, un framework CSS utility-first que permite componer diseños directamente en el HTML mediante clases atómicas como `bg-white`, `p-4` o `dark:bg-gray-800`. Tailwind elimina la necesidad de escribir CSS personalizado y garantiza consistencia visual mediante un sistema de diseño predefinido y personalizable.

### 5.2 Arquitectura cliente-servidor y APIs REST

StudySync implementa una **arquitectura cliente-servidor** de dos capas. En esta arquitectura, el cliente (frontend React) y el servidor (backend Spring Boot) son aplicaciones independientes que se comunican exclusivamente a través de una **API REST** (Representational State Transfer).

REST, definido por Fielding (2000) en su tesis doctoral, es un estilo arquitectónico que impone restricciones como la interfaz uniforme, el sin-estado (cada petición contiene toda la información necesaria) y la separación cliente-servidor. En la práctica, los recursos (usuarios, materias, planes) se exponen como endpoints HTTP con URLs jerárquicas y los verbos HTTP indican la operación:

| Verbo    | Endpoint             | Significado               |
| -------- | -------------------- | ------------------------- |
| `GET`    | `/api/subjects`      | Listar todas las materias |
| `POST`   | `/api/subjects`      | Crear una materia         |
| `GET`    | `/api/subjects/{id}` | Obtener una materia       |
| `PUT`    | `/api/subjects/{id}` | Actualizar una materia    |
| `DELETE` | `/api/subjects/{id}` | Eliminar una materia      |

Este diseño cumple con el nivel 2 del modelo de madurez de Richardson (2008): cada recurso tiene su propia URI y se utilizan los verbos HTTP correctamente. Las respuestas utilizan códigos de estado HTTP semánticos (`201 Created`, `204 No Content`, `404 Not Found`, `409 Conflict`) y el cuerpo se serializa en formato **JSON** (JavaScript Object Notation, RFC 8259), el estándar de facto para APIs web.

La comunicación entre frontend y backend se realiza mediante peticiones HTTP asíncronas con la API `fetch` nativa de los navegadores modernos. En entorno de desarrollo, el servidor Vite actúa como proxy inverso, redirigiendo las peticiones a `/api/*` hacia el backend en `localhost:8080`, lo que evita problemas de CORS (Cross-Origin Resource Sharing) durante el desarrollo.

### 5.3 Autenticación y seguridad con JWT

La seguridad de la aplicación se basa en **JSON Web Tokens (JWT)**, definidos en el estándar RFC 7519 (Jones et al., 2015). Un JWT es un token compacto y autocontenido formado por tres partes codificadas en Base64: cabecera (header), carga útil (payload) y firma (signature).

El flujo de autenticación implementado en StudySync sigue el siguiente esquema:

1. El usuario envía sus credenciales (email y contraseña) al endpoint `POST /api/auth/login`.
2. El servidor verifica las credenciales contra la base de datos. La contraseña se compara utilizando **BCrypt**, un algoritmo de hashing adaptativo diseñado específicamente para contraseñas (Provos y Mazières, 1999). BCrypt incorpora un _salt_ aleatorio y un factor de coste configurable que lo hace resistente a ataques de fuerza bruta.
3. Si las credenciales son correctas, el servidor genera un JWT firmado con el algoritmo **HMAC-SHA256** usando una clave secreta. El payload del token incluye el identificador del usuario (`id`) y su correo electrónico (`sub`), junto con una fecha de expiración (`exp`).
4. El cliente almacena el token en `localStorage` y lo adjunta en cada petición subsiguiente mediante el encabezado HTTP `Authorization: Bearer <token>`.
5. En cada petición, el filtro `JwtAuthenticationFilter` extrae el token del encabezado, verifica su firma y vigencia, y establece el contexto de seguridad de Spring.

El sistema opera en modo **stateless** —el servidor no mantiene sesiones— lo que lo hace escalable horizontalmente sin necesidad de almacenamiento compartido de sesiones. La protección CSRF (Cross-Site Request Forgery) está deshabilitada porque la aplicación es una API sin estado que no utiliza cookies de sesión; en su lugar, la protección contra ataques proviene del propio mecanismo JWT.

### 5.4 Bases de datos relacionales y ORM

StudySync utiliza **MySQL 8.0** como sistema de gestión de bases de datos relacionales (SGBDR). MySQL, originalmente desarrollado por Michael Widenius y David Axmark (1995) y actualmente mantenido por Oracle Corporation, es uno de los SGBDR de código abierto más utilizados en aplicaciones web. Sus características incluyen soporte para transacciones ACID con el motor InnoDB, integridad referencial mediante claves foráneas, índices para optimización de consultas y un ecosistema maduro de herramientas de administración.

El acceso a la base de datos desde Java se realiza mediante **Jakarta Persistence API (JPA)**, la evolución de Java Persistence API bajo el paraguas de la Jakarta EE Working Group. JPA es una especificación que define un conjunto de interfaces y anotaciones para el mapeo objeto-relacional (ORM), permitiendo que las clases Java (entidades) se persistan automáticamente en tablas relacionales sin escribir consultas SQL manuales.

La implementación concreta de JPA utilizada es **Hibernate 6.6**, el ORM más extendido del ecosistema Java. Hibernate genera automáticamente el esquema de base de datos a partir de las anotaciones JPA (estrategia `ddl-auto`), gestiona el ciclo de vida de las entidades (persist, merge, remove) y optimiza las consultas mediante caché de primer nivel y lazy loading.

Las relaciones entre entidades se modelan con las anotaciones `@OneToMany`, `@ManyToOne` y `@OneToOne` de JPA. Por ejemplo, un Usuario tiene muchas Materias (`@OneToMany(mappedBy = "user")`), y cada Materia pertenece a un Usuario (`@ManyToOne @JoinColumn(name = "user_id")`). JPA se encarga de generar las claves foráneas y mantener la integridad referencial, que en MySQL se traduce en restricciones `FOREIGN KEY` que impiden, por ejemplo, eliminar una materia que tenga planes o sesiones asociados.

Para el desarrollo local, la base de datos se ejecuta en un contenedor **Docker** orquestado mediante **Docker Compose**, lo que garantiza que el entorno de base de datos sea reproducible en cualquier máquina sin necesidad de instalar MySQL manualmente.

### 5.5 Metodología de desarrollo

El desarrollo de StudySync ha seguido una metodología **iterativa e incremental**, inspirada en principios ágiles. Aunque no se ha aplicado Scrum o Kanban de forma estricta (el proyecto es unipersonal), sí se han adoptado prácticas ágiles como:

- **Desarrollo por fases**: el proyecto se estructuró en cuatro fases bien delimitadas (análisis, backend, frontend, pruebas) con hitos de validación al final de cada una.
- **Control de versiones con Git**: todo el código fuente se gestiona mediante Git, lo que permite mantener un historial completo de cambios, crear ramas para funcionalidades experimentales y revertir errores. Se ha seguido una convención de commits descriptivos en español.
- **Integración continua manual**: tras cada cambio significativo, se ejecutan las pruebas automatizadas y se verifica la compilación tanto del backend (`mvnw compile`) como del frontend (`npm run build`), garantizando que el código en la rama principal siempre sea funcional.
- **Patrones de diseño**: se aplican patrones consolidados como DTO (Data Transfer Object) para desacoplar las entidades JPA de las respuestas de la API, inyección de dependencias por constructor en lugar de `@Autowired`, e interfaces de servicio con implementaciones separadas.

Esta aproximación permite entregar valor incremental (cada fase produce un artefacto funcional) manteniendo la flexibilidad para adaptarse a hallazgos durante el desarrollo, como bugs descubiertos en las pruebas de integración o decisiones de diseño de la interfaz que surgen al probar con datos reales.

### 5.6 Legislación aplicable (RGPD, LOPDGDD)

Al tratarse de una aplicación que gestiona datos personales —nombre, correo electrónico y actividad de estudio de los usuarios—, el proyecto debe contemplar el marco legal de protección de datos vigente en España y la Unión Europea.

El **Reglamento General de Protección de Datos (RGPD)** o GDPR por sus siglas en inglés (Reglamento UE 2016/679 del Parlamento Europeo y del Consejo) establece los principios que rigen el tratamiento de datos personales: licitud, lealtad y transparencia (art. 5.1.a), limitación de la finalidad (art. 5.1.b), minimización de datos (art. 5.1.c), exactitud (art. 5.1.d), limitación del plazo de conservación (art. 5.1.e) e integridad y confidencialidad (art. 5.1.f).

A nivel nacional, la **Ley Orgánica 3/2018 de Protección de Datos Personales y garantía de los derechos digitales (LOPDGDD)** adapta el RGPD al ordenamiento jurídico español y añade disposiciones específicas sobre derechos digitales.

En el contexto de StudySync, las implicaciones prácticas son:

- **Consentimiento informado**: el registro implica la aceptación de una política de privacidad que detalla qué datos se recogen, con qué finalidad y durante cuánto tiempo.
- **Minimización de datos**: solo se solicita nombre, correo electrónico y contraseña (hasheada). No se recopilan datos sensibles ni de navegación.
- **Derechos ARCO**: los usuarios pueden acceder a sus datos, rectificarlos y suprimirlos. La funcionalidad de eliminación de cuenta implementa el derecho de supresión.
- **Seguridad de los datos**: la contraseña se almacena con BCrypt, la comunicación en producción debería realizarse sobre HTTPS, y las consultas a la base de datos utilizan parámetros preparados (vía JPA) que previenen inyecciones SQL.
- **Portabilidad**: aunque no está implementado en el MVP, la arquitectura permite exportar los datos del usuario en formato CSV o JSON para cumplir con el derecho a la portabilidad (art. 20 RGPD).

Si bien el proyecto se ejecuta en un entorno local controlado y no se despliega públicamente, la memoria documenta estas consideraciones como parte de las buenas prácticas profesionales exigibles en un desarrollo real.

---

## 6. Desarrollo del proyecto

### 6.1 Fase 1 — Análisis y diseño

#### 6.1.1 Requisitos funcionales y no funcionales

El punto de partida del proyecto fue la definición de un conjunto de requisitos que delimitaran el Producto Mínimo Viable (MVP). Estos se clasificaron en funcionales (lo que el sistema debe hacer) y no funcionales (cómo debe hacerlo).

**Requisitos funcionales (RF)**

| ID   | Requisito                                                                                                                                               | Prioridad |
| ---- | ------------------------------------------------------------------------------------------------------------------------------------------------------- | --------- |
| RF01 | El usuario debe poder registrarse proporcionando nombre, correo y contraseña                                                                            | Alta      |
| RF02 | El usuario debe poder iniciar sesión con correo y contraseña, recibiendo un token JWT                                                                   | Alta      |
| RF03 | El usuario debe poder crear, listar, ver, editar y eliminar materias (CRUD)                                                                             | Alta      |
| RF04 | Cada materia debe tener un nombre y un color, y el nombre debe ser único por usuario                                                                    | Alta      |
| RF05 | El usuario debe poder crear, listar, ver, editar y eliminar planes de estudio (Plannings)                                                               | Alta      |
| RF06 | Cada plan debe tener horas planeadas, fecha/hora, materia asociada y estado (CREATED/COMPLETED/DELETED)                                                 | Alta      |
| RF07 | El usuario debe poder crear, listar, ver, editar y eliminar registros de sesiones reales (RoutineLogs)                                                  | Alta      |
| RF08 | Cada registro debe tener horas reales, fecha/hora, materia asociada y notas opcionales                                                                  | Alta      |
| RF09 | El sistema debe mostrar estadísticas: total de horas planeadas y reales, tasa de completado, desglose por materia, tendencia semanal y racha de estudio | Alta      |
| RF10 | El usuario debe poder filtrar planes y sesiones por materia                                                                                             | Media     |
| RF11 | El usuario debe poder ver y editar su perfil (nombre, correo, contraseña, avatar)                                                                       | Media     |
| RF12 | El sistema debe impedir que un usuario acceda a los datos de otro usuario                                                                               | Alta      |
| RF13 | El sistema debe mostrar una landing page pública con información del producto                                                                           | Baja      |

**Requisitos no funcionales (RNF)**

| ID    | Requisito                                                              | Categoría   |
| ----- | ---------------------------------------------------------------------- | ----------- |
| RNF01 | La API debe seguir el estilo arquitectónico REST                       | Diseño      |
| RNF02 | La autenticación debe ser stateless mediante JWT                       | Seguridad   |
| RNF03 | Las contraseñas deben almacenarse con hash BCrypt                      | Seguridad   |
| RNF04 | La interfaz debe ser responsive (escritorio y móvil)                   | Usabilidad  |
| RNF05 | La aplicación debe ofrecer modo oscuro                                 | Usabilidad  |
| RNF06 | Todos los mensajes de error visibles al usuario deben estar en español | Usabilidad  |
| RNF07 | La base de datos debe ejecutarse en un contenedor Docker reproducible  | Operaciones |
| RNF08 | El sistema debe contar con al menos 20 pruebas automatizadas           | Calidad     |

#### 6.1.2 Modelo de datos y diagrama entidad-relación

A partir de los requisitos, se diseñó un modelo de datos con cuatro entidades principales y una entidad auxiliar para el estado de los planes. El modelo responde a las siguientes reglas de negocio:

- Un **Usuario** puede tener muchas Materias, muchos Planes y muchos Registros de Sesión.
- Una **Materia** pertenece a un único Usuario y puede tener muchos Planes y Registros de Sesión.
- Un **Plan** pertenece a un Usuario y a una Materia, y puede estar vinculado opcionalmente a un Registro de Sesión (relación 1:1).
- Un **Registro de Sesión** pertenece a un Usuario y a una Materia.

```
┌──────────┐       ┌──────────┐       ┌──────────┐
│   User   │1────*│ Subject  │1────*│ Planning │
│          │       │          │       │          │
│ id (PK)  │       │ id (PK)  │       │ id (PK)  │
│ name     │       │ name     │       │ plannedH │
│ email    │       │ colorHEX │       │ date     │
│ password │       │ user_id  │       │ status   │
└──────────┘       └──────────┘       │ user_id  │
     │ 1                               │ subject  │
     │                                 │ planng_id│
     │ *                               └────┬─────┘
     │         ┌──────────────┐             │ 1
     └────────*│ RoutineLog   │◄────────────┘
               │              │ 1
               │ id (PK)      │
               │ actualHours  │
               │ date         │
               │ notes        │
               │ user_id (FK) │
               │ subject_id   │
               └──────────────┘
```

El tipo de datos para los identificadores es `BIGINT` (generado por la estrategia `IDENTITY` de JPA), las horas se almacenan como `DOUBLE`, las fechas como `DATETIME`, y el estado del plan como un `ENUM` con valores `CREATED`, `COMPLETED` y `DELETED`. Todas las claves foráneas tienen restricciones de integridad referencial que impiden borrar registros padre con hijos asociados.

#### 6.1.3 Diseño de la API REST

Se diseñó una API REST con 23 endpoints organizados en seis controladores:

**Controlador de Autenticación (`/api/auth`)**

| Método | Endpoint          | Auth | Descripción                                      |
| ------ | ----------------- | ---- | ------------------------------------------------ |
| POST   | `/api/auth/login` | No   | Iniciar sesión, devuelve JWT + datos del usuario |

**Controlador de Usuarios (`/api/users`)**

| Método | Endpoint                 | Auth | Descripción                  |
| ------ | ------------------------ | ---- | ---------------------------- |
| POST   | `/api/users/register`    | No   | Registrar nuevo usuario      |
| GET    | `/api/users/{id}`        | Sí   | Obtener usuario por ID       |
| PUT    | `/api/users/update/{id}` | Sí   | Actualizar datos del usuario |
| DELETE | `/api/users/{id}`        | Sí   | Eliminar cuenta              |

**Controlador de Materias (`/api/subjects`)**

| Método | Endpoint             | Auth | Descripción                             |
| ------ | -------------------- | ---- | --------------------------------------- |
| GET    | `/api/subjects`      | Sí   | Listar materias del usuario autenticado |
| POST   | `/api/subjects`      | Sí   | Crear materia                           |
| GET    | `/api/subjects/{id}` | Sí   | Obtener materia por ID                  |
| PUT    | `/api/subjects/{id}` | Sí   | Actualizar materia                      |
| DELETE | `/api/subjects/{id}` | Sí   | Eliminar materia                        |

**Controlador de Planes (`/api/plannings`)**

| Método | Endpoint              | Auth | Descripción                                          |
| ------ | --------------------- | ---- | ---------------------------------------------------- |
| GET    | `/api/plannings`      | Sí   | Listar planes del usuario (opcional: `?subjectId=X`) |
| POST   | `/api/plannings`      | Sí   | Crear plan                                           |
| GET    | `/api/plannings/{id}` | Sí   | Obtener plan por ID                                  |
| PUT    | `/api/plannings/{id}` | Sí   | Actualizar plan (incluye cambio de estado)           |
| DELETE | `/api/plannings/{id}` | Sí   | Eliminar plan                                        |

**Controlador de Sesiones (`/api/routine-logs`)**

| Método | Endpoint                 | Auth | Descripción                                            |
| ------ | ------------------------ | ---- | ------------------------------------------------------ |
| GET    | `/api/routine-logs`      | Sí   | Listar sesiones del usuario (opcional: `?subjectId=X`) |
| POST   | `/api/routine-logs`      | Sí   | Crear registro de sesión                               |
| GET    | `/api/routine-logs/{id}` | Sí   | Obtener sesión por ID                                  |
| PUT    | `/api/routine-logs/{id}` | Sí   | Actualizar sesión                                      |
| DELETE | `/api/routine-logs/{id}` | Sí   | Eliminar sesión                                        |

**Controlador de Estadísticas (`/api/stats`)**

| Método | Endpoint     | Auth | Descripción                                |
| ------ | ------------ | ---- | ------------------------------------------ |
| GET    | `/api/stats` | Sí   | Obtener estadísticas completas del usuario |

Se definió el patrón DTO (Data Transfer Object) para cada entidad, con clases separadas para creación (`CreationDto`), actualización (`UpdateDto`) y respuesta (`ResponseDto`). Esto desacopla las entidades JPA de la capa de presentación, evita exponer campos sensibles (como la contraseña, anotada con `@JsonIgnore`) y permite validar los datos de entrada con anotaciones Jakarta (`@NotBlank`, `@NotNull`, `@Positive`).

#### 6.1.4 Diseño de la interfaz de usuario

El frontend se concibió como una Single Page Application con las siguientes vistas:

- **Landing page** (`/`): Pública. Hero, tabla comparativa, características, FAQ, formulario de contacto.
- **Login** (`/login`): Formulario de inicio de sesión.
- **Register** (`/register`): Formulario de registro.
- **Dashboard** (`/dashboard`): Protegida. Tarjetas de resumen, barra de progreso, accesos rápidos.
- **Materias** (`/subjects`): Grid de tarjetas con color, modales de creación/edición.
- **Planes** (`/plannings`): Tabla con filtro por materia, badges de estado, modales.
- **Sesiones** (`/routine-logs`): Tabla con filtro por materia, modales con campo de notas.
- **Estadísticas** (`/stats`): Barras de progreso por materia, comparativa semanal lado a lado.
- **Perfil** (`/profile`): Avatar, edición de datos, cambio de contraseña, eliminación de cuenta.

Se diseñó una barra lateral de navegación fija en escritorio y desplegable en móvil (hamburguesa). El diseño sigue un tema claro con soporte completo para modo oscuro, controlado por un toggle persistente en `localStorage`.

### 6.2 Fase 2 — Implementación del backend

#### 6.2.1 Configuración del entorno

El backend se inicializó con **Spring Initializr**, seleccionando las dependencias Spring Web, Spring Security, Spring Data JPA, MySQL Driver, Lombok, Validation y Actuator. El proyecto se configura mediante Maven con empaquetado WAR.

La base de datos MySQL 8.0 se ejecuta en un contenedor Docker orquestado por `docker-compose.yml`:

```yaml
services:
    mysql:
        image: mysql:8.0
        container_name: studysync-db
        environment:
            MYSQL_ROOT_PASSWORD: 1234
            MYSQL_DATABASE: studysync_db
        ports:
            - "3306:3306"
        volumes:
            - mysql-data:/var/lib/mysql
```

Las credenciales de conexión y la configuración de JPA se centralizan en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studysync_db
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### 6.2.2 Implementación de entidades JPA y repositorios

Se crearon cuatro entidades JPA en el paquete `model`:

**User**: entidad principal con campos `id`, `name`, `email` y `password`. La contraseña se anota con `@JsonIgnore` para evitar que se serialice en las respuestas JSON. Las relaciones `@OneToMany` con `mappedBy` establecen el lado inverso de las asociaciones con Subject, Planning y RoutineLog.

**Subject**: entidad que representa una materia de estudio. Tiene una relación `@ManyToOne` con User y relaciones `@OneToMany` con Planning y RoutineLog. El campo `name` se valida a nivel de servicio para garantizar unicidad por usuario.

**Planning**: entidad que representa un plan de estudio. Incluye `plannedHours` (`Double`), `date` (`LocalDateTime`), `status` (enumerado `Status` con valores `CREATED`, `COMPLETED`, `DELETED`), y claves foráneas a User y Subject. Mantiene una relación `@OneToOne` opcional con RoutineLog.

**RoutineLog**: entidad que representa una sesión de estudio real. Incluye `actualHours`, `date`, `notes` y claves foráneas a User y Subject. La relación con Planning es el lado inverso (`mappedBy = "routineLog"`).

Los repositorios extienden `JpaRepository` y añaden métodos de consulta derivados para el filtrado por usuario y materia:

```java
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByNameAndUser(String name, User user);
    List<Subject> findByUser(User user);
    Optional<Subject> findByIdAndUser(Long id, User user);
}
```

Este patrón se repite para PlanningRepository y RoutineLogRepository, que incluyen también `findByUserAndSubjectId` para el filtrado por materia.

#### 6.2.3 Capa de servicio y lógica de negocio

La capa de servicio sigue el patrón **interfaz + implementación**. Cada interfaz (`SubjectService`, `UserService`, `PlanningService`, `RoutineLogService`, `StatisticsService`) define el contrato, y las implementaciones en el paquete `impl` contienen la lógica de negocio.

Todas las implementaciones reciben sus dependencias por **inyección de constructor** (sin `@Autowired` en campos) e incluyen:

- **Verificación de propiedad**: mediante `AuthenticationHelper.getAuthenticatedUser()`, que extrae el usuario del contexto de seguridad JWT, todos los métodos verifican que el recurso solicitado pertenezca al usuario autenticado.
- **Mapeo DTO**: métodos privados `toResponseDto()` que convierten entidades JPA en DTOs de respuesta, evitando exponer relaciones internas o datos sensibles.
- **Validaciones de negocio**: comprobaciones como unicidad de nombre de materia por usuario, estado válido del enumerado `Status`, y pertenencia de la materia al usuario.

El **StatisticsServiceImpl** merece mención especial: implementa el cálculo de estadísticas sin necesidad de queries adicionales a la base de datos. Carga todos los planes y sesiones del usuario en memoria y realiza las agregaciones mediante streams de Java:

- **Resumen general**: suma de horas planeadas (filtrando planes `DELETED`), suma de horas reales, conteo de planes completados y tasa de completado.
- **Racha de estudio**: recolecta todas las fechas únicas con actividad (de Planning y RoutineLog), las ordena descendentemente y cuenta días consecutivos hacia atrás desde hoy.
- **Desglose por materia**: agrupa planes y sesiones por `Subject` usando un `Map<Subject, SubjectStatsDto>` y acumula horas y conteos.
- **Desglose semanal**: itera las últimas 4 semanas (lunes a domingo) y filtra los planes y sesiones cuyo `date` cae dentro de cada intervalo.

#### 6.2.4 Controladores REST y DTOs

Los controladores REST se implementaron siguiendo un patrón consistente:

- Anotación `@RestController` y `@RequestMapping` con la ruta base.
- Inyección del servicio correspondiente por constructor.
- Uso de `@Valid` en los parámetros `@RequestBody` para activar las validaciones Jakarta.
- Retorno de `ResponseEntity` con códigos de estado semánticos (`201 Created`, `200 OK`, `204 No Content`).
- Métodos con nombres descriptivos en español.

Se crearon 14 clases DTO en el paquete `dto`:

| Entidad    | Creation                | Update                | Response                | Stats             |
| ---------- | ----------------------- | --------------------- | ----------------------- | ----------------- |
| User       | `UserRegistrationDto`   | `UserUpdateDto`       | `UserResponseDto`       | —                 |
| Subject    | `SubjectCreationDto`    | `SubjectUpdateDto`    | `SubjectResponseDto`    | `SubjectStatsDto` |
| Planning   | `PlanningCreationDto`   | `PlanningUpdateDto`   | `PlanningResponseDto`   | —                 |
| RoutineLog | `RoutineLogCreationDto` | `RoutineLogUpdateDto` | `RoutineLogResponseDto` | —                 |
| Stats      | —                       | —                     | `StatsResponseDto`      | `WeeklyStatsDto`  |

Los DTOs de creación incluyen anotaciones de validación con mensajes en español, como se muestra en este ejemplo:

```java
public class PlanningCreationDto {
    @NotNull(message = "Las horas planeadas son obligatorias, ¡no me dejes adivinando!")
    @Positive(message = "Las horas planeadas deben ser un número positivo, parce")
    private Double plannedHours;

    @NotNull(message = "La fecha es obligatoria, ¿cuándo vas a estudiar?")
    private LocalDateTime date;

    @NotNull(message = "La materia es obligatoria, ¿de qué vas a estudiar pues?")
    private Long subjectId;
}
```

#### 6.2.5 Seguridad: autenticación JWT y autorización

La capa de seguridad se implementó en el paquete `security` con los siguientes componentes:

**JwtService**: servicio responsable de crear y validar tokens JWT. Utiliza la biblioteca `io.jsonwebtoken` (JJWT 0.12.5) con el algoritmo HMAC-SHA256. Los métodos principales son:

- `createToken(User user)`: genera un token que incluye el ID del usuario como claim personalizado (`id`) y el email como subject, con una expiración configurable.
- `validateToken(String token)`: verifica la firma y la fecha de expiración. Los errores de validación se registran en el log.
- `getSubject(String token)` y `getUserIdFromToken(String token)`: extraen información del payload.

**JwtAuthenticationFilter**: filtro que extiende `OncePerRequestFilter` y se ejecuta antes de `UsernamePasswordAuthenticationFilter`. Su lógica:

1. Extrae el header `Authorization`.
2. Si existe y comienza con `Bearer `, extrae el token.
3. Si el token es válido, crea un `UserPrincipal` con el email y el ID del usuario, lo envuelve en un `UsernamePasswordAuthenticationToken` y lo establece en el `SecurityContextHolder`.

**SecurityConfig**: configuración de Spring Security que:

- Deshabilita CSRF (`csrf.disable()`).
- Establece la política de sesiones como `STATELESS`.
- Permite el acceso sin autenticación a `/api/users/register` y `/api/auth/login`.
- Exige autenticación para todas las demás rutas.
- Registra el `JwtAuthenticationFilter` antes del filtro estándar de autenticación.

**AuthenticationHelper**: componente auxiliar que extrae el usuario autenticado del contexto de seguridad. Es utilizado por todas las implementaciones de servicio para verificar la propiedad de los recursos.

**UserPrincipal**: implementación de `UserDetails` que encapsula el email y el ID del usuario extraídos del JWT.

#### 6.2.6 Manejo de excepciones y validaciones

Se definieron 7 excepciones personalizadas que extienden `RuntimeException`:

| Excepción                           | Código HTTP   | Uso                                     |
| ----------------------------------- | ------------- | --------------------------------------- |
| `EmailAlreadyExistException`        | 409 Conflict  | Registro con email duplicado            |
| `SubjectIdNotFoundException`        | 404 Not Found | Materia no encontrada o acceso denegado |
| `SubjectNameAlreadyExistsException` | 409 Conflict  | Materia duplicada para el mismo usuario |
| `UserIdNotFoundException`           | 404 Not Found | Usuario no encontrado                   |
| `UserEmailNotFoundException`        | 404 Not Found | Email no registrado en login            |
| `PlanningNotFoundException`         | 404 Not Found | Plan no encontrado                      |
| `RoutineLogNotFoundException`       | 404 Not Found | Sesión no encontrada                    |

Todas son manejadas centralizadamente por `GlobalExceptionHandler`, una clase anotada con `@ControllerAdvice` que contiene métodos `@ExceptionHandler` para cada tipo de excepción, devolviendo `ResponseEntity` con el código HTTP adecuado y el mensaje en español.

Además, se manejan dos excepciones del framework:

- `MethodArgumentNotValidException`: errores de validación Jakarta. Se extraen los mensajes campo por campo y se devuelven como un mapa JSON con código 400.
- `DataIntegrityViolationException`: violaciones de integridad referencial (por ejemplo, intentar borrar una materia con planes asociados). Se devuelve un mensaje descriptivo en español con código 409.

### 6.3 Fase 3 — Implementación del frontend

#### 6.3.1 Configuración del entorno

El frontend se inicializó con **Vite** usando la plantilla React:

```bash
npm create vite@latest frontend -- --template react
```

Se instalaron tres dependencias adicionales:

| Dependencia                         | Propósito                                   |
| ----------------------------------- | ------------------------------------------- |
| `react-router-dom`                  | Enrutamiento del lado del cliente (SPA)     |
| `tailwindcss` + `@tailwindcss/vite` | Framework CSS utility-first con plugin Vite |

El archivo `vite.config.js` se configuró con:

- **Proxy inverso**: todas las peticiones a `/api/*` se redirigen a `http://localhost:8080`, eliminando problemas de CORS en desarrollo.
- **Servidor en puerto 3000** con `host: true` para permitir acceso desde dispositivos móviles en la red local.
- **Plugin de Tailwind CSS v4** integrado en el pipeline de Vite.

Para el modo oscuro basado en clases, se añadió una directiva CSS personalizada en `index.css`:

```css
@import "tailwindcss";
@custom-variant dark (&:where(.dark, .dark *));
```

#### 6.3.2 Arquitectura de componentes y enrutamiento

La aplicación sigue una arquitectura de componentes organizada en cuatro capas:

```
src/
├── api/client.js           # Cliente HTTP (fetch wrapper con JWT)
├── context/
│   ├── AuthContext.jsx      # Proveedor de estado de autenticación
│   └── useAuth.js           # Hook personalizado useAuth()
├── components/
│   └── Layout.jsx           # Sidebar + navbar + área de contenido
└── pages/
    ├── WelcomePage.jsx      # Landing page pública
    ├── LoginPage.jsx        # Formulario de inicio de sesión
    ├── RegisterPage.jsx     # Formulario de registro
    ├── DashboardPage.jsx    # Panel de resumen con estadísticas
    ├── SubjectsPage.jsx     # CRUD de materias
    ├── PlanningsPage.jsx    # CRUD de planes
    ├── RoutineLogsPage.jsx  # CRUD de sesiones
    ├── StatsPage.jsx        # Estadísticas detalladas
    └── ProfilePage.jsx      # Perfil de usuario
```

El enrutamiento se define en `App.jsx` con `react-router-dom`. Las rutas se dividen en dos categorías:

- **Públicas** (`/`, `/login`, `/register`): accesibles sin autenticación.
- **Protegidas** (`/dashboard`, `/subjects`, `/plannings`, `/routine-logs`, `/stats`, `/profile`): envueltas en un componente `ProtectedRoute` que verifica la existencia del token JWT en el contexto de autenticación. Si no hay token, redirige a `/login`.

El componente `Layout` proporciona la estructura común (sidebar + contenido) para todas las páginas protegidas. Incluye un menú de navegación con iconos, el avatar del usuario, el toggle de modo oscuro y soporte responsive con menú hamburguesa en móvil.

#### 6.3.3 Cliente HTTP y gestión del token JWT

El archivo `api/client.js` implementa un cliente HTTP unificado basado en la API `fetch` nativa. Sus características principales:

- **Adjunto automático del token JWT**: cada petición incluye el header `Authorization: Bearer <token>` si existe un token en `localStorage`.
- **Content-Type condicional**: solo se envía `Content-Type: application/json` cuando la petición incluye cuerpo (POST/PUT), evitando problemas con peticiones GET y DELETE.
- **Manejo diferenciado de 401**: si una petición GET recibe 401, no se limpia el token ni se redirige (podría ser un falso positivo). Si una petición POST/PUT/DELETE recibe 401, se asume que la sesión expiró realmente y se redirige al login.
- **Parseo inteligente de errores**: las respuestas de error se parsean como JSON (errores de validación de Jakarta) o como texto plano, filtrando valores nulos para evitar mensajes como `", Credenciales incorrectas"`.
- **Sin caché**: todas las peticiones incluyen `cache: 'no-store'` para garantizar datos actualizados.
- **Métodos semánticos**: el objeto `api` exporta métodos con nombres descriptivos (`getSubjects()`, `createPlanning(data)`, `deleteRoutineLog(id)`, etc.) que encapsulan los detalles HTTP.

El **AuthContext** (`context/AuthContext.jsx`) gestiona el estado global de autenticación mediante la Context API de React:

- **Inicialización desde localStorage**: al cargar la aplicación, el estado del usuario y el token se recuperan de `localStorage` sin necesidad de `useEffect`, evitando renders innecesarios.
- **Login**: autentica al usuario contra la API, almacena el token y los datos del usuario en `localStorage`, y actualiza el estado global.
- **Register**: crea la cuenta y automáticamente inicia sesión.
- **Logout**: limpia `localStorage` y reinicia el estado.

El hook `useAuth()` (en `context/useAuth.js`) se separó en su propio archivo para cumplir con las reglas de React Fast Refresh (que requiere que cada archivo exporte solo componentes o solo hooks, no ambos).

#### 6.3.4 Páginas principales

**WelcomePage (`/`)**: landing page pública con diseño oscuro. Incluye navbar con enlaces a login/register, sección hero con llamada a la acción, tabla comparativa StudySync vs otras apps, grid de 6 características, FAQ con acordeón nativo `<details>`, formulario de contacto y footer con enlaces a GitHub y LinkedIn. Todo el contenido está en español y utiliza clases de Tailwind para mantener consistencia con el resto de la aplicación.

**DashboardPage (`/dashboard`)**: panel principal que muestra un saludo personalizado, 4 tarjetas de resumen (horas planeadas, horas estudiadas, planes completados, racha actual), una barra de progreso con la tasa de completado, y accesos rápidos a las demás secciones. Los datos se obtienen del endpoint `GET /api/stats` y se recargan automáticamente cada vez que se navega a la página (gracias a `useEffect` dependiente de `location.key`).

**SubjectsPage (`/subjects`)**: grid de tarjetas con el color de la materia y botones de editar/eliminar. El modal de creación/edición incluye un selector de color con 8 opciones predefinidas. Los errores se muestran dentro del modal para evitar que queden ocultos detrás de él. Incluye validación visual con estados vacíos ("No tienes materias todavía").

**PlanningsPage (`/plannings`)**: tabla con columnas de materia, fecha formateada, horas, estado (badge de color: azul para CREADO, verde para COMPLETADO, gris para ELIMINADO) y acciones. Incluye un selector desplegable para filtrar por materia. El modal permite editar horas, fecha, materia y estado. El estado se valida contra el enumerado `Status` del backend.

**RoutineLogsPage (`/routine-logs`)**: estructura similar a PlanningsPage, con columna adicional de notas. El modal incluye un campo `textarea` para notas. Ambos (Plannings y RoutineLogs) utilizan `useCallback` para memoizar la función `loadData` y evitar el warning de dependencias faltantes en `useEffect`.

**StatsPage (`/stats`)**: panel de estadísticas con dos secciones principales:

- _Por materia_: barras de progreso donde el fondo gris representa las horas planeadas y el relleno coloreado las horas reales. Cada barra muestra el porcentaje con un badge de color (verde ≥100%, azul 70-99%, ámbar <70%).
- _Últimas semanas_: comparativa lado a lado con dos filas por semana (planeado en gris claro, real en color sólido). Las barras usan una escala consistente basada en el máximo global para facilitar la comparación visual. Si las horas reales superan las planeadas, la barra se vuelve verde.

**ProfilePage (`/profile`)**: página de perfil con avatar (imagen subida por el usuario o iniciales), visualización de datos en modo lectura, formulario de edición con validaciones front (nombre vacío, email sin @, contraseña < 6 caracteres), cambio de avatar mediante `FileReader` (imagen convertida a base64 y almacenada en localStorage), y sección de "Zona de peligro" para eliminación de cuenta con doble confirmación.

#### 6.3.5 Diseño responsive y modo oscuro

La aplicación se diseñó con un enfoque **mobile-first** usando las utilidades responsive de Tailwind CSS:

- **Sidebar**: fija en escritorio (`md:flex`), overlay desplegable en móvil con fondo semitransparente y botón de cierre.
- **Header móvil**: barra superior con botón hamburguesa y toggle de modo oscuro, visible solo en dispositivos pequeños (`md:hidden`).
- **Grids**: las tarjetas y tablas se adaptan mediante `grid-cols-1 sm:grid-cols-2 lg:grid-cols-4`.
- **Tablas**: contenedor con `overflow-x-auto` para scroll horizontal en pantallas estrechas.

El **modo oscuro** se implementó con el sistema de clases `dark:` de Tailwind. Un estado `darkMode` en el componente `Layout` controla la clase `dark` en el elemento `<html>`. La preferencia se persiste en `localStorage` y se sincroniza mediante `useEffect`. Todas las páginas incluyen variantes `dark:` en sus elementos estructurales:

- Fondos: `bg-white dark:bg-gray-800`
- Textos: `text-gray-900 dark:text-gray-100`
- Bordes: `border-gray-200 dark:border-gray-700`
- Inputs: `bg-white dark:bg-gray-700 dark:text-white`

### 6.4 Fase 4 — Pruebas y validación

#### 6.4.1 Pruebas unitarias del backend

Se implementaron **4 pruebas unitarias** para el servicio JWT (`JwtServiceTest`) utilizando JUnit Jupiter 5.10 y Mockito 5.2:

| Prueba                                              | Descripción                                                               | Resultado |
| --------------------------------------------------- | ------------------------------------------------------------------------- | --------- |
| `validateToken_WithValidSecret_ShouldReturnTrue`    | Un token creado con la clave secreta correcta debe validarse exitosamente | ✅        |
| `validateToken_WithInvalidSecret_ShouldReturnFalse` | Un token validado con una clave diferente debe ser rechazado              | ✅        |
| `validateToken_WithExpiredToken_ShouldReturnFalse`  | Un token con fecha de expiración pasada debe ser inválido                 | ✅        |
| `validateToken_WithTamperedToken_ShouldReturnFalse` | Un token modificado manualmente debe ser rechazado                        | ✅        |

El `JwtService` se instancia manualmente y sus campos privados (`secretKey`, `expirationMillis`) se inyectan mediante `ReflectionTestUtils.setField()`, simulando la inyección de Spring sin necesidad de cargar el contexto completo.

#### 6.4.2 Pruebas de integración de controladores

Se implementaron **18 pruebas de integración** para los controladores Planning, RoutineLog y Statistics utilizando `@WebMvcTest` con MockMvc:

**PlanningControllerTest (8 pruebas)**

| Prueba                                               | HTTP           | Descripción                             |
| ---------------------------------------------------- | -------------- | --------------------------------------- |
| `createPlanning_ShouldReturn201`                     | POST           | Creación exitosa → 201 + JSON con datos |
| `createPlanning_WithoutPlannedHours_ShouldReturn400` | POST           | Falta horas → 400                       |
| `createPlanning_WithoutSubjectId_ShouldReturn400`    | POST           | Falta materia → 400                     |
| `getPlanningById_ShouldReturn200`                    | GET /{id}      | Obtener por ID → 200                    |
| `getPlannings_ListAll_ShouldReturn200`               | GET            | Listar todos → 200 con array            |
| `getPlannings_FilterBySubject_ShouldReturn200`       | GET ?subjectId | Filtrar → 200                           |
| `updatePlanning_ShouldReturn200`                     | PUT            | Actualizar → 200 con datos nuevos       |
| `deletePlanning_ShouldReturn204`                     | DELETE         | Eliminar → 204 sin contenido            |

**RoutineLogControllerTest (8 pruebas)**: estructura idéntica a PlanningControllerTest, adaptada a sesiones de estudio (incluye validación de `actualHours` y `notes`).

**StatisticsControllerTest (2 pruebas)**

| Prueba                                        | Descripción                                                                       |
| --------------------------------------------- | --------------------------------------------------------------------------------- |
| `getStatistics_ShouldReturn200_WithAllFields` | Verifica que la respuesta incluya todos los campos: totales, por materia, semanal |
| `getStatistics_EmptyData_ShouldReturn200`     | Verifica que con datos vacíos devuelva ceros y listas vacías                      |

Todas las pruebas utilizan `@MockBean` para los servicios, simulando sus respuestas con datos de prueba. La seguridad se deshabilita mediante `@AutoConfigureMockMvc(addFilters = false)` para aislar el comportamiento del controlador.

#### 6.4.3 Pruebas funcionales de la API

Adicionalmente, se realizaron pruebas manuales con **curl** para verificar el comportamiento del backend ante casos límite:

- **Eliminación de materia ajena**: `DELETE /api/subjects/8` con token de Juanito (la materia 8 pertenece a María) → **404 Not Found** (el sistema no revela la existencia del recurso a usuarios no propietarios).
- **Eliminación de materia propia**: `DELETE /api/subjects/1` con token de Juanito → **204 No Content** (eliminación exitosa).
- **Eliminación de materia con dependencias**: `DELETE` de una materia que tiene planes asociados → **409 Conflict** con mensaje descriptivo en español.
- **Login con credenciales incorrectas**: `POST /api/auth/login` con email inexistente → **401** con mensaje `"Credenciales incorrectas"`.

#### 6.4.4 Pruebas de interfaz y experiencia de usuario

Las pruebas de interfaz se realizaron manualmente en dos dispositivos (Mac y iPhone) conectados a la misma red WiFi, verificando:

- **Flujo completo de registro → login → dashboard → CRUD**: creación de materias, planes y sesiones, cambio de estado de planes a COMPLETADO, verificación de actualización de estadísticas, eliminación de recursos.
- **Modo oscuro**: toggle funcional, persistencia entre recargas, consistencia visual en todas las páginas.
- **Responsive**: navegación con menú hamburguesa en móvil, tablas con scroll horizontal, modales adaptados al ancho de pantalla.
- **Validaciones**: mensajes de error en español para campos requeridos, email inválido, contraseña corta, nombre de materia duplicado.
- **Manejo de sesión**: cierre de sesión, redirección al login al expirar el token, imposibilidad de acceder a rutas protegidas sin autenticación.
- **Estados vacíos**: mensajes amigables cuando no hay materias, planes o sesiones ("No tienes materias todavía. ¡Crea la primera!").

---

## 7. Resultados y evaluación

### 7.1 Funcionalidades implementadas frente al MVP planificado

El MVP definido en el apartado 4.4 establecía 7 bloques funcionales. La siguiente tabla muestra el grado de cumplimiento de cada uno:

| Funcionalidad MVP                              | Estado             | Evidencia                                                                                                                                       |
| ---------------------------------------------- | ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| Registro e inicio de sesión con JWT            | ✅ Completado      | Endpoints `/api/users/register` y `/api/auth/login` operativos. Login devuelve token JWT + datos del usuario. Contraseñas hasheadas con BCrypt. |
| CRUD de materias con verificación de propiedad | ✅ Completado      | 5 endpoints REST. Unicidad de nombre por usuario. Mensajes de error en español. Protección contra eliminación con dependencias.                 |
| CRUD de planes de estudio con filtrado         | ✅ Completado      | 5 endpoints REST. Filtro por materia (`?subjectId=`). 3 estados: CREADO, COMPLETADO, ELIMINADO.                                                 |
| CRUD de registros de sesiones con filtrado     | ✅ Completado      | 5 endpoints REST. Filtro por materia. Campo de notas opcional.                                                                                  |
| Panel de estadísticas                          | ✅ Completado      | Endpoint `/api/stats` con: totales, tasa de completado, desglose por materia, tendencia semanal (4 semanas), racha de estudio.                  |
| Interfaz responsive con modo oscuro            | ✅ Completado      | 9 páginas React. Sidebar colapsable en móvil. Modo oscuro con toggle persistente. Accesible desde móvil en red local.                           |
| Landing page pública                           | ✅ Completado      | Página `/` con hero, comparativa, 6 características, FAQ, formulario de contacto, footer.                                                       |
| Perfil de usuario                              | ✅ Completado      | Página `/profile` con avatar, edición de nombre/email/contraseña, eliminación de cuenta.                                                        |
| 20+ pruebas automatizadas                      | ✅ Completado (23) | 4 unitarias (JWT) + 18 de integración (MockMvc) + 1 de contexto. 23 pruebas, 0 fallos.                                                          |

De los 13 requisitos funcionales definidos, **los 13 se han implementado**. De los 8 requisitos no funcionales, **los 8 se cumplen**. El MVP se ha completado en su totalidad.

Más allá del MVP, se implementaron funcionalidades adicionales no planificadas inicialmente:

- **Landing page completa** con tabla comparativa, FAQ con acordeón y formulario de contacto (originalmente era solo una página de login).
- **Perfil de usuario** con avatar (subida de imagen a base64), cambio de contraseña y eliminación de cuenta con doble confirmación.
- **Mensajes de error 100% en español** en backend y frontend, incluyendo validaciones Jakarta con tono informal colombiano.
- **Manejo de integridad referencial**: mensaje descriptivo cuando se intenta borrar una materia con dependencias.
- **Cliente HTTP robusto**: manejo diferenciado de 401 (falsos positivos en GET vs sesión expirada real en POST/PUT/DELETE), sin caché, sin Content-Type en GET/DELETE.
- **SEO**: meta tags Open Graph, Twitter Cards, JSON-LD estructurado y canonical URL en el `index.html`.

### 7.2 Cobertura de pruebas

El proyecto cuenta con un total de **23 pruebas automatizadas** que se ejecutan con `mvnw test` y cubren los siguientes niveles:

| Nivel       | Tipo                | Cantidad | Framework                     | Alcance                                                                                |
| ----------- | ------------------- | -------- | ----------------------------- | -------------------------------------------------------------------------------------- |
| Unitarias   | Lógica de JWT       | 4        | JUnit 5 + ReflectionTestUtils | Creación, validación, expiración y manipulación de tokens                              |
| Integración | Controladores REST  | 18       | JUnit 5 + MockMvc + Mockito   | Comportamiento HTTP de PlanningController, RoutineLogController y StatisticsController |
| Contexto    | Carga de aplicación | 1        | SpringBootTest                | Verificación de que el contexto de Spring Boot se inicializa correctamente             |

**Resultado de la suite completa:**

```
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Las pruebas de integración cubren los siguientes escenarios por controlador:

- **Creación exitosa** → verifica código 201 y estructura JSON de respuesta.
- **Validación de campos requeridos** → verifica código 400 cuando faltan campos obligatorios.
- **Recuperación por ID** → verifica código 200 y contenido de la respuesta.
- **Listado completo** → verifica código 200 y que la respuesta es un array.
- **Filtrado por materia** → verifica que el parámetro `?subjectId=` se propaga correctamente.
- **Actualización** → verifica código 200 y que los datos modificados se reflejan.
- **Eliminación** → verifica código 204.

Las pruebas no incluyen cobertura para Subjects ni Users porque sus controladores se implementaron en una fase anterior del proyecto. Las pruebas existentes se centraron en los controladores desarrollados durante la fase activa de testing (Planning, RoutineLog, Statistics).

### 7.3 Evidencias de funcionamiento

A continuación se documentan las evidencias tangibles del correcto funcionamiento del sistema.

**Compilación del backend (58 archivos fuente):**

```
[INFO] Compiling 58 source files with javac [debug parameters release 21] to target/classes
[INFO] BUILD SUCCESS
```

**Compilación del frontend (37 módulos):**

```
vite v8.0.14 building client environment for production...
✓ 37 modules transformed.
dist/index.html    7.16 kB │ gzip: 1.62 kB
dist/assets/...css 35.81 kB │ gzip: 6.85 kB
dist/assets/...js  297 kB │ gzip: 85 kB
✓ built in 90ms
```

**Prueba funcional de la API con curl — eliminación de materia propia:**

```
$ curl -X DELETE http://localhost:8080/api/subjects/1 -H "Authorization: Bearer <jwt>"
< HTTP/1.1 204 No Content
```

**Prueba funcional — eliminación de materia ajena (protección de propiedad):**

```
$ curl -X DELETE http://localhost:8080/api/subjects/8 -H "Authorization: Bearer <jwt_juanito>"
< HTTP/1.1 404 Not Found
```

**Prueba funcional — login con credenciales incorrectas:**

```
$ curl -X POST http://localhost:8080/api/auth/login -d '{"email":"noexiste@fake.com","password":"123456"}'
{"token":null,"message":"Credenciales incorrectas"}
```

**Prueba funcional — integridad referencial (borrar materia con dependencias):**

```
< HTTP/1.1 409 Conflict
No se puede eliminar porque tiene planes o sesiones asociados.
Borra primero los planes y sesiones de esta materia.
```

**Prueba de interfaz — acceso desde móvil:**

La aplicación fue probada exitosamente desde un iPhone conectado a la misma red WiFi, accediendo a `http://10.34.6.10:3000`. Se verificó la navegación completa (landing → registro → dashboard → creación de materias/planes/sesiones → estadísticas → perfil → cierre de sesión) con diseño responsive y modo oscuro funcionales.

### 7.4 Valoración crítica del trabajo

#### Fortalezas

- **Arquitectura completa**: se ha construido un sistema full-stack funcional con separación clara de responsabilidades (controladores, servicios, repositorios, DTOs en el backend; páginas, API client, contexto en el frontend).
- **Seguridad robusta**: autenticación JWT con BCrypt, verificación de propiedad en cada endpoint, protección contra eliminación con dependencias, mensajes que no revelan información a usuarios no autorizados.
- **Experiencia de usuario pulida**: modo oscuro, responsive, estados vacíos, mensajes de error en español, loading spinners, validaciones frontend y backend.
- **Cobertura de pruebas**: 23 pruebas automatizadas que validan el núcleo del sistema (autenticación y CRUD).
- **Documentación exhaustiva**: el código incluye comentarios en español, el repositorio contiene un `ARCHITECTURE.md` completo, y esta memoria documenta todo el proceso.

#### Debilidades

- **Cobertura de pruebas asimétrica**: los controladores de Subjects y Users carecen de pruebas de integración. Las pruebas existentes se concentraron en Planning, RoutineLog y Statistics.
- **Falta de tests end-to-end**: no se implementaron pruebas con Selenium o Cypress que simulen flujos de usuario completos.
- **Gestión de estado del frontend**: la aplicación no utiliza una biblioteca de gestión de estado global (Redux, Zustand). Para el alcance del MVP, React Context es suficiente, pero escalaría mal con más funcionalidades.
- **Almacenamiento del avatar**: las imágenes de perfil se almacenan en `localStorage` como base64, lo que no escala (límite de 5-10 MB por dominio). Una implementación profesional usaría almacenamiento en servidor o en la nube.
- **Caché del navegador**: aunque se añadió `cache: 'no-store'` tardíamente, durante el desarrollo se observaron problemas de datos desactualizados por caché del navegador.
- **No despliegue en producción**: la aplicación solo se ha ejecutado en entorno local. Un despliegue real requeriría configuración de HTTPS, variables de entorno para secretos, y un servidor de producción.

#### Relación con los objetivos

| Objetivo específico         | Grado de cumplimiento | Valoración                                                                              |
| --------------------------- | --------------------- | --------------------------------------------------------------------------------------- |
| 1. API REST con Spring Boot | 100%                  | 23 endpoints implementados con DTOs, validaciones y códigos HTTP semánticos             |
| 2. Autenticación JWT        | 100%                  | Sistema stateless completo con BCrypt, filtro personalizado y verificación de propiedad |
| 3. Base de datos relacional | 100%                  | 4 entidades con relaciones JPA, ejecutándose en MySQL 8.0 vía Docker                    |
| 4. SPA con React y Tailwind | 100%                  | 9 páginas con diseño responsive, modo oscuro y cliente HTTP unificado                   |
| 5. Panel de estadísticas    | 100%                  | Totales, tasa de completado, desglose por materia, tendencia semanal y racha            |
| 6. Pruebas automatizadas    | 115%                  | 23 pruebas (objetivo inicial: ~15-20)                                                   |
| 7. Documentación            | 100%                  | ARCHITECTURE.md, TFG.md, comentarios en código y Bruno API requests                     |

Todos los objetivos específicos se han cumplido. El objetivo 6 (pruebas) se superó en un 15% respecto a la estimación inicial.

---

## 8. Conclusiones

### 8.1 Reflexión final

El desarrollo de StudySync ha sido, ante todo, un ejercicio de integración real de los conocimientos adquiridos a lo largo del ciclo formativo. No se trata de un proyecto académico aislado, sino de la materialización de competencias que abarcan desde el análisis de requisitos hasta la entrega de un producto funcional con garantías de calidad.

El resultado es una aplicación que cumple el 100% de los objetivos planteados: un backend robusto con 23 endpoints REST protegidos con JWT, un frontend moderno con React y Tailwind que ofrece una experiencia de usuario cuidada, y una suite de 23 pruebas automatizadas que validan el núcleo del sistema. Más allá de las métricas, el proyecto demuestra que un estudiante de Formación Profesional es capaz de diseñar, implementar y documentar un sistema software completo aplicando estándares profesionales.

El camino no ha estado exento de dificultades. La integración entre Spring Security y JWT requirió comprender a fondo el modelo de filtros y la diferencia entre autenticación y autorización. El diseño del frontend exigió aprender React desde cero y lidiar con problemas sutiles como los bucles de renderizado causados por `navigate()` durante el render, los conflictos de Fast Refresh con exportaciones mixtas, o el manejo de la caché del navegador que enmascaraba actualizaciones de datos. Cada obstáculo superado ha sido una lección que ningún tutorial puede enseñar.

### 8.2 Aprendizajes técnicos y personales

**Aprendizajes técnicos**

1. **Spring Boot y JPA**: se adquirió fluidez en la creación de APIs REST con Spring Boot, comprendiendo el ciclo de vida de las entidades JPA, las anotaciones de mapeo (`@OneToMany`, `@ManyToOne`), y el patrón DTO para desacoplar la capa de persistencia de la de presentación.

2. **Seguridad con JWT**: se interiorizó el flujo completo de autenticación stateless: creación de tokens con claims personalizados, validación mediante filtros `OncePerRequestFilter`, protección de endpoints con `SecurityFilterChain`, y hashing de contraseñas con BCrypt.

3. **React y el ecosistema frontend**: se pasó de cero conocimiento de React a construir una SPA completa con enrutamiento, Context API para estado global, hooks (`useState`, `useEffect`, `useCallback`, `useRef`), y un cliente HTTP unificado con manejo avanzado de errores.

4. **Tailwind CSS y diseño responsive**: se aprendió a construir interfaces completas sin escribir CSS tradicional, utilizando utilidades atómicas, variantes `dark:` para modo oscuro, y breakpoints responsive (`sm:`, `md:`, `lg:`).

5. **Pruebas automatizadas**: se comprendió la diferencia entre pruebas unitarias (JWT aislado con `ReflectionTestUtils`) y de integración (controladores con `MockMvc` y `@MockBean`), así como la importancia de deshabilitar la seguridad (`addFilters = false`) para aislar el comportamiento.

6. **Docker y entornos reproducibles**: se utilizó Docker Compose para orquestar MySQL, garantizando que cualquier persona pueda replicar el entorno de desarrollo con un solo comando.

7. **Depuración de bugs complejos**: se enfrentaron problemas no triviales como el 403 en eliminación de subjects (causado por `DataIntegrityViolationException` silenciosa), el `navigate()` durante render que causaba pantallas en blanco, o el `clearToken()` agresivo que rompía la sesión con peticiones GET de fondo.

**Aprendizajes personales**

- **Pensar antes de codificar**: varios bugs se habrían evitado con un mejor diseño inicial. La experiencia enseñó a valorar el tiempo invertido en análisis frente al tiempo perdido en depuración.
- **Leer la documentación oficial**: StackOverflow es útil, pero la documentación de Spring Security, React Router y Tailwind CSS fue la fuente definitiva para resolver los problemas más complejos.
- **La persistencia vence a la inteligencia**: hubo momentos de frustración (el 403 de eliminación llevó horas de diagnóstico), pero la constancia en aislar el problema (curl directo al backend, logs del navegador, reducir el scope) fue clave.
- **Comunicar con claridad**: documentar el proyecto en `ARCHITECTURE.md` y en esta memoria obligó a estructurar el pensamiento y a explicar decisiones que de otro modo quedarían implícitas.

### 8.3 Propuestas de mejora y líneas de continuidad

StudySync es un MVP funcional, pero como todo producto software, tiene un amplio margen de evolución. Se proponen las siguientes líneas de trabajo futuro, ordenadas por prioridad:

**Corto plazo (mejoras inmediatas)**

1. **Completar la cobertura de pruebas**: añadir tests de integración para `SubjectController` y `UserController`, siguiendo el mismo patrón `@WebMvcTest` ya establecido.
2. **Tests end-to-end**: implementar pruebas con Cypress o Playwright que simulen flujos completo de usuario.
3. **Gestión de estado con Zustand o Redux**: migrar el AuthContext y los estados de carga de datos a una biblioteca especializada.
4. **Despliegue en producción**: configurar un droplet en DigitalOcean con Docker Compose, Nginx como proxy inverso con HTTPS (Let's Encrypt).

**Medio plazo (nuevas funcionalidades)**

5. **Recuperación de contraseña**: flujo de "olvidé mi contraseña" con token enviado por correo.
6. **Notificaciones y recordatorios**: correos de planes pendientes o notificaciones push en el navegador.
7. **Exportación real a CSV/PDF**: generación de archivos descargables con OpenCSV o iText.
8. **Internacionalización (i18n)**: arquitectura para añadir inglés u otros idiomas.

**Largo plazo (visión de producto)**

9. **Aplicación móvil nativa** con React Native.
10. **Funcionalidades sociales**: compartir progreso, grupos de estudio, competir en rachas.
11. **Integración con IA**: recomendaciones personalizadas de estudio.
12. **Panel de administración**: métricas agregadas y gestión de usuarios.

StudySync no termina aquí. El MVP entregado sienta las bases sobre las que construir un producto cada vez más completo, y las competencias adquiridas durante su desarrollo proporcionan la confianza necesaria para afrontar proyectos de mayor envergadura en el futuro profesional.

---

## 9. Bibliografía

A continuación se referencian las fuentes consultadas durante la elaboración del proyecto, siguiendo el estilo APA (7ª edición).

**Artículos, RFCs y ponencias académicas**

Fielding, R. T. (2000). _Architectural Styles and the Design of Network-based Software Architectures_ [Tesis doctoral, University of California, Irvine]. https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm

Jones, M., Bradley, J. y Sakimura, N. (2015). _JSON Web Token (JWT)_ (RFC 7519). Internet Engineering Task Force. https://datatracker.ietf.org/doc/html/rfc7519

Bray, T. (2017). _The JavaScript Object Notation (JSON) Data Interchange Format_ (RFC 8259). Internet Engineering Task Force. https://datatracker.ietf.org/doc/html/rfc8259

Provos, N. y Mazières, D. (1999). A Future-Adaptable Password Scheme. _Proceedings of the 1999 USENIX Annual Technical Conference_, 81-91. https://www.usenix.org/legacy/events/usenix99/provos.html

Richardson, L. (2008). _Justice Will Take Us Millions Of Intricate Moves_. Act 2: The Maturity Heuristic [Ponencia]. QCon San Francisco 2008. https://www.crummy.com/writing/speaking/2008-QCon/

**Legislación**

Reglamento (UE) 2016/679 del Parlamento Europeo y del Consejo, de 27 de abril de 2016, relativo a la protección de las personas físicas en lo que respecta al tratamiento de datos personales y a la libre circulación de estos datos (Reglamento General de Protección de Datos). _Diario Oficial de la Unión Europea_, L 119, 4 de mayo de 2016.

Ley Orgánica 3/2018, de 5 de diciembre, de Protección de Datos Personales y garantía de los derechos digitales. _Boletín Oficial del Estado_, 294, 6 de diciembre de 2018.

**Documentación oficial de tecnologías**

Oracle. (2023). _Java 21 Documentation_. https://docs.oracle.com/en/java/javase/21/

VMware. (2024). _Spring Boot 3.5 Reference Documentation_. https://docs.spring.io/spring-boot/index.html

VMware. (2024). _Spring Security Reference_. https://docs.spring.io/spring-security/reference/

Jakarta EE Working Group. (2023). _Jakarta Persistence API Specification 3.1_. https://jakarta.ee/specifications/persistence/3.1/

Hibernate Team. (2024). _Hibernate ORM 6.6 User Guide_. https://hibernate.org/orm/documentation/6.6/

Meta. (2024). _React 19 Reference_. https://react.dev/reference/react

Vite Contributors. (2024). _Vite Documentation_. https://vite.dev/

Tailwind Labs. (2024). _Tailwind CSS v4 Documentation_. https://tailwindcss.com/docs

Oracle. (2024). _MySQL 8.0 Reference Manual_. https://dev.mysql.com/doc/refman/8.0/en/

Docker Inc. (2024). _Docker Compose Documentation_. https://docs.docker.com/compose/

Mozilla Developer Network. (2024). _Fetch API_. https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API

Mozilla Developer Network. (2024). _HTTP response status codes_. https://developer.mozilla.org/en-US/docs/Web/HTTP/Status

**Bibliotecas y herramientas utilizadas**

Les Hazlewood et al. (2024). _JJWT — Java JWT: JSON Web Token for Java and Android_ (v0.12.5). https://github.com/jwtk/jjwt

Project Lombok Authors. (2024). _Project Lombok_ (v1.18.32). https://projectlombok.org/

JUnit Team. (2024). _JUnit 5 User Guide_ (v5.10). https://junit.org/junit5/docs/current/user-guide/

Mockito Team. (2024). _Mockito Documentation_ (v5.2). https://site.mockito.org/

---

## 10. Anexos

### 10.1 Enlace al repositorio de código fuente

El código fuente completo del proyecto está disponible en GitHub:

**Repositorio:** `https://github.com/Juanito2005/StudySync-app-web`

El repositorio incluye:

- Backend Spring Boot (directorio raíz)
- Frontend React + Vite (directorio `frontend/`)
- API requests de Bruno (directorio `StudySync API/`)
- Documentación de arquitectura (`ARCHITECTURE.md`)
- Docker Compose para MySQL (`docker-compose.yml`)

### 10.2 Manual de instalación y despliegue

**Requisitos previos**

- Java 21 (JDK)
- Node.js 18+ y npm
- Docker y Docker Compose (o Orbstack en macOS)
- Maven Wrapper incluido en el proyecto (`mvnw`)

**Paso 1: Base de datos**

```bash
# Iniciar MySQL en Docker
docker compose up -d

# Verificar que está corriendo
docker ps --filter name=studysync-db
```

**Paso 2: Backend**

```bash
# Compilar y ejecutar
./mvnw spring-boot:run

# El backend estará disponible en http://localhost:8080
```

**Paso 3: Frontend**

```bash
cd frontend
npm install
npm run dev

# El frontend estará disponible en http://localhost:3000
```

**Paso 4 (opcional): Poblar con datos de prueba**

Registrar usuarios manualmente desde la interfaz web o mediante curl:

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Juanito Dev","email":"juanito@studysync.com","password":"123456"}'
```

### 10.3 Esquema completo de la base de datos

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE subjects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(7) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE plannings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    planned_hours DOUBLE,
    date DATETIME NOT NULL,
    status ENUM('CREATED','COMPLETED','DELETED'),
    user_id BIGINT,
    subject_id BIGINT,
    planning_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (subject_id) REFERENCES subjects(id),
    FOREIGN KEY (planning_id) REFERENCES routine_logs(id)
);

CREATE TABLE routine_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    actual_hours DOUBLE NOT NULL,
    date DATETIME NOT NULL,
    notes VARCHAR(500),
    user_id BIGINT,
    subject_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (subject_id) REFERENCES subjects(id)
);
```

### 10.4 Capturas de pantalla adicionales

> _[Incluir aquí capturas de pantalla de la aplicación en funcionamiento: landing page, dashboard con datos, panel de estadísticas, modo oscuro, vista móvil, perfil de usuario, etc.]_

### 10.5 Listado completo de endpoints de la API

| Método | Endpoint                 | Auth | Controlador          | Descripción                       |
| ------ | ------------------------ | ---- | -------------------- | --------------------------------- |
| POST   | `/api/users/register`    | No   | UserController       | Registrar nuevo usuario           |
| POST   | `/api/auth/login`        | No   | AuthController       | Iniciar sesión (devuelve JWT)     |
| GET    | `/api/users/{id}`        | Sí   | UserController       | Obtener usuario por ID            |
| PUT    | `/api/users/update/{id}` | Sí   | UserController       | Actualizar datos del usuario      |
| DELETE | `/api/users/{id}`        | Sí   | UserController       | Eliminar cuenta de usuario        |
| GET    | `/api/subjects`          | Sí   | SubjectController    | Listar materias del usuario       |
| POST   | `/api/subjects`          | Sí   | SubjectController    | Crear materia                     |
| GET    | `/api/subjects/{id}`     | Sí   | SubjectController    | Obtener materia por ID            |
| PUT    | `/api/subjects/{id}`     | Sí   | SubjectController    | Actualizar materia                |
| DELETE | `/api/subjects/{id}`     | Sí   | SubjectController    | Eliminar materia                  |
| GET    | `/api/plannings`         | Sí   | PlanningController   | Listar planes (`.?subjectId=X`)   |
| POST   | `/api/plannings`         | Sí   | PlanningController   | Crear plan de estudio             |
| GET    | `/api/plannings/{id}`    | Sí   | PlanningController   | Obtener plan por ID               |
| PUT    | `/api/plannings/{id}`    | Sí   | PlanningController   | Actualizar plan (incluye estado)  |
| DELETE | `/api/plannings/{id}`    | Sí   | PlanningController   | Eliminar plan                     |
| GET    | `/api/routine-logs`      | Sí   | RoutineLogController | Listar sesiones (`.?subjectId=X`) |
| POST   | `/api/routine-logs`      | Sí   | RoutineLogController | Crear registro de sesión          |
| GET    | `/api/routine-logs/{id}` | Sí   | RoutineLogController | Obtener sesión por ID             |
| PUT    | `/api/routine-logs/{id}` | Sí   | RoutineLogController | Actualizar sesión                 |
| DELETE | `/api/routine-logs/{id}` | Sí   | RoutineLogController | Eliminar sesión                   |
| GET    | `/api/stats`             | Sí   | StatisticsController | Obtener estadísticas completas    |
