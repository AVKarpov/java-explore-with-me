package ru.practicum.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "statistics")
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор сервиса для которого записывается информация
     */
    @Column(nullable = false)
    private String app;

    /**
     * URI для которого был осуществлен запрос
     */
    @Column(nullable = false)
    private String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос
     */
    @Column(nullable = false, length = 40)
    private String ip;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hit hit = (Hit) o;
        return id != null && Objects.equals(id, hit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}