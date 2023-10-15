    package com.company.kunuzdemo.controller;

    import com.company.kunuzdemo.entity.Media;
    import com.company.kunuzdemo.service.media.MediaService;
    import io.swagger.v3.oas.annotations.Operation;
    import lombok.RequiredArgsConstructor;
    import org.springframework.core.io.Resource;
    import org.springframework.core.io.UrlResource;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;

    @RestController
    @RequestMapping("/api/v1/attachment")
    @RequiredArgsConstructor
    public class MediaController {

        private final MediaService mediaService;

        /**
         *
         * @param file -> Multipart request file which contains the input send from user
         * @return UploadFileResponse which includes download url and file name
         */
        @Operation(
                description = "POST endpoint to upload any files. Max file size must be 10MB",
                summary = "API to store file"
        )
        @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public Media uploadFile(
                @RequestParam("file") MultipartFile file
        ) {
            return mediaService.saveFile(file);
        }

        @Operation(
                description = "GET endpoint to download file. You need to give file id",
                summary = "API to download file"
        )
        @GetMapping("/download/{fileID}")
        public ResponseEntity<Resource> downloadFile(
                @PathVariable Long fileID
        ) {
            Path file = mediaService.downloadFile(fileID);
            try {
                String mediaType = Files.probeContentType(file);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mediaType))
                        .body(new UrlResource(file.toUri()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @Operation(
                description = "DELETE endpoint to delete media by ID",
                summary = "delete by ID"
        )
        @DeleteMapping("/{mediaID}")
        public ResponseEntity<String> deleteById(
                @PathVariable Long mediaID
        ) {
            mediaService.deleteById(mediaID);
            return ResponseEntity.ok("Successfully deleted");
        }

    }
