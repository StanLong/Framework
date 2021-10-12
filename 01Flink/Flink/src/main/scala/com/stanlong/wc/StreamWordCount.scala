package com.stanlong.wc

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._

/**
 * 流处理 WordCount
 */
object StreamWordCount {
    def main(args: Array[String]): Unit = {
        // 创建流处理的执行环境
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        // env.setParallelism(4) 设置并行度， 默认并行度是电脑的核心数量

        // 从外部命令中提取参数，作为 hostname 和 port
        // val paramTool = ParameterTool.fromArgs(args)
        // val host = paramTool.get("host")
        // val port = paramTool.getInt("port")

        // 接收一个 socket 文本流
        val inputDataStream = env.socketTextStream("localhost", 7777)

        // 进行转换处理统计
        val resultDataStream = inputDataStream.flatMap(_.split(" "))
          .filter(_.nonEmpty)
          .map((_,1)) // map((_,1)).setParallelism(4) 可以给每个算子设置并行度
          .keyBy(0)
          .sum(1)

        // 打印输出
        resultDataStream.print()

        // resultDataStream.print().setParallelism(1) // 并行度为1，不显示子任务编号

        // 启动任务执行
        env.execute("stream word count")
    }
}
