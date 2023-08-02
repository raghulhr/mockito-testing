package com.alaigal.mockitotesting.service;

import com.alaigal.mockitotesting.model.Movie;
import com.alaigal.mockitotesting.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

class MovieServiceTest {

    @Mock private MovieRepository movieRepository;
    @InjectMocks private MovieService movieService;
    private AutoCloseable autoCloseable;
    private Movie movie;
    private final List<Movie> movies = new ArrayList<>();

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        movie = Movie.builder().title("Oppenheimer").rated("PG-13").runtime(180).director("Christopher Nolan").language("English").build();

        movies.add(Movie.builder().title("Interstellar").rated("PG-13").runtime(169).director("Christopher Nolan").language("English").build());

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testPostMovie() {
        mock(Movie.class);
        mock(MovieRepository.class);

        when(movieRepository.save(movie)).thenReturn(movie);

        Assertions.assertThat(movieService.postMovie(movie)).isEqualTo(movie);
    }

    @Test
    void testGetAllMovies() {
        mock(Movie.class);
        mock(MovieRepository.class);

        when(movieRepository.findAll()).thenReturn(movies);

        Assertions.assertThat(movieService.getAllMovies()).isEqualTo(movies);
    }

    @Test
    void testGetMovieById_Found() {
        mock(Movie.class);
        mock(MovieRepository.class);

        when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(movie));
        Assertions.assertThat(movieService.getMovieById(1L)).isEqualTo(movie);
    }

    @Test
    void testGetMovieById_NotFound(){
        mock(Movie.class);
        mock(MovieRepository.class);

        //Only used for void methods
        //doThrow(new ResponseStatusException(NOT_FOUND,"Movie Not Found")).when(movieRepository.findById(4L));

        when(movieRepository.findById(4L)).thenThrow(new ResponseStatusException(NOT_FOUND));
        org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, ()-> movieRepository.findById(4L));
    }
}