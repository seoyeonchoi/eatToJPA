package com.ktds.eattojpa.repository;

import com.ktds.eattojpa.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, String> {

}
