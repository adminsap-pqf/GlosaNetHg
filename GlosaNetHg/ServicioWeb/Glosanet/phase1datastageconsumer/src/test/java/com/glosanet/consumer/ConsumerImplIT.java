package com.glosanet.consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mx.servicio.jobs.datastage.loader.phase1.processing.DatastagePhase1JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/configuration.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
public class ConsumerImplIT {
    @Autowired
    DatastagePhase1JobService ds;

    @Value("${server.glosanet.uri}")
    private String glosanetUri;

    @Value("${trigger.datastage.onfinish}")
    private String endpointWhenFinish;

    @Test
    public void consumeMessage() {
        Map<String, Object> results = ds.process(
                8,
                4,
                "PQF910416FB3",
                "/Users/admin/Documents/Datastages/2019/01_enero/589777_solicitudes.zip",
                1704);
        Assert.assertNotNull(results);
        System.out.println(results);
    }
    @Test
    public void callEndpoint() throws Exception {
        final String uri = glosanetUri + String.format(endpointWhenFinish, 10, 424);
        Assert.assertEquals(uri, "http://localhost:8080/GlosaNet/controller/datastage/request-datastage?size=10&idDatastage=424");

        RestTemplate restTemplate = new RestTemplate();
        String result = (String) restTemplate.getForObject(uri, Object.class);

        System.out.println(result);
    }

    @Test
    public void getIdDatastage() throws Exception {
        Integer idDatastage = ds.getIdDatastage(5, "277904");
        Assert.assertEquals(new Integer(121), idDatastage);
    }
}
