package Battle;

class ShootMapPC {
    MatrixPC shootMapPC;
    static int CountOfSunkShipsPC;

    ShootMapPC() {
        shootMapPC = new MatrixPC(Box.closed);
        CountOfSunkShipsPC = 0;
    }

    Box getBoxMapPC(Coord coord) {
        return shootMapPC.get(coord);
    }
    int getCountOfSunkShipsPC() {
        return CountOfSunkShipsPC;
    }

    void setOpenedToBoxPC(Coord coord) {
        if(Box.closed == shootMapPC.get(coord))
            shootMapPC.set(coord, Box.zero);
        else {
            shootMapPC.set(coord, Box.flaged);
        }
    }

    static void increaseNumbersOfSinkedShipPC() {
        CountOfSunkShipsPC++;
    }

    void setZeroBox(Coord coord) {
        shootMapPC.set(coord, Box.zero);
    }

    void setFlagedBox(Coord coord) {
        shootMapPC.set(coord, Box.flaged);
    }
}


