package com.mrsshubhangi.shubhgyanadmin.service;

import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;

import java.util.List;

public interface TipContentService {

    void saveAll(List<TipContent> tips) throws Exception;

    List<TipContent> getAll() throws Exception;

    TipContent getById(String id) throws Exception;

    void delete(String id) throws Exception;

    void save(TipContent tip) throws Exception;
}