package at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SequenceDiagram {

  Set<LifeLine> lifeLines;
  List<Message> messages;

  public SequenceDiagram() {
    this.messages = new LinkedList<>();
    this.lifeLines = new HashSet<>();
  }

  public Set<LifeLine> getLifeLines() {
    return lifeLines;
  }

  public void setLifeLines(
      Set<LifeLine> lifeLines) {
    this.lifeLines = lifeLines;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(
      List<Message> messages) {
    this.messages = messages;
  }

  public void addMessage(Message message) {
    this.messages.add(message);
  }

  public void addLifeLine(LifeLine lifeLine) {
    this.lifeLines.add(lifeLine);
  }


}
