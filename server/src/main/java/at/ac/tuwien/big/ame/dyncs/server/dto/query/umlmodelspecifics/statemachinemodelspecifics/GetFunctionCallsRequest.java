package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

public class GetFunctionCallsRequest {

  private long projectId;

  private String functionCallNameRegex;

  public GetFunctionCallsRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getFunctionCallNameRegex() {
    return functionCallNameRegex;
  }

  public void setFunctionCallNameRegex(String functionCallNameRegex) {
    this.functionCallNameRegex = functionCallNameRegex;
  }

  @Override
  public String toString() {
    return "GetFunctionCallsRequest{" +
        "projectId=" + projectId +
        ", functionCallNameRegex='" + functionCallNameRegex + '\'' +
        '}';
  }
}
