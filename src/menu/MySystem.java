package menu;

import contracts.Command;

import java.util.Scanner;

public class MySystem {
    private static MySystem instance;
    public static MySystem getInstance(){
        if(instance == null){
            instance =  new MySystem();
        }
        return instance;
    }
    public void start()  {
        Command command = new MainMenu();
        Scanner scanner = new Scanner(System.in);
        String input;
        String[] option;
        while(true){
            System.out.println("> ");
            input = scanner.nextLine();
            if(input.equals("exit")){
                System.out.println("Exiting the system!");
                System.exit(1);
            }
            option = input.split(" ");
            try{
                command.runCommand(option);
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}