package at.ac.tuwien.big.ame.dyncs.server.dto.query;

public class GetEntitiesRequest {

  private long modelId;

  private boolean includeTypeSpecificDetails;

  private String entityNameRegex;

  private String packageNameRegex;

  public GetEntitiesRequest() {
  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  public boolean isIncludeTypeSpecificDetails() {
    return includeTypeSpecificDetails;
  }

  public void setIncludeTypeSpecificDetails(boolean includeTypeSpecificDetails) {
    this.includeTypeSpecificDetails = includeTypeSpecificDetails;
  }

  public String getEntityNameRegex() {
    return entityNameRegex;
  }

  public void setEntityNameRegex(String entityNameRegex) {
    this.entityNameRegex = entityNameRegex;
  }

  public String getPackageNameRegex() {
    return packageNameRegex;
  }

  public void setPackageNameRegex(String packageNameRegex) {
    this.packageNameRegex = packageNameRegex;
  }

  @Override
  public String toString() {
    return "GetEntitiesRequest{" +
        "modelId=" + modelId +
        ", includeTypeSpecificDetails=" + includeTypeSpecificDetails +
        ", entityNameRegex='" + entityNameRegex + '\'' +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        '}';
  }
}
