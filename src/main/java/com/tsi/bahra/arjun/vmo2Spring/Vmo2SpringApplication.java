package com.tsi.bahra.arjun.vmo2Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;


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

	@GetMapping("/actorsInFilm/{filmId}")
	public Set<Actor> getActorsInFilm(@PathVariable int filmId) {
		Film film = filmRepo.findById(filmId)
				.orElseThrow(() -> new ResourceAccessException("Film not found"));
		return film.getActors();
	}

	@PostMapping("/addActor")
	public Actor addActor(@RequestBody Actor actor) {
		return actorRepo.save(actor);
	}

	@PostMapping("/addFilm")
	public Film addFilm(@RequestBody Map<String, Object> filmData) {
		Film film = new Film();
		film.setTitle((String) filmData.get("title"));
		film.setDescription((String) filmData.get("description"));
		film.setReleaseYear((Integer) filmData.get("releaseYear"));
		film.setLanguageID((Integer) filmData.get("languageID"));
		film.setRentalDuration((Integer) filmData.get("rentalDuration"));

		Set<Actor> actors = new HashSet<>();
		// Adjusting to fetch actor IDs from "actors" key
		List<Integer> actorIds = (List<Integer>) filmData.get("actors");
		if (actorIds != null) {
			for (Integer actorId : actorIds) {
				Actor actor = actorRepo.findById(actorId)
						.orElseThrow(() -> new ResourceAccessException("Actor not found with ID: " + actorId));
				actors.add(actor);
			}
		}
		film.setActors(actors);

		return filmRepo.save(film);
	}


	@PostMapping("/addActorToFilm/{filmId}/{actorId}")
	public Film addActorToFilm(@PathVariable int filmId, @PathVariable int actorId) {
		Film film = filmRepo.findById(filmId)
				.orElseThrow(() -> new ResourceAccessException("Film not found"));
		Actor actor = actorRepo.findById(actorId)
				.orElseThrow(() -> new ResourceAccessException("Actor not found"));
		film.getActors().add(actor);
		return filmRepo.save(film);
	}

	@PutMapping("/updateActor/{id}")
	public Actor updateActor(@PathVariable("id") int actorID, @RequestBody Actor actorDetails) {
		Actor actor = actorRepo.findById(actorID)
				.orElseThrow(() -> new ResourceAccessException("Actor not found"));
		actor.setFirstName(actorDetails.getFirstName());
		actor.setLastName(actorDetails.getLastName());
		return actorRepo.save(actor);
	}

	@PutMapping("/updateFilm/{id}")
	public Film updateFilm(@PathVariable("id") int filmID, @RequestBody Film filmDetails) {
		Film film = filmRepo.findById(filmID)
				.orElseThrow(() -> new ResourceAccessException("Film not found"));
		film.setTitle(filmDetails.getTitle());
		film.setDescription(filmDetails.getDescription());
		return filmRepo.save(film);
	}

	@DeleteMapping("/deleteActor/{id}")
	public void deleteActor(@PathVariable("id") int actorID) {
		Actor actor = actorRepo.findById(actorID)
				.orElseThrow(() -> new ResourceAccessException("Actor not found"));
		actorRepo.delete(actor);
	}

	@DeleteMapping("/deleteFilm/{id}")
	public void deleteFilm(@PathVariable("id") int filmID) {
		Film film = filmRepo.findById(filmID)
				.orElseThrow(() -> new ResourceAccessException("Film not found"));
		filmRepo.delete(film);
	}



}
