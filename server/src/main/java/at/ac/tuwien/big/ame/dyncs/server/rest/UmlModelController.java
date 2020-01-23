package at.ac.tuwien.big.ame.dyncs.server.rest;

import at.ac.tuwien.big.ame.dyncs.server.service.RequirementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/umlmodels")
public class UmlModelController {

  private final RequirementsService requirementsService;

  @Autowired
  public UmlModelController(RequirementsService requirementsService) {
    this.requirementsService = requirementsService;
  }


}
