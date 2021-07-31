package com.roninswdstudio.ronin_app.service;

import com.roninswdstudio.ronin_app.dao.ArtistDao;
import com.roninswdstudio.ronin_app.entity.Artist;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ArtistService {
    private ArtistDao artistDao;

    public ArtistService(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    public List<Artist> getArtists(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artistDao.findAll(pageable).getContent();
    }

    public List<Artist> getArtistsSearch(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artistDao.findAllBySearchTerm(search, pageable).getContent();
    }
}
