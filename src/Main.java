import java.util.Scanner;
import model.*;
import service.*;

public class Main {
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
                case "NEW MAP", "new map" -> {
                    System.out.println("This stage is not available yet. Please re-enter it");
                }
//                    //TODO
                case "STAGE1", "stage1"-> {
                    Robot robot = new Robot(1, 0, 0);
                    Map map = new Stage1(robot);
                    Play play =new Play();
                    play.play(robot, map);
                }
                case "STAGE2", "stage2"-> {
                    Robot robot = new Robot(2, 0, 0);
                    Map map = new Stage2(robot);
                    Play play =new Play();
                    play.play(robot, map);
                }
                case "STAGE3", "stage3" -> {
                   Robot robot = new Robot(4, 0, 0);
                    Map map = new Stage3(robot);
                    Play play =new Play();
                    play.play(robot, map);
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

}
