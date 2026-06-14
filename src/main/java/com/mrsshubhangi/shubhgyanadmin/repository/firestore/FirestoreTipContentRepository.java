package com.mrsshubhangi.shubhgyanadmin.repository.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;
import com.mrsshubhangi.shubhgyanadmin.repository.TipContentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Primary
public class FirestoreTipContentRepository implements TipContentRepository {

    private final Firestore firestore;

    private static final String COLLECTION = "tips";

    public FirestoreTipContentRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void save(TipContent tip) throws Exception {

        if (tip.getId() == null || tip.getId().isBlank()) {
            tip.setId(UUID.randomUUID().toString());
        }

        firestore.collection(COLLECTION)
                .document(tip.getId())
                .set(tip)
                .get();
    }

    @Override
    public List<TipContent> findAll() throws ExecutionException, InterruptedException {

        List<TipContent> list = new ArrayList<>();

        ApiFuture<QuerySnapshot> snapshot =
                firestore.collection(COLLECTION).get();

        for (DocumentSnapshot doc : snapshot.get().getDocuments()) {
            try {
                list.add(doc.toObject(TipContent.class));
            } catch (RuntimeException ex) {
                // Fallback mapping when Firestore types don't match Java types
                Map<String, Object> d = doc.getData();
                if (d == null) continue;
                TipContent t = new TipContent();
                t.setId(doc.getId());
                t.setTitle((String) d.get("title"));
                t.setDescription((String) d.get("description"));
                t.setCategory((String) d.get("category"));
                t.setImageUrl((String) d.get("imageUrl"));
                t.setAudioUrl((String) d.get("audioUrl"));
                t.setYoutubeUrl((String) d.get("youtubeUrl"));

                Object featuredObj = d.get("featured");
                t.setFeatured(featuredObj instanceof Boolean ? (Boolean) featuredObj : Boolean.parseBoolean(String.valueOf(featuredObj)));

                t.setViews(parseLongSafely(d.get("views")));
                t.setLikes(parseLongSafely(d.get("likes")));
                t.setShareCount(parseLongSafely(d.get("shareCount")));
                t.setCreatedAt(parseLongSafely(d.get("createdAt")));

                list.add(t);
            }
        }

        return list;
    }

    @Override
    public TipContent findById(String id) throws Exception {

        DocumentSnapshot doc =
                firestore.collection(COLLECTION)
                        .document(id)
                        .get()
                        .get();

        try {
            return doc.toObject(TipContent.class);
        } catch (RuntimeException ex) {
            Map<String, Object> d = doc.getData();
            if (d == null) return null;
            TipContent t = new TipContent();
            t.setId(doc.getId());
            t.setTitle((String) d.get("title"));
            t.setDescription((String) d.get("description"));
            t.setCategory((String) d.get("category"));
            t.setImageUrl((String) d.get("imageUrl"));
            t.setAudioUrl((String) d.get("audioUrl"));
            t.setYoutubeUrl((String) d.get("youtubeUrl"));

            Object featuredObj = d.get("featured");
            t.setFeatured(featuredObj instanceof Boolean ? (Boolean) featuredObj : Boolean.parseBoolean(String.valueOf(featuredObj)));

            t.setViews(parseLongSafely(d.get("views")));
            t.setLikes(parseLongSafely(d.get("likes")));
            t.setShareCount(parseLongSafely(d.get("shareCount")));
            t.setCreatedAt(parseLongSafely(d.get("createdAt")));

            return t;
        }
    }

    private long parseLongSafely(Object o) {
        if (o == null) return 0L;
        if (o instanceof Number) return ((Number) o).longValue();
        try {
            return Long.parseLong(String.valueOf(o));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    @Override
    public void delete(String id) {

        firestore.collection(COLLECTION)
                .document(id)
                .delete();
    }
}