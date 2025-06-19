# EventSpring - Readme

This README document describes the code and functionality of the Wendy Event planer application. It provides information
about the project structure, technologies used, functionalities, and instructions for running the project.

## Project Structure

The EventSpring project consists of the following main components:

- **EventController**: A Spring Controller that manages the various endpoints of the application and handles data
  processing between the frontend and the backend database.
- **EventService**: A service that contains the business logic and manages communication with the database through the
  EventRepository.
- **EventRepository**: A Spring Data JPA repository that provides CRUD operations for the Event entity.
- **Event**: A simple POJO class that represents an Event.
- **DatabaseInitializer**: A component that loads some sample events into the database upon application startup.
- **index.html**: The main HTML page of the frontend, which lists the events and provides the user interface for
  creating new events.
- **styles.css**: A CSS file for styling the HTML pages.
- **scripts.js**: A JavaScript file that controls the interactions and AJAX requests on the user interface.

## Technologies Used

The EventSpring project uses the following technologies and frameworks:

- **Spring Boot**: A Java framework for web application development.
- **Spring MVC**: A part of the Spring framework that supports the implementation of the Model-View-Controller (MVC)
  pattern.
- **Spring Data JPA**: A part of the Spring framework that simplifies database communication and mapping.
- **Thymeleaf**: A server-side Java template engine framework for creating dynamic web pages.
- **H2 Database**: A lightweight, in-memory database for development and testing.
- **JavaScript**: A scripting language for implementing interactive features on the user interface.

## Functionalities

The EventSpring application allows for creating, viewing, and voting on events. The following functionalities are
implemented:

- **Display Events**: The homepage (`index.html`) displays a list of events, including information such as name,
  description, timestamp, and weather forecast (if available).
- **Event Creation**: The user can create new events through the popup menu (`#popupMenu`). Name, location, timestamp,
  description, and event type need to be provided.
- **Event Voting**: Each event has a voting function with upvote and downvote buttons. By clicking these buttons, the
  user can vote for an event, and the count of upvotes and downvotes will be updated.
- **Event Detail View**: Clicking the "Show All Events" button (`#show-modal`) displays a detailed view of all events in
  a popup window.

## Running the Project

To run the EventSpring project, follow these steps:

1. Make sure you have Java JDK and Maven installed on your system.
2. Open a command prompt or terminal window and navigate to the project folder.
3. Run the command `mvn spring-boot:run` to start the application.
4. Open your web browser and navigate to `http://localhost:8080` to use the EventSpring application.

Note: Make sure that port 8080 is not occupied by any other application on your system.

## Authors

The EventSpring application was developed by Lennart Hohls and Leon Föckersperger.
