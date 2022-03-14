package com.data.analyzer.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "TB_FILES_PROCESSED")
@SequenceGenerator(name = "SEQ_FL_PCS", sequenceName = "SEQ_FILES_PROCESSED", allocationSize = 1)
public class FilesProcessedEntity {

    @Id
    @Column(name = "ID_FILES_PROCESSED")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FL_PCS")
    private Long id;
    @Column(name = "NM_FILE")
    private String nameFile;
    @Column(name = "BT_FILE", columnDefinition = "text")
    private String fileByte;
    @Column(name = "TP_FILES")
    private String type;

    public FilesProcessedEntity(Long id, String nameFile, String fileByte, String type) {
        this.id = id;
        this.nameFile = nameFile;
        this.fileByte = fileByte;
        this.type = type;
    }

    public FilesProcessedEntity() {}

    public Long getId() {
        return id;
    }

    public String getNameFile() {
        return nameFile;
    }

    public String getFileByte() {
        return fileByte;
    }

    public String getType() {
        return type;
    }

    public static FilesProcessedEntity builder() {
        return new FilesProcessedEntity();
    }

    public FilesProcessedEntity id(Long id) {
        this.id = id;
        return this;
    }

    public FilesProcessedEntity nameFile(String nameFile) {
        this.nameFile = nameFile;
        return this;
    }

    public FilesProcessedEntity fileByte(String fileByte) {
        this.fileByte = fileByte;
        return this;
    }

    public FilesProcessedEntity type(String type) {
        this.type = type;
        return this;
    }

    public FilesProcessedEntity build() {
        return new FilesProcessedEntity(id, nameFile, fileByte, type);
    }
}
