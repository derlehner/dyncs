package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

public class ClassModelInterfaceRealizationRelationInfo extends ClassModelRelationInfo {

  public ClassModelInterfaceRealizationRelationInfo() {

  }

  public ClassModelInterfaceRealizationRelationInfo(ClassModelEntityInfo source,
      ClassModelEntityInfo target)
      throws IllegalArgumentException {
    super(source, target);
  }

  @Override
  public String toString() {
    return "ClassModelInterfaceRealizationRelationInfo{" +
        "source='" + getSource().getName() + '\'' +
        ", target='" + getTarget().getName() + '\'' +
        '}';
  }
}
