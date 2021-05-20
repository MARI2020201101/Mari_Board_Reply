package org.mariworld.boardreply.repository;

import org.junit.jupiter.api.Test;
import org.mariworld.boardreply.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void insertTest(){

        IntStream.rangeClosed(1,100).forEach(i->
                memberRepository.save(
                        Member.builder().email("user"+i+"@aaa.com")
                        .name("USER"+i)
                        .password("1111").build())
        );

    }
}
