package com.srinivasagency.platocartai.repository;

import com.srinivasagency.platocartai.model.Gallery;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GalleryRepository extends MongoRepository<Gallery, String> {
}
