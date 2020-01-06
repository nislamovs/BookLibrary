package com.dbpopulator.app.converters.beanConverters;


import com.dbpopulator.app.domain.responses.ServiceStatusResponse;
import com.dbpopulator.app.models.ProcessingStatus;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProcessingStatusConverter {

    private static final ModelMapper mapper = new ModelMapper();

    public ProcessingStatusConverter() {
        mapper.getConfiguration().setFieldMatchingEnabled(true);
    }

    public static ServiceStatusResponse toResponse(ProcessingStatus model) {
        return mapper.map(model, ServiceStatusResponse.class);
    }

    public static List<ServiceStatusResponse> toResponse(List<ProcessingStatus> processingStatusModels) {
        return processingStatusModels.stream().map(ProcessingStatusConverter::toResponse).collect(Collectors.toList());
    }
}
