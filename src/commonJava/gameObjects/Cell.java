package gameObjects;

import main.Game;

import java.util.ArrayList;
import java.util.Objects;

import static main.Game.*;

public class Cell {

    private final int x;
    private final int y;
    private final int detail;
    private int maxDetail;
    private ArrayList<Integer> values = new ArrayList<>();
    private ArrayList<Integer> acceptable = new ArrayList<>();
    private ArrayList<Integer> unacceptable;

    public Cell(int x, int y, int detail) {
        this.x = x;
        this.y = y;
        this.detail = detail;
        this.maxDetail = detail * 4;

        for (int i = 0; i < maxDetail; i++) {
            values.add(i);
        }
    }

    public void adaptSides() {
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < maxDetail; j++) {
                if (this.values.get(i) == j) {
                    if (j != 0) {
                        accept(acceptable, j - 1);
                    }
                    accept(acceptable, j);
                    if (j != maxDetail - 1) {
                        accept(acceptable,  + 1);
                    }
                }
            }
        }

        unacceptable = new ArrayList<>();
        for (int c = 0; c < maxDetail; c++) {
            unacceptable.add(c);
        }

        for (int i = 0; i < acceptable.size(); i++) {
            for (int j = 0; j < unacceptable.size(); j++) {
                if (acceptable.get(i) == unacceptable.get(j)) {
                    unacceptable.remove(j);
                }
            }
        }

        if(terrain.get(this.x - 1).get(this.y).values.size() > 1){
            adapt(this.x - 1, this.y, unacceptable);
            terrain.get(this.x - 1).get(this.y).adaptSides();
        }

        if(terrain.get(this.x).get(this.y - 1).values.size() > 1){
            adapt(this.x, this.y - 1, unacceptable);
            terrain.get(this.x).get(this.y - 1).adaptSides();
        }

        if(terrain.get(this.x + 1).get(this.y).values.size() > 1){
            adapt(this.x + 1, this.y, unacceptable);
            terrain.get(this.x + 1).get(this.y).adaptSides();
        }

        if(terrain.get(this.x).get(this.y + 1).values.size() > 1){
            adapt(this.x, this.y + 1, unacceptable);
            terrain.get(this.x).get(this.y + 1).adaptSides();
        }


    }


    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }
}
