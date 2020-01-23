package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics;

public class GetOperationsRequest {

  private long projectId;

  private String operationNameRegex;

  private String entityNameRegex;

  public GetOperationsRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getOperationNameRegex() {
    return operationNameRegex;
  }

  public void setOperationNameRegex(String operationNameRegex) {
    this.operationNameRegex = operationNameRegex;
  }

  public String getEntityNameRegex() {
    return entityNameRegex;
  }

  public void setEntityNameRegex(String entityNameRegex) {
    this.entityNameRegex = entityNameRegex;
  }

  @Override
  public String toString() {
    return "GetOperationsRequest{" +
        "projectId=" + projectId +
        ", operationNameRegex='" + operationNameRegex + '\'' +
        ", entityNameRegex='" + entityNameRegex + '\'' +
        '}';
  }
}
