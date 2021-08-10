package com.roninswdstudio.ronin_app.service;

import com.roninswdstudio.ronin_app.dao.ImageDao;
import com.roninswdstudio.ronin_app.entity.Artist;
import com.roninswdstudio.ronin_app.entity.ExhibitImage;
import com.roninswdstudio.ronin_app.exceptions.ArtistNotFoundException;
import com.roninswdstudio.ronin_app.exceptions.ImageNotFoundException;
import com.roninswdstudio.ronin_app.springsecurity.entity.ERole;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import com.roninswdstudio.ronin_app.springsecurity.services.JwtUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private ImageDao imageDao;
    private ArtistService artistService;
    private JwtUtil jwtUtil;

    public ImageService(ImageDao imageDao, ArtistService artistService, JwtUtil jwtUtil) {
        this.imageDao = imageDao;
        this.artistService = artistService;
        this.jwtUtil = jwtUtil;
    }

    public List<ExhibitImage> imageSearch(String search, int page, int size) {
        return imageDao.findAllImagesBySearchTerm(search, PageRequest.of(page, size)).getContent();
    }

    public ExhibitImage saveImage(ExhibitImage exhibitImage, HttpServletRequest request) throws ArtistNotFoundException {
        User user = jwtUtil.extractUser(JwtUtil.getJWTFromCookie(request));
        if(!user.isAdmin()) {
            Artist artist = user.getArtist();
            exhibitImage.setArtist(artist);
        }
        return imageDao.save(exhibitImage);
    }

    public void deleteImage(long imageId, HttpServletRequest request) throws BadCredentialsException, ImageNotFoundException {
        User user = jwtUtil.extractUser(JwtUtil.getJWTFromCookie(request));
        Optional<ExhibitImage> image = imageDao.findById(imageId);
        if(image.isPresent() && (user.getArtist().getId() == image.get().getArtist().getId() || user.isAdmin())) {
            imageDao.delete(image.get());
        } else if(image.isEmpty()) {
            throw new ImageNotFoundException("Image your trying to exist does not exist");
        }
        else
            throw new BadCredentialsException("Can not delete image belonging to another user");
    }
}
