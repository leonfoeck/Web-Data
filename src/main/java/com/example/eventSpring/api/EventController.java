package com.example.eventSpring.api;

import com.example.eventSpring.model.Event;
import com.example.eventSpring.service.EventService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String index(@NotNull Model model) {
        List<Event> events = eventService.getAllEvents();
        List<Event> futureEvents = events.stream()
                .filter(event -> event.getEvent_date().isAfter(LocalDateTime.now())).toList();
        model.addAttribute("allEvents", events);
        model.addAttribute("events", futureEvents.subList(0, Math.min(futureEvents.size(), 20)));
        return "index";
    }

    @PostMapping("/updateVotes")
    public String upvoteEvent(@Validated @RequestParam Long eventId, @RequestParam int upvote, @RequestParam int downvote) {
        Event event = eventService.getEventById(eventId);
        if (event != null) {
            event.setUpvote(upvote);
            event.setDownvote(downvote);
            eventService.saveEvent(event);
        }
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@RequestParam("name") String name, @RequestParam String place, @RequestParam("eventDate") String eventDate, @RequestParam("description") String description, @NotNull @RequestParam("event_type") String eventType) {
        if (eventService.doesEventNameExist(name)) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("Event name already exists");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            Event newEvent = new Event(name, place, LocalDateTime.parse(eventDate, formatter), description, eventType);
            eventService.saveEvent(newEvent);
        }
        return "redirect:/";
    }

    @PostMapping
    public Event saveEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/allEvents")
    @ResponseBody
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/events")
    @ResponseBody
    public List<Event> getEvents(@RequestParam(defaultValue = "10") int n) {
        int startIndex = Math.max(eventService.getAllEvents().size() - n, 0);
        return eventService.getAllEvents().subList(startIndex, eventService.getAllEvents().size());
    }

}