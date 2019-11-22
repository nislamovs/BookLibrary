package com.booklibrary.app.services;

import com.booklibrary.app.domain.exceptions.VisitorNotFoundException;
import com.booklibrary.app.domain.requests.VisitorRequest;
import com.booklibrary.app.models.nosql.VisitorData;
import com.booklibrary.app.repository.nosql.VisitorDataRepository;
import com.booklibrary.app.services.interfaces.IVisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.booklibrary.app.converters.orikaConverters.ToDocumentConverters.toDocument;
import static java.lang.String.format;
import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitorService implements IVisitorService {

    private final VisitorDataRepository visitorRepository;

    @Override
    public List<VisitorData> getAllVisitors() {
        return visitorRepository.findAll();
    }

    @Override
    public Page<VisitorData> getVisitors(int page, int size) {
        return visitorRepository.findAll(of(page, size));
    }

    @Override
    public VisitorData getVisitorById(String visitorId) {
        return visitorRepository.findVisitorByVisitorId(visitorId)
            .orElseThrow(() -> new VisitorNotFoundException(format("Visitor with Id [%s] was not found.", visitorId)));
    }

    @Override
    public VisitorData addVisitor(VisitorRequest visitorRequest) {
//        TODO convert to entity
        return visitorRepository.save(toDocument(visitorRequest));
    }

    @Override
    public VisitorData updateVisitor(VisitorRequest visitorRequest) {
//        TODO convert to entity
        return visitorRepository.save(toDocument(visitorRequest));
    }

    @Override
    public void deleteVisitorById(String visitorId) {
        visitorRepository.deleteByVisitorId(visitorId);
    }

}
