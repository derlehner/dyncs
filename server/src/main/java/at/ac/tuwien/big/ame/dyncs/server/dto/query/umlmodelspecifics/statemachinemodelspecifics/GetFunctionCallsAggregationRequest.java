package at.ac.tuwien.big.ame.dyncs.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.aggregation.QueryResultAggregationType;

public class GetFunctionCallsAggregationRequest extends GetFunctionCallsRequest {

  private QueryResultAggregationType aggregationType;

  public GetFunctionCallsAggregationRequest() {
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
    return "GetFunctionCallsAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
