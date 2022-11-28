package main;


import com.sun.source.tree.ClassTree;
import gameObjects.AlphaPoint;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120000;
    private Random random;


    private int voroniSize = 1;
    private int detail = 15;
    private int cubeSize = 25;
    private int maxDetail = detail * 4;
    private int size;
    private boolean once = true;
    private ArrayList<Integer>[][] terrain;
    private ArrayList<Integer> acceptable = new ArrayList<>();
    private ArrayList<AlphaPoint> voroni = new ArrayList<>();

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(true);
        gamePanel.requestFocus();
        startGameLoop();
        random = new Random();



        size = 680 / cubeSize;
        terrain = new ArrayList[size][size];
        for (int x = 0; x < terrain.length; x++) {
            for (int y = 0; y < terrain[x].length; y++) {
                terrain[x][y] = new ArrayList<Integer>();
                for (int i = 0; i < maxDetail; i++) {
                    terrain[x][y].add(i);
                }

                if (x == 0) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
                }
                if (y == 0) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
                }
                if (x == size - 1) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
                }
                if (y == size - 1) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
                }

                if (x == 1) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
                }
                if (y == 1) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
                }
                if (x == size - 2) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
                }
                if (y == size - 2) {
                    terrain[x][y] = new ArrayList<Integer>();
                    terrain[x][y].add(0);
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
                updateGame();
            }

            if (System.currentTimeMillis() - lastCheck >= 1000000) {
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
                for (int x = 2; x < terrain.length - 2; x++) {
                    for (int y = 2; y < terrain[x].length - 2; y++) {
                        if (!(terrain[x - 1][y].size() == 1 && terrain[x][y - 1].size() == 1 && terrain[x][y + 1].size() == 1 && terrain[x + 1][y].size() == 1)) {
                            for (int z = 0; z < terrain[x][y].size(); z++) {
                                for (int c = 0; c < maxDetail; c++) {
                                    if (terrain[x][y].get(z).equals(c)) {
                                        if (c != 0) {
                                            accept(c - 1);
                                        }
                                        accept(c);
                                        if (c != maxDetail - 1) {
                                            accept(c + 1);
                                        }
                                    }
                                }
                            }
                            unacceptable = new ArrayList<>();
                            for (int c = 0; c < maxDetail; c++) {
                                unacceptable.add(c);
                            }

                            StringBuilder builder = new StringBuilder();

                            for (int i = 0; i < acceptable.size(); i++) {
                                builder.append(acceptable.get(i) + " ").append(System.lineSeparator());
                            }

                            for (int i = 0; i < acceptable.size(); i++) {
                                for (int j = 0; j < unacceptable.size(); j++) {
                                    if (Objects.equals(acceptable.get(i), unacceptable.get(j))) {
                                        unacceptable.remove(j);
                                    }
                                }
                            }

                            adapt(x - 1, y, unacceptable);

                            adapt(x, y - 1, unacceptable);

                            adapt(x + 1, y, unacceptable);

                            adapt(x, y + 1, unacceptable);

                            adapt(x - 2, y, unacceptable);

                            adapt(x, y - 2, unacceptable);

                            adapt(x + 2, y, unacceptable);

                            adapt(x, y + 2, unacceptable);



                            adapt(x - 1, y - 1, unacceptable);

                            adapt(x + 1, y - 1, unacceptable);

                            adapt(x - 1, y + 1, unacceptable);

                            adapt(x + 1, y + 1, unacceptable);

                            adapt(x - 2, y - 2, unacceptable);

                            adapt(x + 2, y - 2, unacceptable);

                            adapt(x + 2, y + 2, unacceptable);

                            adapt(x - 2, y + 2, unacceptable);
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
                } while (terrain[randX][randY].size() <= 0);
                while (terrain[randX][randY].size() > 1) {
                    terrain[randX][randY].remove(random.nextInt(terrain[randX][randY].size()));
                }
            } else {
                do {
                    if (pointerX > size - 2) {
                        pointerX = 2;
                        pointerY++;
                    } else {
                        pointerX++;
                    }
                } while (terrain[pointerX][pointerY].size() <= 1);
                while (terrain[pointerX][pointerY].size() > 1) {
                    terrain[pointerX][pointerY].remove(random.nextInt(terrain[pointerX][pointerY].size()));
                }
            }
        } else {
            if (once) {
                once = false;

                for (int x = 0; x < terrain.length; x++) {
                    for (int y = 0; y < terrain[x].length; y++) {
                        voroni.add(new AlphaPoint(x, y, (int) terrain[x][y].get(0)));
                    }
                }
            }
        }

        if (once) {
            for (int x = 0; x < terrain.length; x++) {
                for (int y = 0; y < terrain[x].length; y++) {
                    if (terrain[x][y].size() == 1) {
                        if (terrain[x][y].get(0) >= 0 && terrain[x][y].get(0) <= detail - 1) {
                            gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(73, 120, 236, 255));
                        }

                        if (terrain[x][y].get(0) >= detail && terrain[x][y].get(0) <= detail * 2 - 1) {
                            gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(248, 164, 105, 255));
                        }

                        if (terrain[x][y].get(0) >= detail * 2 && terrain[x][y].get(0) <= detail * 3 - 1) {
                            gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(15, 77, 3, 255));
                        }

                        if (terrain[x][y].get(0) >= detail * 3 && terrain[x][y].get(0) <= detail * 4 - 1) {
                            gamePanel.addRects(x , y, cubeSize, cubeSize, new Color(40, 40, 47, 255));
                        }
                    }
                }
            }
        } else {
            for (int x = 0; x < gamePanel.getHeight(); x++) {
                for (int y = 0; y < gamePanel.getHeight(); y++) {
                    int chosenIndex = 0;
                    int dist = 1000;
                    for (int i = 0; i < voroni.size(); i++) {
                        int currentDist = (int) Math.sqrt(Math.pow(x - voroni.get(i).x, 2) + Math.pow(y - voroni.get(i).y, 2));

                        if (dist > currentDist) {
                            dist = currentDist;
                            chosenIndex = i;
                        }
                    }

                    if (voroni.get(chosenIndex).color >= 0 && voroni.get(chosenIndex).color <= detail - 1) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(73, 120, 236, 255));
                    }

                    if (voroni.get(chosenIndex).color >= detail && voroni.get(chosenIndex).color <= detail * 2 - 1) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(248, 164, 105, 255));
                    }

                    if (voroni.get(chosenIndex).color >= detail * 2 && voroni.get(chosenIndex).color <= detail * 3- 1) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(15, 77, 3, 255));
                    }

                    if (voroni.get(chosenIndex).color >= detail * 3 && voroni.get(chosenIndex).color <= detail * 4 - 1) {
                        gamePanel.addRects(x, y, cubeSize, cubeSize, new Color(40, 40, 47, 255));
                    }
                }
            }
        }
    }

    public void accept(int num) {
        boolean acceptnum = true;
        for (int i = 0; i < acceptable.size(); i++) {
            if (acceptable.get(i) == num) {
                acceptnum = false;
            }
        }
        if (acceptnum) {
            acceptable.add(num);
        }
    }

    public void adapt(int x, int y, ArrayList<Integer> unacceptable) {
        if (terrain[x][y].size() > 1) {
            for (int i = 0; i < terrain[x][y].size(); i++) {
                for (int j = 0; j < unacceptable.size(); j++) {
                    if (Objects.equals(unacceptable.get(j), terrain[x][y].get(i))) {
                        terrain[x][y].remove(i);
                    }
                }
            }
        }
    }
}
