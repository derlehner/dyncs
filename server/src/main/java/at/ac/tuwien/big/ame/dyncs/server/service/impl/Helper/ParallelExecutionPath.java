package at.ac.tuwien.big.ame.dyncs.server.service.impl.Helper;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action;
import java.util.List;

public class ParallelExecutionPath<T>{

  public List<ParallelExecutionPath<T>> getFurtherPaths() {
    return furtherPaths;
  }

  public void setFurtherPaths(
      List<ParallelExecutionPath<T>> furtherPaths) {
    this.furtherPaths = furtherPaths;
  }

  public List<T> getAllowedActionsInThisPath() {
    return allowedActionsInThisPath;
  }

  private List<ParallelExecutionPath<T>> furtherPaths;

  public ParallelExecutionPath(List<T> allowedActionsInThisPath) {
    this.allowedActionsInThisPath = allowedActionsInThisPath;
  }

  public ParallelExecutionPath(List<T> allowedActionsInThisPath, List<ParallelExecutionPath<T>> furtherPaths) {
    this.allowedActionsInThisPath = allowedActionsInThisPath;
    this.furtherPaths = furtherPaths;
  }

  private List<T> allowedActionsInThisPath;

  @Override
  public String toString() {
    String stringRepresentation = "";
    for(Action a : (List<Action>)allowedActionsInThisPath){
      stringRepresentation += a.getFullName() + "; ";
    }
    return "ParallelExecutionPath{" +
        ", allowedActionsInThisPath=[" + stringRepresentation + "]" +
        "furtherPaths=" + furtherPaths +
        '}';
  }
}
