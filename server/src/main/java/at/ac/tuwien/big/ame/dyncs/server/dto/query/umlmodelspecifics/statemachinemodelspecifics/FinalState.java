package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

public class FinalState extends State {

  @Override
  public String toString() {
    return "FinalState{" +
        "fullName='" + getFullName() + '\'' +
        ", entryFunctionCall=" + getEntryFunctionCall() +
        ", doActivityFunctionCall=" + getDoActivityFunctionCall() +
        ", exitFunctionCall=" + getExitFunctionCall() +
        '}';
  }

}
