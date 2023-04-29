package model;


public class Robot implements Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

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

    public int getNextX(Map map) {
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

    public int getNextY(Map map) {
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
        if (nextX == -1 || nextY == -1) {
            System.out.println("Can't move, hit the edge!");
        } else if (map.getMapPoint(nextX, nextY) == '●') {
            System.out.println("Can't move, hit the rock!");
        } else if (map.getMapPoint(nextX, nextY) == '■') {
            System.out.println("Can't move, hit the wall!");
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

    public boolean noRockInBag(Map map) {
        return map.getRocksInBagNum() == 0;
    }

    //判断卡雷尔机器人面向的前方一格是否有石头，如果没有，打印 true.如果有，打印false。
    // 对于用石头填满的陷阱，也应打印 false
    public boolean noRockPresent(Map map) {
        int nextX = getNextX(map);
        int nextY = getNextY(map);
        if (nextX == -1 || nextY == -1) {
            return true;
        } else if (map.getMapPoint(nextX, nextY) == '×') {
            return false;
        } else if (map.getMapPoint(nextX, nextY) != '●') {
            return true;
        } else {
            return false;
        }
    }

    public void pickRock(Map map) {
        if (map.getMapPoint(getNextX(map), getNextY(map)) == '×') {
            System.out.println("You can't pick a rock in a filled trap! Please enter again.");
        } else if (noRockPresent(map)) {
            System.out.println("There is no rock ahead! Please enter again.");
        } else {
            map.updateMapAfterPick(getNextX(map), getNextY(map));
            System.out.println("You have got a rock!");
            System.out.println("Now you have " + map.getRocksInBagNum() + " in your bag.");
        }
    }

    public void putRock(Map map) {
        int nextX = getNextX(map);
        int nextY = getNextY(map);
        if (noRockInBag(map)) {
            System.out.println("You have no rock in your bag! Please enter again.");
        } else if (nextX == -1 || nextY == -1) {
            System.out.println("You can't put a rock out of the map! Please enter again.");
        } else if (map.getMapPoint(nextX, nextY) != '⊙') {
            System.out.println("You can't put a rock here! Please enter again.");
        } else {
            map.updateMapAfterPut(nextX, nextY);
            System.out.println("You have put down a rock!");
            System.out.println("Now you have " + map.getRocksInBagNum() + " left.");
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
