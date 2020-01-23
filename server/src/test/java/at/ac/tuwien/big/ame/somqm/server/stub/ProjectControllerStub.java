package at.ac.tuwien.big.ame.somqm.server.stub;

import at.ac.tuwien.big.ame.somqm.server.dto.project.CreateProjectRequest;
import org.springframework.web.client.RestTemplate;

public class ProjectControllerStub {

  private static String BASE_URL = "http://localhost:8080/somqm/projects";

  public long create(CreateProjectRequest request) {
    String methodUrlExtension = "/";
    RestTemplate restTemplate = new RestTemplate();
    long response = restTemplate
        .postForObject(BASE_URL + methodUrlExtension, request, Long.class);
    return response;
  }

  public void delete(long id) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.delete(BASE_URL + "/" + id);
  }

}
