package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

import java.util.List;
import java.util.Objects;

public class GetRelationsResultClassModel {

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
    GetRelationsResultClassModel that = (GetRelationsResultClassModel) o;
    return relations.equals(that.relations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relations);
  }

}
