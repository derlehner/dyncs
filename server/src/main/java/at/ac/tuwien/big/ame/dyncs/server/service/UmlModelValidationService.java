package at.ac.tuwien.big.ame.dyncs.server.service;

import at.ac.tuwien.big.ame.dyncs.server.dto.UmlRequirements;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.SequenceDiagram;
import java.util.List;

public interface UmlModelValidationService {

  /**
   * Load content as model with UML2 a) Verify if uploaded file is valid XML/XMI content b) Verify
   * if uploaded file is valid UML model
   */
  SequenceDiagram loadDynamicSequenceDiagram(byte[] modelContent, List<String> messageOrder)
      throws ServiceException;

  /**
   * Validates if the supplied model is valid according to the supplied supported types.
   *
   * @return Type of the validated model.
   */
  void validateDynamicSequenceDiagram(long projectId, SequenceDiagram sd,
      UmlRequirements requirements) throws ServiceException;

  void validateMessageOrderForSequenceDiagram(List<String> messageOrder, SequenceDiagram sd);
}
