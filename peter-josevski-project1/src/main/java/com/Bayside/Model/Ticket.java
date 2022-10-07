package com.Bayside.Model;

import java.util.Objects;


public class Ticket {

    private int id;
    private String Description;
    private int Amount; // 0 default
    private String Status;

    public Ticket() {
    }

    public Ticket(int id, String Description, int Amount, String Status) {
        this.id = id;
        this.Description = Description;
        this.Amount = Amount;
        this.Status = Status;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket that = (Ticket) o;
        return id == that.id && Description == that.Description && Amount == that.Amount && Objects.equals(Status, that.Status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Description, Amount, Status);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id: " + id +
                ", Description: " + Description + '\'' +
                ", Amount: " + Amount +
                ", Status: " + Status + " ";
    }
}
