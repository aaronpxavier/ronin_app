package com.roninswdstudio.ronin_app.controller;

import com.roninswdstudio.ronin_app.entity.Artist;
import com.roninswdstudio.ronin_app.service.ArtistService;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class UsersController {

    ArtistService artistService;

    UsersController(ArtistService artistService) {
        this.artistService = artistService;
    }
    @GetMapping("/user/artists")
    public ResponseEntity<List<Artist>> getArtists(@RequestParam int page, @RequestParam int size) {
        List<Artist> artists = artistService.getArtists(page, size);
        return ResponseEntity.ok(artists);
    }
    @GetMapping("/user/artists/search")
    public ResponseEntity<List<Artist>> getArtistsSearch(@RequestParam String search, @RequestParam int page, @RequestParam int size) {
        List<Artist> artists = artistService.getArtistsSearch(search, page, size);
        return ResponseEntity.ok(artists);
    }

}
