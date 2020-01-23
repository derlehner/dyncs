package at.ac.tuwien.big.ame.dyncs.server.dto;

import at.ac.tuwien.big.ame.dyncs.server.model.enumeration.UmlModelType;
import java.util.HashMap;
import java.util.Map;

public class UmlRequirements {

  long projectId;
  Map<UmlModelType, Long> requirementIds;

  public UmlRequirements(long projectId) {

    this.projectId = projectId;
    this.requirementIds = new HashMap<>();
  }

  public UmlRequirements(long projectId,
      Map<UmlModelType, Long> requirementIds) {
    this.projectId = projectId;
    this.requirementIds = requirementIds;
  }

  public long getProjectId() {
    return projectId;
  }

  public Map<UmlModelType, Long> getRequirementIds() {
    return requirementIds;
  }

  public void setRequirementIds(
      Map<UmlModelType, Long> requirementIds) {
    this.requirementIds = requirementIds;
  }

  public long getIdForModelType(UmlModelType type) {
    return requirementIds.get(type);
  }

  public void setIdForType(UmlModelType type, long id) {
    this.requirementIds.put(type, id);
  }


}
