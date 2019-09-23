package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestOperations;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.List;


@Repository
public class MoviesClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private RestOperations restTemplate;
    private String moviesUrl;

    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {};

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restTemplate = restOperations;
    }

    public MovieInfo find(Long id) {
        return restTemplate.getForObject(moviesUrl+"/{id}", MovieInfo.class, id);
    }



    public void addMovie(MovieInfo movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.getTitle(), movie.getYear());

        restTemplate.postForObject(moviesUrl, movie,String.class);
    }

    public void deleteMovie(MovieInfo movie) {
        deleteMovieId(movie.getId());
    }




    public void updateMovie(MovieInfo movie) {
        restTemplate.put(moviesUrl, movie);
    }



    public void deleteMovieId(long id) {
        restTemplate.delete(moviesUrl+"/"+id);
    }

    public List<MovieInfo> getMovies() {
        ResponseEntity<List<MovieInfo>> res = restTemplate.exchange(moviesUrl, HttpMethod.GET, null, movieListType);
        return res.getBody();
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(moviesUrl)
                // Add query parameter
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);
        ResponseEntity<List<MovieInfo>> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, movieListType);
        return res.getBody();
    }


    public List<MovieInfo> findRange(String field, String key, int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, movieListType).getBody();
    }



    public int countAll() {
        return restTemplate.getForObject(moviesUrl+"/count", Integer.class);
    }

    public int count(String field, String key) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", key);

        return restTemplate.getForObject(builder.toUriString(), Integer.class);
    }



    public void clean() {
        restTemplate.delete(moviesUrl);
    }
}
