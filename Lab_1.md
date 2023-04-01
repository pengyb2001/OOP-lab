## Lab_1

19307130188 彭一博

### 一.代码运行截图

#### 初始化与结束

<img src="/Users/pengyibo/Library/Application Support/typora-user-images/image-20230318173400326.png" alt="image-20230318173400326" style="zoom: 50%;" />

#### 选择关卡

<img src="/Users/pengyibo/Library/Application Support/typora-user-images/image-20230318172939978.png" alt="image-20230318172939978" style="zoom: 50%;" />

#### 指令操作

##### move：

​	向箭头所指方向移动一格，遇到边缘时不移动并提示。

<img src="/Users/pengyibo/Library/Application Support/typora-user-images/image-20230318173704920.png" alt="image-20230318173704920" style="zoom: 50%;" />

<img src="/Users/pengyibo/Library/Application Support/typora-user-images/image-20230318173749243.png" alt="image-20230318173749243" style="zoom: 50%;" />

##### turnLeft：

​	机器人向左转，箭头逆时针转动90度。

<img src="/Users/pengyibo/Library/Application Support/typora-user-images/image-20230318173918752.png" alt="image-20230318173918752" style="zoom: 50%;" />

##### Q：

​	退出，直接退出进程。

<img src="/Users/pengyibo/Library/Application Support/typora-user-images/image-20230318174007588.png" alt="image-20230318174007588" style="zoom: 50%;" />

### 二.类设计

**Robot类：储存机器人状态，机器人指令操作方法**

**Map类：用二维字符数组表示地图，储存机器人位置信息，打印和更新地图的方法**

**Main类：初始化界面，选择后调用play方法加载地图和机器人，并操作**

<img src="/Users/pengyibo/Library/Application Support/typora-user-images/image-20230318175233723.png" alt="image-20230318175233723" style="zoom: 50%;" />

### 三.程序实现思路

**Map类：用5x5的数组储存地图，X、Y表示机器人所在位置。printMap方法用于打印地图；updateMap通过传入Robot类实例来更新地图，原有的位置变为'.'，新的机器人位置用robot.getDirectionPic()替换。**

```java
import model.Robot;

public static class Map {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private static char[][] map;
    private static int X;
    private static int Y;

    public model.Map(
    Robot robot)

    {
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
        map[model.Map.X][model.Map.Y] = '.';
        map[robot.x][robot.y] = robot.getDirectionPic();
        //在地图中更新机器人的位置
        model.Map.X = robot.x;
        model.Map.Y = robot.y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
```

**Robot类：机器人的位置可以用x和y坐标表示，方向可以用一个整数表示，0表示向右，1表示向上，2表示向左，3表示向下。move方法根据不同的朝向增减坐标值，同时通过传入Map类实例来确定当前地图的边界，提示碰撞；turnLeft方法将direction加1除4的余数达到左转的效果；getDirectionPic方法由direction的数值确定图标。**

```java
import model.Map;

public static class Robot {
    //机器人的位置可以用x和y坐标表示，方向可以用一个整数表示，0表示向右，1表示向上，2表示向左，3表示向下。
    private static int x;
    private static int y;
    private static int direction;

    public model.Robot()

    {
        x = 2;
        y = 0;
        direction = 0;
    }

    public model.Robot(
    int x, int y, int direction)

    {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move(Map map) {
        if (direction == 0) {
            if (y < map.COLUMNS - 1) {
                y++;
            } else {
                System.out.println("Can't move, hit the wall!");
            }

        } else if (direction == 1) {
            if (x > 0) {
                x--;
            } else {
                System.out.println("Can't move, hit the wall!");
            }

        } else if (direction == 2) {
            if (y > 0) {
                y--;
            } else {
                System.out.println("Can't move, hit the wall!");
            }
        } else if (direction == 3) {
            if (x < map.ROWS - 1) {
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
        switch (direction) {
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
```

**play类：游戏操作界面，用永真循环保持输入指令界面。每次输入指令前打印当前地图。使用switch-case语句运行相应。在robot实例中调用相应方法后更新地图。输入‘Q‘或’q‘执行系统进程退出。其他情况报出无该指令的提示。**

```java
public static void play(model.Robot robot, model.Map map) {
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
```

**main类：执行的主类，显示主界面，并通过switch-case语句进入相应地图，根据要求目前所有case进入同一地图，扫描实例response为‘Q'或‘q’时结束while循环，程序实行完毕退出。**

```java
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String response = "";

        while (!response.equalsIgnoreCase("q")) {
            System.out.println("+------------------------------------------------------+");
            System.out.println("|                 Made by Yibo Peng                    |");
            System.out.println("|             Welcome to Karel's world!                |");
            System.out.println("|         Choose a stage or create a new map           |");
            System.out.println("|            STAGE1 STAGE2 STAGE3 NEW MAP              |");
            System.out.println("+------------------------------------------------------+");
            System.out.println("|              Use 'Q' to quit the game                |");
            System.out.println("+------------------------------------------------------+");
            response = scanner.nextLine().toUpperCase();
            switch (response) {
                case "NEW MAP", "new map", "STAGE1", "stage1", "STAGE2", "stage2",  "STAGE3", "stage3" -> {
                    model.Robot robot = new model.Robot();
                    model.Map map = new model.Map(robot);
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
```

### 四.心得与体会

**1.本次lab初步了解了java面向对象开发的特性，合理划分不同类能让代码更清晰，拓展性也更好；**

**2.增强对java类包的使用理解，例如Scanner等；**

**3.对于修饰成员变量和成员的关键字有了更深了解。例如如果在同一个文件中调用/实例化其他类，得使用`static`使之成为静态的，储存在数据共享区才能用。对用于修饰类、属性和方法的访问控制修饰符也理解更深，例如Robot的基本信息不能直接暴露给外界修改，要用`private`修饰，而外界如果想知道它的坐标信息，可以用`public int getX() `。**

**4.维护程序鲁棒性很重要，要时刻考虑到输入是否合法来防止数组溢出等情况**