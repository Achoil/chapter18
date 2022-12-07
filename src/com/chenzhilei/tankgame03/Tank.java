package com.chenzhilei.tankgame03;

public class Tank {
    private int x;
    private int y;
    private int direction = 0;
    private int speed = 5;

    public void moveup() {
        y -= speed;
    }

    public void moveright() {
        x += speed;
    }

    public void movedown() {
        y += speed;
    }

    public void moveleft() {
        x -= speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;

    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
