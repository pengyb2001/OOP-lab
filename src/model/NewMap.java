package model;

import service.MapReader;

public class NewMap extends Map {
    public NewMap(Robot robot, MapReader mapReader) {
        super(robot, mapReader.rows, mapReader.columns);

        for (int i = 0; i < mapReader.rows; i++) {
            for (int j = 0; j < mapReader.columns; j++) {
                // 因为地图和机器人的图标是分开储存，在地图中机器人的位置用'.'代替
                if(i == mapReader.robotX && j == mapReader.robotY) {
                    map[i][j] = '.';
                } else {
                    map[i][j] = mapReader.map[i][j];
                }
//                System.out.println("mapReader为" + mapReader.map[i][j] + " map为" + map[i][j] + " "); //debug
                rocksNum = mapReader.rocksNum;
                trapsNum = mapReader.trapsNum;
            }
        }
//        System.out.println("NewMap: "); //debug
//        printMap(robot); // debug
//        System.out.println("End"); //debug
    }

    @Override
    public void initialize() {
        // 空实现
    }

}
