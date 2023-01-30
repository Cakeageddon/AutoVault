package com.example.AutoVault.controllers;

import com.example.AutoVault.Response.FileUploadResponse;
import com.example.AutoVault.models.DocFile;
import com.example.AutoVault.service.DocFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@CrossOrigin
@RestController
public class UploadDownloadController {

    private final DocFileService docFileService;

    public UploadDownloadController(DocFileService docFileService) {
        this.docFileService = docFileService;
    }

    @PostMapping("/cars/upload")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
    DocFile docFile = docFileService.uploadDocFile(file);
    String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
    String contentType = file.getContentType();
    return new FileUploadResponse(docFile.getFileName(), url, contentType);
    }

    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downloadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
        return docFileService.singleDocFileDownload(fileName, request);
    }

    @PutMapping("/cars/{carId}/docfile/{fileName}")
    public ResponseEntity<Object> assignDocFileToCar(@PathVariable Long carId, @PathVariable String fileName) {
        docFileService.assignDocFileToCar(carId, fileName);
        return ResponseEntity.ok().body("Added filename with name: " + fileName + " to car with id: " + carId);
    }
}
