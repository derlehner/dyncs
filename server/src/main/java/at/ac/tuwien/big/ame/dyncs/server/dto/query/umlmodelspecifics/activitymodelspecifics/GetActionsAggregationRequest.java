package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.aggregation.QueryResultAggregationType;

public class GetActionsAggregationRequest extends GetActionsRequest {

  private QueryResultAggregationType aggregationType;

  public GetActionsAggregationRequest() {
  }

  public QueryResultAggregationType getAggregationType() {
    return aggregationType;
  }

  public void setAggregationType(
      QueryResultAggregationType aggregationType) {
    this.aggregationType = aggregationType;
  }

  @Override
  public String toString() {
    return "GetActionsAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
