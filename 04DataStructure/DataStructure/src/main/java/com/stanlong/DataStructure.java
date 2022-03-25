package com.stanlong;

/**
 * 迪杰斯特拉算法
 */
public class DataStructure {

    private final static int N = 10000;

    public static void main(String[] args) throws Exception {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F'};
        int[][] weight = {
                     /*A*B*C*D*E*F*/
                /*A*/ {N,6,3,N,N,N},
                /*B*/ {6,N,2,5,N,N},
                /*C*/ {3,2,N,3,4,N},
                /*D*/ {N,5,3,N,2,3},
                /*E*/ {N,N,4,2,N,5},
                /*F*/ {N,N,N,3,5,N}};
        Graph graph = new Graph(vertex, weight);
        showGraph(graph);
        int start = 0;
        int[] dij = dij(graph, 0);
        for (int i = 0; i < dij.length; i++) {
            System.out.println("从" + graph.vertex[start] + "出发到" + graph.vertex[i] + "的最短距离为：" + dij[i]);
        }
    }

    public static void showGraph(Graph graph) {
        for (int[] link : graph.matrix) {
            for (int x : link) {
                System.out.printf("%5s\t", x);
            }
            System.out.println();
        }
    }

    /**
     * dij算法
     * @param graph
     * @param start
     * @return
     */
    public static int[] dij(Graph graph, int start) {
        // 权值数组
        int[][] weight = graph.matrix.clone();
        // 顶点数组
        char[] names = graph.vertex;
        // 顶点个数
        int n = names.length;
        // 标记当前顶点的最短路径是否已经求出，1表示已经求出
        int[] visited = new int[n];
        // 保存start到其他各点的最短路径
        int[] shortPath = new int[n];

        // 保存start到其他各点最短路径的字符串表示
        String[] path = new String[n];
        for (int i = 0; i < n; i++) {
            path[i] = names[start] + " => " + names[i];
        }

        // 初始化第一个顶点
        shortPath[start] = 0;
        visited[start] = 1;

        // 加入剩下的节点
        for (int count = 1; count < n; count++) {
            // 标记距离初始顶点start最近的未标记的节点
            int minIndex = -1;
            int minWeight = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (visited[i] == 0 && weight[start][i] <= minWeight) {
                    minIndex = i;
                    minWeight = weight[start][i];
                }
            }

            // 将新选出的顶点标记为已求出最短路径，且到start 最短路径就是minWeight
            visited[minIndex] = 1;
            shortPath[minIndex] = minWeight;

            // 以k为中间点，修正从start到未访问各点的距离
            for (int i = 0; i < n; i++) {
                // 代码核心！！！
                // 如果'起始点到当前点距离' + '当前点到某点距离' < '起始点到某点距离', 则更新
                if (visited[i] ==0 && weight[start][minIndex] + weight[minIndex][i] < weight[start][i]) {
                    weight[start][i] = weight[start][minIndex] + weight[minIndex][i];
                    path[i] = path[minIndex] + " => " + names[i];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println("从" + names[start] + "出发到" + names[i] + "的最短路径为：" + path[i]);
        }
        return shortPath;
    }
}
class Graph {
    /**
     * 顶点数组
     */
    char[] vertex;
    /**
     * 邻接矩阵
     */
    int[][] matrix;

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex.clone();
        this.matrix = matrix.clone();
    }

}