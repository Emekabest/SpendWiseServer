package org.example.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class Home {

    private User user;

    private List<Notification> notifications;

}
