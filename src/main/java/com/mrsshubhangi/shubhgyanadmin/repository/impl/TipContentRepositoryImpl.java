package com.mrsshubhangi.shubhgyanadmin.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;
import com.mrsshubhangi.shubhgyanadmin.repository.TipContentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TipContentRepositoryImpl implements TipContentRepository {

    private final Firestore firestore;
    private final ObjectMapper mapper;
    private static final String COLLECTION = "tips";

    public TipContentRepositoryImpl(Firestore firestore, ObjectMapper mapper) {
        this.firestore = firestore;
        this.mapper = mapper;
    }

    @Override
    public void save(TipContent tip) throws Exception {
        if (tip.getId() == null || tip.getId().isBlank()) {
            throw new IllegalArgumentException("Tip id cannot be null");
        }

        CollectionReference col = firestore.collection(COLLECTION);

        // convert to map to avoid Firestore POJO issues
        Map<String, Object> data = mapper.convertValue(tip, Map.class);

        WriteResult result = col.document(tip.getId()).set(data).get();
        // optional: result.getUpdateTime();
    }

    @Override
    public List<TipContent> findAll() throws Exception {
        List<TipContent> out = new ArrayList<>();
        List<QueryDocumentSnapshot> docs = firestore.collection(COLLECTION).get().get().getDocuments();

        for (QueryDocumentSnapshot d : docs) {
            Map<String, Object> map = d.getData();
            TipContent tip = mapper.convertValue(map, TipContent.class);
            // ensure id is set
            if (tip.getId() == null || tip.getId().isBlank()) {
                tip.setId(d.getId());
            }
            out.add(tip);
        }

        return out;
    }

    @Override
    public TipContent findById(String id) throws Exception {
        DocumentSnapshot snap = firestore.collection(COLLECTION).document(id).get().get();
        if (!snap.exists()) return null;
        Map<String, Object> map = snap.getData();
        TipContent tip = mapper.convertValue(map, TipContent.class);
        if (tip.getId() == null || tip.getId().isBlank()) {
            tip.setId(snap.getId());
        }
        return tip;
    }

    @Override
    public void delete(String id) {
        try {
            firestore.collection(COLLECTION).document(id).delete().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

