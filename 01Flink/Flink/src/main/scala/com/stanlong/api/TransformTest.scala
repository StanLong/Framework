package com.stanlong.api

import org.apache.flink.streaming.api.scala._

/**
 * 滚动聚合算子
 * sum()
 * min()
 * max()
 * minBy()
 * maxBy()
 *
 * Reduce
 *
 * 分流操作 Split 和 Select
 *
 * 合流操作 connect 和 union
 * connect 不要求流的数据类型一致，而 union 要流的数据类型一致才可以合并
 *
 */
object TransformTest {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        // 0. 读取数据
        val inputPath = "D:\\StanLong\\git_repository\\Framework\\01Flink\\Flink\\src\\main\\resources\\sensor.txt"
        val inputStream = env.readTextFile(inputPath)

        // 1. 先转换成样例类类型
        val dataStream = inputStream.map(
            data => {
                val arr = data.split(",")
                SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
            }
        )

        // 2. 根据id分组聚合, 输出每个传感器当前最小值
        val aggStream = dataStream.keyBy("id").minBy("temperature")
        // aggStream.print()

        // 3. 输出当前最小的温度值，以及最新的时间戳，要用reduce
        val resultStream = dataStream.keyBy("id")
          .reduce(
              (curState, newData) => {
                  SensorReading(curState.id, newData.timestamp, curState.temperature.min(newData.temperature))
              }
          )
        // resultStream.print()

        // 4. 多流转换操作
        // 4.1 分流， 将传感器温度数据分为低温，高温两条流
        val splitStream = dataStream.split(
            data => {
                if(data.temperature > 30.0) Seq("high") else Seq("low")
            }
        )
        val highTemperature = splitStream.select("high")
        val lowTemperature = splitStream.select("low")
        val allTemperature = splitStream.select("high", "low")

        // highTemperature.print("high")
        // lowTemperature.print("low")
        // allTemperature.print("all")

        // 4.2 合流， DataStream, DataStream -> ConnectedStreams:
        // 连接两个保持他们类型的数据流，两个数据流被Connect之后，只是被放在了同一个流中，
        // 内部依然保持各自的数据和形式不发生任何变化，两个相互独立
        val waringStream = highTemperature.map( data => (data.id, data.temperature))
        val connectedStreams = waringStream.connect(lowTemperature)

        // 用coMap对数据进行分别处理
        val coMapResultStream = connectedStreams.map(
            waringData => (waringData._1, waringData._2, "warning"),
            lowTempData => (lowTempData.id, "healthy")
        )
        // coMapResultStream.print("coMap")

        // 4.3 union 合流, 要求流的数据类型要一致
        val unionStream = highTemperature.union(lowTemperature)

        unionStream.print("union")





        env.execute("transform test")

    }

}
