package Civilization.Model;

import Civilization.Database.GameDatabase;

import java.util.ArrayList;

public class Demography {

    private ArrayList<Soldier> soldiers;
    private String name;
    private int size;
    private int totalHappiness;
    private int gold;
    private int score;

    public Demography(Civilization civilization) {
        this.soldiers = soldierCalculator(civilization);
        this.size = civilization.getTiles().size();
        this.totalHappiness = civilization.getHappiness();
        this.gold = civilization.getGold();
        this.name = civilization.getNickname();
        this.score = civilization.getScore();
    }

    private ArrayList<Soldier> soldierCalculator(Civilization civilization) {
        ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
        for (City city : civilization.getCities()) {
            for (Unit unit : city.getUnits()) {
                if (unit instanceof Soldier) {
                    soldiers.add((Soldier) unit);
                }
            }
        }
        return soldiers;
    }

    private int rank() {
        int rank = 1;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getScore() > this.score) {
                rank++;
            }
        }
        return rank;
    }

    private int happinessAverage() {
        int totalHappiness = 0;
        for (Civilization civilization : GameDatabase.players) {
            totalHappiness += civilization.getHappiness();
        }
        return totalHappiness / GameDatabase.players.size();
    }

    private int scoreAverage() {
        int totalScore = 0;
        for (Civilization civilization : GameDatabase.players) {
            totalScore += civilization.getScore();
        }
        return totalScore / GameDatabase.players.size();
    }

    private int goldAverage() {
        int totalGold = 0;
        for (Civilization civilization : GameDatabase.players) {
            totalGold += civilization.getGold();
        }
        return totalGold / GameDatabase.players.size();
    }

    private int sizeAverage() {
        int totalSize = 0;
        for (Civilization civilization : GameDatabase.players) {
            totalSize += civilization.getTiles().size();
        }
        return totalSize / GameDatabase.players.size();
    }

    private int happinessMin() {
        int min = Integer.MAX_VALUE / 100;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getHappiness() < min) {
                min = civilization.getHappiness();
            }
        }
        return min;
    }

    private int scoreMin() {
        int min = Integer.MAX_VALUE / 100;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getScore() < min) {
                min = civilization.getScore();
            }
        }
        return min;
    }

    private int goldMin() {
        int min = Integer.MAX_VALUE / 100;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getGold() < min) {
                min = civilization.getGold();
            }
        }
        return min;
    }

    private int sizeMin() {
        int min = Integer.MAX_VALUE / 100;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getTiles().size() < min) {
                min = civilization.getTiles().size();
            }
        }
        return min;
    }

    private int happinessMax() {
        int max = 0;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getHappiness() > max) {
                max = civilization.getHappiness();
            }
        }
        return max;
    }

    private int scoreMax() {
        int max = 0;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getScore() > max) {
                max = civilization.getScore();
            }
        }
        return max;
    }

    private int goldMax() {
        int max = 0;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getGold() > max) {
                max = civilization.getGold();
            }
        }
        return max;
    }

    private int sizeMax() {
        int max = 0;
        for (Civilization civilization : GameDatabase.players) {
            if (civilization.getTiles().size() > max) {
                max = civilization.getTiles().size();
            }
        }
        return max;
    }


    @Override
    public String toString() {
        String result = this.name + "\n";
        result += "Score = " + Integer.toString(this.score) + "\n";
        result += "rank = " + Integer.toString(rank()) + "\n";
        result += "Happiness = " + Integer.toString(this.totalHappiness) + "\n";
        result += "Gold = " + Integer.toString(this.gold) + "\n";
        result += "Size = " + Integer.toString(this.size) + "\n";
        result += "Average of Score = " + Integer.toString(scoreAverage()) + "\n";
        result += "Average of Happiness = " + Integer.toString(happinessAverage()) + "\n";
        result += "Average of Gold = " + Integer.toString(goldAverage()) + "\n";
        result += "Average of Size = " + Integer.toString(sizeAverage()) + "\n";
        result += "min = " + Integer.toString(scoreMin()) + " and max = " + Integer.toString(scoreMax()) + "of score\n";
        result += "min = " + Integer.toString(happinessMin()) + " and max = " + Integer.toString(happinessMax()) + "of happiness\n";
        result += "min = " + Integer.toString(goldMin()) + " and max = " + Integer.toString(goldMax()) + "of Gold\n";
        result += "min = " + Integer.toString(sizeMin()) + " and max = " + Integer.toString(sizeMax()) + "of Size";
        return result;
    }
}
