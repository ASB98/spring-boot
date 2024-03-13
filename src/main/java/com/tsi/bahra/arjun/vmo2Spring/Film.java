package com.tsi.bahra.arjun.vmo2Spring;

import jakarta.persistence.*;

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

    public int getFilmID() {
        return filmID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
