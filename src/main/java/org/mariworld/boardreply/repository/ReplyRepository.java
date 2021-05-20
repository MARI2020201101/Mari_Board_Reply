package org.mariworld.boardreply.repository;

import org.mariworld.boardreply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
