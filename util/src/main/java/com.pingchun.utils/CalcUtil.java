package com.pingchun.utils;

public class CalcUtil {

    public static double windSpeed(double u, double v){
        return Math.sqrt(Math.pow(u, 2) + Math.pow(v, 2));
    }

    public static double windDirection(double u, double v){
        double dird = Math.atan(u / v) * 180 / Math.PI;
        if (u != 0 && v < 0) {
            dird = dird + 180;
        } else if (u < 0 && v > 0) {
            dird = dird + 360;
        } else if (u == 0 && v > 0) {
            dird = 0;
        } else if (u == 0 && v < 0) {
            dird = 180;
        } else if (u > 0 && v == 0) {
            dird = 90;
        } else if (u < 0 && v == 0) {
            dird = 270;
        } else if (u == 0 && v == 0) {
            dird = -999;
        }
        return dird;
    }

    public static int getWindSpeedLevel(double windSpeed){
        if (windSpeed >= 0 && windSpeed < 0.3){
            return 0;
        }else if (windSpeed >= 0.3 && windSpeed < 1.6){
            return 1;
        }else if (windSpeed >= 1.6 && windSpeed < 3.4){
            return 2;
        }else if (windSpeed >= 3.4 && windSpeed < 5.5){
            return 3;
        }else if (windSpeed >= 5.5 && windSpeed < 8){
            return 4;
        }else if (windSpeed >= 8 && windSpeed < 10.8){
            return 5;
        }else if (windSpeed >= 10.8 && windSpeed < 13.9){
            return 6;
        }else if (windSpeed >= 13.9 && windSpeed < 17.2){
            return 7;
        }else if (windSpeed >= 17.2 && windSpeed < 20.8){
            return 8;
        }else if (windSpeed >= 20.8 && windSpeed < 24.5){
            return 9;
        }else if (windSpeed >= 24.5 && windSpeed < 28.5){
            return 10;
        }else if (windSpeed >= 28.5 && windSpeed < 32.7){
            return 11;
        }else if (windSpeed >= 32.7 && windSpeed < 37){
            return 12;
        }else if (windSpeed >= 37 && windSpeed < 41.5){
            return 13;
        }else if (windSpeed >= 41.5 && windSpeed < 46.2){
            return 14;
        }else if (windSpeed >= 46.2 && windSpeed < 51){
            return 15;
        }else if (windSpeed >= 51 && windSpeed < 56.1){
            return 16;
        }else{
            return 17;
        }
    }

    public static String getWindDirectionStr(double dird){
        if (dird > 0 && dird < 90){
            return "东北风";
        }else if (dird == 90){
            return "东风";
        }else if (dird > 90 && dird < 180){
            return "东南风";
        }else if (dird == 180){
            return "南风";
        }else if (dird > 180 && dird < 270){
            return "西南风";
        }else if (dird == 270){
            return "西风";
        }else if (dird > 270 && dird < 360){
            return "西北风";
        }else{
            return "北风";
        }
    }
}
