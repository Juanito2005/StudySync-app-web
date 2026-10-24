package dev.juanito.studysync.service.impl;

import dev.juanito.studysync.dto.StatsResponseDto;
import dev.juanito.studysync.dto.SubjectStatsDto;
import dev.juanito.studysync.dto.WeeklyStatsDto;
import dev.juanito.studysync.model.Planning;
import dev.juanito.studysync.model.RoutineLog;
import dev.juanito.studysync.model.Status;
import dev.juanito.studysync.model.Subject;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.repository.PlanningRepository;
import dev.juanito.studysync.repository.RoutineLogRepository;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.service.StatisticsService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final PlanningRepository planningRepository;
    private final RoutineLogRepository routineLogRepository;
    private final AuthenticationHelper authenticationHelper;

    public StatisticsServiceImpl(
        PlanningRepository planningRepository,
        RoutineLogRepository routineLogRepository,
        AuthenticationHelper authenticationHelper
    ) {
        this.planningRepository = planningRepository;
        this.routineLogRepository = routineLogRepository;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public StatsResponseDto getUserStatistics() {
        User user = authenticationHelper.getAuthenticatedUser();

        // Cargamos toda la data del usuario de una sola vez
        List<Planning> plannings = planningRepository.findByUser(user);
        List<RoutineLog> routineLogs = routineLogRepository.findByUser(user);

        // --- Resumen general ---
        // Filtramos los plannings DELETED porque no cuentan pa' las estadísticas
        List<Planning> activePlannings = plannings
            .stream()
            .filter(p -> p.getStatus() != Status.DELETED)
            .toList();

        double totalPlannedHours = activePlannings
            .stream()
            .mapToDouble(p ->
                p.getPlannedHours() != null ? p.getPlannedHours() : 0.0
            )
            .sum();

        double totalActualHours = routineLogs
            .stream()
            .mapToDouble(r ->
                r.getActualHours() != null ? r.getActualHours() : 0.0
            )
            .sum();

        long completedCount = activePlannings
            .stream()
            .filter(p -> p.getStatus() == Status.COMPLETED)
            .count();

        int totalActivePlannings = activePlannings.size();
        double completionRate =
            totalActivePlannings > 0
                ? (double) completedCount / totalActivePlannings
                : 0.0;

        // --- Racha (streak) ---
        int currentStreak = calculateStreak(plannings, routineLogs);

        // --- Desglose por materia ---
        List<SubjectStatsDto> bySubject = buildSubjectStats(
            plannings,
            routineLogs
        );

        // --- Desglose semanal ---
        List<WeeklyStatsDto> weekly = buildWeeklyStats(
            plannings,
            routineLogs,
            4
        );

        // Armar la respuesta
        StatsResponseDto stats = new StatsResponseDto();
        stats.setTotalPlannedHours(
            Math.round(totalPlannedHours * 100.0) / 100.0
        );
        stats.setTotalActualHours(Math.round(totalActualHours * 100.0) / 100.0);
        stats.setCompletionRate(Math.round(completionRate * 100.0) / 100.0);
        stats.setTotalPlannings(totalActivePlannings);
        stats.setCompletedPlannings((int) completedCount);
        stats.setTotalRoutineLogs(routineLogs.size());
        stats.setCurrentStreak(currentStreak);
        stats.setBySubject(bySubject);
        stats.setWeekly(weekly);

        return stats;
    }

    /**
     * Calcula la racha actual de días consecutivos con actividad de estudio.
     * Mira hacia atrás desde hoy: si hoy no ha estudiado, empieza desde ayer.
     */
    private int calculateStreak(
        List<Planning> plannings,
        List<RoutineLog> routineLogs
    ) {
        // Recolectamos todas las fechas únicas en las que hubo actividad
        Set<LocalDate> activeDays = new TreeSet<>(); // TreeSet ordena naturalmente

        for (Planning p : plannings) {
            if (p.getDate() != null) {
                activeDays.add(p.getDate().toLocalDate());
            }
        }
        for (RoutineLog r : routineLogs) {
            if (r.getDate() != null) {
                activeDays.add(r.getDate().toLocalDate());
            }
        }

        if (activeDays.isEmpty()) {
            return 0;
        }

        // Convertimos a lista ordenada descendente para contar desde hoy hacia atrás
        List<LocalDate> sortedDays = new ArrayList<>(activeDays);
        sortedDays.sort(Comparator.reverseOrder());

        LocalDate today = LocalDate.now();
        int streak = 0;

        // Si el día más reciente con actividad es hoy o ayer, empezamos la racha
        LocalDate checkDate = today;
        if (!activeDays.contains(today)) {
            // Si hoy no ha estudiado, miramos si ayer sí
            if (!activeDays.contains(today.minusDays(1))) {
                return 0; // Ni hoy ni ayer = racha rota
            }
            checkDate = today.minusDays(1);
        }

        // Contamos hacia atrás
        LocalDate pointer = checkDate;
        while (activeDays.contains(pointer)) {
            streak++;
            pointer = pointer.minusDays(1);
        }

        return streak;
    }

    /**
     * Agrupa horas planeadas y reales por materia.
     */
    private List<SubjectStatsDto> buildSubjectStats(
        List<Planning> plannings,
        List<RoutineLog> routineLogs
    ) {
        // Usamos un Map para acumular por subjectId
        Map<Subject, SubjectStatsDto> statsMap = new LinkedHashMap<>();

        // Procesar plannings
        for (Planning p : plannings) {
            Subject subject = p.getSubject();
            if (subject == null) continue;

            SubjectStatsDto dto = statsMap.computeIfAbsent(subject, s -> {
                SubjectStatsDto newDto = new SubjectStatsDto();
                newDto.setSubjectId(s.getId());
                newDto.setSubjectName(s.getName());
                newDto.setColorHEX(s.getColorHEX());
                newDto.setPlannedHours(0.0);
                newDto.setActualHours(0.0);
                newDto.setPlanningCount(0);
                newDto.setRoutineLogCount(0);
                return newDto;
            });

            if (p.getStatus() != Status.DELETED) {
                dto.setPlannedHours(
                    dto.getPlannedHours() +
                        (p.getPlannedHours() != null
                            ? p.getPlannedHours()
                            : 0.0)
                );
                dto.setPlanningCount(dto.getPlanningCount() + 1);
            }
        }

        // Procesar routine logs
        for (RoutineLog r : routineLogs) {
            Subject subject = r.getSubject();
            if (subject == null) continue;

            SubjectStatsDto dto = statsMap.computeIfAbsent(subject, s -> {
                SubjectStatsDto newDto = new SubjectStatsDto();
                newDto.setSubjectId(s.getId());
                newDto.setSubjectName(s.getName());
                newDto.setColorHEX(s.getColorHEX());
                newDto.setPlannedHours(0.0);
                newDto.setActualHours(0.0);
                newDto.setPlanningCount(0);
                newDto.setRoutineLogCount(0);
                return newDto;
            });

            dto.setActualHours(
                dto.getActualHours() +
                    (r.getActualHours() != null ? r.getActualHours() : 0.0)
            );
            dto.setRoutineLogCount(dto.getRoutineLogCount() + 1);
        }

        // Redondear valores
        List<SubjectStatsDto> result = new ArrayList<>(statsMap.values());
        for (SubjectStatsDto dto : result) {
            dto.setPlannedHours(
                Math.round(dto.getPlannedHours() * 100.0) / 100.0
            );
            dto.setActualHours(
                Math.round(dto.getActualHours() * 100.0) / 100.0
            );
        }

        return result;
    }

    /**
     * Construye el desglose semanal de las últimas N semanas.
     */
    private List<WeeklyStatsDto> buildWeeklyStats(
        List<Planning> plannings,
        List<RoutineLog> routineLogs,
        int weeksBack
    ) {
        List<WeeklyStatsDto> weeklyStats = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM");

        LocalDate today = LocalDate.now();
        // Empezamos desde el lunes de esta semana
        LocalDate weekStart = today.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        );

        for (int i = 0; i < weeksBack; i++) {
            LocalDate weekEnd = weekStart.plusDays(6); // domingo
            // Para la semana actual, el fin es hoy (no queremos contar el futuro)
            if (i == 0 && weekEnd.isAfter(today)) {
                weekEnd = today;
            }

            LocalDateTime weekStartTime = weekStart.atStartOfDay();
            LocalDateTime weekEndTime = weekEnd.atTime(23, 59, 59);

            // Horas planeadas en esta semana (excluyendo DELETED)
            double planned = plannings
                .stream()
                .filter(p -> p.getStatus() != Status.DELETED)
                .filter(
                    p ->
                        p.getDate() != null &&
                        !p.getDate().isBefore(weekStartTime) &&
                        !p.getDate().isAfter(weekEndTime)
                )
                .mapToDouble(p ->
                    p.getPlannedHours() != null ? p.getPlannedHours() : 0.0
                )
                .sum();

            // Horas reales en esta semana
            double actual = routineLogs
                .stream()
                .filter(
                    r ->
                        r.getDate() != null &&
                        !r.getDate().isBefore(weekStartTime) &&
                        !r.getDate().isAfter(weekEndTime)
                )
                .mapToDouble(r ->
                    r.getActualHours() != null ? r.getActualHours() : 0.0
                )
                .sum();

            String label =
                weekStart.format(formatter) + " - " + weekEnd.format(formatter);

            WeeklyStatsDto dto = new WeeklyStatsDto();
            dto.setWeekLabel(label);
            dto.setPlannedHours(Math.round(planned * 100.0) / 100.0);
            dto.setActualHours(Math.round(actual * 100.0) / 100.0);
            weeklyStats.add(dto);

            // Retroceder una semana
            weekStart = weekStart.minusWeeks(1);
        }

        // Invertir para que vaya de la más antigua a la más reciente
        List<WeeklyStatsDto> reversed = new ArrayList<>();
        for (int i = weeklyStats.size() - 1; i >= 0; i--) {
            reversed.add(weeklyStats.get(i));
        }

        return reversed;
    }
}
