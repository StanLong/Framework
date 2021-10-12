package com.stanlong.api

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011

/**
 * Source数据源
 * 1. 从集合中读取数据源
 * 2. 从文件中读取数据
 * 3. 从kafka中读取数据
 */
object SourceTest {
    def main(args: Array[String]): Unit = {
        // 创建执行环境
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        // 1. 从集合中读取数据源
        // val dataList = List(
        //    SensorReading("sensor_1", 1547718199, 35.8),
        //     SensorReading("sensor_6", 1547718201, 15.4),
        //     SensorReading("sensor_7", 1547718202, 6.7),
        //     SensorReading("sensor_10", 1547718205, 38.1),
        // )

        // val stream1 = env.fromCollection(dataList)
        // stream1.print()


        // 2.从文件中读取数据
        // val inputPath = "D:\\StanLong\\git_repository\\Framework\\01Flink\\Flink\\src\\main\\resources\\sensor.txt"
        // val stream2 = env.readTextFile(inputPath)
        // stream2.print()

        // 3. 从kafka中读取数据
        //  需要引入kafka连接器的依赖
        val properties = new Properties()
        properties.setProperty("bootstrap.servers", "node01:9092, node02:9092, node03:9092, node04:9092")
        properties.setProperty("group.id", "consumer-group")

        val stream3 = env.addSource(new FlinkKafkaConsumer011[String]("sensor", new SimpleStringSchema(), properties))
        stream3.print()


        // 执行
        env.execute("source test")


    }

}

// 定义样例类，温度传感器
case class SensorReading(id:String, timestamp:Long, temperature:Double)
