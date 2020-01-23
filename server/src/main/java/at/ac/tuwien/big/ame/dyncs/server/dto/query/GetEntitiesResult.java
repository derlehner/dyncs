package at.ac.tuwien.big.ame.dyncs.server.dto.query;

import java.util.List;
import java.util.Objects;

public class GetEntitiesResult {

  private List<EntityInfo> entities;

  public List<EntityInfo> getEntities() {
    return entities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetEntitiesResult that = (GetEntitiesResult) o;
    return entities.equals(that.entities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entities);
  }

}
