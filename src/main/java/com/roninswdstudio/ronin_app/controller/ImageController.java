package com.roninswdstudio.ronin_app.controller;

import com.roninswdstudio.ronin_app.entity.ExhibitImage;
import com.roninswdstudio.ronin_app.entity.response.DeleteImageResponse;
import com.roninswdstudio.ronin_app.exceptions.ArtistNotFoundException;
import com.roninswdstudio.ronin_app.exceptions.ImageNotFoundException;
import com.roninswdstudio.ronin_app.service.ImageService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class ImageController {

    private ImageService imageService;

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

    @PostMapping("/user/image")
    public ResponseEntity<?> postImage(@RequestBody ExhibitImage exhibitImage, HttpServletRequest request) {
        try {
            exhibitImage.setUploadDate(new Date());
            return new ResponseEntity<>(imageService.saveImage(exhibitImage, request), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            System.err.println(e);
            return new ResponseEntity<>("Data integrity violation", HttpStatus.BAD_REQUEST);
        } catch (ArtistNotFoundException e) {
            System.err.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/user/image")
    public ResponseEntity<?> deleteImage(@RequestParam long imageId, HttpServletRequest request) {
        try {
            imageService.deleteImage(imageId, request);
            return new ResponseEntity<>(new DeleteImageResponse(imageId, true), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            System.err.println(e);
            return new ResponseEntity<>(new DeleteImageResponse(), HttpStatus.UNAUTHORIZED);
        } catch (ImageNotFoundException e) {
            System.err.println(e);
            return new ResponseEntity<>(new DeleteImageResponse(), HttpStatus.BAD_REQUEST);
        }
    }
}
