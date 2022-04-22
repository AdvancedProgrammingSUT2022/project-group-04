package Model;

import java.util.ArrayList;

public class Building {

    private String name;
    private int cost;
    private int maintenance;
    private Technology technologyRequired;
    private ArrayList<Citizen> exports;

    public Building(String name) {
        this.name = name;
        this.exports = new ArrayList<Citizen>();
        switch (name) {
            case "Barracks":
                this.cost = 80;
                this.maintenance = 1;
                //
            case "Granary":
                this.cost = 100;
                this.maintenance = 1;
                //
            case "Library":
                this.cost = 80;
                this.maintenance = 1;
                //
            case "Monument":
                this.cost = 60;
                this.maintenance = 1;
                //
            case "Walls":
                this.cost = 100;
                this.maintenance = 1;
                //
            case "Water_Mill":
                this.cost = 120;
                this.maintenance = 2;
                //
            case "Armory":
                this.cost = 130;
                this.maintenance = 3;
                //
            case "Burial_Tomb":
                this.cost = 120;
                this.maintenance = 0;
                //
            case "Circus,":
                this.cost = 150;
                this.maintenance = 3;
                //
            case "Colosseum":
                this.cost = 150;
                this.maintenance = 3;
                //
            case "Courthouse":
                this.cost = 200;
                this.maintenance = 5;
                //
            case " Stable":
                this.cost = 100;
                this.maintenance = 1;
                //
            case "Temple":
                this.cost = 120;
                this.maintenance = 2;
                //
            case "Castle":
                this.cost = 200;
                this.maintenance = 3;
                //
            case "Forge":
                this.cost = 150;
                this.maintenance = 2;
                //
            case "Garden":
                this.cost = 120;
                this.maintenance = 2;
                //
            case "Market":
                this.cost = 120;
                this.maintenance = 0;
                //
            case "Mint":
                this.cost = 120;
                this.maintenance = 0;
                //
            case "Monastery":
                this.cost = 120;
                this.maintenance = 2;
                //
            case "University":
                this.cost = 200;
                this.maintenance = 3;
                //
            case "Workshop":
                this.cost = 100;
                this.maintenance = 2;
                //
        }
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public Technology getTechnologyRequired() {
        return technologyRequired;
    }

    public ArrayList<Citizen> getExports() {
        return exports;
    }
}

