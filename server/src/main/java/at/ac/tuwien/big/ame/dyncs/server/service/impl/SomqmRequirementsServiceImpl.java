package at.ac.tuwien.big.ame.dyncs.server.service.impl;

import at.ac.tuwien.big.ame.dyncs.server.dto.SearchUmlModelsRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.UmlRequirements;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetAllRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetAllResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetEntitiesRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetEntitiesResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetRelationsRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetRelationsResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAllResultClassModel;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetRelationsResultClassModel;
import at.ac.tuwien.big.ame.dyncs.server.dto.umlmodel.UmlModelSummary;
import at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.dyncs.server.service.RequirementsService;
import at.ac.tuwien.big.ame.dyncs.server.service.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
public class SomqmRequirementsServiceImpl implements RequirementsService {

  private static final String SOMQM_BASE_URL = "http://localhost:8080/somqm";


  @Autowired
  public SomqmRequirementsServiceImpl() {
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public UmlRequirements getUMLModelRequirements(long projectId) {
    List<UmlModelType> modelTypes = getSupportedTypes();
    Map<UmlModelType, Long> modelIds = new HashMap<>();
    for (UmlModelType modelType : modelTypes) {
      try {
        modelIds.put(modelType, getModelIdByTypeAndProject(modelType, projectId));
      } catch (IllegalArgumentException ex) {
        // ignore model types that are not available in chosen project
      }
    }

    UmlRequirements result = new UmlRequirements(projectId, modelIds);
    return result;
  }

  @Override
  public GetAllResult getAll(GetAllRequest request) {
    String getAllUrlEnding = "/queries/all";
    RestTemplate restTemplate = new RestTemplate();
    GetAllResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getAllUrlEnding, request, GetAllResult.class);
    System.out.println(response);
    return new GetAllResult();
  }

  public GetAllResultClassModel getAllForClassModel(GetAllRequest request) {
    String getAllUrlEnding = "/queries/all";
    RestTemplate restTemplate = new RestTemplate();
    GetAllResultClassModel response = restTemplate
        .postForObject(SOMQM_BASE_URL + getAllUrlEnding, request, GetAllResultClassModel.class);
    return response;
  }

  @Override
  public GetEntitiesResult getEntities(GetEntitiesRequest request) {
    String getEntitiesUrlEnding = "/queries/entities";
    RestTemplate restTemplate = new RestTemplate();
    GetEntitiesResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getEntitiesUrlEnding, request, GetEntitiesResult.class);
    return response;
  }

  @Override
  public GetRelationsResult getRelations(GetRelationsRequest request) {
    String getRelationsUrlEnding = "/queries/relations";
    RestTemplate restTemplate = new RestTemplate();
    GetRelationsResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getRelationsUrlEnding, request, GetRelationsResult.class);
    return response;
  }

  @Override
  public GetRelationsResultClassModel getRelationsForClassModel(GetRelationsRequest request) {
    String getRelationsUrlEnding = "/queries/relations";
    RestTemplate restTemplate = new RestTemplate();
    GetRelationsResultClassModel response = restTemplate
        .postForObject(SOMQM_BASE_URL + getRelationsUrlEnding, request,
            GetRelationsResultClassModel.class);
    return response;
  }

  @Override
  public GetAttributesResult getAttributes(GetAttributesRequest request) throws ServiceException {
    String getAttributesUrlEnding = "/queries/attributes";
    RestTemplate restTemplate = new RestTemplate();
    GetAttributesResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getAttributesUrlEnding, request, GetAttributesResult.class);
    return response;
  }

  @Override
  public GetOperationsResult getOperations(GetOperationsRequest request)
      throws ServiceException {
    String getOperationsUrlEnding = "/queries/operations";
    RestTemplate restTemplate = new RestTemplate();
    GetOperationsResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getOperationsUrlEnding, request, GetOperationsResult.class);
    return response;
  }

  @Override
  public GetActivitiesResult getActivities(GetActivitiesRequest request) throws ServiceException {
    String getActivitiesUrlEnding = "/queries/activities";
    RestTemplate restTemplate = new RestTemplate();
    GetActivitiesResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getActivitiesUrlEnding, request, GetActivitiesResult.class);
    return response;
  }

  @Override
  public GetActionsResult getActions(GetActionsRequest request) throws ServiceException {
    String getActionsUrlEnding = "/queries/actions";
    RestTemplate restTemplate = new RestTemplate();
    GetActionsResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getActionsUrlEnding, request, GetActionsResult.class);
    return response;
  }

  @Override
  public GetNodesResult getNodes(GetNodesRequest request) throws ServiceException {
    String getNodesUrlEnding = "/queries/nodes";
    RestTemplate restTemplate = new RestTemplate();
    GetNodesResult response = restTemplate
        .postForObject(SOMQM_BASE_URL + getNodesUrlEnding, request, GetNodesResult.class);
    return response;
  }

  private Long getModelIdByTypeAndProject(UmlModelType type, long projectId) {
    SearchUmlModelsRequest request = new SearchUmlModelsRequest();
    request.setProjectIdToSearch(projectId);
    request.setTypeToSearch(type);
    List<UmlModelSummary> response = searchForModel(request);
    if (response.size() == 0) {
      throw new IllegalArgumentException(
          "no model of type " + type + " found in project with id " + projectId);
    }
    return response.get(0).getId();
  }

  private List<UmlModelSummary> searchForModel(SearchUmlModelsRequest request) {
    String searchModelsUrlEnding = "/umlmodels/search";
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<SearchUmlModelsRequest> requestEntity = new HttpEntity<>(request);
    ResponseEntity<List<UmlModelSummary>> response = restTemplate
        .exchange(SOMQM_BASE_URL + searchModelsUrlEnding, HttpMethod.POST, requestEntity,
            new ParameterizedTypeReference<List<UmlModelSummary>>() {
            });
    return response.getBody();
  }

  private List<UmlModelType> getSupportedTypes() {
    String getSupportedTypesUrlEnding = "/umlmodels/supportedTypes";
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<List<UmlModelType>> response = restTemplate
        .exchange(SOMQM_BASE_URL + getSupportedTypesUrlEnding, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<UmlModelType>>() {
            });
    return response.getBody();
  }


}
