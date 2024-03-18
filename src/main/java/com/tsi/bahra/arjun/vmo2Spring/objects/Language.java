package com.tsi.bahra.arjun.vmo2Spring.objects;

import jakarta.persistence.*;

@Entity
@Table(name="language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int languageId;

    @Column(name = "name")
    private String name;

    public int getLanguageId() {
        return languageId;
    }

    public String getName() {
        return name;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
