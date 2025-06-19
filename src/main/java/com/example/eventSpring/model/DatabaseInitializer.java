package com.example.eventSpring.model;

import com.example.eventSpring.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${initialization.enabled}")
    private boolean initializationEnabled;
    private final EventRepository eventRepository;

    @Autowired
    public DatabaseInitializer(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) {
        if (initializationEnabled) {
            LocalDate date = LocalDate.of(2023, 7, 23);
            LocalTime time = LocalTime.of(19, 0);
            Event event1 = new Event("Roy Bianco", "Passau", LocalDateTime.of(date, time), "Schlagerstrudel", "Konzert");
            eventRepository.save(event1);

            LocalDate date2 = LocalDate.of(2023, 7, 16);
            LocalTime time2 = LocalTime.of(19, 0);
            Event event2 = new Event("Trägertal", "Passau", LocalDateTime.of(date2, time2), "The Beat goes on", "Festival");
            eventRepository.save(event2);

            Event event3 = new Event("SLAM", "Passau", LocalDateTime.of(date, time), "skate", "Skate Contest");
            eventRepository.save(event3);
        }
    }
}

