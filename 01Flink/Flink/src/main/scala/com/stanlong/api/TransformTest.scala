package com.stanlong.api

import org.apache.flink.streaming.api.scala._

object TransformTest {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        val inputPath = "D:\\StanLong\\git_repository\\Framework\\01Flink\\Flink\\src\\main\\resources\\sensor.txt"
        val inputStream = env.readTextFile(inputPath)



        // 先转换成样例类类型
        val dataStream = inputStream.map(
            data => {
                val arr = data.split(",")
                SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
            }
        )

        // 根据id分组聚合, 输出每个传感器当前最小值
        val aggStream = dataStream.keyBy("id").minBy("temperature")

        // 输出当前最小的温度值，以及最新的时间戳，要用reduce
        val resultStream = dataStream.keyBy("id")
          .reduce(
              (curState, newData) => {
                  SensorReading(curState.id, newData.timestamp, curState.temperature.min(newData.temperature))
              }
          )

        // aggStream.print()

        resultStream.print()

        env.execute("transform test")

    }

}
