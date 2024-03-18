package com.tsi.bahra.arjun.vmo2Spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    @Test
    public void findById() {

        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actorRepository.save(actor);

        Actor found = actorRepository.findById(actor.getActorID()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo(actor.getFirstName());
    }
}
