package at.ac.tuwien.big.ame.dyncs.server.dto.queryresponse;


import at.ac.tuwien.big.ame.dyncs.server.dto.queryresponse.aggregation.QueryResultAggregationType;

public class GetEntitiesAggregationRequest extends GetEntitiesRequest {

  private QueryResultAggregationType aggregationType;

  public GetEntitiesAggregationRequest() {

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
    return "GetEntitiesAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
