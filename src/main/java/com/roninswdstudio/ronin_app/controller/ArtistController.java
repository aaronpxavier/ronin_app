package com.roninswdstudio.ronin_app.controller;

import com.roninswdstudio.ronin_app.entity.Artist;
import com.roninswdstudio.ronin_app.service.ArtistService;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ArtistController {

    ArtistService artistService;

    ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }
    @GetMapping("/pub/artists")
    public ResponseEntity<List<Artist>> getArtists(@RequestParam int page, @RequestParam int size) {
        List<Artist> artists = artistService.getArtists(page, size);
        return ResponseEntity.ok(artists);
    }
    @GetMapping("/pub/artists/search")
    public ResponseEntity<List<Artist>> getArtistsSearch(@RequestParam String search, @RequestParam int page, @RequestParam int size) {
        List<Artist> artists = artistService.getArtistsSearch(search, page, size);
        if(artists.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(artists);
    }

    @PostMapping("/admin/artists/add")
    public ResponseEntity<User> addArtist(@RequestBody User user) {
        return new ResponseEntity<>(artistService.addArtist(user), HttpStatus.CREATED);
    }




}
