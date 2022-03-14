package com.data.analyzer.domain.service.impl;

import com.data.analyzer.domain.config.ConstantsSystem;
import com.data.analyzer.domain.entity.model.AnalyzeResults;
import com.data.analyzer.domain.service.AnalyzedGeneratorFileService;
import com.data.analyzer.domain.service.DataAnalyticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DataAnalyticServiceImpl implements DataAnalyticService {

    private static final Logger log = LoggerFactory.getLogger(DataAnalyticServiceImpl.class.getSimpleName());

    AnalyzedGeneratorFileService analyzedGeneratorFileService;

    @Autowired
    public DataAnalyticServiceImpl(AnalyzedGeneratorFileService analyzedGeneratorFileService) {
        this.analyzedGeneratorFileService = analyzedGeneratorFileService;
    }

    @Override
    public void dataAnalyzer(List<String> dataList, String nameFile) {

        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "dataAnalyzer", "init data analyzer");

            var sellers = new ArrayList<String>();
            var customers = new ArrayList<String>();
            var sales = new ArrayList<String>();

            dataList.forEach(s -> {
                String idDataType = s.substring(0, 3);
                switch (idDataType) {
                    case "001":
                        sellers.add(s);
                        break;
                    case "002":
                        customers.add(s);
                        break;
                    case "003":
                        sales.add(s);
                        break;
                    default:
                        log.info("Unknown Data");
                        break;
                }
            });

            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "dataAnalyzer", "final data analyzer");

            analyzedGeneratorFileService.generatorFile(dataDistribution(sellers, customers, sales), nameFile);
        } catch (Exception e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "dataAnalyzer", "error data analyzer");
            throw new RuntimeException(e);
        }
    }

    private AnalyzeResults dataDistribution(List<String> sellers, List<String> customers, List<String> sales) {

        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "dataDistribution", "init data distribution");
            Map<String, Object> mapData = analyzerDataSales(sales);

            AnalyzeResults analyzeResults = AnalyzeResults.builder()
                    .amountClients(analyzerDataCustomers(customers))
                    .amountSalesman(analyzerDataSellers(sellers))
                    .bestSale(
                            Long.valueOf(
                                    String.valueOf(mapData.get("idSale"))
                            ))
                    .worstSeller(String.valueOf(mapData.get("worstSeller")))
                    .build();

            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "dataDistribution", "final data distribution");
            return analyzeResults;
        }catch (Exception e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "dataDistribution", "error data distribution");
            throw new RuntimeException(e);
        }
    }

    private Long analyzerDataSellers(List<String> sellers){
        return (long) sellers.size();
    }

    private Long analyzerDataCustomers(List<String> customers){
        return (long) customers.size();
    }

    private Map<String,Object> analyzerDataSales(List<String> sales) {
        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "analyzerDataSales", "init data distribution");

            Map<String, Object> mapData = new HashMap<>();
            AtomicReference<Double> expensiveSale = new AtomicReference<>(0.0);

            sales.forEach(s -> {
                String[] data = s.split("ç");

                Double totalValue = analyzerSale(data[2]);

                if (expensiveSale.get() < totalValue) {

                    expensiveSale.set(totalValue);
                    mapData.put("idSale", Long.valueOf(data[1]));
                } else {
                    mapData.put("worstSeller", data[3]);
                }
            });

            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "analyzerDataSales", "final data distribution");

            return mapData;
        }catch (Exception e){
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "analyzerDataSales", "final data distribution");
            throw new RuntimeException(e);
        }
    }

    private Double analyzerSale(String sale) {

        try {
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "analyzerSale", "init data distribution");
            AtomicReference<Double> currentSale = new AtomicReference<>(0.0);

            List<String> data = Arrays.asList(sale.replace("[", "").replace("]", "").split(","));

            data.forEach(s -> {
                String[] product = s.split("-");
                currentSale.set((Double.parseDouble(product[1]) * Double.parseDouble(product[2])) + currentSale.get());
            });
            log.info(ConstantsSystem.TEMPLATE_LOG_MT_PC, "analyzerSale", "final data distribution");
            return currentSale.get();
        } catch (Exception e) {
            log.error(ConstantsSystem.TEMPLATE_LOG_MT_PC, "analyzerSale", "error data distribution");
            throw new RuntimeException(e);
        }
    }
}
