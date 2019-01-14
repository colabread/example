package com.aidilude.example.utils;

import com.aidilude.example.dto.Point;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class CoordinateUtils {

    //经线（纵向） 向左边越来越小，向右边越来越大，中间0度
    //纬线（横向） 向下越来越小，向上越来越大，中间0度

    /**
     * 默认地球半径（KM）
     */
    private static double EARTH_RADIUS = 6371;

    /**
     * 计算中心经纬度点对应正方形最左侧、右侧的经度，最上侧、下侧的纬度
     * @param longitude   经度
     * @param latitude   纬度
     * @param range   半径（KM）
     * @return
     */
    public static Map<String, Double> calCenterPointEdgeCoordinate(double longitude, double latitude, double range) {
        // 计算经度弧度,从弧度转换为角度
        double dLongitude = 2 * (Math.asin(Math.sin(range / (2 * EARTH_RADIUS)) / Math.cos(Math.toRadians(latitude))));
        dLongitude = Math.toDegrees(dLongitude);
        // 计算纬度角度
        double dLatitude = range / EARTH_RADIUS;
        dLatitude = Math.toDegrees(dLatitude);

        double leftLongitude = longitude - dLongitude;   //左侧经度
        double rightLongitude = longitude + dLongitude;   //右侧经度
        double bottomLatitude = latitude - dLatitude;   //下侧纬度
        double topLatitude = latitude + dLatitude;   //上侧纬度

        Map<String, Double> result = new HashMap<>();
        result.put("leftLongitude", leftLongitude);
        result.put("rightLongitude", rightLongitude);
        result.put("bottomLatitude", bottomLatitude);
        result.put("topLatitude", topLatitude);
        return result;
    }

    /**
     * 计算中心经纬度点对应正方形4个端点坐标
     * @param longitude   经度
     * @param latitude   纬度
     * @param range   半径（KM）
     * @return
     */
    public static Map<String, Point> calCenterPointSquarePoint(double longitude, double latitude, double range){
        Map<String, Double> result = calCenterPointEdgeCoordinate(longitude, latitude, range);
        //正方形
        Point leftTopPoint = new Point(result.get("leftLongitude"), result.get("topLatitude"));
        Point rightTopPoint = new Point(result.get("rightLongitude"), result.get("topLatitude"));
        Point leftBottomPoint = new Point(result.get("leftLongitude"), result.get("bottomLatitude"));
        Point rightBottomPoint = new Point(result.get("rightLongitude"), result.get("bottomLatitude"));
        Map<String, Point> squareMap = new HashMap<String, Point>();
        squareMap.put("leftTopPoint", leftTopPoint);
        squareMap.put("rightTopPoint", rightTopPoint);
        squareMap.put("leftBottomPoint", leftBottomPoint);
        squareMap.put("rightBottomPoint", rightBottomPoint);
        return squareMap;
    }

    public static void main(String[] args) {
//        Map<String, Double> result = calCenterPointEdgeCoordinate(104.070607, 30.582976, 200);
        Map<String, Point> result = calCenterPointSquarePoint(104.070607, 30.582976, 200);
        System.out.println(JSON.toJSONString(result));
    }

}
