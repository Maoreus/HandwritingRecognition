package sample;

/**
 * 一个图片  一个标签 相对应
 */
public class DigitImage {
    int label;
    byte[] imageData;

    DigitImage(int label, byte[] imageData){
        this.label=label;
        this.imageData=imageData;
    }

}
