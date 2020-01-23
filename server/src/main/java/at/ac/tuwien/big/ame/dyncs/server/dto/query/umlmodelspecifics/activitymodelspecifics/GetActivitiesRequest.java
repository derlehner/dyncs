package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics;


public class GetActivitiesRequest {

  private long projectId;

  private String activityNameRegex;

  private String packageNameRegex;

  public GetActivitiesRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
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
    return "GetActivitiesRequest{" +
        "projectId=" + projectId +
        ", activityNameRegex='" + activityNameRegex + '\'' +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        '}';
  }
}
