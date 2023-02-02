package com.example.AutoVault.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@SuppressWarnings("ALL")
@Entity
@Table(name = "docfile")
public class DocFile {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] docFile;

    public DocFile() {
    }
    public DocFile(Long id, String fileName, byte[] docFile, Car car) {
        this.id = id;
        this.fileName = fileName;
        this.docFile = docFile;
        this.car = car;
    }

    @OneToOne(mappedBy = "docFile")
    private Car car;




    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
