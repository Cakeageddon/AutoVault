package com.example.AutoVault.service;

import com.example.AutoVault.exceptions.RecordNotFoundException;
import com.example.AutoVault.models.Car;
import com.example.AutoVault.models.DocFile;
import com.example.AutoVault.repositories.CarRepository;
import com.example.AutoVault.repositories.DocFileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocFileService {

    CarRepository carRepository;
    private final DocFileRepository docFileRepository;

    public DocFileService(DocFileRepository docFileRepository, CarRepository carRepository) {
        this.carRepository = carRepository;
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

    public void assignDocFileToCar(Long carId, String fileName) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<DocFile> optionalDocFile = Optional.ofNullable(docFileRepository.findByFileName(fileName));

        if(optionalCar.isEmpty() || optionalDocFile.isEmpty()) {
            throw new RecordNotFoundException("No car/image combination found. Try different data.");
        } else {
            Car car = optionalCar.get();
            DocFile docFile = optionalDocFile.get();

            car.setDocFile(docFile);
            carRepository.save(car);
        }
    }
}
