package at.ac.tuwien.big.ame.dyncs.server.dto;

import java.util.List;

public class ValidateUmlSequenceModelRequest {

  private Long projectId;
  private List<String> orderOfSequences;

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public List<String> getOrderOfSequences() {
    return orderOfSequences;
  }

  public void setOrderOfSequences(List<String> orderOfSequences) {
    this.orderOfSequences = orderOfSequences;
  }

}
