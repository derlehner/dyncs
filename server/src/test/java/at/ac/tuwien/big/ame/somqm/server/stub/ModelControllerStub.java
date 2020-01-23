package at.ac.tuwien.big.ame.somqm.server.stub;

import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.CreateUmlModelRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ModelControllerStub {

  private static String BASE_URL = "http://localhost:8080/somqm/umlmodels";

  public long create(CreateUmlModelRequest request) {
    String methodUrlExtension = "/";
    RestTemplate restTemplate = new RestTemplate();
    long response = restTemplate
        .postForObject(BASE_URL + methodUrlExtension, request, Long.class);
    return response;
  }

  public void updateContent(long id, FileSystemResource file) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    String methodUrlExtension = "/content";
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body
        = new LinkedMultiValueMap<>();
    try {
      body.add("file", mapper.writer().writeValueAsString(file));
    } catch (Exception ex) {
      throw new RuntimeException(ex.getLocalizedMessage());
    }
    HttpEntity<MultiValueMap<String, Object>> request
        = new HttpEntity<>(body, headers);
    String url = BASE_URL + "/" + id + methodUrlExtension;
    restTemplate
        .put(url, request);
  }

  public void updateContent(long id, String path) {
    String methodUrlExtension = "/contentfrompath";
    RestTemplate restTemplate = new RestTemplate();
    String requestUrl = BASE_URL + "/" + id + methodUrlExtension;
    restTemplate
        .put(requestUrl, path);
  }


}
