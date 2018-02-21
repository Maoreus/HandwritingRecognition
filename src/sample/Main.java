package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DrawBorad;
import javafx.scene.control.Button;

import java.io.IOException;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        DrawBorad drawBorad = new DrawBorad();
        Pane pane = new Pane();
        pane.getChildren().add(drawBorad);
        /*
        stage 是舞台,这个舞台上能放一个Scene,然后 Scene上面可以放无数个pane
         */
        /**
         * 按钮
         */
        Button btClear=new Button("清   空");
        btClear.setPrefWidth(64);
        btClear.setOnAction(e ->
                drawBorad.clear()
        );
        Button btRecgnize=new Button("识   别");
        btRecgnize.setPrefWidth(64);
        //识别按钮的监控事件
        btRecgnize.setOnAction(event -> {
                    int[][] matrix = drawBorad.save();
                    if (matrix == null) {
                        return;
                    }

                    matrix = GraphHelp.cut(matrix);
                    matrix = GraphHelp.PicZoom2(28, 28, matrix);
                    //GraphHelp.print(matrix);

                    //将int数组转为byte类型
                    byte []a=new byte[784];
                    int c=0;
                    for(int i=0;i<matrix.length;i++)
                        for(int j=0;j<matrix[0].length;j++)
                            a[c++]=(byte)(matrix[i][j]==0?0:1);
                    ReadFile rf1=new ReadFile("train-labels.idx1-ubyte","train-images.idx3-ubyte");
                    try {
                        rf1.loadDigitImages();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    KNN k=new KNN();
                    DigitImage x=new DigitImage(0,a);
                    int guessNum=k.knn(rf1.images,x, 4);
                    System.out.println(guessNum);

                }
        );

        VBox v=new VBox(20);
        v.getChildren().addAll(btClear,btRecgnize);

        HBox h=new HBox(20);
        h.setPadding(new Insets(20,20,20,20));
        h.getChildren().addAll(drawBorad,v);

        Scene scene=new Scene(h);
        primaryStage.setTitle("手写数字识别");
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);


    }
}
