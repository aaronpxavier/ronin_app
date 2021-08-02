package com.roninswdstudio.ronin_app.service;

import com.roninswdstudio.ronin_app.dao.ArtistDao;
import com.roninswdstudio.ronin_app.dao.ImageDao;
import com.roninswdstudio.ronin_app.entity.Artist;
import com.roninswdstudio.ronin_app.entity.ExhibitImage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ImageService {

    private ImageDao imageDao;

    public ImageService(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public List<ExhibitImage> imageSearch(String search, int page, int size) {
        return imageDao.findAllImagesBySearchTerm(search, PageRequest.of(page, size)).getContent();
    }
}
