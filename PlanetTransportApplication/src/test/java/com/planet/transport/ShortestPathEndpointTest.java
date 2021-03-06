package com.planet.transport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.planet.transport.ShortestPathEndpoint;
import com.planet.transport.ShortestPathRepository;
import com.planet.transport.config.DatasourceBean;
import com.planet.transport.config.PersistenceBean;
import com.planet.transport.config.WebServiceBean;
import com.planet.transport.dao.EdgeDao;
import com.planet.transport.dao.TrafficDao;
import com.planet.transport.dao.VertexDao;
import com.planet.transport.schema.GetShortestPathRequest;
import com.planet.transport.schema.GetShortestPathResponse;
import com.planet.transport.service.EntityManagerService;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;


/**
 * @author AnandT
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatasourceBean.class, PersistenceBean.class, WebServiceBean.class,
        ShortestPathEndpoint.class, ShortestPathRepository.class, EntityManagerService.class, EdgeDao.class, VertexDao.class,
        TrafficDao.class},
        loader = AnnotationConfigContextLoader.class)
public class ShortestPathEndpointTest {

    @Autowired
    private ShortestPathEndpoint shortestPathEndpoint;

    @Test
    public void verifyThatShortestPathSOAPEndPointIsCorrect() throws Exception {
        // Set Up Fixture
        GetShortestPathRequest shortestPathRequest = new GetShortestPathRequest();
        shortestPathRequest.setName("Moon");

        StringBuilder path = new StringBuilder();
        path.append("Earth (A)\tMoon (B)\t");

        GetShortestPathResponse expectedResponse = new GetShortestPathResponse();
        expectedResponse.setPath(path.toString());

        //Test
        GetShortestPathResponse actualResponse = shortestPathEndpoint.getShortestPath(shortestPathRequest);

        // Verify
        assertThat(actualResponse, sameBeanAs(expectedResponse));
        assertThat(actualResponse.getPath(), sameBeanAs("Earth (A)\tMoon (B)\t"));
    }

}