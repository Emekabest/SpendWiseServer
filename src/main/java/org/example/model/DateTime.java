package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class DateTime {

   private static LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Africa/Lagos"));
   private static LocalDate localDate = LocalDate.now(ZoneId.of("Africa/Lagos"));



    public static LocalDate getLocalDate() {

        return localDate;
    }

    public static LocalDateTime getLocalDateTime() {
        return localDateTime;
    }


}
