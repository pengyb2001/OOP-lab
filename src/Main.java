import java.awt.*;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String response = "";

        while (!response.equalsIgnoreCase("q")) {
            System.out.println("+------------------------------------------------------+");
            System.out.println("|             Welcome to Karel's world!                |");
            System.out.println("|         Choose a stage or create a new map           |");
            System.out.println("|            STAGE1 STAGE2 STAGE3 NEW MAP              |");
            System.out.println("+------------------------------------------------------+");
            System.out.println("|              Use 'Q' to quit the game                |");
            System.out.println("+------------------------------------------------------+");
            response = scanner.nextLine().toUpperCase();
            switch (response) {
                case "NEW MAP", "new map" -> {
                }
                    //TODO
                case "STAGE1", "stage1"-> {
                }
                    //TODO
                case "STAGE2", "stage2"-> {
                }
                    //TODO
                case "STAGE3", "stage3" -> {
                    Robot robot = new Robot();
                    Map map = new Map(robot);
                    play(robot, map);
                }
                case "Q", "q" -> {
                    continue;
                }
                default -> {
                    System.out.println("No such option exists. Please re-enter it");
                }
            }
        }
        System.out.println("See you next time!");
    }

    public static void play(Robot robot, Map map) {
        while(true) {
            map.printMap();
            System.out.println("Enter command: (move, turnLeft, Q)");
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            switch (command) {
                case "move" -> {
                    robot.move(map);
                    map.updateMap(robot);
                }
                case "turnLeft" -> {
                    robot.turnLeft();
                    map.updateMap(robot);
                }
                case "Q", "q" -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("No such command exists. Please re-enter it");
                }
            }
        }
    }

    public static class Map {
        private static final int ROWS = 5;
        private static final int COLUMNS = 5;
        private static char[][] map;
        private static int X;
        private static int Y;

        public Map(Robot robot) {
            map = new char[ROWS][COLUMNS];
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    map[i][j] = '.';
                }
            }
            X = robot.getX();
            Y = robot.getY();
            map[X][Y] = robot.getDirectionPic();
        }

        public void printMap() {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
        }

        public void updateMap(Robot robot) {
            map[Map.X][Map.Y] = '.';
            map[robot.x][robot.y] = robot.getDirectionPic();
            //在地图中更新机器人的位置
            Map.X = robot.x;
            Map.Y = robot.y;
        }

        public int getX() {
            return X;
        }

        public int getY() {
            return Y;
        }
    }

    public static class Robot {
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

        public void move(Map map) {//TODO:检测地图边界
            if (direction == 0) {
                if(y < map.COLUMNS -1) {
                    y++;
                } else {
                    System.out.println("Can't move, hit the wall!");
                }

            } else if (direction == 1) {
                if(x > 0) {
                    x--;
                } else {
                    System.out.println("Can't move, hit the wall!");
                }

            } else if (direction == 2) {
                if(y > 0) {
                    y--;
                } else {
                    System.out.println("Can't move, hit the wall!");
                }
            } else if (direction == 3) {
                if(x < map.ROWS -1) {
                    x++;
                } else {
                    System.out.println("Can't move, hit the wall!");
                }
            }
        }

        public void turnLeft() {
            direction = (direction + 1) % 4;
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



}
