package com.taller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taller.entities.EquipoData;
import com.taller.entities.Equipos;
import com.taller.entities.Jugador;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
public static void main(String[] args) {
        try {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("uefa_teams.json");

        if (inputStream == null) {
                throw new RuntimeException("Archivo no encontrado: uefa_teams.json");
        }

        EquipoData equipoData = objectMapper.readValue(inputStream, EquipoData.class);
        List<Equipos> equipos = equipoData.getEquipos();

        /* 1*/ 
        System.out.println("\n Lista de Equipos:");
        equipos.forEach(e -> System.out.println("- " + e.getName()));

         /*2 */
        System.out.println("\n Equipos fundados después del 2000:");
        equipos.stream()
                .filter(e -> e.getYearfoundation() > 2000)
                .forEach(e -> System.out.println("- " + e.getName()));

        Consumer<Equipos> NEntrenador = equipo -> System.out.println("- " + equipo.getCoach());

        /*3 */
        System.out.println("\n Nombres de los entrenadores:");
        equipos.forEach(NEntrenador);

        BiConsumer<Equipos, List<Jugador>> calcularPromedioEdad = (equipo, jugadores) -> {
                double promedio = jugadores.stream()
                        .mapToInt(Jugador::getAge)
                        .average()
                        .orElse(0);

                System.out.println("- " + equipo.getName() + ": " + promedio + " años");
        };
        /*4 */
        System.out.println("\n Promedio de edad de jugadores por equipo:");
        equipos.forEach(e -> calcularPromedioEdad.accept(e, e.getPlayers()));

        

        /*5 */
        Predicate<Equipos> veintevictorias = e -> e.getStatistics().get(0).getPg() > 20;
        System.out.println("\n Equipos con más de 20 victorias:");
        equipos.stream()
                .filter(veintevictorias) 
                .forEach(e -> System.out
                .println("- " + e.getName() + " (" + e.getStatistics().get(0).getPg() + " victorias)"));

        /*6 */
        System.out.println("\n Jugador más alto de cada equipo:");
        equipos.forEach(e -> {
                e.getPlayers().stream()
                        .max((jg1, jg2) -> Integer.compare(jg1.getHeight(), jg2.getHeight()))
                        .ifPresent(jg -> System.out
                                .println("- " + e.getName() + ": " + jg.getName() + " (" + jg.getHeight() + " cm)"));
        });

        /*7 */
        System.out.println("\n Total de goles a favor por equipo:");
        equipos.forEach(e -> {
                int totalGoles = e.getStatistics().get(0).getGf(); 
                System.out.println("- " + e.getName() + ": " + totalGoles + " goles");
        });

        Predicate<Equipos> masDe15Puntos = e -> e.getStatistics().get(0).getTp() > 15;

        /*8 */
        System.out.println("\n Equipos con más de 15 puntos:");
        equipos.stream()
                .filter(masDe15Puntos)
                .forEach(e -> System.out
                        .println("- " + e.getName() + " (" + e.getStatistics().get(0).getTp() + " puntos)"));

        /*9 */
        double promedioGoles = equipos.stream()
                .collect(Collectors.averagingInt(e -> e.getStatistics().get(0).getGf()));

        System.out.println("\n Promedio de goles a favor por equipo: " + promedioGoles);

        /*10 */
        equipos.stream()
                .max(Comparator.comparingInt(e -> e.getStatistics().get(0).getPg()))
                .ifPresent(e -> System.out.println("\n📌 Equipo con más victorias: " + e.getName() + " ("
                + e.getStatistics().get(0).getPg() + " victorias)"));

        /*11 */
        equipos.stream()
                .flatMap(e -> e.getPlayers().stream())
                .max(Comparator.comparingInt(Jugador::getHeight))
                .ifPresent(j -> System.out
                        .println("\n Jugador más alto: " + j.getName() + " (" + j.getHeight() + " cm)"));

        /*12 */

        long tDelanteros = equipos.stream()
                .flatMap(e -> e.getPlayers().stream())
                .filter(p -> p.getPosition().equalsIgnoreCase("Delantero"))
                .count();

        System.out.println("\n Total de delanteros en la liga: " + tDelanteros);

        /*13 */
        System.out.println("\n Entrenadores de equipos que han empatado al menos un partido:");
        equipos.stream()
                .filter(e -> e.getStatistics().get(0).getPe() > 0)
                .map(Equipos::getCoach)
                .forEach(coach -> System.out.println("- " + coach));

        /*14 */
        Map<String, Integer> golesPorEquipo = equipos.stream()
                .collect(Collectors.toMap(Equipos::getName, e -> e.getStatistics().get(0).getGf()));

        System.out.println("\n Goles a favor por equipo:");
        golesPorEquipo.forEach((equipo, goles) -> System.out.println("- " + equipo + ": " + goles + " goles"));

        /*15 */
        System.out.println("\n Jugadores brasileños ordenados por edad:");
        equipos.stream()
                .flatMap(e -> e.getPlayers().stream())
                .filter(j -> j.getNationality().equalsIgnoreCase("Brasileño"))
                .sorted(Comparator.comparingInt(Jugador::getAge))
                .forEach(j -> System.out.println("- " + j.getName() + " (" + j.getAge() + " años)"));

        

        System.out.println("\n Equipos con entrenadores cuyo nombre tiene más de 10 caracteres:");
        equipos.stream()
                .filter(e -> e.getCoach().length() > 10)
                .forEach(e -> System.out.println("- " + e.getName() + " (Entrenador: " + e.getCoach() + ")"));

        /*17 */
        boolean tieneMasDe25Puntos = equipos.stream()
                .anyMatch(e -> e.getStatistics().get(0).getTp() > 25);

        System.out.println("\n ¿Algún equipo tiene más de 25 puntos? " + (tieneMasDe25Puntos ? "Sí" : "No"));

        /*18 */
        System.out.println("\n Cantidad de jugadores por posición:");
        Map<String, Long> jugadoresPorPosicion = equipos.stream()
                .flatMap(e -> e.getPlayers().stream())
                .collect(Collectors.groupingBy(Jugador::getPosition, Collectors.counting()));

        jugadoresPorPosicion.forEach(
                (posicion, cantidad) -> System.out.println("- " + posicion + ": " + cantidad + " jugadores"));

        /*19 */

        System.out.println("\n Equipos con más de 20 goles a favor (ordenados de mayor a menor):");
        equipos.stream()
                .filter(e -> e.getStatistics().get(0).getGf() > 20)
                .sorted(Comparator.comparingInt(e -> -e.getStatistics().get(0).getGf()))
                .forEach(e -> System.out
                .println("- " + e.getName() + " (" + e.getStatistics().get(0).getGf() + " goles)"));

        } catch (Exception e) {
        System.err.println("Error al leer el JSON: " + e.getMessage());
        e.printStackTrace();
        }
}
}
