package Battle;

import java.util.ArrayList;

public class Game {
    ShipsMapGamer shipsMapGamer;
    ShipsMapPC shipsMapPC;
    ShootMapPC shootMapPC;
    public GameState state;
    public static HitMultipleDeckState hitDeckState;
    int totalShipsGamer;
    int totalShipsPC;

    static boolean makeSecondShootOfPC;

    static boolean shootNorthOneDeck;
    static boolean shootNorthTwoDeck;
    static boolean shootNorthThreeDeck;
    static boolean shootOstOneDeck;
    static boolean shootOstTwoDeck;
    static boolean shootOstThreeDeck;
    static boolean shootSouthOneDeck;
    static boolean shootSouthTwoDeck;
    static boolean shootSouthThreeDeck;
    static boolean shootWestOneDeck;
    static boolean shootWestTwoDeck;
    static boolean shootWestThreeDeck;

    static Coord firstShootOfPC;
    static Coord secondShootOfPC;
    static Coord thirdShootOfPC;
    static Coord fourShootOfPC;

    public Game(int cols, int rows, int totalShipsGamer, int totalShipsPC) {
        Ranges.setSizeOfFieldGamer(new Coord(cols, rows));
        Ranges.setSizeOfFieldPC(new Coord(cols, rows));
        this.totalShipsGamer = totalShipsGamer;
        this.totalShipsPC = totalShipsPC;
    }

    public void start() {
        shipsMapGamer = new ShipsMapGamer(totalShipsGamer);
        shipsMapPC = new ShipsMapPC(totalShipsPC);
        shootMapPC = new ShootMapPC();
        state = GameState.PLAYED;
        hitDeckState = null;
    }

    public GameState getState() {
        return state;
    }

    public Box getBoxGamer(Coord coord) {
        return shipsMapGamer.getBoxMapGamer(coord);
    }

    public Box getBoxPC(Coord coord) {
        if (Box.closed == shootMapPC.getBoxMapPC(coord))
            return shootMapPC.getBoxMapPC(coord);
        else
            return shipsMapPC.getBoxMapPC(coord);
    }

    public void pressLeftButton(Coord coord) throws InterruptedException {
        if (gameOver()) return;
        if (checkPressedCell(coord)) {    // проверяем что игрок стреляет в нетронутую клетку
            openBoxShootOfGamer(coord);  // открываем бокс куда выстрелили игрок
            checkIsShipPCSunk(coord);
            checkWinner();
            if (checkHitGamer(coord)) return; // проверка на продолжение хода игроком если попадание в корабль
//            Thread.sleep(500);         // или продолжение метода и стрельба компьютера если игрок промахнулся
            shootOfPC();
            checkWinner();
        }
    }

    public void pressLeftButtonHitSecondDeck(Coord coord) throws InterruptedException {
        if (gameOver()) return;
        if (checkPressedCell(coord)) {
            openBoxShootOfGamer(coord);
            checkIsShipPCSunk(coord);
            checkWinner();
            if (checkHitGamer(coord)) return;
//            Thread.sleep(500);
            shootOfPCUnderSecondDeckShip();
            checkWinner();
        }
    }

    public void pressLeftButtonHitThirdDeck(Coord coord) throws InterruptedException {
        if (gameOver()) return;
        if (checkPressedCell(coord)) {
            openBoxShootOfGamer(coord);
            checkIsShipPCSunk(coord);
            checkWinner();
            if (checkHitGamer(coord)) return;
//            Thread.sleep(500);
            shootOfPCUnderThirdDeckShip();
            checkWinner();
        }
    }

    public void pressLeftButtonHitFourDeck(Coord coord) throws InterruptedException {
        if (gameOver()) return;
        if (checkPressedCell(coord)) {
            openBoxShootOfGamer(coord);
            checkIsShipPCSunk(coord);
            checkWinner();
            if (checkHitGamer(coord)) return;
//            Thread.sleep(500);
            shootOfPCUnderFourDeckShip();
            checkWinner();
        }
    }

    private void shootOfPC() throws InterruptedException {
        Coord shootOfPC;

        while (true) {
            shootOfPC = Ranges.getRandomCoords();
            if (Box.zero == shipsMapGamer.getBoxMapGamer(shootOfPC)
                    || Box.flaged == shipsMapGamer.getBoxMapGamer(shootOfPC))
                continue;
            break;
        }
        firstShootOfPC = shootOfPC;

        if (Box.closed == shipsMapGamer.getBoxMapGamer(shootOfPC))
            shipsMapGamer.setOpenedToBoxGamer(shootOfPC);
//        Thread.sleep(500);


        if (Box.num1 == shipsMapGamer.getBoxMapGamer(shootOfPC)) {

            if (countOfDecksShip(shootOfPC) == 1) {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(shootOfPC);
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                Thread.sleep(500);
                checkWinner();
                setOpenBoxAroundSunkShipOfGamer(shootOfPC);
//                Thread.sleep(500);
                shootOfPC();


            } else if (countOfDecksShip(shootOfPC) == 2) {
                hitDeckState = HitMultipleDeckState.HIT_SECOND_DECK;
                makeSecondShootOfPC = false;
                shipsMapGamer.setOpenedToBoxGamer(shootOfPC);
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                        makeSecondShootOfPC = true;
                    }
                }
                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                        makeSecondShootOfPC == false) {
                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                        makeSecondShootOfPC = true;
                    }
                }
                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                        makeSecondShootOfPC == false) {
                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                        makeSecondShootOfPC = true;
                    }
                }
                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                        makeSecondShootOfPC == false) {
                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                        makeSecondShootOfPC = true;
                    }
                }

            } else if (countOfDecksShip(shootOfPC) == 3) {
                hitDeckState = HitMultipleDeckState.HIT_THIRD_DECK;
                makeSecondShootOfPC = false;
                shipsMapGamer.setOpenedToBoxGamer(shootOfPC);
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 2))) {
                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 2))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 2));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y + 2));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 2));
                                thirdShootOfPC = new Coord(shootOfPC.x, shootOfPC.y - 1);
                                shootSouthOneDeck = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                        makeSecondShootOfPC = true;
                    }
                } else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 2, shootOfPC.y))) {
                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 2, shootOfPC.y))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 2, shootOfPC.y));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x + 2, shootOfPC.y));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 2, shootOfPC.y));
                                thirdShootOfPC = new Coord(shootOfPC.x - 1, shootOfPC.y);
                                shootWestOneDeck = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                        makeSecondShootOfPC = true;
                    }
                } else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 2, shootOfPC.y))) {
                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 2, shootOfPC.y))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 2, shootOfPC.y));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x - 2, shootOfPC.y));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 2, shootOfPC.y));
                                thirdShootOfPC = new Coord(shootOfPC.x + 1, shootOfPC.y);
                                shootOstOneDeck = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                        makeSecondShootOfPC = true;
                    }
                } else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 2))) {
                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 2))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 2));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y - 2));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 2));
                                thirdShootOfPC = new Coord(shootOfPC.x, shootOfPC.y + 1);
                                shootNorthOneDeck = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                        makeSecondShootOfPC = true;
                    }
                }


            } else if (countOfDecksShip(shootOfPC) == 4) {
                hitDeckState = HitMultipleDeckState.HIT_FOUR_DECK;
                makeSecondShootOfPC = false;
                shipsMapGamer.setOpenedToBoxGamer(shootOfPC);
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 2))) {

                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 2))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 2));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 3)) &&
                                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 3))) {

                                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 3))) {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 3));
                                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                        Thread.sleep(500);
                                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y + 3));
                                        checkWinner();
                                        hitDeckState = null;
                                        makeSecondShootOfPC = true;
//                                        Thread.sleep(500);
                                        shootOfPC();
                                    } else {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 3));
                                        fourShootOfPC = new Coord(shootOfPC.x, shootOfPC.y - 1);
                                        shootSouthOneDeck = true;
                                        makeSecondShootOfPC = true;
                                    }
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 2));
                                thirdShootOfPC = new Coord(shootOfPC.x, shootOfPC.y - 1);
                                fourShootOfPC = new Coord(shootOfPC.x, shootOfPC.y - 2);
                                shootSouthTwoDeck = true;
                                makeSecondShootOfPC = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 2));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y - 2));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                        secondShootOfPC = new Coord(shootOfPC.x, shootOfPC.y - 1);
                        thirdShootOfPC = new Coord(shootOfPC.x, shootOfPC.y - 2);
                        fourShootOfPC = new Coord(shootOfPC.x, shootOfPC.y - 3);
                        shootSouthThreeDeck = true;
                        makeSecondShootOfPC = true;
                    }


                } else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 2))) {

                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 2))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 2));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 3)) &&
                                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 3))) {

                                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 3))) {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 3));
                                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                        Thread.sleep(500);
                                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y - 3));
                                        checkWinner();
                                        hitDeckState = null;
                                        makeSecondShootOfPC = true;
//                                        Thread.sleep(500);
                                        shootOfPC();
                                    } else {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 3));
                                        fourShootOfPC = new Coord(shootOfPC.x, shootOfPC.y + 1);
                                        shootNorthOneDeck = true;
                                        makeSecondShootOfPC = true;
                                    }
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 2));
                                thirdShootOfPC = new Coord(shootOfPC.x, shootOfPC.y + 1);
                                fourShootOfPC = new Coord(shootOfPC.x, shootOfPC.y + 2);
                                shootNorthTwoDeck = true;
                                makeSecondShootOfPC = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 1));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y + 2));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x, shootOfPC.y + 2));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x, shootOfPC.y - 1));
                        secondShootOfPC = new Coord(shootOfPC.x, shootOfPC.y + 1);
                        thirdShootOfPC = new Coord(shootOfPC.x, shootOfPC.y + 2);
                        fourShootOfPC = new Coord(shootOfPC.x, shootOfPC.y + 3);
                        shootNorthThreeDeck = true;
                        makeSecondShootOfPC = true;
                    }


                } else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 2, shootOfPC.y))) {

                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 2, shootOfPC.y))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 2, shootOfPC.y));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 3, shootOfPC.y)) &&
                                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 3, shootOfPC.y))) {

                                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 3, shootOfPC.y))) {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 3, shootOfPC.y));
                                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                        Thread.sleep(500);
                                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x + 3, shootOfPC.y));
                                        checkWinner();
                                        hitDeckState = null;
                                        makeSecondShootOfPC = true;
//                                        Thread.sleep(500);
                                        shootOfPC();
                                    } else {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 3, shootOfPC.y));
                                        fourShootOfPC = new Coord(shootOfPC.x - 1, shootOfPC.y);
                                        shootWestOneDeck = true;
                                        makeSecondShootOfPC = true;
                                    }
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 2, shootOfPC.y));
                                thirdShootOfPC = new Coord(shootOfPC.x - 1, shootOfPC.y);
                                fourShootOfPC = new Coord(shootOfPC.x - 2, shootOfPC.y);
                                shootWestTwoDeck = true;
                                makeSecondShootOfPC = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 2, shootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x - 2, shootOfPC.y));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                        secondShootOfPC = new Coord(shootOfPC.x - 1, shootOfPC.y);
                        thirdShootOfPC = new Coord(shootOfPC.x - 2, shootOfPC.y);
                        fourShootOfPC = new Coord(shootOfPC.x - 3, shootOfPC.y);
                        shootWestThreeDeck = true;
                        makeSecondShootOfPC = true;
                    }


                } else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                        makeSecondShootOfPC == false) {

                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y))) {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)) &&
                                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 2, shootOfPC.y))) {

                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 2, shootOfPC.y))) {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 2, shootOfPC.y));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                                if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 3, shootOfPC.y)) &&
                                        Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 3, shootOfPC.y))) {

                                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 3, shootOfPC.y))) {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 3, shootOfPC.y));
                                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                        Thread.sleep(500);
                                        setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x - 3, shootOfPC.y));
                                        checkWinner();
                                        hitDeckState = null;
                                        makeSecondShootOfPC = true;
//                                        Thread.sleep(500);
                                        shootOfPC();
                                    } else {
//                                        Thread.sleep(500);
                                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 3, shootOfPC.y));
                                        fourShootOfPC = new Coord(shootOfPC.x + 1, shootOfPC.y);
                                        shootOstOneDeck = true;
                                        makeSecondShootOfPC = true;
                                    }
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 2, shootOfPC.y));
                                thirdShootOfPC = new Coord(shootOfPC.x + 1, shootOfPC.y);
                                fourShootOfPC = new Coord(shootOfPC.x + 2, shootOfPC.y);
                                shootOstTwoDeck = true;
                                makeSecondShootOfPC = true;
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 1, shootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x + 2, shootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(shootOfPC.x + 2, shootOfPC.y));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(shootOfPC.x - 1, shootOfPC.y));
                        secondShootOfPC = new Coord(shootOfPC.x + 1, shootOfPC.y);
                        thirdShootOfPC = new Coord(shootOfPC.x + 2, shootOfPC.y);
                        fourShootOfPC = new Coord(shootOfPC.x + 3, shootOfPC.y);
                        shootOstThreeDeck = true;
                        makeSecondShootOfPC = true;
                    }
                }
            }
        }
    }

    private void shootOfPCUnderSecondDeckShip() throws InterruptedException {
        makeSecondShootOfPC = false;

        if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1)) &&
                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1)) &&
                makeSecondShootOfPC == false) {
            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1))) {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                Thread.sleep(500);
                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                checkWinner();
                hitDeckState = null;
                makeSecondShootOfPC = true;
//                Thread.sleep(500);
                shootOfPC();
            } else {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                makeSecondShootOfPC = true;
            }
        }
        if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y)) &&
                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y)) &&
                makeSecondShootOfPC == false) {
            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y))) {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                Thread.sleep(500);
                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                checkWinner();
                hitDeckState = null;
                makeSecondShootOfPC = true;
//                Thread.sleep(500);
                shootOfPC();
            } else {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                makeSecondShootOfPC = true;
            }
        }
        if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y)) &&
                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y)) &&
                makeSecondShootOfPC == false) {
            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y))) {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                Thread.sleep(500);
                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
//                Thread.sleep(500);
                checkWinner();
                hitDeckState = null;
                makeSecondShootOfPC = true;
//                Thread.sleep(500);
                shootOfPC();
            } else {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                makeSecondShootOfPC = true;
            }
        }
        if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1)) &&
                Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1)) &&
                makeSecondShootOfPC == false) {
            if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1))) {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                Thread.sleep(500);
                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                checkWinner();
                hitDeckState = null;
                makeSecondShootOfPC = true;
//                Thread.sleep(500);
                shootOfPC();
            } else {
//                Thread.sleep(500);
                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                makeSecondShootOfPC = true;
            }
        }
    }

    private void shootOfPCUnderThirdDeckShip() throws InterruptedException {
        if (shootNorthTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootNorthTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        } else if (shootOstTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootOstTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        } else if (shootWestTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootWestTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        } else if (shootSouthTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootSouthTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        } else if (shootNorthOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
//            Thread.sleep(500);
            checkWinner();
            hitDeckState = null;
            shootNorthOneDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        } else if (shootOstOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
//            Thread.sleep(500);
            checkWinner();
            hitDeckState = null;
            shootOstOneDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        } else if (shootSouthOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
//            Thread.sleep(500);
            checkWinner();
            hitDeckState = null;
            shootSouthOneDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        } else if (shootWestOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(thirdShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootWestOneDeck = false;
//            Thread.sleep(500);
            shootOfPC();

        } else {
            makeSecondShootOfPC = false;
            if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2))) {
                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2));
                            thirdShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y - 1);
                            shootSouthOneDeck = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                    makeSecondShootOfPC = true;
                }
            } else if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y))) {
                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y));
                            thirdShootOfPC = new Coord(firstShootOfPC.x - 1, firstShootOfPC.y);
                            shootWestOneDeck = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                    makeSecondShootOfPC = true;
                }
            } else if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y))) {
                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        } else {
                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y));
                            thirdShootOfPC = new Coord(firstShootOfPC.x + 1, firstShootOfPC.y);
                            shootOstOneDeck = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                    makeSecondShootOfPC = true;
                }
            } else if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2))) {
                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                            Thread.sleep(500);
                            setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2));
                            checkWinner();
                            hitDeckState = null;
                            makeSecondShootOfPC = true;
//                            Thread.sleep(500);
                            shootOfPC();
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2));
                            thirdShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y + 1);
                            shootNorthOneDeck = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                    makeSecondShootOfPC = true;
                }
            }
        }
    }

    private void shootOfPCUnderFourDeckShip() throws InterruptedException {
        if (shootNorthThreeDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootNorthThreeDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootNorthTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootNorthTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootNorthOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootNorthTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootSouthThreeDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootSouthThreeDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootSouthTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootSouthTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootSouthOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootSouthTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootWestThreeDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootWestThreeDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootWestTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootWestTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootWestOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootWestTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootOstThreeDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(secondShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootOstThreeDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootOstTwoDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(thirdShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootOstTwoDeck = false;
//            Thread.sleep(500);
            shootOfPC();
        }
        if (shootOstOneDeck == true) {
//            Thread.sleep(500);
            shipsMapGamer.setOpenedToBoxGamer(fourShootOfPC);
            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//            Thread.sleep(500);
            setOpenBoxAroundSunkShipOfGamer(fourShootOfPC);
            checkWinner();
            hitDeckState = null;
            shootOstOneDeck = false;
//            Thread.sleep(500);
            shootOfPC();

        } else {
            makeSecondShootOfPC = false;
            if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2))) {

                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                            if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 3)) &&
                                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 3))) {

                                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 3))) {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 3));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 3));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 3));
                                    fourShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y - 1);
                                    shootSouthOneDeck = true;
                                    makeSecondShootOfPC = true;
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2));
                            thirdShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y - 1);
                            fourShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y - 2);
                            shootSouthTwoDeck = true;
                            makeSecondShootOfPC = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                    secondShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y - 1);
                    thirdShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y - 2);
                    fourShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y - 3);
                    shootSouthThreeDeck = true;
                    makeSecondShootOfPC = true;
                }


            } else if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2))) {

                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                            if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 3)) &&
                                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 3))) {

                                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 3))) {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 3));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 3));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 3));
                                    fourShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y + 1);
                                    shootNorthOneDeck = true;
                                    makeSecondShootOfPC = true;
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 2));
                            thirdShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y + 1);
                            fourShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y + 2);
                            shootNorthTwoDeck = true;
                            makeSecondShootOfPC = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 1));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y + 2));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x, firstShootOfPC.y - 1));
                    secondShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y + 1);
                    thirdShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y + 2);
                    fourShootOfPC = new Coord(firstShootOfPC.x, firstShootOfPC.y + 3);
                    shootNorthThreeDeck = true;
                    makeSecondShootOfPC = true;
                }


            } else if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y))) {

                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                            if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x + 3, firstShootOfPC.y)) &&
                                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 3, firstShootOfPC.y))) {

                                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x + 3, firstShootOfPC.y))) {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 3, firstShootOfPC.y));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x + 3, firstShootOfPC.y));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 3, firstShootOfPC.y));
                                    fourShootOfPC = new Coord(firstShootOfPC.x - 1, firstShootOfPC.y);
                                    shootWestOneDeck = true;
                                    makeSecondShootOfPC = true;
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y));
                            thirdShootOfPC = new Coord(firstShootOfPC.x - 1, firstShootOfPC.y);
                            fourShootOfPC = new Coord(firstShootOfPC.x - 2, firstShootOfPC.y);
                            shootWestTwoDeck = true;
                            makeSecondShootOfPC = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                    secondShootOfPC = new Coord(firstShootOfPC.x - 1, firstShootOfPC.y);
                    thirdShootOfPC = new Coord(firstShootOfPC.x - 2, firstShootOfPC.y);
                    fourShootOfPC = new Coord(firstShootOfPC.x - 3, firstShootOfPC.y);
                    shootWestThreeDeck = true;
                    makeSecondShootOfPC = true;
                }


            } else if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y)) &&
                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y)) &&
                    makeSecondShootOfPC == false) {

                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y))) {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();

                    if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y)) &&
                            Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y))) {

                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y))) {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y));
                            ShipsMapGamer.increaseNumberOfSinkedShipGamer();
                            if (Ranges.inRangeFieldGamer(new Coord(firstShootOfPC.x - 3, firstShootOfPC.y)) &&
                                    Box.zero != shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 3, firstShootOfPC.y))) {

                                if (Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(firstShootOfPC.x - 3, firstShootOfPC.y))) {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 3, firstShootOfPC.y));
                                    ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                    Thread.sleep(500);
                                    setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x - 3, firstShootOfPC.y));
                                    checkWinner();
                                    hitDeckState = null;
                                    makeSecondShootOfPC = true;
//                                    Thread.sleep(500);
                                    shootOfPC();
                                } else {
//                                    Thread.sleep(500);
                                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 3, firstShootOfPC.y));
                                    fourShootOfPC = new Coord(firstShootOfPC.x + 1, firstShootOfPC.y);
                                    shootOstOneDeck = true;
                                    makeSecondShootOfPC = true;
                                }
                            } else {
//                                Thread.sleep(500);
                                shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                                ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                                Thread.sleep(500);
                                setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                                checkWinner();
                                hitDeckState = null;
                                makeSecondShootOfPC = true;
//                                Thread.sleep(500);
                                shootOfPC();
                            }
                        } else {
//                            Thread.sleep(500);
                            shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 2, firstShootOfPC.y));
                            thirdShootOfPC = new Coord(firstShootOfPC.x + 1, firstShootOfPC.y);
                            fourShootOfPC = new Coord(firstShootOfPC.x + 2, firstShootOfPC.y);
                            shootOstTwoDeck = true;
                            makeSecondShootOfPC = true;
                        }
                    } else {
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 1, firstShootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y));
                        ShipsMapGamer.increaseNumberOfSinkedShipGamer();
//                        Thread.sleep(500);
                        setOpenBoxAroundSunkShipOfGamer(new Coord(firstShootOfPC.x + 2, firstShootOfPC.y));
                        checkWinner();
                        hitDeckState = null;
                        makeSecondShootOfPC = true;
//                        Thread.sleep(500);
                        shootOfPC();
                    }
                } else {
//                    Thread.sleep(500);
                    shipsMapGamer.setOpenedToBoxGamer(new Coord(firstShootOfPC.x - 1, firstShootOfPC.y));
                    secondShootOfPC = new Coord(firstShootOfPC.x + 1, firstShootOfPC.y);                                       // создаём координаты выстрела с противоволожной стороны корабля
                    thirdShootOfPC = new Coord(firstShootOfPC.x + 2, firstShootOfPC.y);                                       // создаём координаты выстрела с противоволожной стороны корабля
                    fourShootOfPC = new Coord(firstShootOfPC.x + 3, firstShootOfPC.y);                                       // создаём координаты выстрела с противоволожной стороны корабля
                    shootOstThreeDeck = true;
                    makeSecondShootOfPC = true;
                }
            }
        }
    }

    private int countOfDecksShip(Coord shootOfPC) {
        int countOfDecksShip = 1;

        if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)))
            countOfDecksShip = countOfDecksShip + 2;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)))
            countOfDecksShip = countOfDecksShip + 2;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)))
            countOfDecksShip = countOfDecksShip + 2;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)))
            countOfDecksShip = countOfDecksShip + 2;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 3, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 3, shootOfPC.y)))
            countOfDecksShip = countOfDecksShip + 3;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 3, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 3, shootOfPC.y)))
            countOfDecksShip = countOfDecksShip + 3;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 3)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 3)))
            countOfDecksShip = countOfDecksShip + 3;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 3)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 3)))
            countOfDecksShip = countOfDecksShip + 3;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 2, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)))
            countOfDecksShip = countOfDecksShip + 3;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 1, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x - 2, shootOfPC.y)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x + 1, shootOfPC.y)))
            countOfDecksShip = countOfDecksShip + 3;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 2)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)))
            countOfDecksShip = countOfDecksShip + 3;

        else if (Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)) &&
                Ranges.inRangeFieldGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 1)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y + 2)) &&
                Box.num1 == shipsMapGamer.getBoxMapGamer(new Coord(shootOfPC.x, shootOfPC.y - 1)))
            countOfDecksShip = countOfDecksShip + 3;

        else
            for (Coord around : Ranges.getCoordsAround(shootOfPC))
                if (Box.num1 == shipsMapGamer.getBoxMapGamer(around))
                    countOfDecksShip++;

        return countOfDecksShip;
//        private int countOfDecksShip(Coord shootOfPC) {
//        countOfDecksShip = 1;
//
//            for(Coord around: Ranges.getCoordsAround(shootOfPC))                                // обводим вокруг первой палубы
//                if(Box.num1 == shipsMapGamer.getBoxMapGamer(around)) {
//                    countOfDecksShip++;
//                    for(Coord around1: Ranges.getCoordsAround(around))                          // обводим вокруг второй палубы
//                        if(Box.num1 == shipsMapGamer.getBoxMapGamer(around1) && (!around.equals(shootOfPC))) {
//                            countOfDecksShip++;
//                            for (Coord around2 : Ranges.getCoordsAround(around1))                   // обводим по третьей палубе
//                                if(Box.num1 == shipsMapGamer.getBoxMapGamer(around2) && (!around.equals(around1))) {
//                                    countOfDecksShip++;
//                                    for (Coord around3 : Ranges.getCoordsAround(around2))                   // обводим по третьей палубе
//                                        if (Box.num1 == shipsMapGamer.getBoxMapGamer(around3) && (!around.equals(around2))) {
//                                            countOfDecksShip++;
//
//                                    }
//                            }
//                    }
//                }
//
//                for(Coord around1: Ranges.getCoordsAround(around))
//                    if(Box.num1 == shipsMapGamer.getBoxMapGamer(around1) && (!around.equals(shootOfPC))
//                        countOfDecksShip++;

//        return countOfDecksShip;
    }

    private boolean checkHitGamer(Coord coord) {
        if (Box.num1 == shipsMapPC.getBoxMapPC(coord) | Box.flaged == shipsMapPC.getBoxMapPC(coord))
            return true;
        return false;
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }

    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if (shipsMapGamer.getCountOfSunkShipsGamer() == shipsMapGamer.getTotalShipsGamer())
                state = GameState.LOSED;
            if (shootMapPC.getCountOfSunkShipsPC() == shipsMapPC.getTotalShipsPC())
                state = GameState.WINNER;
        }
    }

    private boolean checkPressedCell(Coord coord) {
        if (Box.closed == shootMapPC.getBoxMapPC(coord))
            return true;
        return false;
    }

    private void openBoxShootOfGamer(Coord coord) {
        if(Box.zero == shipsMapPC.getBoxMapPC(coord))
            shootMapPC.setZeroBox(coord);
//        else
        if(Box.num1 == shipsMapPC.getBoxMapPC(coord)) {
            shipsMapPC.setFlagBox(coord);
            shootMapPC.setFlagedBox(coord);
            shootMapPC.increaseNumbersOfSinkedShipPC();
        }
    }

    private void checkIsShipPCSunk(Coord coord) {
        int countOfDeckPC = 0;
        ArrayList<Coord> list = new ArrayList<>();

        if (Box.flaged == shipsMapPC.getBoxMapPC(coord) | Box.num1 == shipsMapPC.getBoxMapPC(coord)) {
            list.add(coord);
            for (Coord around : Ranges.getCoordsAround(coord))                                                        // вокруг первой палубы
                if (Box.flaged == shipsMapPC.getBoxMapPC(around) | Box.num1 == shipsMapPC.getBoxMapPC(around)) {
                    list.add(around);
                    for (Coord around1 : Ranges.getCoordsAround(around))                                                   // вокруг второй палубы
                        if (Box.flaged == shipsMapPC.getBoxMapPC(around1) | Box.num1 == shipsMapPC.getBoxMapPC(around1)) {
                            if (!around1.equals(coord))
                                list.add(around1);
                            for (Coord around2 : Ranges.getCoordsAround(around1))                                              // вокруг третьей палубы
                                if (Box.flaged == shipsMapPC.getBoxMapPC(around2) | Box.num1 == shipsMapPC.getBoxMapPC(around2)) {
                                    if (!around2.equals(around))
                                        list.add(around2);
                                }
                        }
                }
            for(int i = 0; i < list.size(); i++) {
                if(shipsMapPC.getBoxMapPC(list.get(i)) == shootMapPC.getBoxMapPC(list.get(i)))
                    countOfDeckPC++;
            }
            if (countOfDeckPC == list.size())
                setOpenBoxAroundSunkShipOfPC(coord);
        }
    }

    private void setOpenBoxAroundSunkShipOfGamer(Coord coord) {

        for (Coord around : Ranges.getCoordsAround(coord)) {                                                                      // обводим вокруг первой палубы
            if (Box.closed == shipsMapGamer.getBoxMapGamer(around))
                shipsMapGamer.setOpenedToBoxGamer(around);
            if (Box.num1 == shipsMapGamer.getBoxMapGamer(around) || Box.flaged == shipsMapGamer.getBoxMapGamer(around)) {

                for (Coord around1 : Ranges.getCoordsAround(around)) {                                                            // обводим вокруг второй палубы
                    if (Box.closed == shipsMapGamer.getBoxMapGamer(around1))
                        shipsMapGamer.setOpenedToBoxGamer(around1);
                    if (Box.num1 == shipsMapGamer.getBoxMapGamer(around1) ||
                            Box.flaged == shipsMapGamer.getBoxMapGamer(around1)) {

                        for (Coord around2 : Ranges.getCoordsAround(around1)) {                                                     // обводим вокруг третьей палубы
                            if (Box.closed == shipsMapGamer.getBoxMapGamer(around2))
                                shipsMapGamer.setOpenedToBoxGamer(around2);
                            if (Box.num1 == shipsMapGamer.getBoxMapGamer(around2) ||
                                    Box.flaged == shipsMapGamer.getBoxMapGamer(around2)) {
                                shipsMapGamer.setOpenedToBoxGamer(around2);
                                for (Coord around3 : Ranges.getCoordsAround(around2))                                               // обводим вокруг четвёртой палубы
                                    if (Box.closed == shipsMapGamer.getBoxMapGamer(around3))
                                        shipsMapGamer.setOpenedToBoxGamer(around3);
                            }
                        }
                    }
                }
            }
        }
    }
    private void setOpenBoxAroundSunkShipOfPC(Coord coord) {

        for (Coord around : Ranges.getCoordsAround(coord)) {                                                                      // обводим вокруг первой палубы
            if (Box.closed == shootMapPC.getBoxMapPC(around))
                shootMapPC.setOpenedToBoxPC(around);
            if (Box.num1 == shootMapPC.getBoxMapPC(around) ||
                    Box.flaged == shootMapPC.getBoxMapPC(around)) {
                for (Coord around1 : Ranges.getCoordsAround(around)) {                                                            // обводим вокруг второй палубы
                    if (Box.closed == shootMapPC.getBoxMapPC(around1))
                        shootMapPC.setOpenedToBoxPC(around1);
                    if (Box.num1 == shootMapPC.getBoxMapPC(around1) ||
                            Box.flaged == shootMapPC.getBoxMapPC(around1)) {
                        for (Coord around2 : Ranges.getCoordsAround(around1)) {                                                     // обводим вокруг третьей палубы
                            if (Box.closed == shootMapPC.getBoxMapPC(around2))
                                shootMapPC.setOpenedToBoxPC(around2);
                            if (Box.num1 == shootMapPC.getBoxMapPC(around2) ||
                                    Box.flaged == shootMapPC.getBoxMapPC(around2)) {
                                shootMapPC.setOpenedToBoxPC(around2);
                                for (Coord around3 : Ranges.getCoordsAround(around2))                                               // обводим вокруг четвёртой палубы
                                    if (Box.closed == shootMapPC.getBoxMapPC(around3))
                                        shootMapPC.setOpenedToBoxPC(around3);
                            }
                        }
                    }
                }
            }
        }
    }
}
