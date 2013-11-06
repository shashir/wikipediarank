/**
 * (c) Copyright 2013 WibiData, Inc.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kiji.examples.wikipediarank;

import java.io.IOException;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import org.kiji.mapreduce.gather.GathererContext;
import org.kiji.mapreduce.gather.KijiGatherer;
import org.kiji.schema.KijiDataRequest;
import org.kiji.schema.KijiDataRequestBuilder;
import org.kiji.schema.KijiRowData;


public class RankRedistributor extends KijiGatherer<Text, DoubleWritable> {

  private Text mLink;

  private DoubleWritable mRank;

  /** {@inheritDoc} */
  @Override
  public Class<?> getOutputKeyClass() {
    return Text.class;
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getOutputValueClass() {
    return DoubleWritable.class;
  }

  /** {@inheritDoc} */
  @Override
  public void setup(GathererContext<Text, DoubleWritable> context) throws IOException {
    super.setup(context);
    mLink = new Text();
    mRank = new DoubleWritable();
  }

  /** {@inheritDoc} */
  @Override
  public KijiDataRequest getDataRequest() {

    final KijiDataRequestBuilder builder = KijiDataRequest.builder();
    builder.newColumnsDef()
        .withMaxVersions(HConstants.ALL_VERSIONS)
        .add("info", "page")
        .add("info", "links")
        .add("info", "score")
        .add("info", "numoutlinks");
    return builder.build();
  }

  /** {@inheritDoc} */
  @Override
  public void gather(KijiRowData row, GathererContext<Text, DoubleWritable> context)
      throws IOException {
    mLink.set(row.getMostRecentValue("info", "page").toString());
    mRank.set(0.0);
    context.write(mLink, mRank);
    if (row.containsColumn("info", "links")) {
      NavigableMap<Long, CharSequence> links = row.getValues("info", "links");
      mRank.set(((Double) row.getMostRecentValue("info", "score")) 
          / Double.parseDouble(row.getMostRecentValue("info", "numoutlinks").toString()));
      for (CharSequence link : links.values()) {
        mLink.set(link.toString());
        context.write(mLink, mRank);
      }
    }
    mLink.clear();
  }

}
