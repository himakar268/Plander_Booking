package com.mdx.xyphose.usermgmt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private int maxCapacity;
    private int availableCapacity;
    private double ratePerHour;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Venue() {}

    public Venue(String name, String image, int maxCapacity, int availableCapacity, double ratePerHour, Game game) {
        this.name = name;
        this.image = image;
        this.maxCapacity = maxCapacity;
        this.availableCapacity = availableCapacity;
        this.ratePerHour = ratePerHour;
        this.game = game;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public int getAvailableCapacity() { return availableCapacity; }
    public void setAvailableCapacity(int availableCapacity) { this.availableCapacity = availableCapacity; }

    public double getRatePerHour() { return ratePerHour; }
    public void setRatePerHour(double ratePerHour) { this.ratePerHour = ratePerHour; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
}