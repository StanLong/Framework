package com.stanlong;

import java.util.Arrays;

/**
 * 弗洛伊德算法
 */
public class DataStructure {

    private final static int N = 65535;

    public static void main(String[] args) {
        char[] vertex = {'0', '1', '2', '3'};
        int[][] weight = {
                    /*0*1*2*3*/
                /*0*/{0,5,N,7},
                /*1*/{N,0,4,2},
                /*2*/{3,3,0,2},
                /*3*/{N,N,1,0}};
        Graph graph = new Graph(vertex, weight);
        showGraph(graph);
        floyd(graph);
        System.out.println();
        showGraph(graph);
    }

    public static void showGraph(Graph graph) {
        for (int i = 0; i < graph.vertex.length; i++) {
            System.out.printf("%8s", graph.vertex[i] + "的前驱节点为：");
            for (int j = 0; j < graph.vertex.length; j++) {
                System.out.printf("%5s\t", graph.pre[i][j]);
            }
            System.out.println();
            System.out.printf("%8s", graph.vertex[i] + "的各点权值为：");
            for (int j = 0; j < graph.vertex.length; j++) {
                System.out.printf("%5s\t", graph.dis[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 弗洛伊德算法
     * @param graph
     */
    public static void floyd(Graph graph) {
        int length = graph.vertex.length;
        int[][] dis = graph.dis;
        int[][] pre = graph.pre;
        // 变量保存距离
        int len = 0;
        for (int i = 0; i < length; i++) {
            // 从j顶点出发
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length; k++) {
                    // 从i顶点出发，经过中间顶点k，到达j
                    len = dis[j][i] + dis[i][k];
                    if (len < dis[j][k]) {
                        // 更新距离
                        dis[j][k] = len;
                        // 更新前驱
                        pre[j][k] = pre[i][k];
                    }
                }
            }
        }
    }


}

class Graph {
    // 存放顶点的数组
    char[] vertex;

    // 保存从各个顶点出发到其他顶点的距离
    int[][] dis;

    // 保存到目标顶点的前驱节点
    int[][] pre;

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex.clone();
        this.dis = matrix.clone();
        this.pre = new int[vertex.length][vertex.length];
        // 对pre数组初始化
        for (int i = 0; i < vertex.length; i++) {
            Arrays.fill(pre[i], i);
        }
    }
}