package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.RelationInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.ActivityModelRelationInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "relationType")
@JsonSubTypes({
    @Type(value = ClassModelDefaultRelationInfo.class, name = "DEFAULT"),
    @Type(value = ClassModelGeneralizationRelationInfo.class, name = "GENERALIZATION"),
    @Type(value = ClassModelInterfaceRealizationRelationInfo.class, name = "INTERFACE_REALIZATION"),
    @Type(value = ActivityModelRelationInfo.class, name = "FLOW")
})
public abstract class ClassModelRelationInfo extends RelationInfo {

  @JsonProperty("source")
  private ClassModelEntityInfo source;
  @JsonProperty("target")
  private ClassModelEntityInfo target;

  public ClassModelRelationInfo() {
    super();
  }

  public ClassModelRelationInfo(ClassModelEntityInfo source, ClassModelEntityInfo target)
      throws IllegalArgumentException {
    super(source, target);
  }

  public ClassModelEntityInfo getSource() {
    return source;
  }

  public void setSource(
      ClassModelEntityInfo source) {
    this.source = source;
  }

  public ClassModelEntityInfo getTarget() {
    return target;
  }

  public void setTarget(
      ClassModelEntityInfo target) {
    this.target = target;
  }

}
