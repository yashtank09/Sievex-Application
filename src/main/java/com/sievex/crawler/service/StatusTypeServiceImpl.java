package com.sievex.crawler.service;

import com.sievex.crawler.entity.StatusType;
import com.sievex.crawler.repository.StatusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusTypeServiceImpl implements StatusTypeService {

    private final StatusTypeRepository statusTypeRepository;

    @Autowired
    public StatusTypeServiceImpl(StatusTypeRepository statusTypeRepository) {
        this.statusTypeRepository = statusTypeRepository;
    }

    @Override
    public StatusType getStatusTypeByAlias(String alias) {
        return statusTypeRepository.findByAlias(alias);
    }

    @Override
    public List<StatusType> getAllStatusTypes() {
        return statusTypeRepository.findAll();
    }

    @Override
    public StatusType saveStatusType(StatusType statusType) {
        return statusTypeRepository.save(statusType);
    }

    @Override
    public StatusType updateStatusType(StatusType statusType) {
        return statusTypeRepository.save(statusType);
    }

    @Override
    public boolean deleteStatusType(Long statusType) {
        statusTypeRepository.deleteById(statusType);
        return statusTypeRepository.existsById(statusType);
    }
}
