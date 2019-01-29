package Battle;

import java.util.ArrayList;
import java.util.Random;

public class Ranges {
    public static Coord sizeOfFieldGamer;
    public static Coord sizeOfFieldPC;
    public static ArrayList<Coord> allCoordsOfFieldsOfGamer;
    public static ArrayList<Coord> allCoordsOfFieldsOfPC;
    private static Random random = new Random();

    static void setSizeOfFieldGamer(Coord _sizeOfFieldGamer) {
        sizeOfFieldGamer = _sizeOfFieldGamer;
        allCoordsOfFieldsOfGamer = new ArrayList<Coord>();
        for (int x = 0; x < sizeOfFieldGamer.x; x++)
            for (int y = 0; y < sizeOfFieldGamer.y; y++)
                allCoordsOfFieldsOfGamer.add(new Coord(x, y));
    }
    static void setSizeOfFieldPC(Coord _sizeOfFieldPC) {
        sizeOfFieldPC = _sizeOfFieldPC;
        allCoordsOfFieldsOfPC = new ArrayList<Coord>();
        for (int x = 0; x < sizeOfFieldGamer.x; x++)
            for (int y = 0; y < sizeOfFieldPC.y; y++)
                allCoordsOfFieldsOfPC.add(new Coord(x, y));
    }

    public static Coord getSizeOfFieldGamer() {
        return sizeOfFieldGamer;
    }
    public static Coord getSizeOfFieldPC() {
        return sizeOfFieldPC;
    }
    public static ArrayList<Coord> getAllCoordsOfFieldsOfGamer() {
        return allCoordsOfFieldsOfGamer;
    }
    public static ArrayList<Coord> getAllCoordsOfFieldsOfPC() {
        return allCoordsOfFieldsOfPC;
    }

    static boolean inRangeFieldGamer(Coord coord) {
        return  coord.x >= 0 && coord.x < sizeOfFieldGamer.x &&
                coord.y >=0 && coord.y < sizeOfFieldGamer.y;

    }
    static boolean inRangeFieldPC(Coord coord) {
        return coord.x >= 0 && coord.x < sizeOfFieldGamer.x &&
                coord.y >=0 && coord.y < sizeOfFieldGamer.y;
    }

    static Coord getRandomCoordsOfFourDecksShipHorizontal(){
        return new Coord(random.nextInt(sizeOfFieldGamer.x - 3), random.nextInt(sizeOfFieldGamer.y));
    }
    static Coord getRandomCoordsOfFourDecksShipVertical() {
        return new Coord(random.nextInt(sizeOfFieldGamer.x), random.nextInt(sizeOfFieldGamer.y - 3));
    }
    static Coord getRandomCoordsOfThreeDecksShipHorizontal(){
        return new Coord(random.nextInt(sizeOfFieldGamer.x - 2), random.nextInt(sizeOfFieldGamer.y));
    }
    static Coord getRandomCoordsOfThreeDecksShipVertical() {
        return new Coord(random.nextInt(sizeOfFieldGamer.x), random.nextInt(sizeOfFieldGamer.y - 2));
    }
    static Coord getRandomCoordsOfTwoDecksShipHorizontal(){
        return new Coord(random.nextInt(sizeOfFieldGamer.x - 1), random.nextInt(sizeOfFieldGamer.y));
    }
    static Coord getRandomCoordsOfTwoDecksShipVertical() {
        return new Coord(random.nextInt(sizeOfFieldGamer.x), random.nextInt(sizeOfFieldGamer.y - 1));
    }

    static Coord getRandomCoords(){
        return new Coord(random.nextInt(sizeOfFieldGamer.x), random.nextInt(sizeOfFieldGamer.y));
    }
    static ArrayList<Coord> getCoordsAround(Coord coord) {
        Coord around;
        ArrayList<Coord> list = new ArrayList<>();
        for(int x = coord.x - 1; x <= coord.x + 1; x ++)
            for(int y = coord.y - 1; y <= coord.y + 1; y ++)
                if(inRangeFieldGamer(around = new Coord(x, y)))
                    if(!around.equals(coord))
                        list.add(around);
        return list;
    }
}




