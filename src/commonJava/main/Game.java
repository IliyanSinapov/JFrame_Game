package main;


import gameObjects.AlphaPoint;
import gameObjects.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private Random random;

    private int detail = 3;
    private int cubeSize = 10;
    private int maxDetail = detail * 4;
    private int size;
    private boolean once = true;
    public static ArrayList<ArrayList<Cell>> terrain;

    private ArrayList<AlphaPoint> voroni = new ArrayList<>();
    private final int voroniSize = 1;


    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(true);
        gamePanel.requestFocus();
        startGameLoop();
        random = new Random();


        size = 680 / cubeSize;
        terrain = new ArrayList<>(size);
        for (int x = 0; x < size; x++) {
            terrain.add(new ArrayList<>());
            for (int y = 0; y < size; y++) {
                terrain.get(x).add(new Cell(x, y, detail));
            }
        }

        for (int x = 0; x < terrain.size(); x++) {
            for (int y = 0; y < terrain.get(x).size(); y++) {
                if (x == 0) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }
                if (y == 0) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }
                if (x == size - 1) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }
                if (y == size - 1) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }

                if (x == 1) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }
                if (y == 1) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }
                if (x == size - 2) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }
                if (y == size - 2) {
                    terrain.get(x).add(new Cell(x, y, detail));
                    terrain.get(x).get(y).setValues(new ArrayList<Integer>());
                    terrain.get(x).get(y).getValues().add(0);
                }
            }
        }
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {

            now = System.nanoTime();
            if (now - lastFrame > timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
//                updateGame();
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public void updateGame() {

        int passedTime = 0;
        int pointerX = 1;
        int pointerY = 2;
        ArrayList<Integer> unacceptable;


        int randX = 0;
        int randY = 0;

        if (pointerY < size - 3) {
            for (int speed = 0; speed < 1; speed++) {
                for (int x = 1; x < terrain.size() - 1; x++) {
                    for (int y = 1; y < terrain.get(x).size() - 1; y++) {
                        if (!(terrain.get(x - 1).get(y).getValues().size() == 1 && terrain.get(x).get(y - 1).getValues().size() == 1 && terrain.get(x).get(y + 1).getValues().size() == 1 && terrain.get(x + 1).get(y).getValues().size() == 1)) {
//                            adapt(x - 1, y, unacceptable);
//                            adapt(x, y - 1, unacceptable);
//                            adapt(x + 1, y, unacceptable);
//                            adapt(x, y + 1, unacceptable);
                        }
                    }
                }
            }
            passedTime++;

            if (passedTime < 1000) {
                int prevX = randY;
                int prevY = randY;

                do {
                    do {
                        randX = random.nextInt(size);
                    } while (randX > prevX - 12 && randX < prevX + 12);

                    do {
                        randY = random.nextInt(size);
                    } while (randY > prevY - 12 && randY < prevY + 12);
                } while (terrain.get(randX).get(randY).getValues().size() <= 0);
                while (terrain.get(randX).get(randY).getValues().size() > 1) {
                    terrain.get(randX).get(randY).getValues().remove(random.nextInt(terrain.get(randX).get(randY).getValues().size()));
                }
            } else {
                do {
                    if (pointerX > size - 2) {
                        pointerX = 2;
                        pointerY++;
                    } else {
                        pointerX++;
                    }
                } while (terrain.get(pointerX).get(pointerY).getValues().size() <= 1);
                while (terrain.get(pointerX).get(pointerY).getValues().size() > 1) {
                    terrain.get(pointerX).get(pointerY).getValues().remove(random.nextInt(terrain.get(pointerX).get(pointerY).getValues().size()));
                }
            }
        } else {
            if (once) {
                once = false;

                for (int x = 0; x < terrain.size(); x++) {
                    for (int y = 0; y < terrain.get(x).size(); y++) {
                    }
                }
            }
        }

        if (once) {
            for (int x = 0; x < terrain.size(); x++) {
                for (int y = 0; y < terrain.get(x).size(); y++) {
                    if (terrain.get(x).get(y).getValues().size() == 1) {
                        if (terrain.get(x).get(y).getValues().get(0) >= 0 && terrain.get(x).get(y).getValues().get(0) <= detail - 1) {
                            gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(terrain.get(x).get(y).getValues().get(0) * 50, terrain.get(x).get(y).getValues().get(0) * 50, 236, 255));
                        }

                        if (terrain.get(x).get(y).getValues().get(0) >= detail && terrain.get(x).get(y).getValues().get(0) <= detail * 2 - 1) {
                            gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(248, 164, 105, 255));
                        }

                        if (terrain.get(x).get(y).getValues().get(0) >= detail * 2 && terrain.get(x).get(y).getValues().get(0) <= detail * 3 - 1) {
                            gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(15, 77, 3, 255));
                        }

                        if (terrain.get(x).get(y).getValues().get(0) >= detail * 3 && terrain.get(x).get(y).getValues().get(0) <= detail * 4 - 1) {
                            gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(108, 109, 110, 255));
                        }
                    }
                }
            }
        } else {
            for (int x = 0; x < size; x += voroniSize) {
                for (int y = 0; y < size; y += voroniSize) {
                    int dist = 1000;
                    int choseni = 0;
                    for (int i = 0; i < voroni.size(); i++) {
                        int curDist = (int) Math.sqrt(Math.pow(x - voroni.get(i).x, 2) + Math.pow(y - voroni.get(i).y, 2));
                        if (dist > curDist) {
                            dist = curDist;
                            choseni = i;
                        }
                    }

                    if (voroni.get(choseni).color >= 0 && voroni.get(choseni).color <= detail - 1) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(terrain.get(x).get(y).getValues().get(0) * 50, terrain.get(x).get(y).getValues().get(0) * 50, 236, 255));
                    }
                    if (
                            voroni.get(choseni).color >= detail &&
                                    voroni.get(choseni).color <= detail * 2 - 1
                    ) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(248, 164, 105, 255));
                    }
                    if (
                            voroni.get(choseni).color >= detail * 2 &&
                                    voroni.get(choseni).color <= detail * 3 - 1
                    ) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(15, 77, 3, 255));
                    }
                    if (
                            voroni.get(choseni).color >= detail * 3 &&
                                    voroni.get(choseni).color <= detail * 4 - 1
                    ) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(108, 109, 110, 255));
                    }
                }
            }
        }
    }

    public static ArrayList<Integer> accept(ArrayList<Integer> acceptable, int num) {
        boolean acceptnum = true;
        for (int i = 0; i < acceptable.size(); i++) {
            if (acceptable.get(i) == num) {
                acceptnum = false;
            }
        }
        if (acceptnum) {
            acceptable.add(num);
        }
        return acceptable;
    }

    public static void adapt(int x, int y, ArrayList<Integer> unacceptable) {
        if (terrain.get(x).get(y).getValues().size() > 1) {
            for (int i = 0; i < terrain.get(x).get(y).getValues().size(); i++) {
                for (int j = 0; j < unacceptable.size(); j++) {
                    if (Objects.equals(unacceptable.get(j), terrain.get(x).get(y).getValues().get(i))) {
                        terrain.get(x).get(y).getValues().remove(i);
                    }
                }
            }
        }
    }
}
