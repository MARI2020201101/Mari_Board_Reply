package org.mariworld.boardreply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardReplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardReplyApplication.class, args);
	}

}
