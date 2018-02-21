package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * 画板类
 */
public class DrawBorad extends Pane{
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    private static Color color = Color.WHITE;
    /**
     * 描点的起点和终点
     */
    double s1,s2,e1,e2;


    //画板
    Canvas canvas;

    public DrawBorad(){
        //画板
        canvas = new Canvas(WIDTH, HEIGHT);
        //画笔就是GraphisContext
        GraphicsContext brush = canvas.getGraphicsContext2D();
        brush.fillRect(0,0, WIDTH, HEIGHT);
        this.getChildren().add(canvas);

        //记录起点
        canvas.setOnMousePressed(e->{
            s1=e.getX();
            s2=e.getY();
        });

        //拖拽
        canvas.setOnMouseDragged(e->{
            e1=e.getX();
            e2=e.getY();
            brush.save();
            brush.setStroke(color);
            brush.setLineWidth(15);                //设置线的宽度
            brush.strokeLine(s1,s2,e1,e2);       //绘制直线
            brush.restore();
            s1=e1;
            s2=e2;
        });

    }

    public void clear(){
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.fillRect(0,0,WIDTH,HEIGHT);
    }


    /**
     * 返回图像上的灰度矩阵
     * @return
     */
    public int[][] save(){
        //图片对象
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

        int[][] mat = new int[(int)image.getHeight()][(int)image.getWidth()];
        // PixelReader就是像素矩阵
        PixelReader pr = image.getPixelReader();
        int flag = 0;
        //遍历矩阵
        for(int i = 0; i < image.getHeight(); i++)
            for(int j = 0;j < image.getWidth(); j++){
            //灰度=r+g+b / 3
                int gray = (int)((pr.getColor(j, i).getGreen() + pr.getColor(j, i).getBlue()+pr.getColor(j, i).getRed())/3*255);
                mat[i][j] = gray;
                if(gray != 0)
                    flag = 1;
                //System.out.printf("%4d",gray);
            }
        if (flag == 0) return null;
        return mat;
    }
}
