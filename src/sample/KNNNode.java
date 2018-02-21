package sample;

/**
 * 这个node 记录的其实是  目标与所有的样本比对产生de距离
 * distance就是距离   然后c就是样本的所属类别
 */
public class KNNNode {
    private  int index; // 元组标号
    private  double distance; // 与测试元组的距离
    private  int c; // 所属类别
    public KNNNode(int index, double distance, int c) {
        this.index = index;
        this.distance = distance;
        this.c = c;
    }
    public int getIndex() {
        return index;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public int getC() {
        return c;
    }
    public void setC(int c) {
        this.c = c;
    }
}
