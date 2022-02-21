package com.stanlong;

import java.util.*;

/**
 * @author wuyou
 */
public class DataStructure {
    public static  class HuffmanZipData {
        /**
         * 压缩之后的字节数组数据
         */
        byte[] data;
        /**
         * 压缩字符对应的huffman编码Map
         */
        Map<Byte, String> huffmanCode;
        /**
         * 数组压缩前的长度
         */
        int originalLength;
        public HuffmanZipData(byte[] data, Map<Byte, String> huffmanCode, int originalLength) {
            this.data = data;
            this.huffmanCode = huffmanCode;
            this.originalLength = originalLength;
        }
    }

    public static class Node<E> implements Comparable<Node<E>> {
        E data;
        double weight;
        Node leftChild;
        Node rightChild;

        public Node(E data, double weight) {
            super();
            this.data = data;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Node[data=" + data + ", weight=" + weight + "]";
        }

        @Override
        public int compareTo(Node<E> o) {
            return (int) (this.weight - o.weight);
        }
    }

    public static void main(String[] args) {
        String content = "AABBB";
        byte[] bytes = content.getBytes();
        HuffmanZipData huffmanZipData = huffmanZip(bytes);

        byte[] uZip = huffmanUZip(huffmanZipData);
        System.out.println("解码后的字符为：\n" + new String(uZip));
    }

    /**
     * 封装huffman解压缩
     * @param huffmanZipData 压缩对象
     * @return
     */
    private static byte[] huffmanUZip(HuffmanZipData huffmanZipData) {
        String s = bytesToString(huffmanZipData.data, huffmanZipData.originalLength);
        byte[] decode = decode(huffmanZipData.huffmanCode, s);
        return decode;
    }

    /**
     * 封装huffman压缩
     * @param bytes 原始的字节数组
     * @return 压缩后的字节数组
     */
    private static HuffmanZipData huffmanZip(byte[] bytes) {
        List<Node<Byte>> nodes = getNodes(bytes);
        // 根据nodes创建赫夫曼树
        Node<Byte> tree = createHuffmanTree(nodes);
        // 根据赫夫曼树得到赫夫曼编码
        Map<Byte, String> huffmanCode = getHuffmanCode(tree);
        // 根据赫夫曼编码，得到压缩后的赫夫曼编码字节数组
        return zip(bytes, huffmanCode);
    }

    /**
     * 对字符串进行解码
     * @param huffmanCode huffman编码列表
     * @param s
     * @return
     */
    private static byte[] decode(Map<Byte, String> huffmanCode, String s) {
        Map<String, Byte> map = new HashMap<>();
        // 反向赋值map
        for (Map.Entry<Byte, String> entry : huffmanCode.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        List<Byte> list = new ArrayList<>();
        for (int i = 0, j = 0; i < s.length() && j <= s.length();) {
            String temp = s.substring(i, j);
            Byte tempByte = map.get(temp);
            if (tempByte == null) {
                j++;
            } else {
                list.add(tempByte);
                i = j;
            }
        }
        // 因为IO流读取文件是通过数组，所以返回要转成数组
        byte[] result = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 将byte数组转成对应的二进制字符串
     * @param bytes 赫夫曼编码得到的字节数组
     * @return 原来的字符串对应的数组
     */
    private static String bytesToString(byte[] bytes, int originalLength) {
        // 先得到 bytes对应的二进制字符串
        StringBuilder stringBuilder = new StringBuilder();
        // 将byte数组转成二进制的字符串
        // 负数需要符号位，所以一定是8位
        for (int i = 0; i < bytes.length - 1; i++) {
            stringBuilder.append(byteToBitString(bytes[i]));
        }
        stringBuilder.append(byteToBitString(bytes[bytes.length - 1], originalLength));
        return stringBuilder.toString();
    }

    /**
     *
     * 将一个byte 转成一个二进制的字符串
     * @param b 传入的byte
     * @return b对应的二进制的字符串，按照补码返回
     */
    private static String byteToBitString(byte b) {
        // 使用变量保存b
        int temp = b;
        // 如果是正数，需要进行补高位
        // 按位或 1 0000 0000 | 0000 0001 = 1 0000 0001, (256 | 1 = 257), 然后截取后面8位
        temp |= 256;
        // 返回temp对应的二进制的补码
        String s = Integer.toBinaryString(temp);
        return s.substring(s.length() - 8);
    }

    /**
     * 重载方法，处理最后一个字节
     * @param b
     * @param originalLength
     * @return
     */
    private static String byteToBitString(byte b, int originalLength) {
        // 使用变量保存b
        int temp = b;
        // 如果是正数，需要进行补高位
        // 按位或 1 0000 0000 | 0000 0001 = 1 0000 0001, (256 | 1 = 257), 然后截取后面8位
        // 如果是最后一位，并且是非负数，不需要补高位和截取后面8位，但是位数不确定需要判断
        if (b >= 0 && b <= Byte.MAX_VALUE) {
            // 计算需要补位的位数
            int endCount = originalLength % 8;
            // 如果最后也是8位的正数，也需要补齐到8位
            endCount = (endCount == 0 ? 8 : endCount);
            // 得到进行或运算的操作基数，比如补到6位就需要与 100 0000（2^6）进行或运算
            int renRadix = (int) Math.pow(2, endCount);
            temp |= renRadix;
            String s = Integer.toBinaryString(temp);
            return s.substring(s.length() - endCount);
        }
        temp |= 256;
        // 返回temp对应的二进制的补码
        String s = Integer.toBinaryString(temp);
        return s.substring(s.length() - 8);
    }

    /**
     * 将字符数组通过huffman编码进行压缩
     * @param bytes 原始字符串对应的byte[]
     * @param huffmanCode 对应的huffman编码Map
     * @return 处理后的byte[]
     */
    private static HuffmanZipData zip(byte[] bytes, Map<Byte, String> huffmanCode) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(huffmanCode.get(b));
        }
        // 原本的字符串长度
        int originalLength = stringBuilder.length();
        // 通过一个简单的算法获得len，也可以使用三目运算符
        int len = (stringBuilder.length() + 7) / 8;
        byte[] result = new byte[len];
        // 因为每8位对应一个byte，所以步长+8
        for (int i = 0, j = 0; i < stringBuilder.length(); i += 8, j++) {
            String b;
            if (i + 8 > stringBuilder.length()) {
                b = stringBuilder.substring(i);
            } else {
                b = stringBuilder.substring(i, i + 8);
            }
            // 2用于补码转换
            result[j] = (byte) Integer.parseInt(b, 2);
        }
        return new HuffmanZipData(result, huffmanCode, originalLength);
    }

    /**
     * 将tree的所有叶子节点的转换得到对应的赫夫曼编码Map
     * @param tree 传入树的根节点
     * @return
     */
    private static Map<Byte, String> getHuffmanCode(Node<Byte> tree) {
        Map<Byte, String> encodingChar = new HashMap<>();
        encodeChar(tree, "", encodingChar);
        return encodingChar;
    }

    /**
     * 递归方法，具体方法实现获取赫夫曼编码
     * @param node
     * @param encoding
     * @param encodingChar
     */
    private static void encodeChar(Node<Byte> node, String encoding, Map<Byte, String> encodingChar) {
        // 如果是叶子节点直接返回，因为在父节点已经完成了它的编码
        if (isLeaf(node)) {
            encodingChar.put(node.data, encoding);
            return;
        }
        // 左右递归编码
        encodeChar(node.leftChild, encoding + '0', encodingChar);
        encodeChar(node.rightChild, encoding + '1', encodingChar);
    }

    /**
     * 判断当前节点是不是叶子节点
     * @param node
     * @return
     */
    private static boolean isLeaf(Node<Byte> node) {
        if (node.rightChild == null && node.rightChild == null) {
            return true;
        }
        return false;
    }

    /**
     * 创建赫夫曼树
     * @param nodes
     * @return
     */
    private static Node<Byte> createHuffmanTree(List<Node<Byte>> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            // 获取权值最小的两个节点
            Node<Byte> left = nodes.get(0);
            Node<Byte> right = nodes.get(1);
            // 生成新的节点
            Node parent = new Node(null, left.weight + right.weight);

            // 将两个原来节点设置为子节点
            parent.leftChild = left;
            parent.rightChild = right;

            // 删除原来两个节点
            nodes.remove(left);
            nodes.remove(right);

            // 插入父节点
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    /**
     * 获取字节数Node链表
     * @param array
     * @return
     */
    private static List<Node<Byte>> getNodes(byte[] array) {
        List<Node<Byte>> nodes = new ArrayList<>();

        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte b : array) {
            Integer count = counts.get(b);
            if (count != null) {
                counts.put(b, count + 1);
            } else {
                counts.put(b, 1);
            }
        }
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node<>(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    /**
     * 非递归实现前序遍历
     * @param root 根节点
     */
    private static void preOrderTraverse(Node root) {
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (node != null || !stack.empty()) {
            if (node != null) {
                System.out.print(node + " -> ");
                stack.push(node);
                node = node.leftChild;
            } else {
                Node tem = stack.pop();
                node = tem.rightChild;
            }
        }
    }
}
