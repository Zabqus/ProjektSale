package com.example.projektsale.observer;

import com.example.projektsale.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class NotificationObserver implements ReservationObserver {

    @Override
    public void onReservationCreated(Reservation reservation) {
        System.out.println("Powiadomienie: Nowa rezerwacja dla sali " +
                reservation.getRoom().getName() +
                " przez użytkownika " +
                reservation.getUser().getUsername());
    }
}