package service;

import java.util.List;

public class Command {
    private String name;
    private int arg;
    private String argStr;
    private String  leftCommandStr;
    private String rightCommandStr;
    private List<Command> commands;

    public Command(String selfFunctionName, List<Command> commands) {
        this.commands = commands;
        this.name = selfFunctionName;
    }

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

    public List<Command> getSubCommands() {
        return commands;
    }
}
