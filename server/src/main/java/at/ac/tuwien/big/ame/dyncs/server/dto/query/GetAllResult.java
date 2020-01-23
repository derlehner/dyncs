package at.ac.tuwien.big.ame.dyncs.server.dto.query;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelDefaultRelationInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelGeneralizationRelationInfo;
import at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelRelationInfo;
import java.util.LinkedList;
import java.util.List;


public class GetAllResult extends GetRelationsResult {

  private List<RelationInfo> relations;

  public GetAllResult() {

  }

  //@JsonSetter("relations")
  public void convertRelations(List<RelationInfo> relationInfos) {
    List<RelationInfo> result = new LinkedList<>();
    for (RelationInfo relationInfo : relationInfos) {
      System.out.println(relationInfo.getSource().getName());
      if (relationInfo instanceof ClassModelDefaultRelationInfo) {
        ClassModelRelationInfo classModelRelationInfo = (ClassModelRelationInfo) relationInfo;
        result.add(classModelRelationInfo);
      } else if (relationInfo instanceof ClassModelGeneralizationRelationInfo) {

      } else {
        result.add(relationInfo);
      }
    }
    super.relations = result;
  }

}
