package com.fmph.diplomovka.raptor;

import com.fmph.diplomovka.raptor.dataStructure.CsvWriter;
import com.fmph.diplomovka.raptor.dataStructure.DataStructureModel;
import com.fmph.diplomovka.raptor.dataStructure.DataStructureService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataStructure {

  private final CsvWriter csvWriter;
  private DataStructureModel dataStructureModel;

  public DataStructure(
      @Value("${data-structure-serialized-file-path}") String dataStructureFilePath,
      DataStructureService dataStructureService, CsvWriter csvWriter) {
    this.csvWriter = csvWriter;
    try {
      System.out.println("Start deserializing data structure from file.");
      dataStructureModel = readDataStructureFromFile(dataStructureFilePath);
      System.out.println("Data structure successfully deserialized from file.");
    } catch (ClassNotFoundException | IOException e) {
      dataStructureModel = dataStructureService.createDataStructure();
      writeDataStructure(dataStructureFilePath);
    }
  }

  private void writeDataStructure(String fileName) {
    try {
      System.out.println("Serializing data structure to file started.");
      FileOutputStream f = new FileOutputStream(new File(fileName));
      ObjectOutputStream o = new ObjectOutputStream(f);
      o.writeObject(dataStructureModel);

      o.close();
      f.close();
      System.out.println("Data structure successfully serialized to file.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private DataStructureModel readDataStructureFromFile(String fileName)
      throws ClassNotFoundException, IOException {
    FileInputStream fi = null;
    fi = new FileInputStream(new File(fileName));
    ObjectInputStream oi = new ObjectInputStream(fi);
    return (DataStructureModel) oi.readObject();
  }

  //when called, change in DataStructureFiller creating routeSubroute in addSubrouteToRoute
  private void createCSVSheduler(DataStructureModel dataStructureModel, String pathName) {
    csvWriter.createShedulter(dataStructureModel, pathName);
  }

  public DataStructureModel getDataStructureModel() {
    return dataStructureModel;
  }

}
