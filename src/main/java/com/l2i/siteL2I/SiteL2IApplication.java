package com.l2i.siteL2I;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;
import com.l2i.siteL2I.dto.chat.ForumRequest;
import com.l2i.siteL2I.service.chat.ForumService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class SiteL2IApplication implements CommandLineRunner {

	private final ForumService forumService;
	public static void main(String[] args) {
		SpringApplication.run(SiteL2IApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		for (int i = 0; i < 10; i++) {
			Faker faker = new Faker();
			forumService.create(new ForumRequest(faker.team().name()));
			// orderService.create(new Order(null, faker.name().fullName(), faker.commerce().productName(), faker.number().numberBetween(1, 200)));
		}
	}

}
