package com.mrsshubhangi.shubhgyanadmin.service.impl;

import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;
import com.mrsshubhangi.shubhgyanadmin.repository.TipContentRepository;
import com.mrsshubhangi.shubhgyanadmin.service.TipContentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipContentServiceImpl implements TipContentService {

    private final TipContentRepository repository;

    public TipContentServiceImpl(TipContentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveAll(List<TipContent> tips) throws Exception {
        for (TipContent tip : tips) {
            if (tip.getId() == null) {
                tip.setId(UUID.randomUUID().toString());
            }
            repository.save(tip);
        }
    }

    @Override
    public List<TipContent> getAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public TipContent getById(String id) throws Exception {
        return repository.findById(id);
    }

    @Override
    public void delete(String id) throws Exception {
        repository.delete(id);
    }

    @Override
    public void save(TipContent tip) throws Exception {
        repository.save(tip);
    }
}
