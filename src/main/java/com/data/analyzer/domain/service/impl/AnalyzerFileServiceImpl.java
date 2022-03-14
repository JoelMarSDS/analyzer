package com.data.analyzer.domain.service.impl;

import com.data.analyzer.domain.config.ConstantsSystem;
import com.data.analyzer.domain.entity.FilesProcessedEntity;
import com.data.analyzer.domain.repository.FilesProcessedRepository;
import com.data.analyzer.domain.service.AnalyzerFileService;
import com.data.analyzer.domain.service.DataAnalyticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.data.analyzer.domain.config.Constants.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

@Service
public class AnalyzerFileServiceImpl implements AnalyzerFileService {

    private static final Logger log = LoggerFactory.getLogger(AnalyzerFileServiceImpl.class.getSimpleName());

    @Value("${app.locale.file.in}")
    private String input;

    private final DataAnalyticService dataAnalyticService;
    private final FilesProcessedRepository filesProcessedRepository;

    @Autowired
    public AnalyzerFileServiceImpl(
            DataAnalyticService dataAnalyticService,
            FilesProcessedRepository filesProcessedRepository){
        this.dataAnalyticService = dataAnalyticService;
        this.filesProcessedRepository = filesProcessedRepository;
    }

    @Override
    public void processFile() {
        log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC_VL, "processFile", "init process", input);
        try {

            String locale = System.getenv(HOME_PATH) + input;

            listFile(locale).forEach(this::scannerFile);

            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC_VL, "processFile", "final process", input);

        } catch (Exception e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "processFile", "error process file");
            throw new RuntimeException(e);
        }
    }

    private void scannerFile(File file) {
        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "scannerFile", "init scanner file");

            var linesFile = new ArrayList<String>();
            Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());

            while (scanner.hasNextLine()){
                linesFile.add(scanner.nextLine());
            }
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "scannerFile", "final scanner file");

            dataAnalyticService.dataAnalyzer(linesFile, file.getName());
        } catch (IOException e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "scannerFile", "error scanner file");
            throw new RuntimeException(e);
        }
    }

    private List<File> listFile(String locale){
        List<File> filesValid = new ArrayList<>();

        log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "listFile", "init list file");

        try {
            var listFiles = new ArrayList<>(
                    Arrays.asList(
                            Objects.requireNonNull(
                                    new File(locale).listFiles()
                            )
                    ));

            listFiles.forEach(f -> {
                if (validateTypeFile(f.getName()) && validateFileEquals(f)) {
                    filesProcessedRepository.save(
                            FilesProcessedEntity.builder()
                                    .nameFile(f.getName())
                                    .fileByte(fileByte(f))
                                    .type(f.getName().split("\\.")[1]).build());
                    filesValid.add(f);
                }
            });

            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "listFile", "final list file");
            return filesValid;
        } catch (Exception e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "listFile", "error list file");
            throw new RuntimeException(e);
        }
    }

    private boolean validateFileEquals(File f) {
        return filesProcessedRepository.findByFileByte(fileByte(f)) == null;
    }

    private String fileByte(File f) {
        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "fileByte", "file byte convert");
            return new String(Files.readAllBytes(f.toPath()));
        } catch (IOException e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "fileByte", "error file byte convert");
            throw new RuntimeException(e);
        }
    }

    private boolean validateTypeFile (String nameFile) {
        String[] input = nameFile.split("\\.");
        if (!input[1].equals("dat")){
            log.info("File {} invalid", nameFile);
            return false;
        }
        return true;
    }
}
