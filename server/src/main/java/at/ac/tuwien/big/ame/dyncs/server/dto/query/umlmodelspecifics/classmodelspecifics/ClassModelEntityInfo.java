package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.Modifier;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.ClassType;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.VisibilityType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassModelEntityInfo extends EntityInfo {

  private ClassType type;
  private List<ClassAttribute> attributes;
  private List<ClassOperation> operations;
  private List<Modifier> modifiers;
  private VisibilityType visibility;

  public ClassType getType() {
    return type;
  }

  public List<ClassAttribute> getAttributes() {
    return attributes;
  }

  public List<ClassOperation> getOperations() {
    return operations;
  }

  public List<Modifier> getModifiers() {
    return modifiers;
  }

  public VisibilityType getVisibility() {
    return visibility;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), type, attributes, operations, modifiers, visibility);
  }

  @Override
  public String toString() {
    return "ClassModelEntityInfo{" +
        "fullName='" + getFullName() + '\'' +
        ", type=" + type +
        ", attributes=[" + (attributes.isEmpty() ? ""
        : attributes.stream().map(Object::toString).collect(Collectors.joining(", "))) + "]" +
        ", operations=[" + (operations.isEmpty() ? ""
        : operations.stream().map(Object::toString).collect(Collectors.joining(", "))) + "]" +
        ", modifiers=[" + (modifiers.isEmpty() ? ""
        : modifiers.stream().map(Modifier::getName).collect(Collectors.joining(", "))) + "]" +
        ", visibility=" + visibility +
        '}';
  }

}
