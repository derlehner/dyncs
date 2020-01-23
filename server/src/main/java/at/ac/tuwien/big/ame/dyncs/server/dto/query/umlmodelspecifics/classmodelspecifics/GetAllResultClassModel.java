package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;


public class GetAllResultClassModel extends GetRelationsResultClassModel {

  @JsonProperty("relations")
  private List<ClassModelRelationInfo> relations;

  public List<ClassModelRelationInfo> getRelations() {
    return relations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetAllResultClassModel that = (GetAllResultClassModel) o;
    return relations.equals(that.relations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relations);
  }
}
