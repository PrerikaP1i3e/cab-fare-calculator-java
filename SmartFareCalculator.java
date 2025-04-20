
import java.util.Scanner;

public class SmartFareCalculator {

    // Method to calculate multiplier based on traffic condition
    public static double getTrafficMultiplier(int trafficLevel) 
    {
        switch (trafficLevel) {
            case 1:
                return 1.0;  // Low traffic
            case 2:
                return 1.2;  // Medium traffic
            case 3:
                return 1.5;  // High traffic
            default:
                return 1.0; // default multiplier for invalid conditions
        }
    }

    // Method to calculate multiplier based on ride urgency
    public static double getUrgencyMultiplier(int urgency) {
        if (urgency >= 1 && urgency <= 5) {
            return 1.0;  // Least urgent (1 to 5)
        } else if (urgency >= 6 && urgency <= 8) {
            return 1.2;  // Medium urgency (6 to 8)
        } else if (urgency >= 9 && urgency <= 10) {
            return 1.5;  // Most urgent (9 to 10)
        } else {
            return 1.0;  // Default multiplier for invalid input
        }
    }

    // Method to calculate multiplier based on time of day
    public static double getTimeOfDayMultiplier(int timeChoice) {
        switch (timeChoice) {
            case 1:
                return 1.1; // Morning
            case 2:
                return 1.0; // Afternoon
            case 3:
                return 1.3; // Evening
            case 4:
                return 1.2; // Night
            case 5:
                return 1.5; // Peak
            default:
                return 1.0; // Default multiplier for invalid conditions
        }
    }

    // Method to calculate multiplier based on weather condition
    public static double getWeatherMultiplier(int weatherCondition) {
        switch (weatherCondition) {
            case 1:
                return 1.0; // Clear
            case 2:
                return 1.3; // Rainy
            case 3:
                return 1.5; // Stormy
            case 4:
                return 1.6; // Snowy
            default:
                return 1.0; // Default for unexpected input
        }
    }

    // Method to calculate multiplier based on available drivers
    public static double getDriversMultiplier(int availableDrivers) {
        if (availableDrivers <= 2) {
            return 1.5;
        } else if (availableDrivers <= 5) {
            return 1.2;
        } else {
            return 1.0;
        }
    }

    // Method to calculate multiplier based on recent ride requests (demand)
    public static double getDemandMultiplier(int recentRequests) {
        if (recentRequests <= 5) {
            return 1.0; // Low demand
        } else if (recentRequests <= 10) {
            return 1.2; // Medium demand
        } else {
            return 1.5; // High demand
        }
    }

    // Method to calculate the final fare
    public static double applyFareCap(double fare, double distance) {
        if (distance <= 5 && fare > 300) 
        {
            fare = 300;
        } 
        else if (distance <= 10 && fare > 600) 
        {
            fare = 600;
        } 
        else if (distance <= 20 && fare > 1200) 
        {
            fare = 1200;
        } 
        else if (distance <= 30 && fare > 1800) 
        {
            fare = 1800;
        } 
        else if (distance > 30 && fare > 2500) 
        {
            fare = 2500; // Optional cap for long distances
        }
        else if (distance > 40 && fare > 3200) 
        {
            fare = 3200; // Optional cap for long distances
        }
        return fare;
    }

    // Method to calculate the final fare
        public static double calculateFare(double baseFare, double distance, int trafficLevel,
            int availableDrivers, int timeChoice, int weatherCondition,
            int urgency, int recentRequests) {

        // Calculate the distance-based fare
        double ratePerKm = 10.0; // Example rate per km
        double distanceFare = distance * ratePerKm;

        // Get multipliers for different conditions
        double trafficMultiplier = getTrafficMultiplier(trafficLevel);
        double urgencyMultiplier = getUrgencyMultiplier(urgency);
        double timeOfDayMultiplier = getTimeOfDayMultiplier(timeChoice);
        double weatherMultiplier = getWeatherMultiplier(weatherCondition);
        double driversMultiplier = getDriversMultiplier(availableDrivers);
        double demandMultiplier = getDemandMultiplier(recentRequests); // NEW

        // Calculate the final fare using the multipliers
        double totalMultiplier = trafficMultiplier * urgencyMultiplier * timeOfDayMultiplier
                * weatherMultiplier * driversMultiplier * demandMultiplier;

        double finalFare = (baseFare + distanceFare) * totalMultiplier;

        return Math.round(finalFare * 100.0) / 100.0; // Round to 2 decimal places
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double baseFare = 0;
        double distance = 0;
        int trafficLevel = 0;
        int availableDrivers = -1;
        int timeChoice = 0;
        int weatherCondition = 0;
        int urgency = 0;
        int recentRequests = 0;

        // Taking inputs from user
        //To input the base-fare
        while (true) {
            System.out.print("Enter base fare (minimum ₹20): ");
            baseFare = sc.nextDouble();

            if (baseFare >= 20.0) {
                break; // valid input, exit loop
            } else {
                System.out.println("Base fare must be at least ₹20.");
            }
        }

        //To input the distance
        while (true) {
            System.out.print("Enter distance in km: ");
            distance = sc.nextDouble();

            if (distance > 0 && distance < 40) {
                break; // valid input, exit loop
            } 
            else if (distance > 40)
            {
                System.out.println("Please use SmartCab's Intercity Ride booking app for intercity travel.");
                return;
            }
            else 
            {
                System.out.println("Distance must be greater than 0.");
            }
        }

        //To input the traffic condition
        while (true) {
            System.out.println("Enter Traffic Condition:");
            System.out.println("\t1 - Low\n\t2 - Medium\n\t3 - High");
            System.out.print("Your choice: ");

            trafficLevel = sc.nextInt();
            sc.nextLine(); // clear newline

            if (trafficLevel == 1 || trafficLevel == 2 || trafficLevel == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please choose from existing options.");
            }
        }

        //To input the number of drivers available
        while (true) {
            System.out.print("Enter no. of available drivers: ");
            availableDrivers = sc.nextInt();

            if (availableDrivers < 0) {
                System.out.println("Invalid Input.");
            } else if (availableDrivers == 0) {
                System.out.println("No drivers available currently. Please try again later.");
                return; // exit the program
            } else {
                break; // valid input
            }
        }

        //To input the time of day
        while (true) {
            System.out.println("Enter Time of Day:");
            System.out.println("\t1 - Morning\n\t2 - Afternoon\n\t3 - Evening\n\t4 - Night\n\t5 - Peak Hours");
            System.out.print("Your choice: ");

            timeChoice = sc.nextInt();
            sc.nextLine(); // clear newline

            if (timeChoice == 1 || timeChoice == 2 || timeChoice == 3 || timeChoice == 4 || timeChoice == 5) {
                break;
            } else {
                System.out.println("Invalid choice. Please select a valid condition between 1 and 5.");
            }
        }

        //To input the weather condition
        while (true) {
            System.out.println("Enter Weather Condition:");
            System.out.println("\t1 - Clear\n\t2 - Rainy\n\t3 - Stormy\n\t4 - Snowy");
            System.out.print("Your choice: ");

            weatherCondition = sc.nextInt();

            if (weatherCondition == 1 || weatherCondition == 2 || weatherCondition == 3 || weatherCondition == 4) {
                break;
            } else {
                System.out.println("Invalid choice. Please select a condition between 1 and 4.");
            }
        }

        //To input ride urgency
        while (true) {
            System.out.println("Enter Ride Urgency (1 to 10):");
            System.out.print("Your choice (1 - least urgent, 10 - most urgent): ");
            urgency = sc.nextInt();

            if (urgency >= 1 && urgency <= 10) {
                break; // valid input
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 10.");
            }

        }

        // To input number of recent ride requests
        while (true) {
            System.out.print("Enter number of recent ride requests (last 5 minutes): ");
            recentRequests = sc.nextInt();

            if (recentRequests >= 0) {
                break;
            } else {
                System.out.println("Invalid input. Requests cannot be negative.");
            }
        }


        // Calculate the fare
        double calculatedFare = calculateFare(baseFare, distance, trafficLevel, availableDrivers,
                timeChoice, weatherCondition, urgency, recentRequests);

        double finalFare = applyFareCap(calculatedFare, distance);

        // Output the result
        System.out.println("The estimated fare for your ride is: ₹" + finalFare);

        System.out.println("Thank you for choosing to ride with us!");

    }
}
