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
                    System.out.println("Please input your map line by line:");
                    MapReader mapReader = new MapReader();
                    String result = "playAgain";
                    while (result.equals("playAgain")) {
                        System.out.println("OK\n" +"Stage with your own map:");
                        Robot robot = new Robot(mapReader.robotX, mapReader.robotY, mapReader.robotDirection);
                        Map map = new NewMap(robot, mapReader);
                        Play play =new Play();
                        result = play.play(robot, map);
                    }
                }
                case "STAGE1", "stage1"-> {
                    String result = "playAgain";
                    while(result.equals("playAgain")) {
                        Robot robot = new Robot(1, 0, 0);
                        Map map = new Stage1(robot);
                        Play play =new Play();
                        result = play.play(robot, map);
                    }
                }


                case "STAGE2", "stage2"-> {
                    String result = "playAgain";
                    while(result.equals("playAgain")) {
                        Robot robot = new Robot(2, 0, 0);
                        Map map = new Stage2(robot);
                        Play play =new Play();
                        result = play.play(robot, map);
                    }
                }
                case "STAGE3", "stage3" -> {
                    String result = "playAgain";
                    while(result.equals("playAgain")) {
                        Robot robot = new Robot(3, 0, 0);
                        Map map = new Stage3(robot);
                        Play play =new Play();
                        result = play.play(robot, map);
                    }
                }
                case "Q", "q" -> {
                }
                default -> {
                    System.out.println("No such option exists. Please re-enter it");
                }
            }
        }
        System.out.println("See you next time!");
    }

}
