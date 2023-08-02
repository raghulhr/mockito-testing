package com.alaigal.mockitotesting.controller;

import com.alaigal.mockitotesting.model.Movie;
import com.alaigal.mockitotesting.service.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MovieControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private MovieService movieService;

    private Movie movie;
    private final List<Movie> movies = new ArrayList<>();

    @BeforeEach
    void setUp() {
        movie = Movie.builder().id(1L).title("Interstellar").rated("PG-13").director("Christopher Nolan").language("English").build();
        movies.add(movie);
        movies.add(Movie.builder().title("Oppenheimer").rated("PG-13").runtime(180).director("Christopher Nolan").language("English").build());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void postMovie() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(movie);

        when(movieService.postMovie(movie)).thenReturn(movie);
        this.mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getAllMovies() throws Exception{
        when(movieService.getAllMovies()).thenReturn(movies);
        this.mockMvc.perform(get("/movie"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getMovieById() throws Exception {
        when(movieService.getMovieById(1L)).thenReturn(movie);
        this.mockMvc.perform(get("/movie/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}