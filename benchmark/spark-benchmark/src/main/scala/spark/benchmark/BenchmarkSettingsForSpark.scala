package spark.benchmark

import com.typesafe.config.{Config, ConfigFactory}
import common.config.GeneralConfig
import common.config.JobExecutionMode.{CONSTANT_RATE, LATENCY_CONSTANT_RATE}
import common.config.LastStage.{NON_INCREMENTAL_WINDOW_WITHOUT_JOIN, REDUCE_WINDOW_WITHOUT_JOIN}

import scala.collection.JavaConverters._

object BenchmarkSettingsForSpark {

  implicit class OptionValue(val value: Option[String]) extends AnyVal {
    def keyedWith(key: String): Option[(String, String)] = value.map(v => key -> v)
  }

}

class BenchmarkSettingsForSpark(overrides: Map[String, Any] = Map()) extends Serializable {

  val general = new GeneralConfig(overrides)

  object specific extends Serializable {
    private val sparkProperties: Config = ConfigFactory.load()
      .withFallback(ConfigFactory.parseMap(overrides.asJava))
      .withFallback(ConfigFactory.load("spark.conf"))
      .getConfig("spark")
      .getConfig(general.mode.name)

    val checkpointDir: String = if (general.local) general.configProperties.getString("spark.checkpoint.dir")
    else "hdfs://" + general.hdfsActiveNameNode + "/checkpointDirSpark" + general.outputTopic + "/"

    val sparkMaster: String = general.configProperties.getString("spark.master")

    val batchInterval: Int = sparkProperties.getInt("batchInterval")

    val defaultParallelism: Int = general.configProperties.getInt("spark.default.parallelism")
    val sqlShufflePartitions: Int = general.configProperties.getInt("spark.sql.shuffle.partitions")
    val blockInterval: Int = Math.max(batchInterval/defaultParallelism, 50)
    val localityWait: Int = sparkProperties.getInt("locality.wait")
    val writeAheadLogEnabled: Boolean = sparkProperties.getBoolean("spark.streaming.receiver.writeAheadLog.enable")
    val jobProfileKey: String = general.mkJobProfileKey("spark", batchInterval)
  }
}
