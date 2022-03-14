package com.data.analyzer.domain.job;

import com.data.analyzer.domain.config.ConstantsSystem;
import com.data.analyzer.domain.service.AnalyzerFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnalyzerFileJob {

    private static final Logger log = LoggerFactory.getLogger(AnalyzerFileJob.class.getSimpleName());

    private AnalyzerFileService analyzerFileService;

    @Autowired
    public AnalyzerFileJob(AnalyzerFileService analyzerFileService){
        this.analyzerFileService = analyzerFileService;
    }

    @Scheduled(cron = "${app.scheduler.exec-job}", zone = "${app.time-zone}")
    public void jobFile(){
        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC,"jobFile", "init job");
            analyzerFileService.processFile();
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "jobFile","final job");
        } catch (Exception e) {
            log.error("m=jobFile");
            throw new RuntimeException(e);
        }
    }
}
