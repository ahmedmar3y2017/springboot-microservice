package io.javabrains.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class MovieRatingService {


    @Autowired
    private RestTemplate restTemplate;



    @HystrixCommand(fallbackMethod = "getMovieRatingsFallBack")
    public UserRating getMovieRatings(@PathVariable("userId") String userId) {

        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
    }

    // fall back getMovieRatings
    public UserRating getMovieRatingsFallBack(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(new Rating("0" , 0 )));
        return userRating;
    }

}
