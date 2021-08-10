package com.roninswdstudio.ronin_app.dao;

import com.roninswdstudio.ronin_app.entity.ExhibitImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDao extends JpaRepository<ExhibitImage, Long> {
    @Query(
            value = "SELECT * FROM artist a INNER JOIN exhibit_images ei on a.id = ei.artist_id " +
                    "where a.search_index @@ plainto_tsquery(?1)  or ei.search_index @@ plainto_tsquery(?1) " +
                    "order by upload_date, ts_rank(ei.search_index, plainto_tsquery('tomb raider')), ts_rank(a.search_index, plainto_tsquery('tomb raider')) desc",
            countQuery = "SELECT count(*) FROM artist a INNER JOIN exhibit_images ei on a.id = ei.artist_id " +
                    "where a.search_index @@ plainto_tsquery(?1) or ei.search_index @@ plainto_tsquery(?1)",
            nativeQuery = true
    )
    Page<ExhibitImage> findAllImagesBySearchTerm(String search, Pageable pageable);

    ExhibitImage save(ExhibitImage exhibitImage);
}
