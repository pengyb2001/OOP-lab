package model;

public class Stage3 extends Map {
    public Stage3(Robot robot) {
        super(robot, 5 , 8);
        initialize();
    }

    @Override
    public void initialize() {
        map[0][7] = '●';
        map[1][2] = '●';
        rocksNum = 2;
        map[2][4] = '⊙';
        trapsNum = 1;
        map[0][4] = '■';
        map[1][4] = '■';
        for (int i = 3; i < 5; i++) {
            for(int j = 4; j < 8; j++) {
                map[i][j] = '■';
            }
        }

    }
}
