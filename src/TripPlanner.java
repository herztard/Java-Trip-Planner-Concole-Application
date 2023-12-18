import java.text.NumberFormat;
import java.util.Scanner;


public class TripPlanner {
    static Scanner scanner = new Scanner(System.in);
    static String tempString = "";

    public static void main(String[] args) {
        greeting();
        travelTimeBudget();
        timeDifference();
        countryArea();
        nextDestination();
    }

    public static void greeting(){
        System.out.print("Welcome to Vacation Planner! \nWhat is your name: ");
        String username = scanner.nextLine();

        System.out.printf("Nice to meet you %s, where are you traveling to? ", username);
        String destination = scanner.nextLine();

        System.out.printf("Great! %s sounds like a great trip. \n\n", destination);
    }

    public static void travelTimeBudget() {
        int days;
        while (true){ //try catch, чтобы невнимательный пользователь не сломал программу при её исполнении.
            System.out.print("How many days are you going to spend traveling? ");
            tempString = scanner.nextLine();
            try {
                //"qwe" не запарсит интеджер и выдаст ошибку,
                // которую мы ловим с помощью catch
                days = Integer.parseInt(tempString); //"123" > 123 + 123 = 246
                break;
            } catch (Exception e) {
                System.out.print("Please, enter an integer number. ");
            }
        }
        int hours = days * 24, minutes = hours * 60, seconds = minutes * 60;

        //Тип данных float такой же точный, как и double, но double не такой же точный, как и float.
        //Цель: сохранить информацию насколько, насколько возможно.
        float moneyAmount;
        while (true){
            System.out.print("How much money, in USD, are you planning to spend on your trip: ");
            tempString = scanner.nextLine();
            try {
                moneyAmount = Float.parseFloat(tempString);
                break;
            } catch (Exception e) {
                System.out.print("Please, enter a decimal number. ");
            }
        }
        float spendPerDay = moneyAmount / days;

        System.out.print("What is the three letter currency symbol for your travel destination? ");
        String currency = scanner.nextLine().toUpperCase();

        float localPerOneDollar;
        while (true){
            System.out.printf("How many %s are there in 1 USD? ", currency);
            tempString = scanner.nextLine();
            try {
                localPerOneDollar = Float.parseFloat(tempString);
                break;
            } catch (Exception e) {
                System.out.print("Please, enter a decimal number. ");
            }
        }
        float moneyInLocal = moneyAmount * localPerOneDollar, spendLocalPerDay = spendPerDay * localPerOneDollar;

        NumberFormat formatting = NumberFormat.getCurrencyInstance();// for from 2000 to $2,000.00

        System.out.printf("\nIf you are traveling for %d days that is same as %d hours or %d minutes or %d seconds.\n",
                days, hours, minutes, seconds);
        
        System.out.printf("If you are going to spend %s USD that means per day you can spend up to %s USD.\n",
                formatting.format(moneyAmount), formatting.format(spendPerDay));

        System.out.printf("Your total budget in %s is %s %s, which per day is %s %s.\n\n",
                currency, formatting.format(moneyInLocal).substring(1), currency,
                formatting.format(spendLocalPerDay).substring(1), currency);
        /*
        Поскольку .format(число) по умолчанию ставит символ доллара в начале строки, substring(1) не берёт в учёт
        этот символ получаемый в строке отформатированного числа. Символ убирается, поскольку здесь валюта другая.
         */
    }
    public static void timeDifference(){
        int timeDiff;
        while (true){
            System.out.print("What is the time difference, in hours, between your home and your destination? ");
            tempString = scanner.nextLine();
            try {
                timeDiff = Integer.parseInt(tempString) % 24;
                break;
            } catch (Exception e) {
                System.out.print("Please, enter an integer number. ");
            }
        }
        //разница во времени не может быть больше 24
        int noonDiff = timeDiff + 12, midnightDiff = timeDiff;

        if (timeDiff >= 12) //если разница во времени 23 часа, то во время полудня дома не может быть 35 часов в месте путешествия
            noonDiff = timeDiff % 12;
        else if (timeDiff <= -12){//если разница во времени -23 часа, время суток не может быть отрицательным.
            noonDiff = 36 + timeDiff;
            midnightDiff = 24 + timeDiff;
        }
        else if (timeDiff < 0){//если разница во времени -11 часа, время суток не может быть отрицательным.
            noonDiff = 12 + timeDiff;
            midnightDiff = 24 + timeDiff;
        }
        if (noonDiff == 24) // 24:00 не может быть.
            noonDiff = 0;

        System.out.printf("That means that when it is midnight at home it will be %s:00 in your travel " +
                "destination and when it's noon at home it will be %s:00.\n\n", midnightDiff, noonDiff);
    }
    public static void countryArea(){
        double millis2PerKilos2 = 0.38610215854245;
        double areaInKilometers;
        while (true){
            System.out.print("What is square area of your destination country in km2? ");
            tempString = scanner.nextLine();
            try {
                areaInKilometers = Double.parseDouble(tempString);
                break;
            } catch (Exception e) {
                System.out.print("Please, enter a decimal number. ");
            }
        }
        double areaInMillis = Math.floor(areaInKilometers * millis2PerKilos2 * 100) / 100.0;
        System.out.printf("In miles2 that is %.2f\n\n", areaInMillis);
    }
    public static void nextDestination(){

        int traveledTimes;
        while (true){
            System.out.print("How many places you traveled to: ");
            tempString = scanner.nextLine();
            try {
                traveledTimes = Integer.parseInt(tempString);
                break;
            } catch (Exception e) {
                System.out.print("Please, enter an integer number. ");
            }
        }

        String[] traveledPlaces = new String[traveledTimes];
        for (int i = 0; i < traveledTimes; i++){
            System.out.print("Enter the name: ");
            traveledPlaces[i] = scanner.nextLine();
            for (int k = 0; k < i; k++) {
                while (traveledPlaces[i].equalsIgnoreCase(traveledPlaces[k])){
                    System.out.printf("%s is already in your list of visited countries. Please, try another one.\n",
                            traveledPlaces[i]);
                    System.out.print("Enter the name: ");
                    traveledPlaces[i] = scanner.nextLine();
                }
            }
        }
        /*
        Функция выше с вводом стран, которые пользователь уже посетил нигде не используется. Это делает её наличие
        бесполезным. Поэтому я решил добавить пару фич, чтобы список с посещенными странами хоть как-то функционировал.

        Моя программа должна предупреждать пользователя, если он совершил ошибку.
        Если пользователь ввел страну в список посещенных стран, то эта страна не должна встретиться в списке ещё раз.

        В списке со странами куда пользователь хотел бы съездить, он тоже может совершить ошибку.
        Для начала мы проверяем, есть ли введенная страна в списке посещенных стран, если пользователь посещал эту
        страну ранее, то он получит соответствующее уведомление. Если же пользователь всё же не ошибся и действительно
        был намерен посетить эту страну ещё раз, то у него остаётся такая возможность. Но если в списке желаемых
        к посещению стран уже есть эта страна, он не сможет её ввести дважды.
        */
        System.out.println("Next place you want to travel to: ");
        String[] wantToTravel = new String[5];
        for (int i = 0; i < 5; i++) {
            System.out.printf("%d: ", i + 1);
            wantToTravel[i] = scanner.nextLine();
            for (int j = 0; j < traveledTimes; j++) {
                for (int k = 0; k < i; k++) {
                    while (wantToTravel[i].equalsIgnoreCase(wantToTravel[k])){
                        System.out.printf("%s is already in your list of desirable countries to travel to.\n",
                                wantToTravel[i]);
                        System.out.printf("%d: ", i + 1);
                        wantToTravel[i] = scanner.nextLine();
                    }
                }
                if (wantToTravel[i].equalsIgnoreCase(traveledPlaces[j])) {
                    System.out.printf("You have already visited %s before. Please, re-enter the country " +
                            "if you want to visit it again. \n", wantToTravel[i]);
                    System.out.printf("%d: ", i + 1);
                    wantToTravel[i] = scanner.nextLine();
                }
            }
        }

        int nextDestination;
        while (true){
            System.out.print("Choose your next destination: ");
            tempString = scanner.nextLine();
            try {
                nextDestination = Integer.parseInt(tempString) - 1;
                break;
            } catch (Exception e) {
                System.out.print("Please, enter an integer number. ");
            }
        }
        System.out.printf("Thank you, Your next location is: %s", wantToTravel[nextDestination]);

    }
}
