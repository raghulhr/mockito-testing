package com.alaigal.mockitotesting.controller;

import com.alaigal.mockitotesting.model.Movie;
import com.alaigal.mockitotesting.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<?> postMovie(@RequestBody Movie movie){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.postMovie(movie));
    }

    @GetMapping
    public ResponseEntity<?> getAllMovies(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieById(id));
    }
}
