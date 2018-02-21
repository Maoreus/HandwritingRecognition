package sample;

/**
 * 图像处理类
 */
public class GraphHelp {


    public static int [][] cut(int [][]src){
        int [][]res1=new int[src.length][src[0].length];
        int row=0,col=0;

        /**
         * 扫描矩阵---------从上到下扫描一遍
         * 在j循环之前flag是0，一旦碰到了不是0的像素点，也就是不是黑色的地方，flag就会变成1
         */
        for(int i=0;i<src.length;i++){
            int flag=0;
            for(int j=0;j<src[0].length;j++){
                res1[row][j]=src[i][j];
                if(src[i][j]!=0)
                    flag=1;
            }
            //如果flag是1（白色）  row++
            if(flag==1)
                row++;
        }

        //扫描矩阵---------从上到下扫描一遍
        int [][]res2=new int[row][src[0].length];
        for(int j=0;j<res1[0].length;j++){
            int flag=0;
            for(int i=0;i<row;i++){
                res2[i][col]=res1[i][j];
                if(res1[i][j]!=0)
                    flag=1;
            }
            if(flag==1)
                col++;
        }
        //从截到的图中选取最长的边作为正方形的边长
        int len=Math.max(row,col);
        int [][]res=new int[len][len];
        int k=0,q=0;

        /**
         * 此处分两种情况
         * 长一点的和扁一点的
         */
        if(row>col){
            /**
             * 填充之前得到的扫描矩阵
             *     **********
             *     ***    ***
             *     ***    ***
             *     ***    ***
             *     **********
             */
            for(int i=0;i<row;i++,k++){
                q=0;
                for(int j=(row-col)/2;j<(row-col)/2+col;j++,q++)
                    res[i][j]=res2[k][q];
            }
        }else{
            for(int i=(col-row)/2;i<(col-row)/2+row;i++,k++){
                q=0;
                for(int j=0;j<col;j++,q++)
                    res[i][j]=res2[k][q];
            }
        }
        return res;
    }


    /**
     * 图像缩放
     * @param w
     * @param h
     * @param Src
     * @return
     */
    public static int [][] PicZoom2(double w,double h,int [][]Src)
    {
        long xrIntFloat_16=(long)((Src[0].length<<16)/w+1); //16.16格式定点数
        long yrIntFloat_16=(long)((Src.length<<16)/h+1); //16.16格式定点数
        //可证明: (Dst.width-1)*xrIntFloat_16<Src.width成立
        int [][]res=new int[(int)h][(int)w];
        for (long y=0;y<h;++y){
            for (long x=0;x<w;++x){
                long srcx=(x*xrIntFloat_16)>>16;
                long srcy=(y*yrIntFloat_16)>>16;
                //Pixels(Dst,x,y)=Pixels(Src,srcx,srcy);
                res[(int)x][(int)y]=Src[(int)srcx][(int)srcy];
            }
        }
        return res;
    }

    public static void print(int [][]src){
        for(int i=0;i<src.length;System.out.println("\n"),i++)
            for(int j=0;j<src[0].length;j++)
                System.out.printf("%4d",src[i][j]);
    }


    public static void main(String[] args){

    }
}
