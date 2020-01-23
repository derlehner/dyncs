package at.ac.tuwien.big.ame.somqm.server.integration;


import at.ac.tuwien.big.ame.dyncs.server.dto.UmlRequirements;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.SequenceDiagram;
import at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.dyncs.server.service.ServiceException;
import at.ac.tuwien.big.ame.dyncs.server.service.UmlModelValidationService;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.SomqmRequirementsServiceImpl;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.UmlModelValidationServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.util.TestHelper;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class VerifySequenceDiagramIT {

  private static final long PROJECT_ID = 1;
  private static final long CLASS_DIAGRAM_ID = 1;
  private static final long ACTIVITY_DIAGRAM_ID = 2;

  private UmlModelValidationService modelValidationService;
  private UmlRequirements umlRequirements;

  @DataProvider
  public static Object[][] activityDiagramPositive() {
    return new Object[][]{
        {"da-sequence-ad-activityend-ok.xmi", Arrays.asList("stop", "return", "startNavigation", "return")},
        {"da-sequence-ad-basic-ok.xmi", Arrays.asList("invoke", "return", "getRoute", "return", "start", "return")},
        {"da-sequence-ad-decisionjoin-ok.xmi", Arrays.asList("speedUp", "return", "adaptSpeed")},
        {"da-sequence-ad-decisionjoin-ok2.xmi", Arrays.asList("brake", "return", "adaptSpeed")},
        {"da-sequence-ad-decisionjoin-ok3.xmi", Arrays.asList("brake", "return", "stop", "return")},
        {"da-sequence-ad-decisionjoin-ok4.xmi", Arrays.asList("speedUp", "return", "stop", "return")},
        {"da-sequence-ad-decision-ok.xmi", Arrays.asList("calculateDesiredSpeed", "brake", "return")},
        {"da-sequence-ad-decision-ok2.xmi", Arrays.asList("calculateDesiredSpeed", "speedUp", "return")},
          {"da-sequence-ad-flowend-ok.xmi", Arrays.asList("checkForTrafficObject", "handleOccurrence", "return", "return", "checkForTrafficObject", "return")},
         {"da-sequence-ad-fork-ok1.xmi", Arrays.asList("start", "return", "checkForTrafficObject", "navigateToDestination")},
         {"da-sequence-ad-fork-ok2.xmi", Arrays.asList("start", "return", "navigateToDestination", "checkForTrafficObject")},
          {"da-sequence-ad-fork-ok3.xmi", Arrays.asList("checkForTrafficObject", "return foundTO", "handleOccurrence", "reply", "getCurrentColor", "calculateDistanceToCar", "brakeToZero", "reply")},
         {"da-sequence-ad-fork-ok4.xmi", Arrays.asList("checkForTrafficObject", "return foundTO", "handleOccurrence", "checkForTrafficObject", "return foundTO2", "handleOccurrence", "getCurrentColor", "calculateDistanceToCar", "brakeToZero", "reply", "getCurrentColor", "calculateDistanceToCar", "brakeToZero", "return")}
    };
  }

  @DataProvider
  public static Object[][] activityDiagramNegative() {
    return new Object[][]{
        {"da-sequence-ad-decisionjoin-nok.xmi", "Action :Brake.brake() not allowed!",
            Arrays.asList("speedUp", "return", "brake", "return", "stop", "return")},
        {"da-sequence-ad-basic-nok-wrongsequence.xmi", "Action Start motor:Motor.start() not allowed!",
            Arrays.asList("invoke", "return", "start", "return", "getRoute", "return")},
        {"da-sequence-ad-activityend-nok.xmi", "Action :Accelerator.speedUp() not allowed!",
            Arrays.asList("stop", "return", "speedUp", "return")},
        {"da-sequence-ad-decision-nok.xmi", "Action :Accelerator.speedUp() not allowed!",
            Arrays.asList("calculateDesiredSpeed", "brake", "return", "speedUp", "return")},
        {"da-sequence-ad-flowend-nok.xmi", "Action :CarHandler.startNavigation() not allowed!",
            Arrays.asList("checkForTrafficObject", "handleOccurrence", "return", "return",
                "startNavigation", "return")}
    };
  }

  @DataProvider
  public static Object[][] classDiagramPositive() {
    return new Object[][]{
        {"da-sequence-cd-ok-relation.xmi", Arrays.asList("start", "return")},
        {"da-sequence-cd-ok-returntype.xmi", Arrays.asList("handleOccurrence", "return")},
    };
  }

  @DataProvider
  public static Object[][] classDiagramNegative() {
    return new Object[][]{
        {"da-sequence-cd-nok-noreturntypeorrelation.xmi",
            "Call of handleOccurrence in TrafficLight from CarSystem is not allowed",
            Arrays.asList("handleOccurrence", "return")},
        {"da-sequence-cd-nok-operationprivate.xmi",
            "Call of getCurrentColor in TrafficLight from CarSystem is not allowed",
            Arrays.asList("getCurrentColor", "return")},
        {"da-sequence-cd-nok-nonexistingclass.xmi",
            "LifeLine myMotor:MotorX does not correspond to any Class in Class Diagram",
            Arrays.asList("start", "return")}
    };
  }

  @Before
  public void setUp() {
    this.modelValidationService = new UmlModelValidationServiceImpl(
        new SomqmRequirementsServiceImpl());
    this.umlRequirements = new UmlRequirements(PROJECT_ID);
    this.umlRequirements.setIdForType(UmlModelType.CLASS_MODEL, CLASS_DIAGRAM_ID);
    this.umlRequirements.setIdForType(UmlModelType.ACTIVITY_MODEL, ACTIVITY_DIAGRAM_ID);
  }

  @Test
  @UseDataProvider("activityDiagramPositive")
  public void verifySD_ActivityDiagram_Positive(String fileName, List<String> messageOrder)
      throws ServiceException {
    // Arrange
    byte[] sdContent = TestHelper.loadTestDataContent(fileName);
    SequenceDiagram sd = modelValidationService.loadDynamicSequenceDiagram(sdContent, messageOrder);

    // Act
    modelValidationService.validateDynamicSequenceDiagram(PROJECT_ID, sd, umlRequirements);

    // Assert
    // no error means that test is ok
  }

  @Test
  @UseDataProvider("activityDiagramNegative")
  public void verifySD_ActivityDiagram_Negative(String fileName, String expectedErrorMessage,
      List<String> messageOrder)
      throws ServiceException {
    // Arrange
    byte[] sdContent = TestHelper.loadTestDataContent(fileName);
    SequenceDiagram sd = modelValidationService.loadDynamicSequenceDiagram(sdContent, messageOrder);
    String actualErrorMessage = "No error thrown";

    // Act
    try {
      modelValidationService.validateDynamicSequenceDiagram(PROJECT_ID, sd, umlRequirements);
    } catch (ServiceException ex) {
      actualErrorMessage = ex.getMessage();
    }

    // Assert
    Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Test
  @UseDataProvider("classDiagramPositive")
  public void verifySD_ClassDiagram_Positive(String fileName, List<String> messageOrder)
      throws ServiceException {
    // Arrange
    byte[] sdContent = TestHelper.loadTestDataContent(fileName);
    SequenceDiagram sd = modelValidationService.loadDynamicSequenceDiagram(sdContent, messageOrder);

    // Act
    modelValidationService.validateDynamicSequenceDiagram(PROJECT_ID, sd, umlRequirements);

    // Assert
    // no error means that test is ok
  }

  @Test
  @UseDataProvider("classDiagramNegative")
  public void verifySD_ClassDiagram_Negative(String fileName, String expectedErrorMessage,
      List<String> messageOrder)
      throws ServiceException {
    // Arrange
    byte[] sdContent = TestHelper.loadTestDataContent(fileName);
    SequenceDiagram sd = modelValidationService.loadDynamicSequenceDiagram(sdContent, messageOrder);
    String actualErrorMessage = "No error thrown";

    // Act
    try {
      modelValidationService.validateDynamicSequenceDiagram(PROJECT_ID, sd, umlRequirements);
    } catch (ServiceException ex) {
      actualErrorMessage = ex.getMessage();
    }

    // Assert
    Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
  }
}
