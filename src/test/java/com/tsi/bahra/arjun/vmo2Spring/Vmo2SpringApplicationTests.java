package com.tsi.bahra.arjun.vmo2Spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class Vmo2SpringApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ActorRepository actorRepository;

	@MockBean
	private FilmRepository filmRepository;

	@Test
	public void testActor() throws Exception {
		Actor actor = new Actor();
		actor.setFirstName("John");
		actor.setLastName("Doe");

		given(actorRepository.findAll()).willReturn(Collections.singletonList(actor));

		mockMvc.perform(get("/home/allActors")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].firstName").value(actor.getFirstName()));
	}

	@Test
	public void testFilm() throws Exception {
		Film film = new Film();
		film.setTitle("Test Title");
		film.setDescription("Test Description");

		given(filmRepository.findById(1)).willReturn(Optional.of(film));

		mockMvc.perform(get("/home/film/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(film.getTitle()));
	}
}
