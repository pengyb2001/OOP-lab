package model;

public class Stage2 extends Map {
    public Stage2(Robot robot) {
        super(robot, 3 , 6);
        initialize();
    }

    @Override
    public void initialize() {
        map[1][5] = '●';
        rocksNum = 1;
        //set walls
        map[0][0] = '■';
        map[1][0] = '■';
        map[2][2] = '■';
        map[2][3] = '■';
        map[2][4] = '■';
        map[2][5] = '■';
    }
}

