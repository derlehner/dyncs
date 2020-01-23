package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;


public class GetAllResultActivityModel {

  @JsonProperty("relations")
  private List<ActivityModelRelationInfo> relations;

  public List<ActivityModelRelationInfo> getRelations() {
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
    GetAllResultActivityModel that = (GetAllResultActivityModel) o;
    return relations.equals(that.relations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relations);
  }
}
