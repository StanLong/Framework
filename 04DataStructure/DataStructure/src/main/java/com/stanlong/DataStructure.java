package com.stanlong;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 图的快速入门
 * 这是一个不带权的无向图， 邻接矩阵延左上右下对称
 * 思路分析
 * 1. 使用 ArrayList 存储图的顶点
 * 2. 使用二维数组保存矩阵
 *
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        Graph graph = new Graph(5);
        String[] vertexes = {"A","B","C","D","E"};

        // 添加节点
        for (String vertex : vertexes){
            graph.insertVertex(vertex);
        }

        // 添加边
        // A-B A-C B-C B-D B-E
        graph.insertEdges(0, 1,1);
        graph.insertEdges(0, 2,1);
        graph.insertEdges(1, 2,1);
        graph.insertEdges(1, 3,1);
        graph.insertEdges(1, 4,1);

        // 遍历
        graph.showGraph();

        // 深度遍历
        System.out.println("----深度优先----");
        graph.dfs();


    }
}

class Graph{
    private ArrayList<String> vertexList; // 存储定点
    private int[][] edges;  // 存储图对应的邻接矩阵的边
    private int numOfEdges; // 边的数目
    private boolean[] isVisited;  // 记录被访问过的节点

    // 构造器初始化
    public Graph(int n){
        edges = new int[n][n];
        vertexList = new ArrayList<String>(n);
        numOfEdges = 0;
        isVisited = new boolean[n];
    }

    // 插入顶点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 插入边
     * @param v1 边的一个节点对应的下标
     * @param v2 边的另一个节点对应的下标
     * @param weight 权重。 1 表示有连接， 0 表示无连接
     */
    public void insertEdges(int v1, int v2, int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    // 返回节点的个数
    public int getVertex(){
        return vertexList.size();
    }

    // 返回边的数目
    public int getNumOfEdges(){
        return numOfEdges;
    }

    // 返回节点对应的数据
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    // 返回V1和V2的权值
    public int getWeight(int v1, int v2){
        return edges[v1][v2];
    }

    // 返回图对应的矩阵
    public void showGraph(){
        for(int[] link : edges){
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 获取 index 邻接节点的下标
     * @param index index节点的下标
     * @return index 邻接节点的下标
     */
    public int getFirstNeighbour(int index){
        for(int j =0; j<vertexList.size(); j++){
            if(edges[index][j]>0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标获取下一个邻接节点
     * @param v1 前一个邻接节点的下标
     * @param v2 前一个邻接节点的下标
     * @return 下一个邻接节点的下标
     */
    public int getNextNeighbour(int v1, int v2){
        for(int j = v2 + 1; j<vertexList.size(); j++){
            if(edges[v1][j] > 0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 深度遍历
     * @param isVisited
     * @param i
     */
    public void dfs(boolean[] isVisited, int i){
        // 访问节点
        System.out.print(getValueByIndex(i) + "->");
        // 节点标记为已访问
        isVisited[i] = true;
        // 查找i节点的第一个邻接节点w
        int w = getFirstNeighbour(i);
        while (w != -1){
            if(!isVisited[w]){
                dfs(isVisited, w);
            }
            // 如果 w 已经被访问过， 就需要查找邻接节点的下一个节点
            w = getNextNeighbour(i, w);
        }
    }

    // 重载
    public void dfs(){
        // 遍历所有的节点进行dfs
        for(int i=0; i<getNumOfEdges(); i++){
            if(!isVisited[i]){
                dfs(isVisited, i);
            }
        }
    }
}