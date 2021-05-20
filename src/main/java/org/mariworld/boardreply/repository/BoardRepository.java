package org.mariworld.boardreply.repository;

import org.mariworld.boardreply.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
