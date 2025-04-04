package com.taller.entities;

import java.util.List;

public class Equipos {
    private int id;
    private String name;
    private int yearfoundation;
    private List<Jugador> players;
    private List<Estadisticas> statistics;
    private String coach;

    public Equipos() {
    }

    public Equipos(int id, String name, int yearfoundation, List<Jugador> players, List<Estadisticas> statistics,
            String coach) {
        this.id = id;
        this.name = name;
        this.yearfoundation = yearfoundation;
        this.players = players;
        this.statistics = statistics;
        this.coach = coach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearfoundation() {
        return yearfoundation;
    }

    public void setYearfoundation(int yearfoundation) {
        this.yearfoundation = yearfoundation;
    }

    public List<Jugador> getPlayers() {
        return players;
    }

    public void setPlayers(List<Jugador> players) {
        this.players = players;
    }

    public List<Estadisticas> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Estadisticas> statistics) {
        this.statistics = statistics;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    

}