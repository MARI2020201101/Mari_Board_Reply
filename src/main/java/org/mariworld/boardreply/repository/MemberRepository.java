package org.mariworld.boardreply.repository;

import org.mariworld.boardreply.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
}
