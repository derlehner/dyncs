package at.ac.tuwien.big.ame.dyncs.server.dto.query;

public class GetAllRequest {

  private long modelId;

  private String sourceEntityNameRegex;

  private String targetEntityNameRegex;

  public GetAllRequest() {
  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
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
    return "GetAllRequest{" +
        "modelId=" + modelId +
        ", sourceEntityNameRegex='" + sourceEntityNameRegex + '\'' +
        ", targetEntityNameRegex='" + targetEntityNameRegex + '\'' +
        '}';
  }
}
