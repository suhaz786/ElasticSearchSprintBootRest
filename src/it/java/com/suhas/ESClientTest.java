package com.suhas;
/*
 * Created by hakdogan on 02/12/2017
 */

import com.suhas.dao.QueryDAO;
import com.suhas.entity.Document;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSearchStarter.class)
@ActiveProfiles("test")
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ESClientTest {

    @Autowired
    private QueryDAO dao;

    private Document doc = new Document(null, "Test Title", "Testing Subject", "Testable Content!");

    @Test
    public void testA() {
        assertNotNull(dao.indexRequest(doc));
    }

    @Test
    public void testB() {
        assertFalse(dao.matchAllQuery().isEmpty());
    }

    @Test
    public void testC(){
        assertFalse(dao.wildcardQuery("test").isEmpty());
    }

    @Test
    public void testD() throws IOException {
        final List<Document> documentList = dao.matchAllQuery();
        documentList.forEach(doc -> dao.deleteDocument(doc.getId()));
        dao.refreshRequest();
        assertTrue(dao.matchAllQuery().isEmpty());
    }
}