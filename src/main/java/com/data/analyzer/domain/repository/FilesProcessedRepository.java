package com.data.analyzer.domain.repository;

import com.data.analyzer.domain.entity.FilesProcessedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesProcessedRepository extends JpaRepository<FilesProcessedEntity, Long> {
    FilesProcessedEntity findByFileByte(String fileByte);
}
