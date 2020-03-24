package com.mobiquity.implementation.packer;

/** This represents an item to be included in a Package.*/
public class Item implements Comparable<Item> {

    private String index;
    private double weight;
    private double cost;

    public Item(String index, double weight, double cost){
        this.index = index;
        this.weight=weight;
        this.cost=cost;
    }

    public String getIndex(){return this.index;}
    public double getWeight(){return this.weight;}
    public double getCost(){return this.cost;}

    @Override
    public int compareTo(Item itemToCompare)
    {
        double deltaCost = itemToCompare.getCost() - this.getCost();
        double deltaWeight = itemToCompare.getWeight() - this.getWeight();

        if(deltaCost > 0.00001) return 1;
        if(deltaCost < -0.00001) return -1;

        if(deltaWeight < -0.00001) return 1;
        if(deltaWeight > 0.00001) return -1;

        return 0;
    }
}
