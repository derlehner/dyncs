package at.ac.tuwien.big.ame.dyncs.server.dto.query;

import at.ac.tuwien.big.ame.dyncs.server.dto.query.aggregation.QueryResultAggregationType;

public class GetRelationsAggregationRequest extends GetRelationsRequest {

  private QueryResultAggregationType aggregationType;

  public GetRelationsAggregationRequest() {
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
    return "GetRelationsAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
