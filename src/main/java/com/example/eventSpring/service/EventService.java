package com.example.eventSpring.service;

import com.example.eventSpring.model.Event;
import com.example.eventSpring.repository.EventRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class EventService {
    private final EventRepository eventRepository;

    Map<String, String> WEATHER_ICON_MAP = Map.ofEntries(
            Map.entry("snow", "weather_snowy"),
            Map.entry("rain", "rainy"),
            Map.entry("fog", "foggy"),
            Map.entry("wind", "air"),
            Map.entry("cloudy", "cloudy"),
            Map.entry("partly-cloudy-day", "partly_cloudy_day"),
            Map.entry("partly-cloudy-night", "nights_stay"),
            Map.entry("clear-day", "clear_day"),
            Map.entry("clear-night", "clear_night")
    );

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        for (Event event : eventRepository.findAll()) {
            if (event.getEvent_date().isBefore(LocalDateTime.now().plusDays(7)) && event.getEvent_date().isAfter(LocalDateTime.now())) {
                String weatherReport = getWeatherReport(event);
                if (!weatherReport.equals("")) {
                    event.setHas_weather_report(true);
                    event.setWeather_report(weatherReport);
                    eventRepository.save(event);
                }
            }
        }
        return eventRepository.findAll();
    }

    public boolean doesEventNameExist(String eventName) {
        return eventRepository.existsByName(eventName);
    }

    public Event getEventById(Long Id) {
        return eventRepository.findById(Id).orElse(null);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long Id) {
        eventRepository.deleteById(Id);
    }

    private String getWeatherReport(Event event) {
        String API_TOKEN = "GKBQXLU4QFB39S4CCBUXL6VKF";

        LocalDate eventDate = LocalDate.from(event.getEvent_date());
        String dateString = eventDate.format(DateTimeFormatter.ISO_DATE);

        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
        url += event.getPlace() + "/" + dateString + "/" + dateString;
        url += "?unitGroup=metric&include=day&key=" + API_TOKEN + "&contentType=json";

        try {
            URL weatherUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) weatherUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                return processWeatherResponse(response.toString(), event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String processWeatherResponse(String response, Event event) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        try {
            jsonMap = objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Map<String, Object>> dayList = (ArrayList<Map<String, Object>>) jsonMap.get("days");
        String weatherIcon = (String) dayList.get(0).get("icon");
        event.setWeather_icon(WEATHER_ICON_MAP.get(weatherIcon));
        return "Max: " + dayList.get(0).get("tempmax") + "°C, Min: " + dayList.get(0).get("tempmin") + "°C";
    }
}

