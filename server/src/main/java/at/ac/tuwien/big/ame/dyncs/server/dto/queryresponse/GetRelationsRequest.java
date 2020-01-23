package at.ac.tuwien.big.ame.dyncs.server.dto.queryresponse;

import io.swagger.annotations.ApiModelProperty;

public class GetRelationsRequest {

  @ApiModelProperty(required = true)
  private long modelId;

  @ApiModelProperty(required = false, notes = "If not set: Filters relations to those with no start-entity")
  private String startEntityNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filters relations to those with no end-entity")
  private String endEntityNameRegex;

  public GetRelationsRequest() {

  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  public String getStartEntityNameRegex() {
    return startEntityNameRegex;
  }

  public void setStartEntityNameRegex(String startEntityNameRegex) {
    this.startEntityNameRegex = startEntityNameRegex;
  }

  public String getEndEntityNameRegex() {
    return endEntityNameRegex;
  }

  public void setEndEntityNameRegex(String endEntityNameRegex) {
    this.endEntityNameRegex = endEntityNameRegex;
  }

  @Override
  public String toString() {
    return "GetRelationsRequest{" +
        "modelId=" + modelId +
        ", startEntityNameRegex='" + startEntityNameRegex + '\'' +
        ", endEntityNameRegex='" + endEntityNameRegex + '\'' +
        '}';
  }
}
