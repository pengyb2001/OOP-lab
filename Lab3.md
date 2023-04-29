## Lab3

19307130188 彭一博

### 一.代码运行截图

#### 选择关卡

![image-20230413171347574](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413171347574.png)



#### 指令操作

##### putRock()：

面向陷阱且背包中有石头时，卡雷尔机器人可以放下石头以填平陷阱

![image-20230413171839767](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413171839767.png)

##### noRockPresent()：

判断卡雷尔机器人面向的前方一格是否有石头，如果没有，打印 true；如果有，打印 false。对于用石头填满的陷阱，也应打印 false

面对石头填满的陷阱：

![image-20230413172128987](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413172128987.png)

面对前方没有石头：

![image-20230413172146026](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413172146026.png)

面对前方石头：

![image-20230413172243116](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413172243116.png)

##### noRockInBag()：

判断卡雷尔机器人包里是否有石头，如果没有，打印 true；如果有，打印 false

![image-20230413172404066](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413172404066.png)



#### 游戏结束：

胜利情况：地图上除了用于填平陷阱的石头外，没有其他任何石头（与之前lab一致，为了用户体验一致性，不是直接退出而是提示按Q退出。）

![image-20230413172604677](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413172604677.png)

失败情况：若卡雷尔机器人试图走到陷阱上，提示游戏失败，退出程序

![image-20230413172741432](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413172741432.png)



#### 异常处理：

1. 机器人只允许在地面和填平的陷阱上移动。若用户试图将机器人移动到区域外或墙壁、石头上，则

会有错误提示，移动失败。 	

墙壁：

![image-20230413173003371](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413173003371.png)

区域外：

![image-20230413173045261](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413173045261.png)

石头：

![image-20230413173146949](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413173146949.png)



2. 若用户试图捡起不存在的石头、陷阱里的石头，或填补已经填补了的陷阱，则会有错误提示，行动

失败。

不存在的石头：

![image-20230413173313905](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413173313905.png)

陷阱里的石头：

![image-20230413173420208](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413173420208.png)

填补已经填补了的陷阱（为了演示，本处是STAGE3已经胜利后自由操作的界面）：

![image-20230413173712961](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413173712961.png)



3. 用户输入了不存在的指令或不存在的地图序号：与之前lab一致，不再赘述

   

### 二.类设计

**Robot类：**储存机器人状态，机器人指令操作方法

**Map类：**抽象类，用二维字符数组表示地图，储存机器人位置信息和石头的信息，打印和更新地图的方法，捡石头后更新地图的方法，判断游戏是否胜利结束和失败的方法，离机器人最近的石头的距离，放下一颗石头后更新地图

**Stage1、Stage2、 Stage3:** 继承自Map，根据要求进行不同的初始化操作

**Main类：**初始化界面，选择后调用play加载地图和机器人，并操作

**Play类：**接收并处理指令，根据不同指令调用方法。其中Command内部类用于处理指令，可以将指令和参数分别提取

![image-20230413180345955](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230413180345955.png)





### 三.程序实现思路

**Map类：**

用5x5的数组储存地图，X、Y表示机器人所在位置。printMap方法用于打印地图；updateMap通过传入Robot类实例来更新地图，原有的位置变为'.'，新的机器人位置用robot.getDirectionPic()替换。updateMapAfterPick传入石头的坐标来进行捡起后的地图更新，包括图标和石头在图上、在背包里的数量。

isWon()通过看rockNum是否为零来判断游戏胜利。抽象方法initialize()预留为不同的地图实现。getDistanceToNearestRock()通过遍历地图找出机器人离石头的最短距离，每次循环都进行比较，选较小的距离然后更新，此处调用Math.abs来计算两坐标的距离。

isFailed()通过看机器人所在位置是否为陷阱来判断游戏失败，在移动后被调用。

```java
public boolean isFailed(Robot robot) {
        if (getMapPoint(robot.getX(), robot.getY()) == '⊙') {
            return true;
        } else {
            return false;
        }
    }
```

updateMapAfterPut(int rockX, int rockY)在陷阱处放下一颗石头后更新地图的参数，在putRock中被调用。

```java
public void updateMapAfterPut(int rockX, int rockY) {
    map[rockX][rockY] = '×';
    rocksInBagNum--;
    trapsNum--;
    filledTrapsNum++;
}
```



**Stage类：**

继承自Map类，在自身的构造函数中用super方法调用父类的构造函数来设置地图大小以及机器人位置，覆盖重写initialize()来初始化设置石头、墙、陷阱。



**Robot类：**

机器人的位置可以用x和y坐标表示，方向可以用一个整数表示，0表示向右，1表示向上，2表示向左，3表示向下。move方法根据不同的朝向增减坐标值，同时通过传入Map类实例来确定当前地图的边界，提示碰撞；turnLeft方法将direction加1除4的余数达到左转的效果；

getDirectionPic方法由direction的数值确定图标。

showInformation(Map)通过使用Map里的各种get属性方法来呈现信息。pickRock(Map)用switch-case语句，根据机器人的朝向，判断前面一个是否有石头，有的话就捡起并更新地图信息。

noRockInBag(Map map)返回map.getRocksInBagNum() == 0来判断背包中有无石头。

noRockPresent(Map map)判断卡雷尔机器人面向的前方一格是否有石头。

```java
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
```

putRock(Map map)向前方一格放石头，根据不同情况打印不同信息，可以放则用map.updateMapAfterPut更新地图信息。




**play类：**

游戏操作界面，用永真循环保持输入指令界面。每次输入指令前打印当前地图。使用switch-case语句运行相应。在robot实例中调用相应方法后更新地图。输入‘Q‘或’q‘执行系统进程退出。其他情况报出无该指令的提示。

为了能实现move(int x)，建立Command类来储存指令的名称和参数，parseCommand方法采用Pattern和Matcher通过正则表达式解析指令格式，因为数字匹配是可选的，所以同时能适用在move()和move(int x)上，最后匹配成功则返回Command对象。再使用for循环执行指定步数的move操作

每移动一次都要用isFailed来判断是否踩到陷阱。

```java
robot.move(map);
if (robot.getX() == oldX && robot.getY() == oldY) {
    break;
} else if (map.isFailed(robot)) {
    System.out.println("You are trapped! Game over!");
    System.exit(0);
}
else {
    map.updateMap(robot);
}
```



**main类：**

执行的主类，显示主界面，并通过switch-case语句进入相应地图，根据不同case进入不同地图，扫描实例response为‘Q'或‘q’时结束while循环，程序实行完毕退出。



### 四.心得与体会

1. 本次提高了程序的封装性，在Robot中加入getNextX和getNextY来获取下一步的坐标，遇到边界的时候就返回-1，这样使得move、noRockPresent、pickRock、putRock等都能复用这部分代码，更加简洁，维护起来也更方便；
2. 改进之前的储存地图的方式，把机器人和地图的信息分开储存，而不是在地图的字符数组中直接储存机器人图标，打印时再判断，当打印到机器人位置时才调用getDirectionPic打印机器人图标。这样即使经过了被填满的陷阱，也能根据地图的信息正确打印，而不是覆盖了这个信息；
3. 注意传入参数的正确性，之前在写程序时将判断是否失败放在移动之前，即判断前方一个是否为陷阱，但是那时候getNextX和getNextY把前方有石头也返回-1，这样导致传入getMapPoint的两个参数是-1，导致运行失败。于是改进成现在的样子：获取下一个位置的坐标只在判断为边界时才返回-1，并且判断是否失败放在机器人移动之后，那么isFailed就是把机器人目前的坐标传入getMapPoint来看是否为陷阱，当然，这也离不开上一条中分开储存地图和机器人信息的作用。

