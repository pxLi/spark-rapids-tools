/*
 * Copyright (c) 2024, NVIDIA CORPORATION.
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

package com.nvidia.spark.rapids.tool.tuning

import com.nvidia.spark.rapids.tool.{AppSummaryInfoBaseProvider, Platform}
import com.nvidia.spark.rapids.tool.profiling.DriverLogInfoProvider

/**
 * Implementation of the `AutoTuner` designed the Qualification Tool. This class can be used to
 * implement the logic to recommend AutoTuner configurations by the Qualification Tool.
 */
class QualificationAutoTuner(
    clusterProps: ClusterProperties,
    appInfoProvider: AppSummaryInfoBaseProvider,
    platform: Platform,
    driverInfoProvider: DriverLogInfoProvider)
  extends AutoTuner(clusterProps, appInfoProvider, platform, driverInfoProvider,
    QualificationAutoTunerConfigsProvider)

/**
 * Provides configuration settings for the Qualification Tool's AutoTuner
 */
object QualificationAutoTunerConfigsProvider extends AutoTunerConfigsProvider {

  // For qualification tool's auto-tuner, the batch size to be recommended is 1GB
  // See https://github.com/NVIDIA/spark-rapids-tools/issues/1399
  override val BATCH_SIZE_BYTES = 1073741824

  override def createAutoTunerInstance(
      clusterProps: ClusterProperties,
      appInfoProvider: AppSummaryInfoBaseProvider,
      platform: Platform,
      driverInfoProvider: DriverLogInfoProvider): AutoTuner = {
    new QualificationAutoTuner(clusterProps, appInfoProvider, platform, driverInfoProvider)
  }
}
