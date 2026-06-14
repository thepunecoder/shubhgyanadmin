package com.mrsshubhangi.shubhgyanadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;
import com.mrsshubhangi.shubhgyanadmin.service.TipContentService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipContentControllerTest {

    static class StubService implements TipContentService {
        private final List<TipContent> list = new ArrayList<>();

        @Override
        public void saveAll(List<TipContent> tips) throws Exception {
            list.addAll(tips);
        }

        @Override
        public List<TipContent> getAll() throws Exception {
            return list;
        }

        @Override
        public TipContent getById(String id) throws Exception {
            return list.stream().filter(t -> id.equals(t.getId())).findFirst().orElse(null);
        }

        @Override
        public void delete(String id) throws Exception {
            list.removeIf(t -> id.equals(t.getId()));
        }

        @Override
        public void save(TipContent tip) throws Exception {
            list.add(tip);
        }
    }

    @Test
    void listShouldReturnTipsModel() throws Exception {
        StubService service = new StubService();
        ObjectMapper mapper = null;
        TipContentController controller = new TipContentController(service, mapper);

        // initially empty
        String view = controller.list(new org.springframework.ui.ConcurrentModel());
        assertEquals("tips", view);
    }

    @Test
    void createTipShouldSaveAndRedirect() throws Exception {
        StubService service = new StubService();
        ObjectMapper mapper = null;
        TipContentController controller = new TipContentController(service, mapper);

        TipContent tip = new TipContent();
        tip.setTitle("t1");

        String result = controller.createTip(tip);
        assertEquals("redirect:/tips", result);
        // saved
        List<TipContent> all = service.getAll();
        assertEquals(1, all.size());
    }
}



