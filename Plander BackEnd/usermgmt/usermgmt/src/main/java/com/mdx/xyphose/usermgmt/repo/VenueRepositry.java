package com.mdx.xyphose.usermgmt.repo;

import com.mdx.xyphose.usermgmt.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueRepositry extends JpaRepository<Venue, Long> {
    @Query("SELECT v FROM Venue v WHERE v.game.id = :gameId")
    List<Venue> findByGameId(@Param("gameId") Long gameId);
}