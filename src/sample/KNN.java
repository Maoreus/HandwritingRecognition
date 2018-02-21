package sample;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class KNN {


    /**
     * 以距离作为优先级来比较的
     * 肯定距离越小的优先级越高
     */
    public Comparator<KNNNode> comparator = (o1, o2) -> {
        if (o2.getDistance() > o1.getDistance()){
            return 1;
        }
        else {
            return -1;
        }
    };

    /**
     * 计算两张图片的距离的函数
     * @param d1
     * @param d2
     * @return
     */
    public double calDistance(byte[] d1, byte []d2) {

        /**
         * 图片是28*28的矩阵，我们把它展开成了一行  也就是784个元素的数组
         * 这样一行更好比   byte是因为样本读出来就是byte
         */
        double distance = 0.00;
        for (int i = 0; i < d1.length; i++) {
            byte a=d1[i];
            byte b=d2[i];
            if(a!=0)
                a=1;
            if(b!=0)
                b=1;
            //    distance += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
            distance += (a-b)*(a-b);
        }
        return Math.sqrt(distance);
    }

    /**
     * KNN算法
     * 与样本比对  选k个最近的
     * @param datas     就是我们的样本
     * @param testData    testData  就是我们在画板上画的图片
     * @param k
     * @return
     */
    public int knn(List<DigitImage> datas, DigitImage testData, int k){
        PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);

        //对样本进行处理，去除样本原有的黑边
        for(int i=0;i<datas.size();i++) {
            DigitImage x = datas.get(i);
            //这里是784大小的图
            byte[] tmp = x.imageData;
            //System.out.println(tmp.length);
            /**
             * 我们现在有一个784的图   我们想把边剪掉
             * 但是我们的cut函数  传的是二维数组呀
             * 所以我们先把784的变成28*28的呀   就可以调用cut剪掉啦
             */
            int[][] a = new int[28][28];
            for (int j = 0; j < 28; j++)
                for (int q = 0; q < 28; q++)
                    a[j][q] = tmp[j * 28 + q];
            //剪掉黑边
            a = GraphHelp.cut(a);
            a = GraphHelp.PicZoom2(28, 28, a);
            int c = 0;
            for (int j = 0; j < 28; j++)
                for (int q = 0; q < 28; q++)
                    tmp[c++] = (byte) a[j][q];
        }

        // 找到topK
        for(int i2 = 0; i2 < datas.size(); i2++){
            int index =i2;
            int c2=datas.get(i2).label;
            double distance=calDistance(datas.get(i2).imageData,testData.imageData);
            KNNNode node = new KNNNode(index,distance , c2);
            if(i2<k)
                pq.add(node);
            else{
                if(distance<pq.peek().getDistance()){
                    pq.remove();
                    pq.add(node);
                }
            }
        }
        return getMostClass(pq, k);
    }

    /**
     * 在这k个里  找出现次数最多的一个
     * @param pq
     * @param k
     * @return
     */
    public int getMostClass(PriorityQueue<KNNNode> pq,int k){
        int a[] =new int[k];
        for(int i=0;i<k;i++){
            a[i]=pq.poll().getC();
            //  System.out.println(a[i]);
        }
        //记录次数
        int b[] =new int[10];
        for(int i=0; i<a.length; i++){
            b[a[i]]++;
        }
        //找出现次数最多的
        double max=b[0];
        int dex=0;
        for(int i=0;i<b.length;i++){
            if(b[i]>max){
                max=b[i];
                dex=i;
            }
        }
        return dex;
    }

}
