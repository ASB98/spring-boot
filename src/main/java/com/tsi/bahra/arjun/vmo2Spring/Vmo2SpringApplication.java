package com.tsi.bahra.arjun.vmo2Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

@SpringBootApplication
@RestController
@RequestMapping("/home")
@CrossOrigin
public class Vmo2SpringApplication {

	@Autowired
	private ActorRepository actorRepo;

	@Autowired
	private FilmRepository filmRepo;

	public Vmo2SpringApplication(ActorRepository actorRepo, FilmRepository filmRepo) {
		this.actorRepo = actorRepo;
		this.filmRepo = filmRepo;
	}


	public static void main(String[] args) {
		SpringApplication.run(Vmo2SpringApplication.class, args);
	}

	@GetMapping("/allActors")
	public Iterable<Actor> getAllActors() {
		return actorRepo.findAll();
	}

	@GetMapping("/allFilms")
	public Iterable<Film> getAllFilms() {
		return filmRepo.findAll();
	}

	@GetMapping("actor/{id}")
	public Actor getActorByID(@PathVariable("id") int actorID){
		return actorRepo.findById(actorID).
				orElseThrow(() -> new ResourceAccessException("Actor not found"));
	}
}
