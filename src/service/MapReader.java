package service;

import java.util.ArrayList;
import java.util.Scanner;

public class MapReader {
    public char[][] map;
    public int rows;
    public int columns;
    public int rocksNum;
    public int trapsNum;
    public int robotDirection;
    public int robotX = -1;
    public int robotY = -1;
    public boolean legal = false;
    public int robotNum = 0;

    public MapReader() {
        while (!legal) {
            reset(); // 每次循环先初始化
            setMap();
        }
    }

    public void setMap() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> inputMap = new ArrayList<>();

        // 读取命令行中输入的地图
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            // 移除该行中的所有空格
            String lineWithoutSpaces = line.replaceAll(" ", "");
            inputMap.add(lineWithoutSpaces);
        }


        // 获取地图的行数和列数
        this.rows = inputMap.size();
        if  (this.rows == 0) {
            System.out.println("You have not input the map! Please input the map again!");
            return;
        }
//        System.out.println(this.rows); // debug

        int numCols = inputMap.get(0).length();

        for (String line : inputMap) {
            int cols = line.length();
            if (cols != numCols) {
                System.out.println("The number of elements in each line is not the same! Please input the map again!");
                return;
            }
        }

        this.columns = numCols;
//        System.out.println(this.columns); // debug

        // 创建对应大小的二维数组
        this.map = new char[rows][columns];

        // 将每一行地图的字符串表示转换为字符数组，并去除空格后赋值给二维数组
        for (int i = 0; i < rows; i++) {
            char[] line = inputMap.get(i).toCharArray();
            for (int j = 0; j < columns; j++) {
                map[i][j] = line[j];
                if (map[i][j] != '.' && map[i][j] != '■' && map[i][j] != '●' && map[i][j] != '⊙' && map[i][j] != '►' && map[i][j] != '▲'
                        && map[i][j] != '◄' && map[i][j] != '▼') {
                    System.out.println("The map contains illegal characters! Please input the map again!");
                    return;
                }
                detectElement(map[i][j], i, j);
            }
        }

//        System.out.println("rocksNum: " + rocksNum); // debug
//        System.out.println("trapsNum: " + trapsNum); // debug
//        System.out.println("robotDirection: " + robotDirection); // debug
//        System.out.println("robotX: " + robotX); // debug
//        System.out.println("robotY: " + robotY); // debug

//        // debug 输出二维数组，检查地图是否正确读取
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }

        // 检查地图是否有且仅有一个机器人
        if (robotX == -1 || robotY == -1) {
            System.out.println("The map does not contain the robot! Please input the map again!");
            return;
        }
        if (robotNum > 1) {
            System.out.println("The map contains more than one robot! Please input the map again!");
            return;
        }

        legal = true;
    }

    public void detectElement (char element, int x, int y) {
        switch (element) {
            case '●' -> {
                this.rocksNum++;
            }
            case '⊙' -> {
                this.trapsNum++;
            }
            case '►' -> {
                this.robotDirection = 0;
                this.robotX = x;
                this.robotY = y;
                robotNum++;
            }
            case '▲' -> {
                this.robotDirection = 1;
                this.robotX = x;
                this.robotY = y;
                robotNum++;
            }
            case '◄' -> {
                this.robotDirection = 2;
                this.robotX = x;
                this.robotY = y;
                robotNum++;
            }
            case '▼' -> {
                this.robotDirection = 3;
                this.robotX = x;
                this.robotY = y;
                robotNum++;
            }
        }

    }

    public void reset() {
        this.map = null;
        this.rows = 0;
        this.columns = 0;
        this.rocksNum = 0;
        this.trapsNum = 0;
        this.robotDirection = 0;
        this.robotX = -1;
        this.robotY = -1;
        this.legal = false;
        this.robotNum = 0;
    }

}
