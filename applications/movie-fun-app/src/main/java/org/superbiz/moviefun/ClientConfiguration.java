package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.moviesapi.MoviesClient;

@Configuration
public class ClientConfiguration {

    @Value("//album-service/albums") String albumsUrl;
    @Value("//movie-service/movies") String moviesUrl;


    @Bean
    public AlbumsClient albumsClient(RestOperations restOperations) {
        return new AlbumsClient(albumsUrl, restOperations);
    }

    @Bean
    public MoviesClient moviesClient(RestOperations restOperations) {
        return new MoviesClient(moviesUrl, restOperations);
    }
}
