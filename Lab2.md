## Lab2

19307130188 彭一博

### 一.代码运行截图

#### 选择关卡

![image-20230401210418683](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401210418683.png)

![image-20230401210522904](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401210522904.png)

#### 指令操作

##### move(int x)：

​	向箭头所指方向移动x格，遇到墙/石头/边缘时停止移动并提示。

![image-20230401210804534](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401210804534.png)

![image-20230401210828123](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401210828123.png)



##### pickRock() 和 游戏结束：

​	如果卡雷尔机器人正前方一格有石头，则捡起石头；否则提示没有石头。

![image-20230401211030285](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401211030285.png)

![image-20230401211109356](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401211109356.png)

​	如果收集到了所有的石头，即地图上没有任何石头，则提示游戏胜利，退出程序：本处为了用户体验一致性，不是直接退出而是提示按Q退出。

![image-20230401211356867](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401211356867.png)

##### showInformation()：

​	打印当前状态。包括地图上有多少未拾起的石头，卡雷尔机器人的背包中有多少石头，距离最近的石头有几格。

![image-20230401211525110](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401211525110.png)



### 二.类设计

**Robot类：储存机器人状态，机器人指令操作方法**

**Map类：抽象类，用二维字符数组表示地图，储存机器人位置信息和石头的信息，打印和更新地图的方法**，**捡石头后更新地图的方法，判断游戏是否胜利结束的属性，离机器人最近的石头的距离**

**Stage1、Stage2:** **继承自Map，根据要求进行不同的初始化操作**

**Main类：初始化界面，选择后调用play加载地图和机器人，并操作**

**Play类：接收并处理指令，根据不同指令调用方法。其中Command内部类用于处理指令，可以将指令和参数分别提取**

![image-20230401211823857](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230401211823857.png)





### 三.程序实现思路

**Map类：**

**用5x5的数组储存地图，X、Y表示机器人所在位置。printMap方法用于打印地图；updateMap通过传入Robot类实例来更新地图，原有的位置变为'.'，新的机器人位置用robot.getDirectionPic()替换。updateMapAfterPick传入石头的坐标来进行捡起后的地图更新，包括图标和石头在图上、在背包里的数量。**

**isFinished()通过看rockNum是否为零来判断游戏胜利。抽象方法initialize()预留为不同的地图实现。getDistanceToNearestRock()通过遍历地图找出机器人离石头的最短距离，每次循环都进行比较，选较小的距离然后更新，此处调用Math.abs来计算两坐标的距离。**

```java
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
```

**Stage类：**

**继承自Map类，在自身的构造函数中用super方法调用父类的构造函数来设置地图大小以及机器人位置，覆盖重写initialize()来初始化设置石头和墙。以Stage1为例：**

```java
public class Stage1 extends Map {
    public Stage1(Robot robot) {
        super(robot, 3 , 6);
        initialize();
    }

    @Override
    public void initialize() {
        map[1][5] = '●';
        rocksNum = 1;
    }
}
```

**Robot类：机器人的位置可以用x和y坐标表示，方向可以用一个整数表示，0表示向右，1表示向上，2表示向左，3表示向下。move方法根据不同的朝向增减坐标值，同时通过传入Map类实例来确定当前地图的边界，提示碰撞；turnLeft方法将direction加1除4的余数达到左转的效果；**

**getDirectionPic方法由direction的数值确定图标。**

**showInformation(Map)通过使用Map里的各种get属性方法来呈现信息。pickRock(Map)用switch-case语句，根据机器人的朝向，判断前面一个是否有石头，有的话就捡起并更新地图信息。**

```java
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
                ...
                }
            }
            ...
        }
    }
```

**play类：游戏操作界面，用永真循环保持输入指令界面。每次输入指令前打印当前地图。使用switch-case语句运行相应。在robot实例中调用相应方法后更新地图。输入‘Q‘或’q‘执行系统进程退出。其他情况报出无该指令的提示。**

**为了能实现move(int x)，建立Command类来储存指令的名称和参数，parseCommand方法采用Pattern和Matcher通过正则表达式解析指令格式，因为数字匹配是可选的，所以同时能适用在move()和move(int x)上，最后匹配成功则返回Command对象**

```java
public Command parseCommand(String input) {
        /*
        ^ 表示匹配字符串的起始位置；
        (\\w+) 表示匹配一个或多个字母、数字或下划线；
        \\( 和 \\) 表示匹配左右括号；
        (\\d*) 表示匹配零个或多个数字；
        $ 表示匹配字符串的结束位置
         (?:\\((\\d*)\\))? 表示一个可选的数字串，可以出现一次或者不出现。
         */
        Pattern pattern = Pattern.compile("^(\\w+)(?:\\((\\d*)\\))?"); 
  			// 定义正则表达式解析指令格式
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) { // 如果匹配成功
            String commandName = matcher.group(1); // 获取指令名称
            String argStr = matcher.group(2); // 获取参数字符串
            int arg = argStr == null || argStr.isEmpty() ? 0 : Integer.parseInt(argStr); // 将参数字符串转换为整数，如果为空则默认为 0
            return new Command(commandName, arg);
        } else {
            return null;
        }
    }
```

**再使用for循环执行指定步数的move操作**

```java
case "move" -> {
    int steps = parsedCommand.getArg() == 0 ? 1 : parsedCommand.getArg(); 
  	// 如果未指定步数，默认为 1 步
    for (int i = 0; i < steps; i++) {
        robot.move(map);
        map.updateMap(robot);
    }
}
```

**main类：执行的主类，显示主界面，并通过switch-case语句进入相应地图，根据不同case进入不同地图，扫描实例response为‘Q'或‘q’时结束while循环，程序实行完毕退出。**



### 四.心得与体会

**1.本次lab对继承有了更多了解，被继承的类称为父类或超类，继承的类称为子类或派生类。子类可以通过继承获得父类中的所有非私有成员变量和方法，并且可以重写父类中的方法，实现自己特定的功能。例如Map父类定义后，不同地图继承它能够大大降低代码量，实现可复用（如使用super关键字可以方便地引用父类中的成员变量、构造方法和成员方法，从而实现对父类的扩展和重写）；**

**2.对多态加深了解，区分了重载和覆盖。在Java中，多态性主要体现在方法的重载和覆盖上。**

**重载是指在一个类中定义多个同名的方法，但是参数列表不同，这样可以根据传入的参数类型和数量来确定调用哪个方法。例如lab中Robot类的含参和不含参同名构造方法。**

**覆盖是指一个子类重写了其父类的方法，使得该方法在子类中具有不同的实现。在运行时，如果调用者使用的是父类的引用，而引用实际指向的是子类的对象，那么就会根据实际对象的类型来决定调用哪个方法，从而实现多态行为。**

**3.增强对@Override注解的理解；**

**当我们在子类中写一个方法，并使用了`@Override`注解时，编译器会检查该方法是否真正的覆盖了父类或接口中的对应方法，如果没有则编译器会报错。增加可读性、稳定性。本次lab的不同地图初始化就用到了这一注解**

**4.区分接口和继承：使用接口和抽象类都可以实现多态。但是，它们之间有一些区别。**

**首先，一个类只能继承一个抽象类，而一个类可以实现多个接口。这意味着接口可以更好地支持多态性，因为一个类可以同时表现出多种类型的行为。**

**其次，抽象类可以包含字段、构造函数、非抽象方法以及抽象方法，而接口只能包含常量和抽象方法。因此，如果需要在接口中定义通用的方法实现或者状态变量，就必须使用 default 方法或者静态常量。**

**另外，如果一个类继承了一个抽象类，就必须实现所有的抽象方法，否则该类也必须被定义为抽象类。而如果一个类实现了一个接口，可以只实现部分方法。这使得使用接口时更加灵活，因为可以在不同的场景下只实现必要的方法。**

**在设计程序时，如果需要定义一组相关的方法，并且这些方法的实现方式可能因为具体类的不同而异，那么应该使用接口。如果需要提供一些通用的方法实现并且要求子类必须实现某些方法，那么应该使用抽象类。**

**对此次lab，因为大量字段和构造函数需要被复用，所以将Map作为抽象类。**

**5.本次debug还遇到一个情况：输入带参数的指令时要按原本的字符串顺序输入，不能先把括号输入了再往中间填数字，不然命令行是读不到后输进去的数字的；提醒我们有时候不一定是程序错误，而是测试时有误，同时也引发进一步思考，命令行格式的输入的确对用户不友好，如果是图形化界面，每次输入指令后一条一条提交就不会有这种错误。**

**6.另外对正则表达式有了更多了解，这的确是一个常用的工具，但规则又很繁琐，适当情况可以使用人工智能工具来进行辅助。**