package com.alaigal.mockitotesting.repository;

import com.alaigal.mockitotesting.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitle(String title);
    List<Movie> findByRated(String rated);
    List<Movie> findByRuntimeGreaterThan(int runtime);
    List<Movie> findByRuntimeLessThan(int runtime);
    List<Movie> findByLanguage(String language);
    List<Movie> findByDirector(String director);
}
