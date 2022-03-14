package com.data.analyzer.domain.service;

import com.data.analyzer.domain.entity.model.AnalyzeResults;

public interface AnalyzedGeneratorFileService {
    void generatorFile(AnalyzeResults analyzeResults, String nameFile);
}
