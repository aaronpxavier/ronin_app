package com.roninswdstudio.ronin_app.controller;

import com.roninswdstudio.ronin_app.entity.Artist;
import com.roninswdstudio.ronin_app.entity.ExhibitImage;
import com.roninswdstudio.ronin_app.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImageController {

    ImageService imageService;

    ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/pub/image/search")
    public ResponseEntity<List<ExhibitImage>> getImagesSearch(@RequestParam String search, @RequestParam int page, @RequestParam int size) {
        List<ExhibitImage> artists = imageService.imageSearch(search, page, size);
        if(artists.isEmpty())
            return new ResponseEntity<>(artists, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(artists);
    }
}
