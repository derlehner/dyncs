package at.ac.tuwien.big.ame.dyncs.server.service;

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

public interface RequirementsService {

  UmlRequirements getUMLModelRequirements(long projectId);

  GetAllResult getAll(GetAllRequest request);

  GetAllResultClassModel getAllForClassModel(GetAllRequest request);

  GetEntitiesResult getEntities(GetEntitiesRequest request);

  GetRelationsResult getRelations(GetRelationsRequest request);

  GetRelationsResultClassModel getRelationsForClassModel(GetRelationsRequest request);

  // Class model specific

  GetAttributesResult getAttributes(GetAttributesRequest request) throws ServiceException;

  GetOperationsResult getOperations(GetOperationsRequest request) throws ServiceException;

  // Activity model specific

  GetActivitiesResult getActivities(GetActivitiesRequest request) throws ServiceException;

  GetActionsResult getActions(GetActionsRequest request) throws ServiceException;

  GetNodesResult getNodes(GetNodesRequest projectId) throws ServiceException;
}
