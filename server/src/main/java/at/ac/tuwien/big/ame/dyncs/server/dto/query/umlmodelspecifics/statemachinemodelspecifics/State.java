package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import java.util.Objects;

public class State extends StateMachineModelEntityInfo {

  private FunctionCall entryFunctionCall;
  private FunctionCall doActivityFunctionCall;
  private FunctionCall exitFunctionCall;


  public FunctionCall getEntryFunctionCall() {
    return entryFunctionCall;
  }

  public FunctionCall getDoActivityFunctionCall() {
    return doActivityFunctionCall;
  }

  public FunctionCall getExitFunctionCall() {
    return exitFunctionCall;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    State state = (State) o;
    return Objects.equals(entryFunctionCall, state.entryFunctionCall) &&
        Objects.equals(doActivityFunctionCall, state.doActivityFunctionCall) &&
        Objects.equals(exitFunctionCall, state.exitFunctionCall);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(super.hashCode(), entryFunctionCall, doActivityFunctionCall, exitFunctionCall);
  }

  @Override
  public String toString() {
    return "State{" +
        "fullName='" + getFullName() + '\'' +
        ", entryFunctionCall=" + entryFunctionCall +
        ", doActivityFunctionCall=" + doActivityFunctionCall +
        ", exitFunctionCall=" + exitFunctionCall +
        '}';
  }

}
