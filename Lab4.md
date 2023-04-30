## Lab4

19307130188 彭一博

### 一.代码运行截图

#### 选择关卡

**NEW MAP**

一行一行地输入自建地图 空行作为结束标志

![image-20230430213513627](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430213513627.png)

#### 指令操作

##### ifelse 语句解析：

用户可以实现一条简单的 if-else 逻辑命令：if(){}else{}。if 的判断条件可以是 noRockPresent() 或noRockInBag()。每个大括号内的代码 块仅有一条简单语句，无需支持多条语句

![image-20230430214129733](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430214129733.png)

##### 函数封装：

用户可以自定义函数：functionName(){}。定义完成后，就可以像内置的指令一样使用。用户同时只能定义一个函数，新的函数会替换原来的函数。此处的大括号中可能有多条简单语句，但是无需考虑与 ifelse 的嵌套，if-else 中也不需要支持用户自定义的指令（即如果遇到这种情况，只需提示输入的指令错误，要求重新输入即可）。

![image-20230430214234828](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430214234828.png)



#### 游戏结束：

胜利和失败的判定条件与前置 Lab 相同，但是胜利或失败后，不应退出程序，而是询问用户是否重新开始此关卡。如果用户选择是，则重置刚才游玩的关卡；选择否，则回到开始界面。

失败选择重新开始

![image-20230430214722010](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430214722010.png)

失败不选择重新开始

![image-20230430214828068](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430214828068.png)

通关选择重新开始

![image-20230430214439160](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430214439160.png)

通关选择不要重新开始

![image-20230430214459598](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430214459598.png)

#### 异常处理：

1. if-else指令格式不正确![image-20230430214945552](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430214945552.png)



2. 自创函数格式不正确![image-20230430215043614](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430215043614.png)
3. 自创地图格式不正确![image-20230430215337033](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430215337033.png)
4. 与之前lab一致的部分，如用户输入了不存在的指令或不存在的地图序号，不再赘述

### 二.类设计

**Robot类：**储存机器人状态，机器人指令操作方法

**Map类：**抽象类，用二维字符数组表示地图，储存机器人位置信息和石头的信息，打印和更新地图的方法，捡石头后更新地图的方法，判断游戏是否胜利结束和失败的方法，离机器人最近的石头的距离，放下一颗石头后更新地图

**Stage1、Stage2、 Stage3、NEW MAP:** 继承自Map，根据要求进行不同的初始化操作

**MapReader类：**用来读自创的地图并进行格式处理

**Main类：**初始化界面，选择后调用play加载地图和机器人，并操作

**Play类：**接收并处理指令，根据不同指令调用方法。

**Command类：**定义指令

![image-20230430215705231](/Users/pengyibo/Library/Application Support/typora-user-images/image-20230430215705231.png)





### 三.程序实现思路

**NewMap类：**

与其他地图不同的是NewMap通过传入Robot和MapReader构建，不采用预先initialize的方法，而因为这个方法的是抽象的，所以为了避免编译错误，需要空实现

```java
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
                rocksNum = mapReader.rocksNum;
                trapsNum = mapReader.trapsNum;
            }
        }
    }

    @Override
    public void initialize() {
        // 空实现
    }

}
```



**Stage类：**

继承自Map类，在自身的构造函数中用super方法调用父类的构造函数来设置地图大小以及机器人位置，覆盖重写initialize()来初始化设置石头、墙、陷阱。



**MapReader类：**

MapReader类的构造函数包含两个方法：reset()和setMap()。reset() 方法用于将所有属性重置为默认值，而setMap() 方法则用于从命令行读取输入的地图，并将其储存在map数组中，并对其进行验证以确保其合法性。其中，该方法通过Scanner对象来读取命令行中输入的地图，并将其转换为二维数组的形式。同时，该方法还会检查地图是否符合规范，如地图是否有且仅有一个机器人、地图是否包含非法字符等。

另外，MapReader类还包含一个detectElement()方法，用于检测地图中的元素类型，如岩石、陷阱和机器人，并统计它们出现的次数，同时将机器人的位置信息记录到robotX和robotY中。

```java
public class MapReader {
   ...  
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
          	...
            // 移除该行中的所有空格
            String lineWithoutSpaces = line.replaceAll(" ", "");
            inputMap.add(lineWithoutSpaces);
        }


        // 获取地图的行数和列数
        this.rows = inputMap.size();
        ...

        this.columns = numCols;
      
        // 创建对应大小的二维数组
        this.map = new char[rows][columns];

        // 将每一行地图的字符串表示转换为字符数组，并去除空格后赋值给二维数组
        for (int i = 0; i < rows; i++) {
            char[] line = inputMap.get(i).toCharArray();
            for (int j = 0; j < columns; j++) {
               ...
                detectElement(map[i][j], i, j);
            }
        }

        // 检查地图是否有且仅有一个机器人
        ...
    }

    public void detectElement (char element, int x, int y) {
        switch (element) {
            case '●' -> {
              ...
    }

    public void reset()
    ...

}

```




**play类：**

主要包含了机器人游戏的核心逻辑。它提供了play方法，该方法用于在控制台中运行游戏，并接受用户输入的命令，根据输入的命令执行相应的操作。其中，机器人的移动和相关操作是由executeCommand方法实现的。

通过调用parseCommand方法，它能够将用户输入的字符串解析成一个Command对象，并依据这个对象来执行相应的命令。同时，还可以通过调用removeSemicolon方法去除字符串末尾的分号。

该类还提供了一个parseSelfCommand方法，用于解析用户自定义的函数，将其解析成一个Command对象并存储在selfCommand变量中，以备后续使用。

**对于解析if-else**

在Play类的parseCommand方法中，可以通过正则表达式来解析if-else语句。具体来说，它使用了以下正则表达式：

```java
public Command parseCommand(String input) {
        Pattern pattern = Pattern.compile("^(if\\((noRockPresent\\(\\)|noRockInBag\\(\\)))\\)\\{(.*)\\}\\s*else\\s*\\{(.*)\\}|(\\w+)\\((\\d*)\\)$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) { // 如果匹配成功
            String ifCondition = matcher.group(2); // 获取 if 命令的条件部分
            String leftCommandStr = matcher.group(3); // 获取 if 命令中的左侧命令部分
            String rightCommandStr = matcher.group(4); // 获取 if 命令中的右侧命令部分
            if (ifCondition != null) { // 如果是 if-else 命令，则返回一个特殊的 Command 对象
                return new Command("if", ifCondition, leftCommandStr, rightCommandStr);
            } else { // 否则按照原有代码的方式解析单个命令
                String commandName = matcher.group(5); // 获取指令名称
                String argStr = matcher.group(6); // 获取参数字符串
                int arg = argStr == null || argStr.isEmpty() ? 0 : Integer.parseInt(argStr); // 将参数字符串转换为整数，如果为空则默认为 0
                return new Command(commandName, arg);
            }
        } else {
            return null;
        }
    }
```

该正则表达式首先匹配if-else语句，如果匹配成功，则返回一个特殊的Command对象。这个正则表达式的含义如下：

- "^(if\((noRockPresent\(\)|noRockInBag\(\)))\)\{(.

  )\}\s

  else\s*\{(.*)\}"：匹配if-else语句的正则表达式，其中包括：

  - "if\((noRockPresent\(\)|noRockInBag\(\)))"：匹配if语句中的条件，其形式为if(condition)，condition可以是noRockPresent()或noRockInBag()；
  - "\{(.*)\}"：匹配if语句中的左侧命令部分，即大括号内的内容；
  - "\s*else\s*\{(.*)\}"：匹配if语句中的右侧命令部分，即else后面的大括号内的内容。

- "|(\w+)\((\d*)\)$"：匹配单个命令的正则表达式，其中：

  - "(\w+)"：匹配指令名称，其中\w+表示任意多个字母或数字字符；
  - "\((\d*)\)"：匹配指令参数部分，即括号内的内容，可以为空；(\d*)表示任意多个数字字符。

如果用户输入的字符串符合以上正则表达式，则Match.matches方法返回true，然后就可以通过Match.group方法提取出if-else语句或单个命令中的各个部分，并使用它们来创建相应的Command对象。如果不匹配，则parseCommand方法返回null。

**对于用户自定义函数**

在Play类中，有一个名为parseSelfCommand的私有方法，用于解析用户自定义的函数。具体来说，该方法使用了以下正则表达式：

```java
public Command parseSelfCommand(String input) {
        Pattern pattern = Pattern.compile("^(\\w+)\\(\\)\\{(.*)\\}$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String functionName = matcher.group(1);
            String commandsStr = matcher.group(2);
            String commandsArr[] = commandsStr.split(";");
            List<Command> commands = new ArrayList<>();
            for (String commandStr : commandsArr) {
                commands.add(parseCommand(commandStr));
            }
            return new Command(functionName, commands);
        } else {
            return null;
        }
    }
```

该正则表达式可以匹配形如"functionName(param)"的字符串，其中functionName是函数名，param是参数，可以为空。

当用户输入一个自定义函数时，parseCommand方法会先检查该函数是否已经被解析过（即存储在selfCommand变量中），如果已经解析过，则直接使用存储在selfCommand变量中的Command对象；否则，就调用parseSelfCommand方法解析出相应的Command对象，并将其存储在selfCommand变量中供后续使用。

一旦用户输入的命令被解析成Command对象，就可以通过执行executeCommand方法来依次运行该命令的子命令。在执行单个命令之前，Play类需要判断当前解析出来的Command对象是否是自定义函数，如果是，就要先执行自定义函数对应的Command对象。

**对于处理游戏结束**

根据输入return不同字符串，让main根据此选择返回主页面还是重制地图

```java
...
} else if (map.isFailed(robot)) {
    System.out.println("You are trapped! Game over!");
    while (true) {
        System.out.println("Do you want to play again? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toUpperCase();
        if (input.equals("Y")) {
            // 重置地图
            return "playAgain";
        } else if (input.equals("N")) {
            return "exit";
        } else {
            System.out.println("Invalid input. Please enter Y or N.");
        }
    }
} else {
    map.updateMap(robot);
}
```

### 四.心得与体会

1. 要提前预估工作量，之前都是最多半天能搞定，这次没能搞定加上最后一天才开始导致延期交付。
1. 对ArrayList了解更深，在 Java 中，ArrayList 是一种基于数组实现的动态数组数据结构。它是 List 接口的可序列化实现，并实现了 RandomAccess 接口。它提供了动态添加和删除元素的能力，因此非常适合需要频繁对列表操作的场景。
1. MapReader读取地图的时候要注意各种处理，比如每次重新读要记得初始化，移除空格、检测是否合法等。
1. 提高程序封装性，如处理单个命令时统一用executeCommand。降低程序耦合性，比如把对解析自创命令parseSelfCommand的方法和解析一般命令的分开，有利于维护，层次也更加分明。

