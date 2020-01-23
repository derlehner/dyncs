package at.ac.tuwien.big.ame.somqm.server.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestHelper {

  public static byte[] getValidTxtContent() {
    return loadTestDataContent("test.txt");
  }

  public static byte[] getValidXmlContent() {
    return loadTestDataContent("note.xml");
  }

  public static byte[] getInvalidXmiContent() {
    return loadTestDataContent("invalid_xmi.uml");
  }

  public static byte[] getValidClassModelContent() {
    // return loadTestDataContent("car_class.uml");
    return loadTestDataContent("test_class_diagram.uml");
  }

  public static byte[] getValidActivityModelContent() {
    return loadTestDataContent("test_case_4_valid_r1.uml");
  }

  public static byte[] getValidStateMachineModelContent() {
    return loadTestDataContent("state_machine_example.uml");
  }

  public static byte[] loadPerformanceTestDataContent(String fileName) {
    //URL fileUrl = ClassLoader.getSystemResource("C:/Users/danie/Desktop/Git/diplomarbeit/Implementation/CheckingService/server/src/test/resources/performance-models/basic/basic-class-diagram.uml");
    Path filePath;
    try {
      filePath = Paths.get("./src/test/resources/performance-models/" + fileName);
    } catch (NullPointerException e) {
      throw new RuntimeException("Failed to get path to test-data content: " + e.getMessage(), e);
    }
    byte[] content;
    try {
      content = Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load test-data content: " + e.getMessage(), e);
    }
    return content;
  }

  public static byte[] loadTestDataContent(String fileName) {
    URL fileUrl = ClassLoader.getSystemResource("test-data/" + fileName);
    Path filePath;
    try {
      filePath = Paths.get(fileUrl.toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException("Failed to get path to test-data content: " + e.getMessage(), e);
    }
    byte[] content;
    try {
      content = Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load test-data content: " + e.getMessage(), e);
    }
    return content;
  }
}
