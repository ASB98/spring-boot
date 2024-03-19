package com.tsi.bahra.arjun.vmo2Spring.services;

import com.tsi.bahra.arjun.vmo2Spring.objects.Actor;
import com.tsi.bahra.arjun.vmo2Spring.repos.ActorRepository;
import com.tsi.bahra.arjun.vmo2Spring.objects.Film;
import com.tsi.bahra.arjun.vmo2Spring.repos.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepo;

    @Autowired
    private ActorRepository actorRepo;

    public Iterable<Film> getAllFilms() {
        return filmRepo.findAll();
    }

    public Film getFilmByID(int filmID) {
        return filmRepo.findById(filmID)
                .orElseThrow(() -> new ResourceAccessException("Film not found"));
    }

    public Set<Actor> getActorsInFilm(int filmId) {
        Film film = filmRepo.findById(filmId)
                .orElseThrow(() -> new ResourceAccessException("Film not found"));
        return film.getActors();
    }

    public Film addFilm(Map<String, Object> filmData) {
        Film film = new Film();
        film.setTitle((String) filmData.get("title"));
        film.setDescription((String) filmData.get("description"));
        film.setReleaseYear((Integer) filmData.get("releaseYear"));
        film.setLanguageID((Integer) filmData.get("languageID"));
        film.setRentalDuration((Integer) filmData.get("rentalDuration"));

        Set<Actor> actors = new HashSet<>();
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

    public Film addActorToFilm(int filmId, int actorId) {
        Film film = filmRepo.findById(filmId)
                .orElseThrow(() -> new ResourceAccessException("Film not found"));
        Actor actor = actorRepo.findById(actorId)
                .orElseThrow(() -> new ResourceAccessException("Actor not found"));
        film.getActors().add(actor);
        return filmRepo.save(film);
    }

    //should probably have used a DTO here for type safety at compile time, will do if I have more time.
    public Film updateFilm(int filmID, Map<String, Object> filmData) {
        Film film = filmRepo.findById(filmID)
                .orElseThrow(() -> new ResourceAccessException("Film not found"));

        //extract values from filmData, checking for null
        String title = (String) filmData.getOrDefault("title", film.getTitle());
        String description = (String) filmData.getOrDefault("description", film.getDescription());
        Integer releaseYear = (Integer) filmData.getOrDefault("releaseYear", film.getReleaseYear());
        Integer languageID = (Integer) filmData.getOrDefault("languageID", film.getLanguageID());
        Integer rentalDuration = (Integer) filmData.getOrDefault("rentalDuration", film.getRentalDuration());

        film.setTitle(title);
        film.setDescription(description);
        film.setReleaseYear(releaseYear);
        film.setLanguageID(languageID);
        film.setRentalDuration(rentalDuration);

        // Handle actor IDs separately
        if (filmData.containsKey("actors") && filmData.get("actors") != null) {
            Set<Actor> actors = new HashSet<>();
            @SuppressWarnings("unchecked")
            List<Integer> actorIds = (List<Integer>) filmData.get("actors");
            for (Integer actorId : actorIds) {
                Actor actor = actorRepo.findById(actorId)
                        .orElseThrow(() -> new IllegalArgumentException("Actor not found with ID: " + actorId));
                actors.add(actor);
            }
            film.setActors(actors);
        }

        return filmRepo.save(film);
    }



    public void deleteFilm(int filmID) {
        Film film = filmRepo.findById(filmID)
                .orElseThrow(() -> new ResourceAccessException("Film not found"));
        filmRepo.delete(film);
    }
}
