package com.tsi.bahra.arjun.vmo2Spring;

import jakarta.persistence.*;

@Entity
@Table(name="actor")
public class Actor {

    @Id
    @Column(name="actor_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actorID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public int getActorID() {
        return actorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
