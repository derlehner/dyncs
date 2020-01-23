package at.ac.tuwien.big.ame.dyncs.server.service.impl.Helper;

import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.LifeLine;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.Message;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.SequenceDiagram;
import java.util.HashSet;
import java.util.Set;

public class DtoExtractor {

  public static Set<LifeLine> extractLifeLinesFromSequenceDiagram(SequenceDiagram sd) {
    Set<LifeLine> result = new HashSet<>();
    for (Message message : sd.getMessages()) {
      result.add(message.getSource());
      result.add(message.getDestination());
    }
    return result;
  }

}
