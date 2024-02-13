package com.ktds.eattojpa.repository;

import com.ktds.eattojpa.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
}
