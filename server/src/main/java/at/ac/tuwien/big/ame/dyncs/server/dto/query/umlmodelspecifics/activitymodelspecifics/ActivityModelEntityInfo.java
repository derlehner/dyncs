package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.Package;

public abstract class ActivityModelEntityInfo extends EntityInfo {

  public ActivityModelEntityInfo() {
  }

  public ActivityModelEntityInfo(String name, Package package_) throws IllegalArgumentException {
    super(name, package_);
  }

}
