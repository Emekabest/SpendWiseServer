package org.example.listener;

import org.example.event.AddFundsEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddFundsListener {



    @EventListener
    public void updateTotalBalance(AddFundsEvent addFundsEvent){




    }

}
