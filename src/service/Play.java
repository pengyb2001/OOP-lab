package service;

import model.*;

import java.util.Scanner;
import java.util.regex.*;

public class Play {
    public void play(Robot robot, Map map) {
        while(true) {
            map.printMap();
            System.out.println("Enter command: (move(), move(n), turnLeft(), showInformation(), pickRock(), Q)");
            //注意输入带参数的指令时要按原本的字符串顺序输入，不能先把括号输入了再往中间填数字，不然命令行是读不到后输进去的数字的
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            Command parsedCommand = parseCommand(command);
            if (parsedCommand != null) {
                switch (parsedCommand.getName()) {
                    case "move" -> {
                        int steps = parsedCommand.getArg() == 0 ? 1 : parsedCommand.getArg(); // 如果未指定步数，默认为 1 步
                        for (int i = 0; i < steps; i++) {
                            int oldX = robot.getX();
                            int oldY = robot.getY();
                            robot.move(map);
                            if (robot.getX() == oldX && robot.getY() == oldY) {
//                                System.out.println("You can't move any further. Please re-enter the command.");
                                break;
                            }
                            else {
                                map.updateMap(robot);
                            }
                        }
                    }
                    case "turnLeft" -> {
                        robot.turnLeft();
                        map.updateMap(robot);
                    }
                    case "showInformation" -> {
                        robot.showInformation(map);
                    }
                    case "pickRock" -> {
                        robot.pickRock(map);
                        if (map.isFinished()) {
                            System.out.println("Congratulations! You win the game! Press Q to quit. Press any other key to continue.");
                            Scanner scanner1 = new Scanner(System.in);
                            String command1 = scanner1.nextLine();
                            if(command1.equals("Q") || command1.equals("q")) {
                                System.exit(0);
                            }
                        }
                    }
                    case "turnRight" -> {
                        for(int i = 0; i < 3; i++) {
                            robot.turnLeft();
                            map.updateMap(robot);
                        }
                    }
//                    case "noRockPresent" -> {
//                        if(robot.getRock() == 0) {
//                            System.out.println("true");
//                        }
//                        else {
//                            System.out.println("false");
//                        }
//                    }
                    case "Q", "q" -> {
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("No such command exists. Please re-enter it");
                    }
                }
            } else {
                System.out.println("Invalid command. Please re-enter it.");
            }

        }
    }

    public class Command {
        private String name;
        private int arg;

        public Command(String name, int arg) {
            this.name = name;
            this.arg = arg;
        }

        public String getName() {
            return name;
        }

        public int getArg() {
            return arg;
        }
    }

    // 解析用户输入的指令字符串，返回一个 Command 对象
    public Command parseCommand(String input) {
        /*
        ^ 表示匹配字符串的起始位置；
        (\\w+) 表示匹配一个或多个字母、数字或下划线；
        \\( 和 \\) 表示匹配左右括号；
        (\\d*) 表示匹配零个或多个数字；
        $ 表示匹配字符串的结束位置
         (?:\\((\\d*)\\))? 表示一个可选的数字串，可以出现一次或者不出现。
         */
        Pattern pattern = Pattern.compile("^(\\w+)(?:\\((\\d*)\\))?"); // 定义正则表达式解析指令格式
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) { // 如果匹配成功
            String commandName = matcher.group(1); // 获取指令名称
            String argStr = matcher.group(2); // 获取参数字符串
//            System.out.println("commandName: "+commandName+"\t"+"argStr: "+argStr);//test
            int arg = argStr == null || argStr.isEmpty() ? 0 : Integer.parseInt(argStr); // 将参数字符串转换为整数，如果为空则默认为 0
            return new Command(commandName, arg);
        } else {
            return null;
        }
    }
}
