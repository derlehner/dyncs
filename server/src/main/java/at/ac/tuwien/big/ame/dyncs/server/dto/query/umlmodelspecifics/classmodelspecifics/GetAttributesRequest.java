package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

public class GetAttributesRequest {

  private long projectId;

  private String attributeNameRegex;

  private String entityNameRegex;

  public GetAttributesRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getAttributeNameRegex() {
    return attributeNameRegex;
  }

  public void setAttributeNameRegex(String operationNameRegex) {
    this.attributeNameRegex = operationNameRegex;
  }

  public String getEntityNameRegex() {
    return entityNameRegex;
  }

  public void setEntityNameRegex(String entityNameRegex) {
    this.entityNameRegex = entityNameRegex;
  }

  @Override
  public String toString() {
    return "GetAttributesRequest{" +
        "projectId=" + projectId +
        ", attributeNameRegex='" + attributeNameRegex + '\'' +
        ", entityNameRegex='" + entityNameRegex + '\'' +
        '}';
  }
}
