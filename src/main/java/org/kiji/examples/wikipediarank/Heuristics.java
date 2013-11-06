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

public class Heuristics {
  
  public static final int NUMBER_OF_PAGES = 5716808;
  public static final int DEADEND_PAGES = 10438;
  public static final double FARM_TAX = 0.15;

  public static final double INITIAL_SCORE = 1.0 / NUMBER_OF_PAGES;
  public static final double RANK_WELFARE = DEADEND_PAGES / NUMBER_OF_PAGES / NUMBER_OF_PAGES
      + FARM_TAX * (NUMBER_OF_PAGES - DEADEND_PAGES) / NUMBER_OF_PAGES / NUMBER_OF_PAGES;

}
