package com.tsi.bahra.arjun.vmo2Spring.controllers;

import com.tsi.bahra.arjun.vmo2Spring.objects.Actor;
import com.tsi.bahra.arjun.vmo2Spring.services.ActorService;
import com.tsi.bahra.arjun.vmo2Spring.objects.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/home")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/allActors")
    public Iterable<Actor> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/actor/{id}")
    public Actor getActorByID(@PathVariable("id") int actorID) {
        return actorService.getActorByID(actorID);
    }

    @GetMapping("/filmsByActor/{actorId}")
    public Set<Film> getFilmsByActorId(@PathVariable("actorId") int actorId) {
        return actorService.getFilmsByActorId(actorId);
    }

    @PostMapping("/addActor")
    public Actor addActor(@RequestBody Actor actor) {
        return actorService.addActor(actor);
    }

    @PutMapping("/updateActor/{id}")
    public Actor updateActor(@PathVariable("id") int actorID, @RequestBody Actor actorDetails) {
        return actorService.updateActor(actorID, actorDetails);
    }

    @DeleteMapping("/deleteActor/{id}")
    public void deleteActor(@PathVariable("id") int actorID) {
        actorService.deleteActor(actorID);
    }
}
