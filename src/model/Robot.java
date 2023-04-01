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

    public void move(Map map) {
        if (direction == 0) {
            if(y == map.getColumns() -1) {
                System.out.println("Can't move, hit the wall!");
            }
            else if (map.getMapPoint(x, y+1) == '■') {
                System.out.println("Can't move, hit the wall!");
            }
            else if (map.getMapPoint(x, y+1) == '●') {
                System.out.println("Can't move, hit the rock!");
            }
            else {
                y++;
            }

        } else if (direction == 1) {
            if(x == 0) {
                System.out.println("Can't move, hit the wall!");
            } else if (map.getMapPoint(x-1, y) == '■') {
                System.out.println("Can't move, hit the wall!");
            } else if (map.getMapPoint(x-1, y) == '●') {
                System.out.println("Can't move, hit the rock!");
            }
            else {
                x--;
            }

        } else if (direction == 2) {
            if(y == 0) {
                System.out.println("Can't move, hit the wall!");
            } else if (map.getMapPoint(x, y-1) == '■') {
                System.out.println("Can't move, hit the wall!");
            } else if (map.getMapPoint(x, y-1) == '●') {
                System.out.println("Can't move, hit the rock!");
            }
            else {
                y--;
            }
        } else if (direction == 3) {
            if(x == map.getRows() -1) {
                System.out.println("Can't move, hit the wall!");
            } else if (map.getMapPoint(x+1, y) == '■') {
                System.out.println("Can't move, hit the wall!");
            } else if (map.getMapPoint(x+1, y) == '●') {
                System.out.println("Can't move, hit the rock!");
            }
            else {
                x++;
            }
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
        switch (direction) {
            case 0 -> {
                if (map.getMapPoint(x, y + 1) != '●' || y == map.getColumns() - 1) {
                    System.out.println("There is no rock ahead! Please enter again.");
                } else {
                    map.updateMapAfterPick(x, y + 1);
                    System.out.println("You have got a rock!");
                    System.out.println("Now you have " + map.getRocksInBagNum() + " in your bag.");
                }
            }
            case 1 -> {
                if (map.getMapPoint(x - 1, y) != '●' || x == 0) {
                    System.out.println("There is no rock ahead! Please enter again.");
                } else {
                    map.updateMapAfterPick(x - 1, y);
                    System.out.println("You have got a rock!");
                    System.out.println("Now you have " + map.getRocksInBagNum() + " in your bag.");
                }
            }
            case 2 -> {
                if (map.getMapPoint(x, y - 1) != '●' || y == 0) {
                    System.out.println("There is no rock ahead! Please enter again.");
                } else {
                    map.updateMapAfterPick(x, y - 1);
                    System.out.println("You have got a rock!");
                    System.out.println("Now you have " + map.getRocksInBagNum() + " in your bag.");
                }
            }
            case 3 -> {
                if (map.getMapPoint(x + 1, y) != '●' || x == map.getRows() - 1) {
                    System.out.println("There is no rock ahead! Please enter again.");
                } else {
                    map.updateMapAfterPick(x + 1, y);
                    System.out.println("You have got a rock!");
                    System.out.println("Now you have " + map.getRocksInBagNum() + " in your bag.");
                }
            }

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
