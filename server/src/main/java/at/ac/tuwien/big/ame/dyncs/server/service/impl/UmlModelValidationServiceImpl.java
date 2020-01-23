package at.ac.tuwien.big.ame.dyncs.server.service.impl;

import at.ac.tuwien.big.ame.dyncs.server.dto.UmlRequirements;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetAllRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetEntitiesRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetEntitiesResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetRelationsRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.GetRelationsResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.RelationInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.Node;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.ActionType;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelEntityInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelGeneralizationRelationInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelInterfaceRealizationRelationInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelRelationInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperation;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.GetRelationsResultClassModel;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.VisibilityType;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.LifeLine;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.Message;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.SequenceDiagram;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.enumeration.MessageType;
import at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.dyncs.server.service.RequirementsService;
import at.ac.tuwien.big.ame.dyncs.server.service.ServiceException;
import at.ac.tuwien.big.ame.dyncs.server.service.UmlModelValidationService;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.Helper.DtoExtractor;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.Helper.ParallelExecutionPath;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.management.relation.Relation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIConverter.ReadableInputStream;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.MessageOccurrenceSpecificationImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmlModelValidationServiceImpl implements UmlModelValidationService {

  private RequirementsService requirementsService;

  @Autowired
  public UmlModelValidationServiceImpl(RequirementsService requirementsService) {
    this.requirementsService = requirementsService;
  }

  private static ResourceSet createResourceSet() {
    ResourceSet resourceSet = new ResourceSetImpl();

    UMLResourcesUtil.init(resourceSet);
    Registry packageRegistry = resourceSet.getPackageRegistry();
    packageRegistry.put("http://www.omg.org/spec/UML/20090901",
        UMLPackage.eINSTANCE); // Required to load models from http://www.omgwiki.org/model-interchange/doku.php?id=start
    packageRegistry.put("http://schema.omg.org/spec/UML/2.1", UMLPackage.eINSTANCE);
    packageRegistry.put(" http://www.omg.org/spec/UML/20131001", UMLPackage.eINSTANCE);
    // TODO: required?
    packageRegistry
        .put("http://schema.omg.org/spec/UML/2.1/uml.xml#Integer", UMLPackage.PRIMITIVE_TYPE);

    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
        UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
        "xmi", UMLResource.Factory.INSTANCE);

    return resourceSet;
  }

  @Override
  public SequenceDiagram loadDynamicSequenceDiagram(byte[] modelContent, List<String> messageOrder)
      throws ServiceException {
    ResourceSet resourceSet = createResourceSet();
    URI modelUri = URI.createURI("dummyURI.uml");
    Resource resource = resourceSet.createResource(modelUri);
    ByteArrayInputStream bais = new ByteArrayInputStream(modelContent);
    try (BufferedReader bfReader = new BufferedReader(
        new InputStreamReader(bais, StandardCharsets.UTF_8))) {
      try (ReadableInputStream readableInputStream = new URIConverter.ReadableInputStream(
          bfReader)) {
        resource.load(readableInputStream, Collections.emptyMap());
      }
    } catch (IOException e) {
      throw new ServiceException("Failed to load model from supplied content: " + e.getMessage(),
          e);
    }

    UMLResource umlResource = (UMLResource) resource;
    Model model = (Model) EcoreUtil
        .getObjectByType(umlResource.getContents(), UMLPackage.Literals.MODEL);
    if (model == null) {
      throw new ServiceException("Failed to extract model from supplied content");
    }
    return modelToDto(model, messageOrder);
  }

  private SequenceDiagram modelToDto(Model model, List<String> messageOrder) {
    SequenceDiagram result = new SequenceDiagram();
    for (EObject obj : model.eContents()) {
      switch (obj.eClass().getName()) {
        case "Interaction":
          Interaction interaction = (Interaction) obj;
          for (EObject interactionObj : interaction.eContents()) {
            switch (interactionObj.eClass().getName()) {
              case "Message":
                org.eclipse.uml2.uml.Message msg = (org.eclipse.uml2.uml.Message) interactionObj;
                MessageOccurrenceSpecification senderSpecification = (MessageOccurrenceSpecificationImpl) msg
                    .getSendEvent();
                MessageSort umlMessageSort = msg.getMessageSort();
                MessageType messageType = convertToMessageType(umlMessageSort);
                String senderName = senderSpecification.getCovereds().get(0).getName();
                LifeLine sender = getLifeLineFromName(senderName);
                MessageOccurrenceSpecification receiverSpecification = (MessageOccurrenceSpecificationImpl) msg
                    .getReceiveEvent();
                String receiverName = receiverSpecification.getCovereds().get(0).getName();
                LifeLine receiver = getLifeLineFromName(receiverName);
                result.addMessage(new Message(sender, receiver, msg.getName(),
                    messageType));
                result.addLifeLine(sender);
                result.addLifeLine(receiver);
                break;
              case "BehaviorExecutionSpecification":
                InteractionFragment interactionFragment = (BehaviorExecutionSpecification) interactionObj;
                if (interactionFragment.getName() != null) {
                  LifeLine lifeLine = getLifeLineFromName(
                      interactionFragment.getCovereds().get(0).getName());
                  result.addMessage(new Message(lifeLine, lifeLine, interactionFragment.getName(),
                      MessageType.SELF_EXECUTION));
                  result.addLifeLine(lifeLine);
                }
                break;
            }
          }
      }
    }
    return getOrderedSequenceDiagram(result, messageOrder);
  }

  private SequenceDiagram getOrderedSequenceDiagram(SequenceDiagram sd, List<String> messageOrder) {
    SequenceDiagram result = new SequenceDiagram();
    result.setLifeLines(sd.getLifeLines());
    for (String messageName : messageOrder) {
      String operationName = messageName;
      String className = null;
      if(messageName.contains(":")){
        String[] messageSplit = messageName.split(":");
        className = messageSplit[0];
        operationName = messageSplit[1];
      }
      Message messageToSet = null;
      for (Message currentMessage : sd.getMessages()) {
        if (currentMessage.getName().equals(operationName)) {
          if(className == null) {
            messageToSet = currentMessage;
          }else{
            if(className.equals(currentMessage.getDestination().getClassName())){
              messageToSet = currentMessage;
            }

          }
        }
      }
      if (messageToSet == null) {
        throw new IllegalArgumentException(
            "Message " + messageName + " not found in Sequence Diagram");
      }
      result.addMessage(messageToSet);
    }
    return result;
  }

  private MessageType convertToMessageType(MessageSort sort) {
    if (sort.getValue() == MessageSort.ASYNCH_CALL) {
      return MessageType.ASYNC_CALL;
    } else if (sort.getValue() == (MessageSort.SYNCH_CALL)) {
      return MessageType.SYNC_CALL;
    } else if (sort.getValue() == MessageSort.REPLY) {
      return MessageType.SYNC_RESPONSE;
    }
    throw new IllegalArgumentException("Illegal/Unknown Message Sort:" + sort.getName());
  }

  private LifeLine getLifeLineFromName(String name) {
    LifeLine result;
    String[] names = name.split(":");
    if (names.length == 2) {
      if (names[0].equals("")) {
        names[0] = null;
      }
      result = new LifeLine(names[0], names[1]);
    } else {
      throw new IllegalArgumentException("LifeLine " + name + " has wrong format");
    }
    return result;
  }

  @Override
  public void validateDynamicSequenceDiagram(long projectId, SequenceDiagram sd,
      UmlRequirements requirements) throws ServiceException {
    ModelVerifier verifier = new ModelVerifier();
    verifier.verifyStaticProperties(sd, requirements);
    verifier.verifyDynamicBehavior(sd, requirements);
  }

  @Override
  public void validateMessageOrderForSequenceDiagram(List<String> messageOrder,
      SequenceDiagram sd) {
    List<Message> messagesInSD = sd.getMessages();
    if (messagesInSD.size() != messageOrder.size()) {
      throw new IllegalArgumentException(
          "Number of found Messages in Sequence Diagram and Messages specified in request do not match!");
    }
    for (Message currentMessage : messagesInSD) {
      if (!messageOrder.contains(currentMessage.getName())) {
        throw new IllegalArgumentException(
            "Order of Message " + currentMessage.getName() + " is not specified!");
      }
    }
  }

  public class ModelVerifier {

    public void verifyStaticProperties(SequenceDiagram sd, UmlRequirements requirements)
        throws ServiceException {
      Long classModelId = requirements.getIdForModelType(UmlModelType.CLASS_MODEL);
      GetOperationsRequest operationsRequest = new GetOperationsRequest();
      operationsRequest.setProjectId(requirements.getProjectId());
      ;
      GetEntitiesRequest entitiesRequest = new GetEntitiesRequest();
      entitiesRequest.setModelId(classModelId);
      entitiesRequest.setIncludeTypeSpecificDetails(false);
      GetRelationsRequest relationsRequest = new GetRelationsRequest();
      relationsRequest.setModelId(classModelId);
      relationsRequest.setIncludeTypeSpecificDetails(false);
      // verify that all lifelines exist in cd
      for (LifeLine cl : DtoExtractor.extractLifeLinesFromSequenceDiagram(sd)) {
        // todo: verify that result size of getEntities is not larger than 1
        entitiesRequest.setModelId(classModelId);
        entitiesRequest.setIncludeTypeSpecificDetails(false);
        entitiesRequest
            .setEntityNameRegex(
                cl.getClassName());
        GetEntitiesResult entitiesResult = requirementsService.getEntities(entitiesRequest);
        if (entitiesResult.getEntities().isEmpty()) {
          throw new ServiceException(
              "LifeLine " + cl.getName() + " does not correspond to any Class in Class Diagram");
        }
      }

      // verify that all message calls are allowed according to the cd
      for (Message message : sd.getMessages()) {
        if (message.getType() == MessageType.SYNC_CALL
            || message.getType() == MessageType.ASYNC_CALL) {
          // verify that connection between sender and receiver of message exists in class diagram
          // todo: verify that result size of getRelations is not larger than 1
          // todo: also handle super-types of destination class
          String sourceClassName = message.getSource().getClassName();
          List<String> possibleTargetClassNames = getSuperClasses(
              message.getDestination().getClassName(), classModelId);
          relationsRequest.setModelId(classModelId);
          for (String targetClassName : possibleTargetClassNames) {
            relationsRequest.setIncludeTypeSpecificDetails(false);
            relationsRequest.setSourceEntityNameRegex(sourceClassName);
            relationsRequest.setTargetEntityNameRegex(targetClassName);
            List<RelationInfo> foundRelations = requirementsService.getRelations(relationsRequest)
                .getRelations();
            if (foundRelations.isEmpty()) {
              relationsRequest.setSourceEntityNameRegex(targetClassName);
              relationsRequest.setTargetEntityNameRegex(sourceClassName);
              foundRelations = requirementsService.getRelations(relationsRequest).getRelations();
              if (foundRelations.isEmpty()) {
                if (!checkMessageAllowedByOperationReturnType(classModelId,
                    message.getSource().getClassName(), message.getDestination().getClassName())) {
                  throw new ServiceException(
                      "Call of " + message.getName() + " in " + message.getDestination()
                          .getClassName() +
                          " from " + message.getSource().getClassName()
                          + " is not allowed");
                }
              }
            }
          }

          // verify that name of message exists as method of receiver in class diagram
          operationsRequest.setProjectId(requirements.getProjectId());
          operationsRequest.setEntityNameRegex(message.getDestination().getClassName());
          operationsRequest.setOperationNameRegex(message.getName());
          List<ClassOperation> receiverOperations = requirementsService
              .getOperations(operationsRequest)
              .getOperations();
          List<String> superClasses = getSuperClasses(message.getDestination().getClassName(),
              classModelId);
          for (String superClassName : superClasses) {
            operationsRequest.setEntityNameRegex(superClassName);
            receiverOperations
                .addAll(requirementsService.getOperations(operationsRequest).getOperations());
          }
          ClassOperation receiverOperation = null;
          if (receiverOperations.isEmpty()) {
            throw new ServiceException(
                "There must be at least one operation called " + message.getName() + ""
                    + " in Class " + message.getDestination().getClassName()
                    + " or its superclasses. Actually, there are " + receiverOperations.size()
                    + ".");
          } else {
            receiverOperation = receiverOperations.get(0);
          }
          // verify that corresponding method is defined as "not private" in class diagram
          if (receiverOperation.getVisibility() == VisibilityType.PRIVATE) {
            throw new ServiceException(
                "Visibility of Method " + receiverOperation.getName() + " is private"
                    + "in Class " + message.getDestination().getClassName()
                    + " and therefore cannot be called by"
                    + " Class " + message.getSource().getClassName() + ".");
          } // todo: implement logic for protected visibility

        }
      }
    }

    private boolean checkMessageAllowedByOperationReturnType(long classModelId,
        String sourceClassName, String targetClassName) {
      GetAllRequest allRequest = new GetAllRequest();
      allRequest.setModelId(classModelId);
      allRequest.setSourceEntityNameRegex(sourceClassName);
      List<ClassModelEntityInfo> entities = new LinkedList<>();
      List<ClassModelRelationInfo> relations = requirementsService.getAllForClassModel(allRequest)
          .getRelations();
      for (RelationInfo rel : relations) {
        entities.add((ClassModelEntityInfo) rel.getTarget());
      }
      allRequest.setSourceEntityNameRegex(null);
      allRequest.setTargetEntityNameRegex(sourceClassName);
      relations = requirementsService.getAllForClassModel(allRequest).getRelations();
      for (RelationInfo rel : relations) {
        entities.add((ClassModelEntityInfo) rel.getSource());
      }
      List<String> allowedClasses = new LinkedList<>(); // todo: this should actually be a List<EntityInfo> to also differentiate classes with same name but different package
      allowedClasses.add(targetClassName);
      allowedClasses.addAll(getSuperClasses(targetClassName, classModelId));
      for (ClassModelEntityInfo classInfo : entities) {
        List<ClassOperation> operationsInClass = classInfo.getOperations();
        for (ClassOperation op : operationsInClass) {
          if (op.getReturnType() != null) {
            String currentOperationReturnTypeName = op.getReturnType().getName();
            if (allowedClasses.contains(currentOperationReturnTypeName)) {
              return true;
            }
          }
        }
      }
      return false;
    }

    private List<String> getSuperClasses(String subEntityName, long modelId) {
      List<String> result = new LinkedList<>();
      GetRelationsRequest request = new GetRelationsRequest();
      request.setIncludeTypeSpecificDetails(true);
      request.setModelId(modelId);
      request.setSourceEntityNameRegex(subEntityName);
      GetRelationsResultClassModel response = requirementsService
          .getRelationsForClassModel(request);
      for (RelationInfo relation : response.getRelations()) {
        if (relation instanceof ClassModelInterfaceRealizationRelationInfo
            || relation instanceof ClassModelGeneralizationRelationInfo) {
          result.add(relation.getTarget().getName());
          result.addAll(getSuperClasses(relation.getTarget().getName(),
              modelId));  // recursively add super-superclasses
        }
      }
      return result;
    }

    private List<Action> getAllActions(UmlRequirements requirements){
      GetActionsRequest actionsRequest = new GetActionsRequest();
      actionsRequest.setProjectId(requirements.getProjectId());
      try {
        return requirementsService.getActions(actionsRequest)
            .getActions();
      }catch (ServiceException ex){
        throw new IllegalArgumentException("Could not fetch actions of Activity Diagram in Project with id " + requirements.getProjectId());
      }
    }

    public void verifyDynamicBehavior(SequenceDiagram sd, UmlRequirements requirements)
        throws ServiceException {
      List<ParallelExecutionPath<Action>> parallelExecutionPaths = new LinkedList<>();
      List<Action> initialActions = getAllActions(requirements);
      GetActionsRequest actionsRequest = new GetActionsRequest();
      actionsRequest.setProjectId(requirements.getProjectId());

      ParallelExecutionPath initialPath = new ParallelExecutionPath(initialActions);
      parallelExecutionPaths.add(initialPath);
      for (Message message : sd.getMessages()) {
        // todo: verify that result size of getActions is not larger than 1
        // .*\.stop(.*)
        if (message.getType() == MessageType.SYNC_CALL
            || message.getType() == MessageType.ASYNC_CALL
            || message.getType() == MessageType.SELF_EXECUTION) {
          actionsRequest.setActionNameRegex(".*\\:.*\\." + message.getName() + "\\(.*\\)");
          Action currentAction;
          try {
            currentAction = requirementsService.getActions(actionsRequest).getActions().get(0);
          } catch (IndexOutOfBoundsException ex) {
            throw new ServiceException(
                "no action found for pattern " + actionsRequest.getActionNameRegex() +
                    " (message name was " + message.getName() + ")");
          }
          System.out.println("ActionNameFilterRegex: " + actionsRequest.getActionNameRegex());
          System.out.println(
              "#####################################################################");
          System.out.println("possible actions: " + parallelExecutionPaths);
          boolean actionAllowed = false;
          for(ParallelExecutionPath<Action> currentPath : parallelExecutionPaths){
            if(executionPathContainsAction(currentPath, currentAction, requirements)){
              actionAllowed = true;
            }
          }
          System.out.print("current action: ");
          System.out.println(currentAction.getName());
          if (!actionAllowed) {
            throw new ServiceException("Action " + currentAction.getName() + " not allowed!");
          }
          parallelExecutionPaths = updateAllowedExecutionPaths(parallelExecutionPaths, currentAction, requirements);

          // if error, then further analyse whether the current message
          // represents and unknown activity, or if there's an activity left out

          // State Machine Diagrams: https://www.uml-diagrams.org/state-machine-diagrams.html

          // Behavioral State Machine Diagrams: https://www.uml-diagrams.org/state-machine-diagrams.html#behavioral-state-machine

          // Interation Overview Diagrams: https://www.uml-diagrams.org/interaction-overview-diagrams.html

          // Existing Sequence Diagram
          // is the dynamically created diagram valid according to the allowed paths defined in the static SD?

        }
      }
    }

    private boolean executionPathContainsAction(ParallelExecutionPath<Action> currentPath, Action action, UmlRequirements requirements){
      if(isOperationAllowed(currentPath.getAllowedActionsInThisPath(), action, requirements)){
        return true;
      }
      boolean actionIsContainedBelow = false;
      if(currentPath.getFurtherPaths() != null){
        for(ParallelExecutionPath<Action> temp : currentPath.getFurtherPaths()){
          if(executionPathContainsAction(temp, action, requirements)){
            actionIsContainedBelow = true;
          }
        }
      }
      return actionIsContainedBelow;
    }

    private boolean isOperationAllowed(List<Action> possibleNextActions, Action currentAction, UmlRequirements requirements){
      if (possibleNextActions.contains(currentAction)) {
        return true;
      }
      String currentString = currentAction.getName().split("\\:")[1];
      String[] currentStrings = currentString.split("\\.");
      String currentClass = currentStrings[0];
      String currentOperation = currentStrings[1];
      for(Action tempAction : possibleNextActions){
        String tempString = tempAction.getName().split("\\:")[1];
        String[] relevantStrings = tempString.split("\\.");
        String tempClass = relevantStrings[0];
        String tempOperation = relevantStrings[1];
        if(tempClass.equals(currentClass) && tempOperation.equals(currentOperation)){
          return true;
        }
      }

        return false;
    }

    private List<ParallelExecutionPath<Action>> updateAllowedExecutionPaths(List<ParallelExecutionPath<Action>> currentPaths, Action currentAction, UmlRequirements requirements) throws ServiceException{
      List<ParallelExecutionPath<Action>> result = new LinkedList<>();
      boolean listContainsMultiplePaths = currentPaths.size() != 1;

      for(ParallelExecutionPath<Action> tempPath : currentPaths){
        if(isOperationAllowed(tempPath.getAllowedActionsInThisPath(), currentAction, requirements)){
          // current path contains currentAction
          ParallelExecutionPath<Action> newPath = getPossibleNextActions(currentAction, requirements, listContainsMultiplePaths);
          if(newPath != null){  // if newPath is null, flow end is reached, which means that this path should be ignored
            tempPath = newPath;
          }
        }else if(tempPath.getFurtherPaths() != null){
          // recursively check (and update) furtherPaths
          List<ParallelExecutionPath<Action>> furtherPaths = updateAllowedExecutionPaths(tempPath.getFurtherPaths(), currentAction, requirements);
          tempPath.setFurtherPaths(furtherPaths);
        }
        result.add(tempPath);
      }
      return result;
    }

    private ParallelExecutionPath<Action> updateAllowedExecutionPaths(ParallelExecutionPath<Action> currentPath, Action currentAction, UmlRequirements requirements){
      ParallelExecutionPath<Action> result = currentPath;
      if(isOperationAllowed(result.getAllowedActionsInThisPath(), currentAction, requirements)){
        // current path contains currentAction

      }else{
        if(result.getFurtherPaths() != null) {

          List<ParallelExecutionPath<Action>> furtherPaths = new LinkedList<>();
          for (ParallelExecutionPath<Action> currentFurtherPath : result.getFurtherPaths()) {
            furtherPaths
                .add(updateAllowedExecutionPaths(currentFurtherPath, currentAction, requirements));
          }
          result.setFurtherPaths(furtherPaths);
        }
      }
      return result;
    }

    private ParallelExecutionPath<Action> getPossibleNextActions(Action action, UmlRequirements requirements, boolean hasFurtherParallelExecutionPaths)
        throws ServiceException {
      List<Action> foundActions = new LinkedList<>();
      // get next node
      GetRelationsRequest getRelationsRequest = new GetRelationsRequest();
      getRelationsRequest.setModelId(requirements.getIdForModelType(UmlModelType.ACTIVITY_MODEL));
      getRelationsRequest
          .setSourceEntityNameRegex(convertActionDtoToNameFilterRegex(action.getName()));
      RelationInfo outgoingRelationFromCurrentAction;
      try {
        outgoingRelationFromCurrentAction = requirementsService
            .getRelations(getRelationsRequest).getRelations()
            .get(0); // size must be one, because each action can only have one outgoing relation
      } catch (IndexOutOfBoundsException ex) {
        throw new ServiceException(
            "Dead End found in Activity Diagram: Action " + getRelationsRequest
                .getSourceEntityNameRegex() + " has no outgoig relations!");
      }
      EntityInfo outgoingEntity = outgoingRelationFromCurrentAction.getTarget();
      // get action entity for outgoing entity
      GetActionsRequest getActionsRequest = new GetActionsRequest();
      getActionsRequest.setProjectId(requirements.getProjectId());
      getActionsRequest
          .setActionNameRegex(convertActionDtoToNameFilterRegex(outgoingEntity.getName()));
      if (outgoingEntity.getPackage() != null) {
        getActionsRequest.setPackageNameRegex(outgoingEntity.getPackage().getName());
      }
      GetActionsResult getActionsResult = requirementsService.getActions(getActionsRequest);
      if (!getActionsResult.getActions().isEmpty()) { // if outgoing entity is an action, return this action
        if (getActionsResult.getActions().size() > 1) {
          throw new ServiceException(
              "More than one occurrence found for outgoing action " + outgoingEntity.getName());
        }
        Action foundAction = getActionsResult.getActions().get(0);
        // iteratively continue if found action does not map to code
        if (!foundAction.getName().contains(":")) {
          return getPossibleNextActions(foundAction, requirements, hasFurtherParallelExecutionPaths);
        }
        foundActions.add(foundAction);
        return new ParallelExecutionPath<Action>(foundActions);
      }
      // if outgoing entity is a node, this node has to be found and further treated
      GetNodesRequest getNodesRequest = new GetNodesRequest();
      getNodesRequest.setProjectId(requirements.getProjectId());
      String outgoingNameRegex = outgoingEntity.getName();
      outgoingNameRegex = outgoingNameRegex.replace("?", ".");
      getNodesRequest.setNodeNameRegex(outgoingNameRegex);
      if (outgoingEntity.getPackage() != null) {
        getNodesRequest.setPackageNameRegex(outgoingEntity.getPackage().getName());
      }
      GetNodesResult getNodesResult = requirementsService.getNodes(getNodesRequest);
      if (!getNodesResult.getNodes().isEmpty()) {
        if (getNodesResult.getNodes().size() > 1) {
          throw new ServiceException(
              "More than one occurrence found for outgoing node " + outgoingEntity.getName());
        }
        Node outgoingNode = getNodesResult.getNodes().get(0);
        return getNextActionsForNode(outgoingNode, requirements, hasFurtherParallelExecutionPaths);
      }
      throw new ServiceException("No occurrence found for action or node " + outgoingEntity.getName());
    }

    private String convertActionDtoToNameFilterRegex(String actionName) {
      String result = actionName;
      result = result.replace(".", "\\.");
      result = result.replaceAll("\\(.*\\)", "\\\\(.*\\\\)");
      return result;
    }

    private ParallelExecutionPath<Action> getNextActionsForNode(Node node, UmlRequirements requirements, boolean hasFurtherParallelExecutionPaths)
        throws ServiceException {
      // todo: implement me
      // handle decision split and decision join node
      if (node.getType() == NodeType.DECISION_SPLIT || node.getType() == NodeType.DECISION_MERGE) {
        List<Action> foundActions = new LinkedList<>();
        List<ParallelExecutionPath<Action>> foundFurtherPaths = new LinkedList<>();
        GetRelationsRequest getRelationsRequest = new GetRelationsRequest();
        getRelationsRequest.setModelId(requirements.getIdForModelType(UmlModelType.ACTIVITY_MODEL));
        getRelationsRequest.setSourceEntityNameRegex(node.getName() + ".?");
        getRelationsRequest.setIncludeTypeSpecificDetails(false);
        GetRelationsResult getRelationsResult = requirementsService
            .getRelations(getRelationsRequest);
        for (RelationInfo currentRelation : getRelationsResult.getRelations()) {
          EntityInfo currentEntity = currentRelation.getTarget();
          if (!currentEntity.getName().contains(":")) { // ignore current entity if it does not contain a method reference
            Node currentNode = getNodeFromEntity(currentEntity, requirements);
            if (currentNode != null) {
              ParallelExecutionPath<Action> furtherPath = getNextActionsForNode(currentNode, requirements, hasFurtherParallelExecutionPaths);
              if(furtherPath.getAllowedActionsInThisPath() != null){
                foundActions.addAll(furtherPath.getAllowedActionsInThisPath());
              }
              if(furtherPath.getFurtherPaths() != null) {
                foundFurtherPaths.addAll(furtherPath.getFurtherPaths());
              }
            }
          } else {
            Action currentAction = new Action(currentEntity.getName(), currentEntity.getPackage(),
                ActionType.OPAQUE_ACTION);
            foundActions.add(currentAction);
          }
        }
        return new ParallelExecutionPath<>(foundActions, foundFurtherPaths);
      }
      // handle end nodes
      if(node.getType() == NodeType.ACTIVITY_FINAL){
        // get first available node after start node
        GetNodesRequest getNodesRequest = new GetNodesRequest();
        getNodesRequest.setProjectId(requirements.getProjectId());
        getNodesRequest.setNodeType(NodeType.INITIAL);
        Node initialNode = requirementsService.getNodes(getNodesRequest).getNodes().get(0);
        Action initialNodeAsAction = new Action(initialNode.getName(), new Package("", null), ActionType.OPAQUE_ACTION);
        return getPossibleNextActions(initialNodeAsAction, requirements, hasFurtherParallelExecutionPaths);
      }

      if(node.getType() == NodeType.FLOW_FINAL){
        return new ParallelExecutionPath<>(new LinkedList<>());
      }

      // handle fork split node
      if(node.getType() == NodeType.FORK){
        // get outgoing actions
        ParallelExecutionPath<Action> result = new ParallelExecutionPath<>(new LinkedList<Action>());
        List<ParallelExecutionPath<Action>> furtherPaths = new LinkedList<>();
        GetRelationsRequest getRelationsRequest = new GetRelationsRequest();
        getRelationsRequest.setModelId(requirements.getIdForModelType(UmlModelType.ACTIVITY_MODEL));
        getRelationsRequest.setSourceEntityNameRegex(node.getName() + ".?");
        getRelationsRequest.setIncludeTypeSpecificDetails(false);
        GetRelationsResult getRelationsResult = requirementsService
            .getRelations(getRelationsRequest);
        for (RelationInfo currentRelation : getRelationsResult.getRelations()) {
          // create a new parallel execution path for each outgoing relation
          EntityInfo currentEntity = currentRelation.getTarget();
          if (!currentEntity.getName().contains(":")) {
            // current action does not contain a method reference
            Node currentNode = getNodeFromEntity(currentEntity, requirements);
            if (currentNode != null) {
              // current action is a node
              furtherPaths.add(getNextActionsForNode(currentNode, requirements, hasFurtherParallelExecutionPaths));
            }
          } else {
            Action currentAction = new Action(currentEntity.getName(), currentEntity.getPackage(),
                ActionType.OPAQUE_ACTION);
            List<Action> allowedActions = new LinkedList<>();
            allowedActions.add(currentAction);
            furtherPaths.add(new ParallelExecutionPath<Action>(allowedActions));
          }
        }
        result.setFurtherPaths(furtherPaths);
        return result;
      }

      // handle synchronization (fork join) node

      // merge nodes
      // throw new ServiceException("Handling of Node Type " + node.getType() + " for node " +
       //   node.getName() + " not implemented yet.");
      return null;
    }

    private Node getNodeFromEntity(EntityInfo entity, UmlRequirements requirements)
        throws ServiceException {
      GetNodesRequest getNodesRequest = new GetNodesRequest();
      getNodesRequest.setProjectId(requirements.getProjectId());
      getNodesRequest.setNodeNameRegex(entity.getName() + "(\\?)?");
      if (entity.getPackage() != null) {
        getNodesRequest.setPackageNameRegex(entity.getPackage().getName());
      }
      GetNodesResult getNodesResult = requirementsService.getNodes(getNodesRequest);
      if (!getNodesResult.getNodes().isEmpty()) {
        if (getNodesResult.getNodes().size() > 1) {
          throw new ServiceException(
              "More than one occurrence found for outgoing node " + entity.getName());
        }
        return getNodesResult.getNodes().get(0);
      }
      return null;
    }
  }
}
