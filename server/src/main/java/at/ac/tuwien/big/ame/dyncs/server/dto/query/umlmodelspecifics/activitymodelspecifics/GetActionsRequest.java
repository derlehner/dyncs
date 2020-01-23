package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics;


public class GetActionsRequest {

  private long projectId;

  private String actionNameRegex;

  private String activityNameRegex;

  private String packageNameRegex;

  public GetActionsRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getActionNameRegex() {
    return actionNameRegex;
  }

  public void setActionNameRegex(String actionNameRegex) {
    this.actionNameRegex = actionNameRegex;
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

  @Override
  public String toString() {
    return "GetActionsRequest{" +
        "projectId=" + projectId +
        ", actionNameRegex='" + actionNameRegex + '\'' +
        ", activityNameRegex='" + activityNameRegex + '\'' +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        '}';
  }
}
