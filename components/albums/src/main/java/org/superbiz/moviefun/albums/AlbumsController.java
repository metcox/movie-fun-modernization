package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumsController {


    private AlbumsRepository albumsRepository;

    public AlbumsController(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }

    @PostMapping
    public void addMovie(@RequestBody Album album) {
        albumsRepository.addAlbum(album);
    }


    @GetMapping
    public List<Album> find() {
        return albumsRepository.getAlbums();
    }

}
