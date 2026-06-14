package com.mrsshubhangi.shubhgyanadmin.repository;

import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;

import java.util.List;

public interface TipContentRepository {

    void save(TipContent tip) throws Exception;

    List<TipContent> findAll() throws Exception;

    TipContent findById(String id) throws Exception;

    void delete(String id);
}