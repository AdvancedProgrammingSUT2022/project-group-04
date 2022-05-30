package Civilization.Database;

public class GlobalVariables {

    public static final String[] BUILDINGS = new String[]{"Barracks", "Granary", "Library", "Monument", "Walls", "Water_Mill", "Armory", "Burial_Tomb", "Circus",
            "Colosseum", "Courthouse", "Stable", "Temple", "Castle", "Forge", "Garden", "Market", "Mint", "Monastery", "University", "Workshop", "Bank", "MilitaryAcademy",
            "Museum", "OperaHouse", "PublicSchool", "SatrapsCourt", "Theater", "Windmill", "Arsenal", "BroadcastTower", "Factory", "Hospital", "MilitaryBase", "StockExchange"};

    public static final String[] TECHNOLOGIES = new String[]{"Agriculture", "AnimalHusbandry", "Archery", "BronzeWorking", "Calendar", "Masonry",
            "Mining", "Pottery", "TheWheel", "Trapping", "Writing", "Construction", "HorsebackRiding", "IronWorking", "Mathematics", "Philosophy",
            "Chivalry", "CivilService", "Currency", "Education", "Engineering", "Machinery", "MetalCasting", "Physics", "Steel", "Theology", "Acoustics",
            "Archaeology", "Banking", "Chemistry", "Economics", "Fertilizer", "Gunpowder", "Metallurgy", "MilitaryScience", "PrintingPress", "Rifling",
            "ScientificTheory", "Biology", "Combustion", "Dynamite", "Electricity", "Radio", "Railroad", "ReplaceableParts", "SteamPower", "Telegraph"};

    public static final String[] BASE_TERRAINS = new String[]{"Desert", "Meadow", "Hill", "Mountain", "Ocean", "Plain", "Snow", "Tundra"};

    public static final String[] RESOURCES = new String[]{"Banana", "Cow", "Gazelle", "Sheep", "Wheat", "Cotton", "Color", "Fur", "Gemstone",
            "Gold", "Incense", "Ivory", "Marble", "Silk", "Silver", "Sugar", "Coal", "Horse", "Iron"};

    public static final String[] TERRAIN_FEATURES = new String[]{"Prairie", "Jungle", "Ice", "DenseJungle", "Swamp", "Oasis"};

    public static final String[] IMPROVEMENTS = new String[]{"Camp", "Farm", "LumberMill", "Mine", "Pasture", "Plantation", "Quarry",
            "TradingPost", "Factory"};

    public static final String[] UNITS = new String[]{"Archer", "ChariotArcher", "Scout", "Settler", "Spearman", "Warrior", "worker", "Catapult",
            "Horseman", "Swordsman", "Crossbowman", "Knight", "Longswordsman", "Pikeman", "Trebuchet", "Canon", "Cavalry", "Lancer", "Musketman",
            "Rifleman", "AntiTankGun", "Artillery", "Infantry", "Panzer", "Tank"};

    public static final int firstHappiness = 50;
    public static final int happinessForEachCitizen = 1;
    public static final int happinessForEachCity = 1;
    public static final int happinessForColonizedCities = 1;
    public static final String SYSTEM_NAME = "SYSTEM";

    public static final String USER_DATABASE_FILE_NAME = "UserDatabase.json";
    public static final String USER_DATABASE_TEST_FILE_NAME = "UserDatabaseTest.json";
}
