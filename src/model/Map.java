package model;

public abstract class Map {
    protected int rows;
    protected int columns;
    protected char[][] map;
    protected int X;
    protected int Y;
    protected int rocksNum;
    protected int rocksInBagNum = 0;
    protected int trapsNum;
    protected int filledTrapsNum = 0;


    public Map(Robot robot, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        map = new char[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                map[i][j] = '.';
            }
        }
        X = robot.getX();
        Y = robot.getY();
    }

    public abstract void initialize(); // 初始化地图

    public void printMap(Robot robot) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == X && j == Y) {
                    System.out.print(robot.getDirectionPic() + " ");
                }
                else System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void updateMap(Robot robot) {
        //在地图数据中更新机器人的位置
        this.X = robot.getX();
        this.Y = robot.getY();
    }

    public boolean isWon() {
        if(rocksNum == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFailed(Robot robot) {
        if (getMapPoint(robot.getX(), robot.getY()) == '⊙') {
            return true;
        } else {
            return false;
        }
    }
    public int getDistanceToNearestRock() {
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (map[i][j] == '●') {
                    int dist = Math.abs(i - X) + Math.abs(j - Y);
                    if (dist < minDist) {
                        minDist = dist;
                    }
                }
            }
        }
        return minDist;
    }

    //机器人捡起一颗石头后更新地图的参数
    public void updateMapAfterPick(int rockX, int rockY) {
        map[rockX][rockY] = '.';
        rocksNum--;
        rocksInBagNum++;
    }

    //机器人放下一颗石头后更新地图的参数
    public void updateMapAfterPut(int rockX, int rockY) {
        map[rockX][rockY] = '×';
        rocksInBagNum--;
        trapsNum--;
        filledTrapsNum++;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public char getMapPoint(int x, int y) {
        return map[x][y];
    }

    public int getRocksNum() {
        return rocksNum;
    }

    public int getRocksInBagNum() {
        return rocksInBagNum;
    }
}
