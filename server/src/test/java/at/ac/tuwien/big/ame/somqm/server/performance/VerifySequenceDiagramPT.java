package at.ac.tuwien.big.ame.somqm.server.performance;


import at.ac.tuwien.big.ame.dyncs.server.dto.UmlRequirements;
import at.ac.tuwien.big.ame.dyncs.server.dto.ValidateUmlSequenceModelRequest;
import at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.dyncs.server.rest.ModelValidationController;
import at.ac.tuwien.big.ame.dyncs.server.service.RequirementsService;
import at.ac.tuwien.big.ame.dyncs.server.service.UmlModelValidationService;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.SomqmRequirementsServiceImpl;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.UmlModelValidationServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.dto.project.CreateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.CreateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import at.ac.tuwien.big.ame.somqm.server.stub.ModelControllerStub;
import at.ac.tuwien.big.ame.somqm.server.stub.ProjectControllerStub;
import at.ac.tuwien.big.ame.somqm.server.util.TestHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringJUnit4ClassRunner.class)
public class VerifySequenceDiagramPT {

  private static final long PROJECT_ID = 1;
  private static final long CLASS_DIAGRAM_ID = 1;
  private static final long ACTIVITY_DIAGRAM_ID = 2;

  private static final String PATH_TEST_DATA = "C:\\Users\\danie\\Desktop\\Git\\diplomarbeit\\Implementation\\CheckingService\\server\\src\\test\\resources\\performance-models\\";
  private static final String PATH_BASIC_CD = "basic\\basic-class-diagram.uml";
  private static final String PATH_BASIC_AD = "basic\\basic-activity-diagram.uml";
  private static final String PATH_BASIC_SD = "basic\\basic-sequence-diagram.uml";
  private static final String PATH_BASIC_SD_SEQUENCE_ORDER = "basic/basic-sequence-diagram-message-order.txt";
  private static UmlRequirements requirements;

  private static ProjectControllerStub projectController;
  private static ModelControllerStub modelController;
  private static ModelValidationController modelValidationController;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private FileWriter csvWriter;
  private FilePersistanceHandler filePersistanceHandler;

  @BeforeClass
  public static void beforeAll() {
    projectController = new ProjectControllerStub();
    modelController = new ModelControllerStub();
  }

  public static void storeRequirements(
      Map<at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType, String> modelContents)
      {
    for (Map.Entry<at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType, String> modelContent : modelContents
        .entrySet()) {
      CreateUmlModelRequest createModelRequest = new CreateUmlModelRequest();
      createModelRequest.setProjectId(requirements.getProjectId());
      createModelRequest.setName("performance test model");
      createModelRequest.setDescription("This model was created during performance tests");
      long modelId = modelController.create(createModelRequest);
      requirements.setIdForType(modelContent.getKey(), modelId);
      modelController.updateContent(modelId, modelContent.getValue());
    }
  }

  @Before
  public void beforeTest() {
    CreateProjectRequest request = new CreateProjectRequest();
    request.setName("Performance Test Project");
    request.setDescription("Whatever.");
    long projectId = projectController.create(request);
    requirements = new UmlRequirements(projectId);
    RequirementsService requirementsService = new SomqmRequirementsServiceImpl();
    UmlModelValidationService modelValidationService = new UmlModelValidationServiceImpl(
        requirementsService);
    modelValidationController = new ModelValidationController(modelValidationService,
        requirementsService);
    filePersistanceHandler = new FilePersistanceHandler();
  }

  @After
  public void cleanUpTest() {
    projectController.delete(requirements.getProjectId());
    requirements = null;
  }

  private List<String> extractMessageSequenceFromFile(String fileName) {
    List<String> result = new LinkedList<>();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(fileName));
    }catch (FileNotFoundException ex){
      throw new IllegalArgumentException("Provided filename not found: " + fileName);
    }
    String[] messages = null;
    try {
      messages = reader.readLine().split(";");
    }catch(IOException ex){
      throw new IllegalArgumentException("Could not read from file " + fileName);
    }
    for(int i = 0; i < messages.length; i++){
      result.add(messages[i]);
    }
    return result;
  }

  @Test
  public void verifySD_Performance_SDVaryNumberOfMessages()
      throws at.ac.tuwien.big.ame.dyncs.server.service.ServiceException {
    // Arrange
    String fileName = "sd_varynumberofmessages_equallydistributed.csv";
    String modelFolderPath = "sd_varynumberofmessages/";
    try {
      filePersistanceHandler.createCsv(fileName, "Number of Classes");
    } catch (IOException ex) {
      Assert.fail("CSV-File could not be created. \n" + ex.getMessage());
    }
    RequirementsManager requirementsManager = new RequirementsManager();
    requirementsManager.storeBasicRequirements();
    // Act
    for (int i = 5; i <= 50; i += 5) {
      System.out.println("************************************************************");
      System.out.println("New Test Case Started");
      System.out.println("************************************************************");
      String cd_path = modelFolderPath + "CD_cd-varyentities-equaldistribution-" + 50 + "entities.uml";
      String ad_path = modelFolderPath + "AD_cd-varyentities-equaldistribution-" + 50 + "entities.uml";
      String sd_path = modelFolderPath + "SD_cd-varyentities-equaldistribution-" + i + "entities.uml";
      String message_order_path = modelFolderPath + i + "classes-sequence-diagram-message-order.txt";
      requirementsManager.updateClassDiagram(cd_path);
      requirementsManager.updateActivityDiagram(ad_path);
      System.out.println("Current SD: " + sd_path);
      float duration = new SequenceDiagramValidator()
          .validate(sd_path, message_order_path);
      try {
        filePersistanceHandler.addLineToCsv("" + duration, "" + i);
      } catch (IOException ex) {
        Assert.fail("The following line could not be added to the CSV-File:" + i + ";"
            + duration);
      }
    }
    // Finalize
    try {
      filePersistanceHandler.persistCsv();
    } catch (IOException ex) {
      Assert.fail("CSV-file could not be persisted. \n" + ex.getMessage());
    }
  }

  @Test
  public void verifySD_Performance_CDVaryNumberOfEntitiesEqualDistribution()
      throws at.ac.tuwien.big.ame.dyncs.server.service.ServiceException {
    // Arrange
    String fileName = "cd_varynumberofentities_equallydistributed.csv";
    try {
      filePersistanceHandler.createCsv(fileName, "Number of Classes");
    } catch (IOException ex) {
      Assert.fail("CSV-File could not be created. \n" + ex.getMessage());
    }
    RequirementsManager requirementsManager = new RequirementsManager();
    requirementsManager.storeBasicRequirements();
    // Act
    for (int i = 10; i <= 50; i += 5) {
      System.out.println("************************************************************");
      System.out.println("New Test Case Started");
      System.out.println("************************************************************");
      String cd_path = "cd_varynumberofentities_equaldistribution/CD_cd-varyentities-equaldistribution-" + i + "entities.uml";
      String ad_path = "cd_varynumberofentities_equaldistribution/AD_cd-varyentities-equaldistribution-" + i + "entities.uml";
      String sd_path = "cd_varynumberofentities_equaldistribution/SD_cd-varyentities-equaldistribution-" + i + "entities.uml";
      String message_order_path = "cd_varynumberofentities_equaldistribution/" + i + "classes-sequence-diagram-message-order.txt";
      requirementsManager.updateClassDiagram(cd_path);
      requirementsManager.updateActivityDiagram(ad_path);
      System.out.println("Current SD: " + sd_path);
      float duration = new SequenceDiagramValidator()
          .validate(sd_path, message_order_path);
      try {
        filePersistanceHandler.addLineToCsv("" + duration, "" + i);
      } catch (IOException ex) {
        Assert.fail("The following line could not be added to the CSV-File:" + i + ";"
            + duration);
      }
    }
    // Finalize
    try {
      filePersistanceHandler.persistCsv();
    } catch (IOException ex) {
      Assert.fail("CSV-file could not be persisted. \n" + ex.getMessage());
    }
  }

  @Test
  public void verifySD_Performance_CDVaryNumberOfEntitiesRandomDistribution()
      throws at.ac.tuwien.big.ame.dyncs.server.service.ServiceException {
    // Arrange
    String fileName = "cd_varynumberofentities_equallydistributed";
    try {
      filePersistanceHandler.createCsv(fileName, "Number of Classes");
    } catch (IOException ex) {
      Assert.fail("CSV-File could not be created. \n" + ex.getMessage());
    }
    Map<String, String> testCaseInformation = new HashMap<>();
    // todo: set relevant file names
    RequirementsManager requirementsManager = new RequirementsManager();
    requirementsManager.storeBasicRequirements();
    // Act
    for (Map.Entry<String, String> currentInformation : testCaseInformation.entrySet()) {
      requirementsManager.updateClassDiagram(currentInformation.getKey());
      float duration = new SequenceDiagramValidator()
          .validate(PATH_BASIC_SD, PATH_BASIC_SD_SEQUENCE_ORDER);
      try {
        filePersistanceHandler.addLineToCsv("" + duration, currentInformation.getValue());
      } catch (IOException ex) {
        Assert.fail("The following line could not be added to the CSV-File:" + duration + ","
            + currentInformation.getValue());
      }
    }

    // Finalize
    try {
      filePersistanceHandler.persistCsv();
    } catch (IOException ex) {
      Assert.fail("CSV-file could not be persisted. \n" + ex.getMessage());
    }
  }

  public class FilePersistanceHandler{
    public void addLineToCsv(String time, String action) throws IOException {
      csvWriter.append(action + ";" + time);
      csvWriter.append("\n");
    }

    public void persistCsv() throws IOException {
      csvWriter.flush();
      csvWriter.close();
    }

    public void createCsv(String fileName, String variableName) throws IOException {
      csvWriter = new FileWriter("./src/test/resources/performance-results/" + fileName);
      csvWriter.append(variableName);
      csvWriter.append(";");
      csvWriter.append("Time");
      csvWriter.append("\n");
    }
  }

  public class RequirementsManager {

    public void storeBasicRequirements() throws
        at.ac.tuwien.big.ame.dyncs.server.service.ServiceException {

      // set up requirements in somqm
      Map<at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType, String> requirements = new HashMap<>();
      //byte[] classModelContent = TestHelper.loadPerformanceTestDataContent(PATH_BASIC_CD);
      requirements.put(UmlModelType.CLASS_MODEL, PATH_TEST_DATA + PATH_BASIC_CD);
      //byte[] activityModelContent = TestHelper.loadPerformanceTestDataContent(classModelFileName);
      requirements.put(UmlModelType.ACTIVITY_MODEL, PATH_TEST_DATA + PATH_BASIC_AD);
      try {
        storeRequirements(requirements);
      } catch (Exception ex) {
        throw new at.ac.tuwien.big.ame.dyncs.server.service.ServiceException(
            ex.getLocalizedMessage());
      }
    }

    public void updateClassDiagram(String pathToClassDiagram) {
      long classModelId = requirements.getIdForModelType(UmlModelType.CLASS_MODEL);
      modelController.updateContent(classModelId, PATH_TEST_DATA + pathToClassDiagram);
    }

    public void updateActivityDiagram(String pathToActivityDiagram){
      long classModelId = requirements.getIdForModelType(UmlModelType.ACTIVITY_MODEL);
      modelController.updateContent(classModelId, PATH_TEST_DATA + pathToActivityDiagram);
    }

  }

  public class SequenceDiagramValidator {

    public float validate(String pathToSequenceDiagram, String pathToSequenceOrder)
        throws at.ac.tuwien.big.ame.dyncs.server.service.ServiceException {
      // verify SD using DynCS
      byte[] content = TestHelper.loadPerformanceTestDataContent(pathToSequenceDiagram);
      ValidateUmlSequenceModelRequest validateUmlSequenceModelRequest = new ValidateUmlSequenceModelRequest();
      validateUmlSequenceModelRequest.setProjectId(requirements.getProjectId());
      validateUmlSequenceModelRequest
          .setOrderOfSequences(extractMessageSequenceFromFile(PATH_TEST_DATA + pathToSequenceOrder));
      MultipartFile modelContent = new MockMultipartFile("name", content);
      // Act
      long startTime = System.nanoTime();
      modelValidationController
          .validateUmlSequenceModel(validateUmlSequenceModelRequest, modelContent);
      long endTime = System.nanoTime();
      float duration = endTime - startTime;
      return duration / 1000000000;
    }
  }


}
