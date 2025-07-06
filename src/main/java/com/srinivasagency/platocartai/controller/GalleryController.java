package com.srinivasagency.platocartai.controller;

import com.srinivasagency.platocartai.model.Gallery;
import com.srinivasagency.platocartai.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://srinivas-agency-frontend.vercel.app"})
@RequestMapping("/api/gallery")
public class GalleryController {

    @Autowired
    private GalleryRepository galleryRepository;

    @GetMapping
    public List<Gallery> getAllGalleryImages() {
        return galleryRepository.findAll();
    }
}
