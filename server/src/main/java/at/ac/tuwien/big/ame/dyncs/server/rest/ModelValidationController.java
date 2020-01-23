package at.ac.tuwien.big.ame.dyncs.server.rest;

import at.ac.tuwien.big.ame.dyncs.server.dto.UmlRequirements;
import at.ac.tuwien.big.ame.dyncs.server.dto.ValidateUmlSequenceModelRequest;
import at.ac.tuwien.big.ame.dyncs.server.dto.runtime.modelspecifics.SequenceDiagram;
import at.ac.tuwien.big.ame.dyncs.server.service.RequirementsService;
import at.ac.tuwien.big.ame.dyncs.server.service.ServiceException;
import at.ac.tuwien.big.ame.dyncs.server.service.UmlModelValidationService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/validate")
public class ModelValidationController {

  private final UmlModelValidationService validationService;
  private final RequirementsService requirementsService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public ModelValidationController(UmlModelValidationService validationService,
      RequirementsService requirementsService) {
    this.validationService = validationService;
    this.requirementsService = requirementsService;
  }

  @PostMapping(value = "/sequencediagram")
  @ApiOperation(value = "validates current model")
  public void validateUmlSequenceModel(@RequestBody ValidateUmlSequenceModelRequest request,
      @RequestPart MultipartFile modelContent) throws ServiceException {
    byte[] contentBytes;
    try {
      contentBytes = modelContent.getBytes();
    } catch (IOException e) {
      throw new ServiceException("Supplied content is invalid", e);
    }
    SequenceDiagram sd = this.validationService
        .loadDynamicSequenceDiagram(contentBytes, request.getOrderOfSequences());
    UmlRequirements requirements = requirementsService.getUMLModelRequirements(
        request.getProjectId());
    this.validationService.validateDynamicSequenceDiagram(request.getProjectId(), sd, requirements);
    logger.trace(
        "Requested checking for file" + modelContent + " for project " + request.getProjectId()
            + " resulted in response: ");
    return;
  }

  @PostMapping(value = "/notsupporteddiagram")
  @ApiOperation(value = "validates current model")
  public void validateUmlNotImplementedModel(@RequestPart MultipartFile content)
      throws ServiceException {
    throw new ServiceException("Model Type not yet supported!");
  }

}
