package at.ac.tuwien.big.ame.dyncs.server.dto.query;

import java.util.List;
import java.util.Objects;

public class GetRelationsResult {

  protected List<RelationInfo> relations;

  public List<RelationInfo> getRelations() {
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
    GetRelationsResult that = (GetRelationsResult) o;
    return relations.equals(that.relations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relations);
  }

}
