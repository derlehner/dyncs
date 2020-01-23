package at.ac.tuwien.big.ame.dyncs.server.dto.query;

public class GetPackagesRequest {

  private long modelId;

  private String packageNameRegex;

  public GetPackagesRequest() {
  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  public String getPackageNameRegex() {
    return packageNameRegex;
  }

  public void setPackageNameRegex(String packageNameRegex) {
    this.packageNameRegex = packageNameRegex;
  }

  @Override
  public String toString() {
    return "GetPackagesRequest{" +
        "modelId=" + modelId +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        '}';
  }
}
