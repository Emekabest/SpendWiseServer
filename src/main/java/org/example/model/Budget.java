package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;

    private String type;
    private int limitAmount;

    private LocalDateTime alterDate;


    private int accessAmount;
    private LocalDate accessAmountLastRestoredDate;
    private LocalDate accessAmountNextRestoredDate;


    public void setAccessAmountNextRestoredDate(int restorationDays){

        if (restorationDays == 1 || restorationDays == 7 || restorationDays == 30){

            this.accessAmountNextRestoredDate = this.accessAmountLastRestoredDate.plusDays(restorationDays);

        }
    }



    public void setAccessAmountRestoration(int restorationDays){

      //AccessAmountNextRestoredDate
      LocalDate NRD = getAccessAmountNextRestoredDate();

      //CurrentDate
      LocalDate CD = DateTime.getLocalDate();




      while (CD.isEqual(NRD) || CD.isAfter(NRD)){
          setAccessAmount(getLimitAmount());

          setAccessAmountLastRestoredDate(NRD);

          setAccessAmountNextRestoredDate(restorationDays);

          NRD = getAccessAmountNextRestoredDate();
      }
    }

}
