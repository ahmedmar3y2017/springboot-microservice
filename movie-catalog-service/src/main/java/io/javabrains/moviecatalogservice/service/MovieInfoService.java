package io.javabrains.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MovieInfoService {


    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getMovieInfoFallBack")
    public CatalogItem getMovieInfo(Rating rating) {

        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());

    }
    // fall back getMovieRatings
    public CatalogItem getMovieInfoFallBack(Rating rating) {

        return new CatalogItem("Movie Not Found", "",0);

    }

}
