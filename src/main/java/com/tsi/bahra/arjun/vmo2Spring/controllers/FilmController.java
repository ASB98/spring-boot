package com.tsi.bahra.arjun.vmo2Spring.controllers;

import com.tsi.bahra.arjun.vmo2Spring.objects.Actor;
import com.tsi.bahra.arjun.vmo2Spring.objects.Film;
import com.tsi.bahra.arjun.vmo2Spring.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/home")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/allFilms")
    public Iterable<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/film/{id}")
    public Film getFilmByID(@PathVariable("id") int filmID) {
        return filmService.getFilmByID(filmID);
    }

    @GetMapping("/actorsInFilm/{filmId}")
    public Set<Actor> getActorsInFilm(@PathVariable int filmId) {
        return filmService.getActorsInFilm(filmId);
    }

    @PostMapping("/addFilm")
    public Film addFilm(@RequestBody Map<String, Object> filmData) {
        return filmService.addFilm(filmData);
    }

    @PostMapping("/addActorToFilm/{filmId}/{actorId}")
    public Film addActorToFilm(@PathVariable int filmId, @PathVariable int actorId) {
        return filmService.addActorToFilm(filmId, actorId);
    }

    @PutMapping("/updateFilm/{id}")
    public Film updateFilm(@PathVariable("id") int filmID, @RequestBody Map<String, Object> filmData) {
        return filmService.updateFilm(filmID, filmData);
    }

    @DeleteMapping("/deleteFilm/{id}")
    public void deleteFilm(@PathVariable("id") int filmID) {
        filmService.deleteFilm(filmID);
    }
}
