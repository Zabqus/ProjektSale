package com.example.projektsale.observer;

import com.example.projektsale.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class NotificationObserver implements ReservationObserver {

    @Override
    public void onReservationCreated(Reservation reservation) {
        if (reservation == null) {
            System.out.println("Powiadomienie: Otrzymano null reservation");
            return;
        }

        String roomName = (reservation.getRoom() != null)
                ? reservation.getRoom().getName()
                : "Nieznana sala";

        String userName = (reservation.getUser() != null)
                ? reservation.getUser().getUsername()
                : "Nieznany użytkownik";

        System.out.println("Powiadomienie: Nowa rezerwacja dla sali " + roomName +
                " przez użytkownika " + userName);
    }
}