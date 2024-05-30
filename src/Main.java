import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File sportsFile = new File("src/Sports Question Bank - Sheet1.csv");
        File entertainmentFile = new File("src/Entertainment Question Bank - Sheet1.csv");
        File worldFile = new File("src/World Question Bank - Sheet1.csv");
        File scienceFile = new File("src/Science Question Bank - Sheet1.csv");
        File christmasFile = new File("src/Christmas Question Bank - Sheet1.csv");
        // finds each file in the src code
        int sportsQCount = questionCount(sportsFile);
        int entertainmentQCount = questionCount(entertainmentFile);
        int worldQCount = questionCount(worldFile);
        int scienceQCount = questionCount(scienceFile);
        int christmasQCount = questionCount(christmasFile);
        // counts the number of questions in each file
        String[][] sportsQBank = new String[sportsQCount][6];
        String[][] entertainmentQBank = new String[entertainmentQCount][6];
        String[][] worldQBank = new String[worldQCount][6];
        String[][] scienceQBank = new String[scienceQCount][6];
        String[][] christmasQBank = new String[christmasQCount][6];
        // creates a 2D array to store the file data in a question bank
        fileTo2DArray(sportsFile, sportsQCount, sportsQBank);
        fileTo2DArray(entertainmentFile, entertainmentQCount, entertainmentQBank);
        fileTo2DArray(worldFile, worldQCount, worldQBank);
        fileTo2DArray(scienceFile, scienceQCount, scienceQBank);
        fileTo2DArray(christmasFile, christmasQCount, christmasQBank);
        // moves data from the file into the 2D array for easier data manipulation
        while (true) {
            // the while loop was created to allow for the user to retry the programme infinitely until they are satisfied with the final quiz
            Scanner scanner = new Scanner(System.in);
            int sportsInput = 0;
            int entertainmentInput = 0;
            int worldInput = 0;
            int scienceInput = 0;
            int christmasInput = 0;
            //initializes integers to store user input for the number of questions he/she wants for each section
            System.out.println("How many questions do you want for the Sports category?");
            sportsInput = questionNumberInput(sportsQCount, sportsInput, scanner);
            //each section uses the same method to check user's input. However, the sout statement needed to be separated in order to make the question outputted to the user correspond with the data it is collecting.
            System.out.println("How many questions do you want for the Entertainment category?");
            entertainmentInput = questionNumberInput(entertainmentQCount, entertainmentInput, scanner);
            System.out.println("How many questions do you want for the World category?");
            worldInput = questionNumberInput(worldQCount, worldInput, scanner);
            System.out.println("How many questions do you want for the Science category?");
            scienceInput = questionNumberInput(scienceQCount, scienceInput, scanner);
            System.out.println("How many questions do you want for the Christmas category?");
            christmasInput = questionNumberInput(christmasQCount, christmasInput, scanner);
            String[][] sportsChosenQuestions = new String[sportsInput][6];
            String[][] entertainmentChosenQuestions = new String[entertainmentInput][6];
            String[][] worldChosenQuestions = new String[worldInput][6];
            String[][] scienceChosenQuestions = new String[scienceInput][6];
            String[][] christmasChosenQuestions = new String[christmasInput][6];
            //creates a 2D array based on the user's input of how many questions they want
            randomisedInput(sportsQCount, sportsInput, sportsQBank, sportsChosenQuestions);
            randomisedInput(entertainmentQCount, entertainmentInput, entertainmentQBank, entertainmentChosenQuestions);
            randomisedInput(worldQCount, worldInput, worldQBank, worldChosenQuestions);
            randomisedInput(scienceQCount, scienceInput, scienceQBank, scienceChosenQuestions);
            randomisedInput(christmasQCount, christmasInput, christmasQBank, christmasChosenQuestions);
            //randomises the questions and stores them into the chosenQuestions 2D array
            boolean testQuiz = testQuiz(scanner);
            //checks to see if the user would like to try out the quiz
            if (testQuiz)
                quiz(sportsChosenQuestions, entertainmentChosenQuestions, worldChosenQuestions, scienceChosenQuestions, christmasChosenQuestions, scanner);
            //if the user wants to test the quiz, it will run the quiz method
            else
                printQuiz(sportsChosenQuestions, entertainmentChosenQuestions, worldChosenQuestions, scienceChosenQuestions, christmasChosenQuestions);
            //if the user doesn't want to test the quiz, it will print out the quiz instead
            boolean saveQuiz = saveQuiz(scanner);
            //checks to see if the user would like to save out the quiz
            if (saveQuiz)
                writeFile(sportsChosenQuestions, entertainmentChosenQuestions, worldChosenQuestions, scienceChosenQuestions, christmasChosenQuestions);
                //if the user wants to save the quiz, it will run the writeFile method
            else tryAgain(scanner);
            //if the user doesn't want to save the quiz, it will ask the user if they want to try again
        }
    }

    private static void tryAgain(Scanner scanner) {
        System.out.println("Do you want to regenerate a new quiz? Input yes if you wish to regenerate a new quiz. Input no if you do not wish to regenerate a new quiz.");
        while (true) {
            String input = scanner.next().toLowerCase();
            //this allows for users to use both uppercase & lowercase when inputting
            if (input.equals("yes")) return;
            // this allows the while loop to restart
            if (input.equals("no")) {
                System.out.println("Thank you for your participation.");
                System.exit(0);
                // It ends the programme
            }
            else System.out.println("Please input a valid input");
            //It asks the user to input 'yes' or 'no' again
        }
    }

    private static int questionCount(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        int count = 0;
        while (scan.hasNextLine()) {
            scan.nextLine();
            count++;
            // if the scanner finds a next line in the file, the count increases, effectively counting the number of lines in the file and the number of questions stored
        }
        return count;
    }

    private static void fileTo2DArray(File file, int count, String[][] bank) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String[] temp = new String[count];
        for (int i = 0; i < count; i++) {
            temp[i] = scan.nextLine();
            //it first extracts all the data from the file and stores each line as a index in a 1D array
        }
        for (int i = 0; i < count; i++) {
            String[] temparr = temp[i].split(",");
            System.arraycopy(temparr, 0, bank[i], 0, 6);
            //it splits the commas in each index of the temp array into a new temporary array in order to copy all the values into the question bank
        }
    }

    private static int questionNumberInput(int qCount, int input, Scanner scanner) {
        while (input < 1) {
            input = scanner.nextInt();
            //this stores the input as the variable input
            if (input > qCount) {
                System.out.println("The amount of questions in this category are less than the inputted amount. Please try again");
                //this checks if the user input is within the number of questions in the file
                input = 0;
                //this resets the while loop to allow for a second input
            }
        }
        return input;
    }

    private static void randomisedInput(int qCount, int userInput, String[][] qBank, String[][] qChosen) {
        int[] questionNumber = new int[userInput];
        //this stores the randomised question numbers into an array
        int count = 0;
        Random rand = new Random();

        while (count < userInput) {
            int random = rand.nextInt(qCount);
            //this creates a new randomised number based on the number of questions in the file
            boolean isDuplicate = false;
            for (int i = 0; i < count; i++) {
                if (questionNumber[i] == random) {
                    isDuplicate = true;
                    break;
                    //this checks if there are duplicate numbers in the questionNumber array
                }
            }

            if (!isDuplicate) {
                questionNumber[count] = random;
                //if there are no duplicates, it will be stored in the questionNumber array and the programme will run again
                count++;
            }
        }

        for (int i = 0; i < userInput; i++) {
            System.arraycopy(qBank[questionNumber[i]], 0, qChosen[i], 0, qBank[questionNumber[i]].length);
            //the question bank values correspond to the question number in the questionNumber array are copied into the chosenQuestions array.
        }
    }

    private static boolean testQuiz(Scanner scanner) {
        System.out.println("Do you want to test the quiz? Input yes if you wish to test the quiz. Input no if you do not wish to test the quiz.");
        while (true){
        String input = scanner.next().toLowerCase();
        //this allows the user to input in both uppercase and lowercase
        if (input.equals("yes")) return true;
        if (input.equals("no")) return false;
        //this changes the user's string input into a boolean value
        else System.out.println("Please input a valid input");
        //the while loop runs again if the user didn't input yes or no
        }
    }
    private static void quiz (String[][] sportsQuestions, String[][] entertainmentQuestions, String[][] worldQuestions, String[][] scienceQuestions, String[][] christmasQuestions, Scanner scanner){
        System.out.println("Your Multiple Choice Quiz:\n\n");
        System.out.println("Input in either 'A', 'B', 'C'. or 'D' when answering");
        if (sportsQuestions.length > 0) {
            System.out.println("Sports:\n");
            quizSection(sportsQuestions,scanner);
            //this checks if the section exists, and it runs the individual section as a separate nested method
        }
        if (entertainmentQuestions.length > 0) {
            System.out.println("Entertainment:\n");
            quizSection(entertainmentQuestions,scanner);
        }
        if (worldQuestions.length > 0) {
            System.out.println("World:\n");
            quizSection(worldQuestions,scanner);
        }
        if (scienceQuestions.length > 0) {
            System.out.println("Science:\n");
            quizSection(scienceQuestions,scanner);
        }
        if (christmasQuestions.length > 0) {
            System.out.println("Christmas:\n");
            quizSection(christmasQuestions,scanner);
        }
    }
    private static void quizSection (String[][] questions, Scanner scanner) {
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i+1) + ":");
            System.out.println(questions[i][0]);
            System.out.println("Option A: " + questions[i][1]);
            System.out.println("Option B: " + questions[i][2]);
            System.out.println("Option C: " + questions[i][3]);
            System.out.println("Option D: " + questions[i][4]);
            //this prints out the question and the four possible answers
            boolean correctInput = false;
            String userInput = "";
            while (!correctInput) {
                userInput = scanner.next().toUpperCase();
                if (userInput.equals("A") || userInput.equals("B") || userInput.equals("C") || userInput.equals("D")){
                    correctInput = true;
                    //it checks if the user has inputted a valid answer
                }
                else System.out.println("Please input a valid answer.");
            }
            if (userInput.equals(questions[i][5])) System.out.println("You are correct!");
            //checks if the user's answer is correct
            else System.out.println("You are incorrect. The correct answer was " + questions[i][5] + ".");
            //tells the user the correct answer

        }
        System.out.println("\n\n");
    }
    private static void printQuiz(String[][] sportsQuestions, String[][] entertainmentQuestions, String[][] worldQuestions, String[][] scienceQuestions, String[][] christmasQuestions){
        System.out.println("Your Multiple Choice Quiz:\n");
        //this prints out the entire quiz
        if (sportsQuestions.length > 0) {
            System.out.println("Sports:\n");
            quizPrintSection(sportsQuestions);
        }
        if (entertainmentQuestions.length > 0) {
            System.out.println("Entertainment:\n");
            quizPrintSection(entertainmentQuestions);
        }
        if (worldQuestions.length > 0) {
            System.out.println("World:\n");
            quizPrintSection(worldQuestions);
        }
        if (scienceQuestions.length > 0) {
            System.out.println("Science:\n");
            quizPrintSection(scienceQuestions);
        }
        if (christmasQuestions.length > 0) {
            System.out.println("Christmas:\n");
            quizPrintSection(christmasQuestions);
        }
    }
    private static void quizPrintSection(String[][] questions){
        //this is an individual section used to print out each section in order to simplify the cose
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(questions[i][0]);
            System.out.println("Option A: " + questions[i][1]);
            System.out.println("Option B: " + questions[i][2]);
            System.out.println("Option C: " + questions[i][3]);
            System.out.println("Option D: " + questions[i][4]);
            System.out.println("Correct Answer: " + questions[i][5]);
            System.out.println("\n");
        }
    }
    private static boolean saveQuiz(Scanner scanner) {
        System.out.println("Do you want to save the quiz? Input yes if you wish to save the quiz. Input no if you do not wish to save the quiz.");
        while (true){
            String input = scanner.next().toLowerCase();
            if (input.equals("yes")) return true;
            if (input.equals("no")) return false;
            else System.out.println("Please input a valid input");
        }
    }
    private static void writeFile(String[][] sportsQuestions, String[][] entertainmentQuestions, String[][] worldQuestions, String[][] scienceQuestions, String[][] christmasQuestions) throws FileNotFoundException {
        PrintStream writer = new PrintStream("Your Multiple Choice Quiz");
        writer.println("Your Multiple Choice Quiz:\n");
        if (sportsQuestions.length > 0) {
            writer.println("Sports:\n");
            quizWriteSection(sportsQuestions,writer);
        }
        if (entertainmentQuestions.length > 0) {
            writer.println("Entertainment:\n");
            quizWriteSection(entertainmentQuestions,writer);
        }
        if (worldQuestions.length > 0) {
            writer.println("World:\n");
            quizWriteSection(worldQuestions,writer);
        }
        if (scienceQuestions.length > 0) {
            writer.println("Science:\n");
            quizWriteSection(scienceQuestions,writer);
        }
        if (christmasQuestions.length > 0) {
            writer.println("Christmas:\n");
            quizWriteSection(christmasQuestions,writer);
        }
        System.out.println("Your file has been created. Thank you for your participation!");
        System.exit(0);
        //this exits the programme
    }
    private static void quizWriteSection(String[][] questions, PrintStream writer){
        for (int i = 0; i < questions.length; i++) {
            writer.println("Question " + (i + 1) + ":");
            writer.println(questions[i][0]);
            writer.println("Option A: " + questions[i][1]);
            writer.println("Option B: " + questions[i][2]);
            writer.println("Option C: " + questions[i][3]);
            writer.println("Option D: " + questions[i][4]);
            writer.println("Correct Answer: " + questions[i][5]);
        }
        writer.println("\n");
    }
}