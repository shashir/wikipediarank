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
package org.kiji.examples.wikipediarank.bulkimport;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.kiji.examples.wikipediarank.Heuristics;
import org.kiji.mapreduce.KijiTableContext;
import org.kiji.mapreduce.bulkimport.KijiBulkImporter;
import org.kiji.schema.EntityId;

public class PagesBulkImporter extends KijiBulkImporter<LongWritable, Text> {

  /** {@inheritDoc} */
  @Override
  public void produce(LongWritable filePos, Text line, KijiTableContext context)
      throws IOException {
    StringTokenizer tokens = new StringTokenizer(line.toString());

    // Parse line
    final String page = tokens.nextToken();
    final String title = tokens.nextToken();
    final EntityId eid = context.getEntityId(page);

    // Write to table
    context.put(eid, "info", "page", 0, page);
    context.put(eid, "info", "title", 0, title);
    context.put(eid, "info", "score", 0, Heuristics.INITIAL_SCORE);
    context.put(eid, "info", "numoutlinks", 0, 0);
  }
}
