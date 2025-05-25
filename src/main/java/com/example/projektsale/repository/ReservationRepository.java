package com.example.projektsale.repository;

import com.example.projektsale.entity.Reservation;
import com.example.projektsale.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByRoomId(Long roomId);

    List<Reservation> findByStatus(ReservationStatus status);

    List<Reservation> findByUserIdOrderByStartTimeDesc(Long userId);

    List<Reservation> findByRoomIdOrderByStartTime(Long roomId);
}