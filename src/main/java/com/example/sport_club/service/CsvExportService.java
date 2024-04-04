package com.example.sport_club.service;

import com.example.sport_club.model.TrainingModel;
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
public class CsvExportService {

    private final TrainingRepo trainingRepo;

    public CsvExportService(TrainingRepo trainingRepo) {
        this.trainingRepo = trainingRepo;
    }

    public void writeTrainingsToCsv(Writer writer) {

        Iterable<TrainingModel> trainingModels = trainingRepo.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("id", "coach_mark", "date_time", "open", "trainplan_id","traintype_id");
            for (TrainingModel trainingModel : trainingModels) {
                csvPrinter.printRecord(trainingModel.getId(), trainingModel.getCoachMark(), trainingModel.getDateTime(), trainingModel.getStatus(), trainingModel.getTraintype().getId(), trainingModel.getTrainplan().getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
