package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {

    private String app; //Идентификатор сервиса для которого записывается информация

    private String uri; //URI для которого был осуществлен запрос

    private String ip; //IP-адрес пользователя, осуществившего запрос

    private String timestamp; //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}