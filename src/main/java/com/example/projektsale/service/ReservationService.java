package com.example.projektsale.service;

import com.example.projektsale.entity.Reservation;
import com.example.projektsale.entity.Room;
import com.example.projektsale.entity.User;
import com.example.projektsale.enums.ReservationStatus;
import com.example.projektsale.repository.ReservationRepository;
import com.example.projektsale.repository.RoomRepository;
import com.example.projektsale.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Long userId, Long roomId, LocalDateTime startTime, LocalDateTime endTime, String purpose) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setPurpose(purpose);
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation savedReservation = reservationRepository.save(reservation);

        System.out.println("Powiadomienie: Nowa rezerwacja dla sali " +
                room.getName() + " przez użytkownika " + user.getUsername());

        return savedReservation;
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserIdOrderByStartTimeDesc(userId);
    }

    public List<Reservation> getReservationsByRoom(Long roomId) {
        return reservationRepository.findByRoomIdOrderByStartTime(roomId);
    }


    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        reservationRepository.delete(reservation);
        System.out.println("Reservation deleted: " + reservation.getId() + " for room " + reservation.getRoom().getName());
    }
}