package com.data.analyzer.domain.service.impl;

import com.data.analyzer.domain.config.ConstantsSystem;
import com.data.analyzer.domain.entity.model.AnalyzeResults;
import com.data.analyzer.domain.job.AnalyzerFileJob;
import com.data.analyzer.domain.service.AnalyzedGeneratorFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static com.data.analyzer.domain.config.Constants.HOME_PATH;

@Component
public class AnalyzedGeneratorFileServiceImpl implements AnalyzedGeneratorFileService {

    private static final Logger log = LoggerFactory.getLogger(AnalyzedGeneratorFileServiceImpl.class.getSimpleName());

    @Value("${app.locale.file.out}")
    private String output;
    private static final String DONE = ".done.";

    @Override
    public void generatorFile(AnalyzeResults analyzeResults, String nameFile) {

        String locale = System.getenv(HOME_PATH) + output;

        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "generatorFile", "init data distribution");

            String[] fileArray = nameFile.split("\\.");

            PrintWriter printWriter = new PrintWriter(
                    locale.concat("/")
                          .concat(
                                  fileArray[0]
                          ).concat(DONE)
                           .concat(fileArray[1]), StandardCharsets.UTF_8);

            printWriter.println("Amount of clients: ".concat(analyzeResults.getAmountClients().toString()));
            printWriter.println("Amount of salesman: ".concat(analyzeResults.getAmountSalesman().toString()));
            printWriter.println("Best sale ID: ".concat(analyzeResults.getBestSale().toString()));
            printWriter.print("Worst salesman: ".concat(analyzeResults.getWorstSeller()));

            printWriter.close();

            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "generatorFile", "final data distribution");
        } catch (IOException e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "generatorFile", "error data distribution");
            throw new RuntimeException(e);
        }
    }
}
