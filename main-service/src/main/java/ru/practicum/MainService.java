package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class MainService {

    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
//        StatClient statClient = new StatClient();
//        HitDto hitDto = HitDto.builder()
//                .app("ewm-service")
//                .uri("/events/1")
//                .ip("192.168.0.1")
//                .timestamp("2023-05-05 01:10:30")
//                .build();
//        statClient.addHit(hitDto);

//        ResponseEntity<Object> stat = statClient.getStats("2023-04-05 01:10:30",
//                "2023-06-05 01:10:30", null, false);

//        String[] uris = {"/events/1", "/events/2"};
//        ResponseEntity<Object> stat = statClient.getStats("2023-04-05 01:10:30",
//                "2023-06-05 01:10:30", uris, false);

    }
}
