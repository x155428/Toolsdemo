package src.test;

import src.utils.imgRepair;

import java.io.IOException;

public class test {
    public static void main(String[] args ) throws IOException {
        String input="C:/Users/ะกำใ/Desktop/111.png";
        String output="d:/test.png";
        imgRepair.twobig(input,output);
        imgRepair.binarization(output,output);
        imgRepair.opening(output,output);

    }
}
