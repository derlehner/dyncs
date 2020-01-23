package at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.enumeration.MessageType;

public class Message {

  private String name;
  private LifeLine source;
  private LifeLine destination;
  private MessageType type;

  public Message(LifeLine source, LifeLine destination, String name, MessageType type) {
    if (source == null || destination == null) {
      throw new IllegalArgumentException("Message must have source and destination");
    }
    if (type == null) {
      throw new IllegalArgumentException("Message type must be specified");
    }
    this.source = source;
    this.destination = destination;
    this.name = name;
    this.type = type;
  }

  public MessageType getType() {
    return type;
  }

  public LifeLine getSource() {
    return source;
  }

  public LifeLine getDestination() {
    return destination;
  }

  public String getName() {
    return name;
  }


}
