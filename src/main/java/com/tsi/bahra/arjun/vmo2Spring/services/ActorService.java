package com.tsi.bahra.arjun.vmo2Spring.services;

import com.tsi.bahra.arjun.vmo2Spring.objects.Actor;
import com.tsi.bahra.arjun.vmo2Spring.repos.ActorRepository;
import com.tsi.bahra.arjun.vmo2Spring.repos.FilmRepository;
import com.tsi.bahra.arjun.vmo2Spring.objects.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Set;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepo;

    @Autowired
    private FilmRepository filmRepo;

    public Iterable<Actor> getAllActors() {
        return actorRepo.findAll();
    }

    public Actor getActorByID(int actorID) {
        return actorRepo.findById(actorID)
                .orElseThrow(() -> new ResourceAccessException("Actor not found"));
    }

    public Set<Film> getFilmsByActorId(int actorId) {
        Actor actor = actorRepo.findById(actorId)
                .orElseThrow(() -> new ResourceAccessException("Actor not found with ID: " + actorId));
        return actor.getFilms();
    }

    public Actor addActor(Actor actor) {
        return actorRepo.save(actor);
    }

    public Actor updateActor(int actorID, Actor actorDetails) {
        Actor actor = actorRepo.findById(actorID)
                .orElseThrow(() -> new ResourceAccessException("Actor not found"));
        actor.setFirstName(actorDetails.getFirstName());
        actor.setLastName(actorDetails.getLastName());
        return actorRepo.save(actor);
    }

    public void deleteActor(int actorID) {
        Actor actor = actorRepo.findById(actorID)
                .orElseThrow(() -> new ResourceAccessException("Actor not found"));

        // Iterate over each film associated with the actor and remove the actor
        actor.getFilms().forEach(film -> {
            film.getActors().remove(actor);
            filmRepo.save(film); // Save the film with the actor removed
        });

        // Now that the actor is no longer associated with any films, delete the actor
        actorRepo.delete(actor);
    }
}
