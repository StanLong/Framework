package com.stanlong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 克鲁斯卡尔算法
 */
public class DataStructure {

    private final static int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        char[] vertex = {'1', '2', '3', '4', '5', '6'};
        int[][] weight = {
                       /*1*//*2*//*3*//*4*//*5*//*6*/
                /*1*/ {  0,   6,   1,   5,  INF, INF},
                /*2*/ {  6,   0,   5,  INF,  3,  INF},
                /*3*/ {  1,   5,   0,   5,   6,   4},
                /*4*/ {  5,  INF,  5,   0,  INF,  2},
                /*5*/ { INF, INF,  5,   4,   0,   6},
                /*6*/ { INF, INF,  4,   2,   6,   0}};
        System.out.println("最终结果如下：\n" + kruskal(vertex, weight));
    }

    /**
     * 封装kruskal算法
     * @param vertex 顶点列表
     * @param weight 顶点边的权值
     * @return 最短路径
     */
    public static List<Data> kruskal(char[] vertex, int[][] weight) {
        List<Data> dataList = getSort(vertex, weight);
        System.out.println("排序好的路径为：\n" + dataList);
        MGraph mGraph = new MGraph(vertex, weight, dataList.size());
        System.out.println("矩阵图如下：");
        showGraph(mGraph);
        return  kruskalConnect(mGraph, dataList);
    }

    /**
     * kruskal实现
     * @param mGraph 图对象
     * @param dataList 排序好的路径
     * @return 封装修路的链表
     */
    public static List<Data> kruskalConnect(MGraph mGraph, List<Data> dataList) {
        char[] vertx = mGraph.vertx;
        int edgeNum = mGraph.edgeNum;
        // 表示最后结果数组的索引
        int index = 0;
        int[] connections = new int[mGraph.edgeNum];
        // 创建结果数组
        List<Data> result = new ArrayList<>();

        // 遍历dataList，将边添加到最小生成树中，判断是否生成回路
        for (Data data : dataList) {
            // 获取第data条边的起点
            int p1 = getPosition(vertx, data.start);
            // 获取第data条边的终点
            int p2 = getPosition(vertx, data.end);

            // 获取p1这个顶点在最小数的终点
            int end1 = getEnd(connections, p1);
            // 获取p2这个顶点在最小数的终点
            int end2 = getEnd(connections, p2);

            // 判断是否生成回路
            if (end1 != end2) {
                // 说明没有构成回路
                connections[end1] = end2;
                result.add(data);
            }
        }
        return result;
    }

    /**
     * 算法核心点，获取下标顶点为i的顶点的终点
     * @param connections 连接点的集合
     * @param i 下标
     * @return 下标顶点为i的顶点的终点
     */
    public static int getEnd(int[] connections, int i) {
        while (connections[i] != 0) {
            i = connections[i];
        }
        return i;
    }

    /**
     * 获得某个点对应的数组下标
     * @param vertex 顶点
     * @param value 值
     * @return 某个点对应的数组下标
     */
    public static int getPosition(char[] vertex, char value) {
        if (vertex == null) {
            return -1;
        }
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 边排序
     * @param vertex 顶点数组
     * @param weight 顶点边的权
     * @return 排序后的边列表
     */
    public static List<Data> getSort(char[] vertex, int[][] weight) {
        List<Data> dataList = new ArrayList<>();
        for (int i = 0; i < weight.length; i++) {
            for (int j = i + 1; j < weight.length; j++) {
                if (weight[i][j] != INF) {
                    Data data = new Data(vertex[i], vertex[j], weight[i][j]);
                    dataList.add(data);
                }
            }
        }
        // 进行排序
        Collections.sort(dataList);
        return dataList;
    }

    /**
     * 打印图
     * @param mGraph 图
     */
    public static void showGraph(MGraph mGraph) {
        for (int[] link : mGraph.matrix) {
            for (int x : link) {
                System.out.printf("%5s\t", x);
            }
            System.out.println();
        }
    }
}
class MGraph {

    // 边的个数
    int edgeNum;

    // 顶点数组
    char[] vertx;

    // 邻接矩阵
    int[][] matrix;

    public MGraph(char[] vertx, int[][] matrix, int edgeNum) {
        this.edgeNum = edgeNum;
        this.vertx = vertx.clone();
        this.matrix = matrix.clone();
    }
}

class Data implements Comparable<Object>{

    // 边的起点
    char start;

    // 边的终点
    char end;

    // 边的权重
    int weight;

    public Data(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Data{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Data data = (Data) o;
        return this.weight - data.weight;
    }
}