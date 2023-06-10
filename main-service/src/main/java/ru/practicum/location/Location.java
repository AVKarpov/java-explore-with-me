package ru.practicum.location;

import lombok.*;

import javax.persistence.*;

/**
 * Широта и долгота места проведения события
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Широта (например, 55.754167)
     */
    private float lat;

    /**
     * Долгота (например, 37.62)
     */
    private float lon;

}
