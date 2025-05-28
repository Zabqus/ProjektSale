package com.example.projektsale.observer;

import com.example.projektsale.entity.Reservation;

public interface ReservationObserver {
    void onReservationCreated(Reservation reservation);
}