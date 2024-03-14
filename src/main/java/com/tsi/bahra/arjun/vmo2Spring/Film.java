package com.tsi.bahra.arjun.vmo2Spring;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="film")
public class Film {

    @Id
    @Column(name="film_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int filmID;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "language_id")
    private int languageID;

    @Column(name = "rental_duration")
    private int rentalDuration;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    public int getFilmID() {
        return filmID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<Actor> getActors() {
        return actors;
    }


    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }
    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
