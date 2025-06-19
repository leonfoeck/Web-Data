package com.example.eventSpring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String place;
    private LocalDateTime event_date;
    private String description;
    private String event_type;
    private LocalDateTime time_of_creation;

    private int upvote;

    private int downvote;

    private boolean has_weather_report;

    private String weather_report;

    private String weather_icon;

    public Event(String name, String place, LocalDateTime event_date, String description, String event_type) {
        this.name = name;
        this.place = place;
        this.event_date = event_date;
        this.description = description;
        this.event_type = event_type;
        this.time_of_creation = LocalDateTime.now();
        downvote = 0;
        upvote = 0;
    }

    public Event() {
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public boolean hasWeatherReport() {
        return has_weather_report;
    }

    public void setHas_weather_report(boolean hasWeatherReport) {
        this.has_weather_report = hasWeatherReport;
    }

    public String getWeather_report() {
        return weather_report;
    }

    public void setWeather_report(String weatherReport) {
        this.weather_report = weatherReport;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public LocalDateTime getEvent_date() {
        return event_date;
    }

    public String getDescription() {
        return description;
    }

    public String getEvent_type() {
        return event_type;
    }

    public LocalDateTime getTime_of_creation() {
        return time_of_creation;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", event_date=" + event_date +
                ", description='" + description + '\'' +
                ", event_type='" + event_type + '\'' +
                ", time_of_creation=" + time_of_creation +
                ", upvote=" + upvote +
                ", downvote=" + downvote +
                ", has_weather_report=" + has_weather_report +
                ", weather_report='" + weather_report + '\'' +
                ", weather_icon='" + weather_icon + '\'' +
                '}';
    }

}
