package com.stanlong.api

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011

import scala.util.Random

/**
 * Source数据源
 * 1. 从集合中读取数据源
 * 2. 从文件中读取数据
 * 3. 从kafka中读取数据
 * 4. 自定义Source
 */
object SourceTest {
    def main(args: Array[String]): Unit = {
        // 创建执行环境
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        // env.setParallelism(1) // 全局并行度设置成1，解决乱序问题

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
        // val properties = new Properties()
        // properties.setProperty("bootstrap.servers", "node01:9092, node02:9092, node03:9092, node04:9092")
        // properties.setProperty("group.id", "consumer-group")

        // val stream3 = env.addSource(new FlinkKafkaConsumer011[String]("sensor", new SimpleStringSchema(), properties))
        // stream3.print()

        // 4. 自定义Source
        val stream4 = env.addSource(new MySensorSource())
        stream4.print()

        // 执行
        env.execute("source test")
    }
}

// 定义样例类，温度传感器
case class SensorReading(id:String, timestamp:Long, temperature:Double)

// 自定义Source
class MySensorSource() extends SourceFunction[SensorReading]{

    // 定义一个flag，用来表示数据源是否正常运行发出数据
    var running = true

    // 定义一个随机数发生器
    val rand = new Random()

    // 随机生成一组（10个） 传感器的初始温度
    var currentTemp = 1.to(10).map(i => ("sensor_" + i, rand.nextDouble() * 100))

    override def run(sourceContext: SourceFunction.SourceContext[SensorReading]): Unit = {
        // 定义无限循环，不停的产生数据，除非被cancel
        while (running){
            // 在上次数据基础上微调更新温度值
            currentTemp = currentTemp.map(
                data => (data._1, data._2 + rand.nextGaussian())
            )

            // 获取当前时间戳，加入到数据中
            val currentTime = System.currentTimeMillis()
            currentTemp.foreach(
                data => sourceContext.collect(SensorReading(data._1, currentTime, data._2))
            )
            // 间隔100ms
            Thread.sleep(100)
        }

    }

    override def cancel(): Unit = {
        running = false
    }
}
