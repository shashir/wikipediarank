package org.kiji.examples.wikipediarank;

import java.io.IOException;

import org.kiji.mapreduce.produce.KijiProducer;
import org.kiji.mapreduce.produce.ProducerContext;
import org.kiji.schema.KijiDataRequest;
import org.kiji.schema.KijiRowData;

public class ResetRank extends KijiProducer {
  public static enum Counters {
    NO_OUTLINKS
  }

  /** {@inheritDoc} */
  @Override
  public KijiDataRequest getDataRequest() {
    return KijiDataRequest.create("info", "page");
  }

  /** {@inheritDoc} */
  @Override
  public String getOutputColumn() {
    return "info:score";
  }

  /** {@inheritDoc} */
  @Override
  public void produce(KijiRowData row, ProducerContext context) throws IOException {
    context.put(0, Heuristics.INITIAL_SCORE);
  }
}
