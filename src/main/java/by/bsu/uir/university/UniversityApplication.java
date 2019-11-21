package by.bsu.uir.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import by.arhor.utility.structure.NetherWorld;
import by.arhor.utility.structure.Soul;
import by.bsu.uir.university.domain.model.User;

@SpringBootApplication
public class UniversityApplication {

	public static void main(String[] args) throws IllegalAccessException {

		final var world = new NetherWorld();

		final var user = new User();

		final Soul<User> userSoul = world.soulLookup(user);

    System.out.println(userSoul);

		SpringApplication.run(UniversityApplication.class, args);
	}

}
