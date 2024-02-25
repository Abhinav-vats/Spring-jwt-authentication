package com.jwt.demo.Springjwtauthentication.api.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageUrlsRepository extends JpaRepository<ImagesUrls, Long> {
    List<ImagesUrls> findAllByColId(Long id);
}
