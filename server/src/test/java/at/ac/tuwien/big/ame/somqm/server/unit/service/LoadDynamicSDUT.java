package at.ac.tuwien.big.ame.somqm.server.unit.service;

import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.LifeLine;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.Message;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.SequenceDiagram;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.enumeration.MessageType;
import at.ac.tuwien.big.ame.dyncs.server.service.RequirementsService;
import at.ac.tuwien.big.ame.dyncs.server.service.ServiceException;
import at.ac.tuwien.big.ame.dyncs.server.service.UmlModelValidationService;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.SomqmRequirementsServiceImpl;
import at.ac.tuwien.big.ame.dyncs.server.service.impl.UmlModelValidationServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.util.TestHelper;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoadDynamicSDUT {

  private static final String FILEPATH_POSITIVE = "da-test-sequence.xmi";
  private static final List<String> MESSAGE_ORDER_POSITIVE = Arrays
      .asList("synchronousMessageCall", "return synchronousMessageResponse",
          "asynchronousMessageCall", "selfMessageExecution");
  private static final String FILEPATH_NO_CLASSNAME = "da-test-sequence-negative-noclassname.xmi";
  private static final List<String> MESSAGE_ORDER_NO_CLASSNAME = Arrays.asList("brake");
  private static final String FILEPATH_POSITIV_ADVANCED = "da-test-sequence-complex.xmi";
  private static final List<String> MESSAGE_ORDER_POSITIVE_ADVANCED = Arrays
      .asList("synchronousMessageCall", "intermediateMessageCall", "return whatever",
          "return synchronousMessageResponse", "asynchronousMessageCall", "selfMessageExecution",
          "call");

  private UmlModelValidationService validationService;

  private SequenceDiagram actualDiagram;

  @Before
  public void setup() {
    RequirementsService requirementsService = new SomqmRequirementsServiceImpl();
    this.validationService = new UmlModelValidationServiceImpl(requirementsService);
  }

  @Test
  public void loadSD_assertMessageOrderCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIV_ADVANCED);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE_ADVANCED;
    String expectedName = "testObject";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);
    List<Message> actualMessages = actualDiagram.getMessages();

    // Assert
    Assert.assertEquals(actualMessages.size(), expectedMessageOrder.size());
    for (int i = 0; i < expectedMessageOrder.size(); i++) {
      Assert.assertEquals(expectedMessageOrder.get(0), actualMessages.get(0).getName());
    }
  }


  @Test
  public void loadSD_assertObjectNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedName = "testObject";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkObjectNameExistsInList(actualDiagram.getLifeLines(), expectedName));
  }

  @Test
  public void loadSD_assertClassNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedName = "TestClass";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkClassNameExistsInList(actualDiagram.getLifeLines(), expectedName));
  }

  @Test
  public void loadSD_assertNoObjectNameObjectNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedName = null;

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkObjectNameExistsInList(actualDiagram.getLifeLines(), expectedName));
  }

  @Test
  public void loadSD_assertNoObjectNameClassNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedName = "NonObjectTestClass";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkClassNameExistsInList(actualDiagram.getLifeLines(), expectedName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadSD_assertNoClassNameError() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_NO_CLASSNAME);
    List<String> expectedMessageOrder = MESSAGE_ORDER_NO_CLASSNAME;

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);
  }

  @Test
  public void loadSD_synchronous_assertCallMessageExists() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    MessageType expectedType = MessageType.SYNC_CALL;

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageTypeExistsInList(actualDiagram.getMessages(), expectedType));
  }

  @Test
  public void loadSD_synchronous_assertCallMessageNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedMessage = "synchronousMessageCall";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageNameExistsInList(actualDiagram.getMessages(), expectedMessage));
  }

  @Test
  public void loadSD_synchronous_assertResponseMessageExists() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    MessageType expectedType = MessageType.SYNC_RESPONSE;

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageTypeExistsInList(actualDiagram.getMessages(), expectedType));
  }

  @Test
  public void loadSD_synchronous_assertResponseMessageNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedMessage = "return synchronousMessageResponse";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageNameExistsInList(actualDiagram.getMessages(), expectedMessage));
  }

  @Test
  public void loadSD_asynchronous_assertCallMessageExists() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    MessageType expectedType = MessageType.ASYNC_CALL;

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageTypeExistsInList(actualDiagram.getMessages(), expectedType));
  }

  @Test
  public void loadSD_asynchronous_assertMessageNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedMessage = "asynchronousMessageCall";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageNameExistsInList(actualDiagram.getMessages(), expectedMessage));
  }

  @Test
  public void loadSD_selfexecution_assertCallMessageExists() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    MessageType expectedType = MessageType.SELF_EXECUTION;

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageTypeExistsInList(actualDiagram.getMessages(), expectedType));
  }

  @Test
  public void loadSD_selfexecution_assertMessageNameCorrect() throws ServiceException {
    // Arrange
    byte[] model = TestHelper.loadTestDataContent(FILEPATH_POSITIVE);
    List<String> expectedMessageOrder = MESSAGE_ORDER_POSITIVE;
    String expectedMessage = "selfMessageExecution";

    // Act
    actualDiagram = validationService.loadDynamicSequenceDiagram(model, expectedMessageOrder);

    // Assert
    Assert.assertTrue(checkIfMessageNameExistsInList(actualDiagram.getMessages(), expectedMessage));
  }

  private boolean checkObjectNameExistsInList(Set<LifeLine> lifeLines, String name) {
    for (LifeLine currentLifeLine : lifeLines) {
      if (currentLifeLine.getObjectName() == name) {
        return true;
      }
      if (currentLifeLine.getObjectName() != null) {
        if (currentLifeLine.getObjectName().equals(name)) {
          return true;
        }
      }

    }
    return false;
  }

  private boolean checkClassNameExistsInList(Set<LifeLine> lifeLines, String name) {
    for (LifeLine currentLifeLine : lifeLines) {
      if (currentLifeLine.getClassName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  private boolean checkIfMessageNameExistsInList(List<Message> messages, String name) {
    for (Message currentMessage : messages) {
      if (currentMessage.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  private boolean checkMessageNameOrderEqual(List<Message> actualMessages,
      List<String> expectedNames) {
    if (actualMessages.size() != expectedNames.size()) {
      return false;
    }

    for (int i = 0; i < actualMessages.size(); i++) {
      if (!actualMessages.get(i).getName().equals(expectedNames.get(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean checkIfMessageTypeExistsInList(List<Message> messages, MessageType name) {
    for (Message currentMessage : messages) {
      if (currentMessage.getType().equals(name)) {
        return true;
      }
    }
    return false;
  }

}
