package at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics;

import java.util.Objects;

public class LifeLine {

  private String objectName;
  private String className;

  public LifeLine(String objectName, String className) {
    if (className == null) {
      throw new IllegalArgumentException("Lifeline must have an assigned class");
    }
    this.objectName = objectName;
    this.className = className;
  }

  public String getObjectName() {
    return objectName;
  }

  public String getClassName() {
    return className;
  }

  public String getName() {
    return objectName + ":" + className;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LifeLine lifeLine = (LifeLine) o;
    return Objects.equals(objectName, lifeLine.objectName) &&
        className.equals(lifeLine.className);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectName, className);
  }
}
