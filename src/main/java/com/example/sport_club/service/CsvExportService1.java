package com.example.sport_club.service;

import com.example.sport_club.model.OnceTrainModel;
import com.example.sport_club.model.TrainingModel;
import com.example.sport_club.repo.OnceTrainRepo;
import com.example.sport_club.repo.TrainingRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.System.getLogger;

@Service
public class CsvExportService1 {

    private final OnceTrainRepo trainingRepo;

    public CsvExportService1(OnceTrainRepo trainingRepo) {
        this.trainingRepo = trainingRepo;
    }

    public void writeOncesToCsv(Writer writer) {

        Iterable<OnceTrainModel> onceTrainModels = trainingRepo.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("id", "date_time", "open", "coach_id","typetrain_id", "user_id");
            for (OnceTrainModel onceTrainModel : onceTrainModels) {
                csvPrinter.printRecord(onceTrainModel.getId(), onceTrainModel.getDateTime(), onceTrainModel.getStatus(), onceTrainModel.getUseronce().getId(), onceTrainModel.getCoachonce().getId(), onceTrainModel.getTraintypeonce().getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
