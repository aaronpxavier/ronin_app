package com.roninswdstudio.ronin_app.dao;

import com.roninswdstudio.ronin_app.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ArtistDao extends JpaRepository<Artist, Long> {

    Page<Artist> findAll(Pageable pageable);

    @Query(
            value = "SELECT * FROM artist where search_index @@ plainto_tsquery(?1) order by ts_rank(search_index, plainto_tsquery(?1)) desc",
            countQuery = "SELECT count(*) FROM artist where search_index @@ plainto_tsquery(?1)",
            nativeQuery = true
    )
    Page<Artist> findAllBySearchTerm(String search, Pageable pageable);

    Artist save(Artist artist);

    Optional<Artist> findArtistByUsername(String username);

    Optional<Artist> findArtistById(long id);
}

