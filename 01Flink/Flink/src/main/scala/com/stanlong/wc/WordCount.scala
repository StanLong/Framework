package com.stanlong.wc

import org.apache.flink.api.scala._

/**
 * 批处理 WordCount
 */
object WordCount {
    def main(args: Array[String]): Unit = {
        // 创建一个批处理执行环境
        val env = ExecutionEnvironment.getExecutionEnvironment

        // 从文件中读取数据
        val inputPath = "D:\\StanLong\\git_repository\\Framework\\01Flink\\Flink\\src\\main\\resources\\hello.txt"
        val inputDataSet = env.readTextFile(inputPath)

        // 对数据进行转换处理统计, 先分词，再按照word进行分组，最后进行聚合统计
        val resultDataSet = inputDataSet.flatMap(_.split(" "))
          .map((_, 1))
          .groupBy(0) // 以第1个元素作为key进行分组
          .sum(1) // 对当前分组的所有数据的第二个元素进行求和

        // 打印输出
        resultDataSet.print()


    }
}
