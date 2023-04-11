package model;


public class Robot {
    //机器人的位置可以用x和y坐标表示，方向可以用一个整数表示，0表示向右，1表示向上，2表示向左，3表示向下。
    private static int x;
    private static int y;
    private static int direction;

    public Robot() {
        x = 2;
        y = 0;
        direction = 0;
    }

    public Robot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    private int getNextX(Map map) {
        //返回-1表示以目前的方向走下一步会撞到边界
        switch(direction) {
            case 0 -> {
                if(y == map.getColumns() - 1)
                    return -1;
                else return x;
            }
            case 1 -> {
                if(x == 0)
                    return -1;
                else return x - 1;
            }
            case 2 -> {
                if(y == 0)
                    return -1;
                else return x;
            }
            case 3 -> {
                if(x == map.getRows() - 1)
                    return -1;
                else return x + 1;
            }
            default -> {
                return -1;
            }
        }
    }

    private int getNextY(Map map) {
        //返回-1表示以目前的方向走下一步会撞到边界
        switch(direction) {
            case 0 -> {
                if(y == map.getColumns() - 1)
                    return -1;
                else return y + 1;
            }
            case 1 -> {
                if(x == 0)
                    return -1;
                else return y;
            }
            case 2 -> {
                if(y == 0)
                    return -1;
                else return y - 1;
            }
            case 3 -> {
                if(x == map.getRows() - 1)
                    return -1;
                else return y;
            }
            default -> {
                return -1;
            }
        }
    }

    public void move(Map map) {
        int nextX = getNextX(map);
        int nextY = getNextY(map);
        if(nextX == -1 || nextY == -1) {
            System.out.println("Can't move, hit the wall!");
        } else if (map.getMapPoint(nextX, nextY) == '●') {
            System.out.println("Can't move, hit the rock!");
        } else {
            x = nextX;
            y = nextY;
        }
    }



    public void turnLeft() {
        direction = (direction + 1) % 4;
    }

    public void showInformation(Map map) {
        if(map.getRocksNum() == 1)
            System.out.println("There is " + map.getRocksNum() + " rock on the map that you need to collect.");
        else if(map.getRocksNum() > 1){
            System.out.println("There are " + map.getRocksNum() + " rocks on the map that you need to collect.");
        } else {
            System.out.println("There is no rock on the map.");
        }

        if(map.getRocksInBagNum() == 0) {
            System.out.println("You have no rock in your bag.");
        }
        else if(map.getRocksInBagNum() == 1) {
            System.out.println("You have " + map.getRocksInBagNum() + " rock in your bag.");
        }
        else if(map.getRocksInBagNum() > 1) {
            System.out.println("You have" + map.getRocksInBagNum() + "rocks in your bag.");
        }

        if(map.getRocksNum() > 0) {
            System.out.println("You are " + map.getDistanceToNearestRock() + " steps away from the nearest rock.");

        }
        else {
            System.out.println("You have collected all the rocks!");
        }

    }

    public void pickRock(Map map) {
        int nextX = getNextX(map);
        int nextY = getNextY(map);
        if(nextX == -1 || nextY == -1) {
            System.out.println("There is no rock ahead! Please enter again.");
        } else if (map.getMapPoint(nextX, nextY) != '●') {
            System.out.println("There is no rock ahead! Please enter again.");
        } else {
            map.updateMapAfterPick(nextX, nextY);
            System.out.println("You have got a rock!");
            System.out.println("Now you have " + map.getRocksInBagNum() + " in your bag.");
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getDirectionPic() {
        char directionPic;
        switch (direction){
            case 0:
                directionPic = '►';
                break;
            case 1:
                directionPic = '▲';
                break;
            case 2:
                directionPic = '◄';
                break;
            case 3:
                directionPic = '▼';
                break;
            default:
                directionPic = 'x';
        }
        return directionPic;
    }
}
