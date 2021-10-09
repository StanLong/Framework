package com.stanlong.wc

import org.apache.flink.streaming.api.scala._

/**
 * 流处理 WordCount
 */
object StreamWordCount {
    def main(args: Array[String]): Unit = {
        // 创建流处理的执行环境
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        // 接收一个 socket 文本流
        val inputDataStream = env.socketTextStream("localhost", 7777)

        // 进行转换处理统计
        val resultDataStream = inputDataStream.flatMap(_.split(" "))
          .filter(_.nonEmpty)
          .map((_,1))
          .keyBy(0)
          .sum(1)

        // 打印输出
        resultDataStream.print()

        // 启动任务执行
        env.execute("stream word count")
    }
}
