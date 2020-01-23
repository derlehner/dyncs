package at.ac.tuwien.big.ame.dyncs.server.dto.query;

public class GetRelationsRequest {

  private long modelId;

  private boolean includeTypeSpecificDetails;

  private String sourceEntityNameRegex;

  private String targetEntityNameRegex;

  public GetRelationsRequest() {

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

  public String getSourceEntityNameRegex() {
    return sourceEntityNameRegex;
  }

  public void setSourceEntityNameRegex(String sourceEntityNameRegex) {
    this.sourceEntityNameRegex = sourceEntityNameRegex;
  }

  public String getTargetEntityNameRegex() {
    return targetEntityNameRegex;
  }

  public void setTargetEntityNameRegex(String targetEntityNameRegex) {
    this.targetEntityNameRegex = targetEntityNameRegex;
  }

  @Override
  public String toString() {
    return "GetRelationsRequest{" +
        "modelId=" + modelId +
        ", includeTypeSpecificDetails=" + includeTypeSpecificDetails +
        ", sourceEntityNameRegex='" + sourceEntityNameRegex + '\'' +
        ", targetEntityNameRegex='" + targetEntityNameRegex + '\'' +
        '}';
  }
}
