package com.tsi.bahra.arjun.vmo2Spring.repos;

import com.tsi.bahra.arjun.vmo2Spring.objects.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Integer> {
}
