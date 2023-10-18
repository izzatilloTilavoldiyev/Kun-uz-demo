package com.company.kunuzdemo.service.media;

import com.company.kunuzdemo.entity.Media;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.MediaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final Path fileLocation;

    @Autowired
    private MediaRepository mediaRepository;

    public MediaService() {
        String fileUploadDir = "src/main/resources/uploads";
        this.fileLocation = Paths.get(fileUploadDir)
                .toAbsolutePath().normalize();
    }

    public Media saveFile(MultipartFile file) {
        String fullFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            Path targetLocation = fileLocation.resolve(fullFileName);
            Files.copy(file.getInputStream(), targetLocation);
        } catch (FileAlreadyExistsException e) {
            String[] fileNameAndType = fullFileName.split("\\.");
            fullFileName = fileNameAndType[0] + System.currentTimeMillis() + "." + fileNameAndType[1];
            Path targetLocation = fileLocation.resolve(fullFileName);
            try {
                Files.copy(file.getInputStream(), targetLocation);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Media media = Media.builder()
                .originalName(file.getOriginalFilename())
                .fileDownloadUri(fileLocation + "\\" + file.getOriginalFilename())
                .build();

        return mediaRepository.save(media);
    }


    @Transactional
    @Modifying
    public void deleteById(Long mediaID) {
        Media media = getMediaById(mediaID);
        Path filePath = Paths.get(media.getFileDownloadUri());
        if (!Files.exists(filePath))
            throw new DataNotFoundException("Media file not found for ID: " + mediaID);
        try {
            Files.delete(filePath);
            mediaRepository.deleteById(mediaID);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete media file: " + e.getMessage());
        }
    }


    public Path downloadFile(Long fileId) {
        Media media = getMediaById(fileId);
        return fileLocation.resolve(media.getOriginalName());
    }

    public Media getMediaById(Long mediaID) {
        return mediaRepository.findById(mediaID).orElseThrow(
                () -> new DataNotFoundException("Media not found with ID: " + mediaID)
        );
    }
}
