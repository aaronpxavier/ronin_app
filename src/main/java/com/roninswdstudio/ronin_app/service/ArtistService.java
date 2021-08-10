package com.roninswdstudio.ronin_app.service;

import com.roninswdstudio.ronin_app.dao.ArtistDao;
import com.roninswdstudio.ronin_app.entity.Artist;
import com.roninswdstudio.ronin_app.springsecurity.dao.UserDao;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private ArtistDao artistDao;
    private UserDao userDao;

    public ArtistService(ArtistDao artistDao, UserDao userDao) {
        this.artistDao = artistDao;
        this.userDao = userDao;
    }

    public List<Artist> getArtists(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artistDao.findAll(pageable).getContent();
    }

    public List<Artist> getArtistsSearch(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artistDao.findAllBySearchTerm(search, pageable).getContent();
    }

    public Optional<Artist> getArtistByUsername (String username) {
        return artistDao.findArtistByUsername(username);
    }

    public Optional<Artist> getArtistById(long id) {
        return artistDao.findArtistById(id);
    }

    public User addArtist(User user) {
        return userDao.save(user);
    }

}
