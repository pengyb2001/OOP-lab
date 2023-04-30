package service;

import model.*;

import java.util.Scanner;
import java.util.regex.*;

public class Play {
    public String play(Robot robot, Map map) {
        while (true) {
            map.printMap(robot);
            System.out.println("Enter command: (move(), move(n), turnLeft(), showInformation(), pickRock(), " +
                    "putRock(), noRockPresent(), noRockInBag(), Q)");
            //注意输入带参数的指令时要按原本的字符串顺序输入，不能先把括号输入了再往中间填数字，不然命令行是读不到后输进去的数字的
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            if (command.equals("Q")) {
                System.out.println("Bye!");
                System.exit(0);
            }
            Command parsedCommand = parseCommand(command);
            if (parsedCommand != null) {
                if (parsedCommand.getName().equals("if")) {
                    String condition = parsedCommand.getArgStr();
                    boolean conditionResult = false;
                    if (condition.equals("noRockPresent()")) {
                        conditionResult = robot.noRockPresent(map);
                    } else if (condition.equals("noRockInBag()")) {
                        conditionResult = robot.noRockInBag(map);
                    }
                    String commandStr = conditionResult ? parsedCommand.getLeftCommandStr() : parsedCommand.getRightCommandStr();
                    System.out.println("Executing command: " + commandStr);//DEBUG
                    Command subCommand = parseCommand(removeSemicolon(commandStr)); // 解析去除分号后的子命令
                    String executeResult = executeCommand(subCommand, robot, map); // 执行子命令
                    if(executeResult != "continue") {
                        return executeResult;
                    }
                } else {
                    String executeResult = executeCommand(parsedCommand, robot, map); // 执行命令
                    if(executeResult != "continue") {
                        return executeResult;
                    }
                }
            } else {
                System.out.println("Invalid command. Please re-enter it.");
            }
        }
    }

    /**
     * 解析用户输入的指令字符串，返回一个 Command 对象。
     *
     * @param input 用户输入的指令字符串
     * @return 返回解析出来的 Command 对象，如果无法解析则返回 null
     */
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

    /**
     * 执行一个命令。
     *
     * @param command 待执行的命令
     * @param robot   机器人对象
     * @param map     地图对象
     */
    public String executeCommand(Command command, Robot robot, Map map) {
        if (command == null) {
            System.out.println("Invalid command.");
            return "continue";
        }
        switch (command.getName()) {
            case "move" -> {
                int steps = command.getArg() == 0 ? 1 : command.getArg(); // 如果未指定步数，默认为 1 步
                for (int i = 0; i < steps; i++) {
                    int oldX = robot.getX();
                    int oldY = robot.getY();

                    robot.move(map);
                    if (robot.getX() == oldX && robot.getY() == oldY) {
                        break;
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
                if (map.isWon()) {
                    System.out.println("Congratulations! You win the game! Game Over.");
                    while (true) {
                        System.out.println("Do you want to play again? (Y/N)");
                        Scanner scanner = new Scanner(System.in);
                        String input = scanner.nextLine().toUpperCase();
                        if (input.equals("Y")) {
                            return "playAgain";
                        } else if (input.equals("N")) {
                            return "exit";
                        } else {
                            System.out.println("Invalid input. Please enter Y or N.");
                        }
                    }
                }
            }
            case "putRock" -> {
                robot.putRock(map);
            }
            case "turnRight" -> {
                for (int i = 0; i < 3; i++) {
                    robot.turnLeft();
                    map.updateMap(robot);
                }
            }
            case "noRockPresent" -> {
                if (robot.noRockPresent(map)) {
                    System.out.println("true");
                } else {
                    System.out.println("false");
                }
            }
            case "noRockInBag" -> {
                if (robot.noRockInBag(map)) {
                    System.out.println("true");
                } else {
                    System.out.println("false");
                }
            }
            default -> {
                System.out.println("No such command exists. Please re-enter it");
            }
        }
        return "continue";
    }
    public class Command {
        private String name;
        private int arg;
        private String argStr;
        private String  leftCommandStr;
        private String rightCommandStr;

        public Command(String name, int arg) {
            this.name = name;
            this.arg = arg;
        }

        public Command(String name, String argStr) {
            this.name = name;
            this.argStr = argStr;
        }

        public Command(String name, String argStr, String leftCommandStr, String rightCommandStr) {
            this.name = name;
            this.argStr = argStr;
            this.leftCommandStr = leftCommandStr;
            this.rightCommandStr = rightCommandStr;
        }

        public String getName() {
            return name;
        }

        public int getArg() {
            return arg;
        }

        public String getArgStr() {
            return argStr;
        }

        public String getLeftCommandStr() {
            return leftCommandStr;
        }

        public String getRightCommandStr() {
            return rightCommandStr;
        }
    }

    public String removeSemicolon(String commandStr) {
        String trimmedStr = commandStr.trim(); // 去除前后空格

        if (trimmedStr.endsWith(";")) { // 判断是否以分号结尾
            return trimmedStr.substring(0, trimmedStr.length() - 1); // 截取字符串
        } else {
            return trimmedStr;
        }
    }



}

