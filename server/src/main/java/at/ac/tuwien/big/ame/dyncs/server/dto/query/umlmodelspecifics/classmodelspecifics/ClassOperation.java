package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.Modifier;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.VisibilityType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassOperation {

  private String name;
  private EntityInfo returnType;
  private VisibilityType visibility;
  private List<Modifier> modifiers;
  private List<ClassOperationParameter> parameters;

  public ClassOperation() {

  }

  public ClassOperation(String name,
      EntityInfo returnType,
      VisibilityType visibility,
      List<Modifier> modifiers,
      List<ClassOperationParameter> parameters) throws IllegalArgumentException {
    if (name == null || visibility == null || modifiers == null || parameters == null) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.returnType = returnType;
    this.visibility = visibility;
    this.modifiers = modifiers;
    this.parameters = parameters;
  }

  public String getName() {
    return name;
  }

  public EntityInfo getReturnType() {
    return returnType;
  }

  public VisibilityType getVisibility() {
    return visibility;
  }

  public List<Modifier> getModifiers() {
    return modifiers;
  }

  public List<ClassOperationParameter> getParameters() {
    return parameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClassOperation that = (ClassOperation) o;
    return name.equals(that.name) &&
        Objects.equals(returnType, that.returnType) &&
        parameters.equals(that.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, returnType, parameters);
  }

  @Override
  public String toString() {
    String result = "ClassOperation{";
    result += visibility + "";
    result += (modifiers.isEmpty() ? ""
        : " " + modifiers.stream().map(Modifier::getName).collect(Collectors.joining(" ")));
    result += " " + name + "(" + (parameters.isEmpty() ? ""
        : parameters.stream().map(Object::toString).collect(Collectors.joining(", "))) + ")";
    result += (returnType == null ? "" : ": " + returnType.getName());
    result += "}";
    return result;
  }
}
