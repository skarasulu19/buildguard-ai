package com.buildguard.controller;

import com.buildguard.model.InspectionItem;
import com.buildguard.model.InspectionPhoto;
import com.buildguard.repository.InspectionItemRepository;
import com.buildguard.repository.InspectionPhotoRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InspectionPhotoController {

    private final InspectionItemRepository inspectionItemRepository;
    private final InspectionPhotoRepository inspectionPhotoRepository;

    private final Path uploadRoot = Paths.get("uploads/inspection-photos");

    public InspectionPhotoController(
            InspectionItemRepository inspectionItemRepository,
            InspectionPhotoRepository inspectionPhotoRepository
    ) {
        this.inspectionItemRepository = inspectionItemRepository;
        this.inspectionPhotoRepository = inspectionPhotoRepository;
    }

    @PostMapping(
            value = "/inspection-items/{itemId}/photos",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<List<InspectionPhoto>> uploadPhotos(
            @PathVariable Long itemId,
            @RequestParam("files") MultipartFile[] files
    ) {
        InspectionItem item = inspectionItemRepository.findById(itemId)
                .orElse(null);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        if (files.length > 4) {
            return ResponseEntity.badRequest().build();
        }

        List<InspectionPhoto> savedPhotos = new ArrayList<>();

        try {
            Files.createDirectories(uploadRoot);

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                if (file.getContentType() == null ||
                        !file.getContentType().startsWith("image/")) {
                    continue;
                }

                String originalName = file.getOriginalFilename() != null
                        ? file.getOriginalFilename()
                        : "photo";

                String safeName = UUID.randomUUID() + "-" + originalName.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

                Path destination = uploadRoot.resolve(safeName);

                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

                InspectionPhoto photo = new InspectionPhoto();
                photo.setFileName(originalName);
                photo.setFileType(file.getContentType());
                photo.setFilePath(destination.toString());
                photo.setInspectionItem(item);

                savedPhotos.add(inspectionPhotoRepository.save(photo));
            }

            return ResponseEntity.ok(savedPhotos);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/inspection-items/{itemId}/photos")
    public ResponseEntity<List<InspectionPhoto>> getPhotos(
            @PathVariable Long itemId
    ) {
        if (!inspectionItemRepository.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                inspectionPhotoRepository.findByInspectionItemId(itemId)
        );
    }

    @GetMapping("/photos/{photoId}/file")
    public ResponseEntity<Resource> getPhotoFile(
            @PathVariable Long photoId
    ) throws MalformedURLException {
        InspectionPhoto photo = inspectionPhotoRepository.findById(photoId)
                .orElse(null);

        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(photo.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (photo.getFileType() != null) {
            mediaType = MediaType.parseMediaType(photo.getFileType());
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    @DeleteMapping("/photos/{photoId}")
    public ResponseEntity<Void> deletePhoto(
            @PathVariable Long photoId
    ) {
        InspectionPhoto photo = inspectionPhotoRepository.findById(photoId)
                .orElse(null);

        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            if (photo.getFilePath() != null) {
                Files.deleteIfExists(Paths.get(photo.getFilePath()));
            }

            inspectionPhotoRepository.delete(photo);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}