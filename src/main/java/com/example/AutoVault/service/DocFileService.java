package com.example.AutoVault.service;

import com.example.AutoVault.models.DocFile;
import com.example.AutoVault.repositories.DocFileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Service
public class DocFileService {

    private final DocFileRepository docFileRepository;

    public DocFileService(DocFileRepository docFileRepository) {
        this.docFileRepository = docFileRepository;
    }

    public DocFile uploadDocFile(MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        DocFile docFile = new DocFile();
        docFile.setFileName(name);
        docFile.setDocFile(file.getBytes());

        docFileRepository.save(docFile);
        return docFile;
    }

    public ResponseEntity<byte[]> singleDocFileDownload(String fileName, HttpServletRequest request){
        DocFile docFile = docFileRepository.findByFileName(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + docFile.getFileName()).body(docFile.getDocFile());
    }
}
