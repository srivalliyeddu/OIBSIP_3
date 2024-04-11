import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Transaction {
    private Date date;
    private double amount;
    private String description;

    public Transaction(Date date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}

class BankAccount {
    private double balance;
    private List<Transaction> transactions;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
        transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction(new Date(), amount, "Deposit"));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction(new Date(), -amount, "Withdrawal"));
            return true;
        } else {
            return false;
        }
    }

    public void transfer(double amount, BankAccount recipient) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactions.add(new Transaction(new Date(), -amount, "Transfer to " + recipient.toString()));
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public List<Transaction> getTransactionHistory() {
        return transactions;
    }
}

public class SimpleATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount account = new BankAccount(0);

        while (true) {
            System.out.println("Choose 1 for Deposit");
            System.out.println("Choose 2 for Withdraw");
            System.out.println("Choose 3 for Transfer");
            System.out.println("Choose 4 for Transaction History");
            System.out.println("Choose 5 for Check Balance");
            System.out.println("Choose 6 for EXIT\n");
            System.out.print("Choose the operation:");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("");
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = sc.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient funds");
                    }
                    System.out.println("");
                    break;

                case 3:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = sc.nextDouble();
                    System.out.print("Enter recipient's account number: ");
                    int recipientAccountNumber = sc.nextInt();
                    BankAccount recipientAccount = new BankAccount(0); // Dummy recipient account
                    account.transfer(transferAmount, recipientAccount);
                    System.out.println("");
                    break;

                case 4:
                    System.out.println("Transaction history:");
                    for (Transaction transaction : account.getTransactionHistory()) {
                        System.out.println(transaction.getDate() + " - " + transaction.getDescription() + ": " + transaction.getAmount());
                    }
                    System.out.println("");
                    break;

                case 5:
                    System.out.println("Balance: " + account.getBalance());
                    System.out.println("");
                    break;

                case 6:
                    System.exit(0);
            }
        }
    }
}