package kr.ac.tukorea.ge.spgp.framework.util;
import java.lang.Math;

public class MathHelper {
    public static float[] normalize(float x, float y){
        float length = (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        float[] norm = {x / length, y / length};
        return norm;
    }

    public static float distance(float x1, float y1, float x2, float y2){
        float dist = (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return dist;
    }
}

