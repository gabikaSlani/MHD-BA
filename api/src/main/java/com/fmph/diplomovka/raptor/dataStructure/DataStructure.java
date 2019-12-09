package com.fmph.diplomovka.raptor.dataStructure;

import org.springframework.stereotype.Component;

@Component
public class DataStructure {

    private final DataStructureService dataStructureService;

    public DataStructureModel getDataStructureModel() {
        return dataStructureModel;
    }

    private DataStructureModel dataStructureModel;

    public DataStructure(DataStructureService dataStructureService) {
        this.dataStructureService = dataStructureService;
        this.dataStructureModel = dataStructureService.createDataStructure();
    }
}