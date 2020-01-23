package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

public class ClassModelGeneralizationRelationInfo extends ClassModelRelationInfo {

  public ClassModelGeneralizationRelationInfo() {

  }

  public ClassModelGeneralizationRelationInfo(ClassModelEntityInfo source,
      ClassModelEntityInfo target)
      throws IllegalArgumentException {
    super(source, target);
  }

  @Override
  public String toString() {
    return "ClassModelGeneralizationRelationInfo{" +
        "source='" + getSource().getName() + '\'' +
        ", target='" + getTarget().getName() + '\'' +
        '}';
  }
}
