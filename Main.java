import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Date implements Comparable<Date> {
    private int day, month, year;

    public Date(int day, int month, int year) {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            System.out.println("Invalid date! Please, check your date.");
        }
    }

    public boolean isValidDate(int d, int m, int y) {
        if (m < 1 || m > 12 || d < 1) return false;
        return d <= getDaysInMonth(m, y);
    }

    private int getDaysInMonth(int m, int y) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return (m == 2 && isLeapYear(y)) ? 29 : days[m - 1];
    }

    private boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    public void printDate() {
        System.out.println(day + "/" + month + "/" + year);
    }

    public int calculateDifference(Date other) {
        return Math.abs(toDays() - other.toDays());
    }

    private int toDays() {
        int days = year * 365 + day;
        for (int i = 1; i < month; i++) {
            days += getDaysInMonth(i, year);
        }
        return days;
    }

    public String getDayOfWeek() {
        int[] monthOffsets = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
        int y = year - (month < 3 ? 1 : 0);
        int century = y / 100, yearPart = y % 100;
        int dayIndex = (yearPart + yearPart / 4 + century / 4 - 2 * century + monthOffsets[month - 1] + day) % 7;
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return days[(dayIndex + 7) % 7];
    }

    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Date> dates = new ArrayList<>();

        while (true) {
            System.out.println("\nDate Structure Program");
            System.out.println("1) Add new date");
            System.out.println("2) Get day of the week for a date");
            System.out.println("3) Difference between two dates");
            System.out.println("4) Print all dates");
            System.out.println("0) Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter day, month, year: ");
                int day = scanner.nextInt(), month = scanner.nextInt(), year = scanner.nextInt();
                Date newDate = new Date(day, month, year);
                if (newDate.isValidDate(day, month, year)) {
                    dates.add(newDate);
                    System.out.println("Date added successfully!");
                }
            } else if (choice == 2) {
                if (dates.isEmpty()) {
                    System.out.println("No dates available.");
                    continue;
                }
                System.out.print("Enter index: ");
                int index = scanner.nextInt();
                if (index >= 0 && index < dates.size()) {
                    System.out.println(dates.get(index).getDayOfWeek());
                } else {
                    System.out.println("Invalid index!");
                }
            } else if (choice == 3) {
                if (dates.size() < 2) {
                    System.out.println("At least two dates required.");
                    continue;
                }
                System.out.print("Enter two indices: ");
                int idx1 = scanner.nextInt(), idx2 = scanner.nextInt();
                if (idx1 >= 0 && idx1 < dates.size() && idx2 >= 0 && idx2 < dates.size()) {
                    System.out.println("Difference: " + dates.get(idx1).calculateDifference(dates.get(idx2)) + " days");
                } else {
                    System.out.println("Invalid indices!");
                }
            } else if (choice == 4) {
                if (dates.isEmpty()) {
                    System.out.println("No dates to display.");
                } else {
                    Collections.sort(dates);
                    for (Date d : dates) {
                        d.printDate();
                    }
                }
            } else if (choice == 0) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }
}