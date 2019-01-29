package Battle;

import java.util.Random;

class ShipsMapGamer {
    MatrixGamer shipsMapGamer;
    private int totalShipsGamer;
    static int CountOfSunkShipsGamer;

    ShipsMapGamer(int totalShips) {
        this.totalShipsGamer = totalShips;
        shipsMapGamer = new MatrixGamer(Box.closed);

        placeFourDeckShipGamer();

        for (int i = 0; i < 2; i++)
            placeThreeDeckShipGamer();

        for (int i = 0; i < 3; i++)
            placeTwoDeckShipGamer();

        for (int i = 0; i < 4; i++)
            placeOneDeckShipGamer();

        removeLabelGamer();
        CountOfSunkShipsGamer = 0;
    }

    private void placeOneDeckShipGamer() {
        while (true) {
            Coord coord = Ranges.getRandomCoords();
            if (Box.num1 == shipsMapGamer.get(coord) || Box.flaged == shipsMapGamer.get(coord))
                continue;
            shipsMapGamer.set(coord, Box.num1);
            for (Coord around : Ranges.getCoordsAround(coord))
                shipsMapGamer.set(around, Box.flaged);
            break;
        }
    }
    private void placeTwoDeckShipGamer() {
        Random rand = new Random();
        int direction = rand.nextInt(2);

        if (direction == 0) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfTwoDecksShipHorizontal();
                if (Box.num1 == shipsMapGamer.get(coord) || Box.flaged == shipsMapGamer.get(coord)
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x + 1, coord.y))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x + 1, coord.y)))
                    continue;

                shipsMapGamer.set(coord,  Box.num1);
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x + 1, coord.y), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x + 1, coord.y)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);
                break;
            }
        }
        if (direction == 1) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfFourDecksShipVertical();
                if (Box.num1 == shipsMapGamer.get(coord) || Box.flaged == shipsMapGamer.get(coord)
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x, coord.y + 1))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x, coord.y + 1)))
                    continue;

                shipsMapGamer.set(coord, Box.num1);
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x, coord.y + 1), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 1)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);
                break;
            }
        }
    }
    private void placeThreeDeckShipGamer() {
        Random rand = new Random();
        int direction = rand.nextInt(2);

        if (direction == 0) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfThreeDecksShipHorizontal();
                if (Box.num1 == shipsMapGamer.get(coord) || Box.flaged == shipsMapGamer.get(coord)
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x + 1, coord.y))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x + 1, coord.y))
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x + 2, coord.y))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x + 2, coord.y)))
                    continue;

                shipsMapGamer.set(coord, Box.num1);
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x + 1, coord.y), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x + 1, coord.y)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x + 2, coord.y), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x + 2, coord.y)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);
                break;
            }
        }
        if (direction == 1) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfThreeDecksShipVertical();
                if (Box.num1 == shipsMapGamer.get(coord) || Box.flaged == shipsMapGamer.get(coord)
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x, coord.y + 1))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x, coord.y + 1))
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x, coord.y + 2))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x, coord.y + 2)))
                    continue;

                shipsMapGamer.set(coord, Box.num1);
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x, coord.y + 1), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 1)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x, coord.y + 2), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 2)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);
                break;
            }
        }
    }
    private void placeFourDeckShipGamer() {
        Random rand = new Random();
        int direction = rand.nextInt(2);

        if (direction == 0) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfFourDecksShipHorizontal();
                if (Box.num1 == shipsMapGamer.get(coord) || Box.flaged == shipsMapGamer.get(coord)
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x + 1, coord.y))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x + 1, coord.y))
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x + 2, coord.y))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x + 2, coord.y))
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x + 3, coord.y))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x + 3, coord.y)))
                    continue;

                shipsMapGamer.set(coord, Box.num1);
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x + 1, coord.y), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x + 1, coord.y)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x + 2, coord.y), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x + 2, coord.y)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x + 3, coord.y), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x + 3, coord.y)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);
                break;
            }
        }
        if (direction == 1) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfFourDecksShipVertical();
                if (Box.num1 == shipsMapGamer.get(coord) || Box.flaged == shipsMapGamer.get(coord)
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x, coord.y + 1))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x, coord.y + 1))
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x, coord.y + 2))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x, coord.y + 2))
                        || Box.num1 == shipsMapGamer.get(new Coord(coord.x, coord.y + 3))
                        || Box.flaged == shipsMapGamer.get(new Coord(coord.x, coord.y + 3)))
                    continue;

                shipsMapGamer.set(coord, Box.num1);
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x, coord.y + 1), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 1)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x, coord.y + 2), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 2)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                shipsMapGamer.set(new Coord(coord.x, coord.y + 3), Box.num1);
                for (Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 3)))
                    if (Box.closed == shipsMapGamer.get(around))
                        shipsMapGamer.set(around, Box.flaged);

                break;
            }
        }
    }
    private void removeLabelGamer() {
        for (Coord coord : Ranges.getAllCoordsOfFieldsOfGamer())
            if (Box.flaged == shipsMapGamer.get(coord))
                shipsMapGamer.set(coord, Box.closed);
    }

    int getTotalShipsGamer() {
        return totalShipsGamer;
    }

    Box getBoxMapGamer(Coord coord) {
        return shipsMapGamer.get(coord);
    }

    int getCountOfSunkShipsGamer() {
        return CountOfSunkShipsGamer;
    }

    static void increaseNumberOfSinkedShipGamer() {
        CountOfSunkShipsGamer++;
    }

    void setOpenedToBoxGamer(Coord shootOfPC) {
        if (Box.closed == shipsMapGamer.get(shootOfPC))
            shipsMapGamer.set(shootOfPC, Box.zero);
        else  {
            shipsMapGamer.set(shootOfPC, Box.flaged);
        }
    }
}