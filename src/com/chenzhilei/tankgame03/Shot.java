package com.chenzhilei.tankgame03;

/**
 * @auther chenzhilei
 */
public class Shot implements Runnable {
    int x;
    int y;
    int direction = 0;
    int speed = 2;
    boolean shotlive = true;

    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {//射击
        while (shotlive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direction) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            System.out.println("x=" + x + "，y=" + y);
            if (!(x > 0 && x < 1000 && y > 0 && y < 700)) {
                shotlive = false;
                break;
            }

        }
    }
}
