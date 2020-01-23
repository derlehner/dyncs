package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics;


import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;

public class GetNodesRequest {

  private long projectId;

  private String nodeNameRegex;

  private String activityNameRegex;

  private String packageNameRegex;

  private NodeType nodeType;

  public GetNodesRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getNodeNameRegex() {
    return nodeNameRegex;
  }

  public void setNodeNameRegex(String nodeNameRegex) {
    this.nodeNameRegex = nodeNameRegex;
  }

  public String getActivityNameRegex() {
    return activityNameRegex;
  }

  public void setActivityNameRegex(String activityNameRegex) {
    this.activityNameRegex = activityNameRegex;
  }

  public String getPackageNameRegex() {
    return packageNameRegex;
  }

  public void setPackageNameRegex(String packageNameRegex) {
    this.packageNameRegex = packageNameRegex;
  }

  public NodeType getNodeType() {
    return nodeType;
  }

  public void setNodeType(
      NodeType nodeType) {
    this.nodeType = nodeType;
  }

  @Override
  public String toString() {
    return "GetNodesRequest{" +
        "projectId=" + projectId +
        ", nodeNameRegex='" + nodeNameRegex + '\'' +
        ", activityNameRegex='" + activityNameRegex + '\'' +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        ", nodeType=" + nodeType +
        '}';
  }
}
