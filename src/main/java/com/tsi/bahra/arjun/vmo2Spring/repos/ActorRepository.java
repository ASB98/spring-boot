package com.tsi.bahra.arjun.vmo2Spring.repos;

import com.tsi.bahra.arjun.vmo2Spring.objects.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
