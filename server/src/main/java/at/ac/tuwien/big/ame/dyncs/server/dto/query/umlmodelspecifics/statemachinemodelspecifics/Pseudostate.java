package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.enumeration.PseudostateType;
import java.util.Objects;

public class Pseudostate extends StateMachineModelEntityInfo {

  private PseudostateType type;

  public PseudostateType getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Pseudostate that = (Pseudostate) o;
    return type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), type);
  }

  @Override
  public String toString() {
    return "Pseudostate{" +
        "fullName='" + getFullName() + '\'' +
        ", type=" + type +
        '}';
  }

}
