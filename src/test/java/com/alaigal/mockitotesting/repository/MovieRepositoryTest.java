package com.alaigal.mockitotesting.repository;

import com.alaigal.mockitotesting.model.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:mysql:5.7.34:///databasename"
})
public class MovieRepositoryTest {

    private final MovieRepository movieRepository;
    private final List<Movie> movies = new ArrayList<>();

    @Autowired
    public MovieRepositoryTest(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @BeforeEach
    void setUp() {
        movies.add(Movie.builder().title("Interstellar").rated("PG-13").runtime(169).director("Christopher Nolan").language("English").build());

        movieRepository.saveAll(movies);
    }

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
    }

    //Positive Scenario for FindByTitle
    @Test
    public void testFindByTitle_Found(){
        Assertions.assertThat(movieRepository.findByTitle("Interstellar").get(0).getTitle()).isEqualTo(movies.get(0).getTitle());
    }

    //Negative Scenario for FindByTitle
    @Test
    public void testFindByTitle_NotFound(){
        Assertions.assertThat(movieRepository.findByTitle("Oppenheimer")).isEmpty();
    }

    @Test
    public void testFindRunTimeGreaterThan_Success(){
        Assertions.assertThat(movieRepository.findByRuntimeGreaterThan(150).size()).isEqualTo(1);
    }

    @Test
    public void testFindByDirector_Found_Size(){
        Assertions.assertThat(movieRepository.findByDirector("Christopher Nolan").size()).isGreaterThanOrEqualTo(1);
    }
}
