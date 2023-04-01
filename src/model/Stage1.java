package model;

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
