package com.chenzhilei.tankgame03;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable {
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankssize = 3;

    public MyPanel() {
        hero = new Hero(100, 100);//初始化自己的坦克
        //初始化敌人坦克
        for (int i = 0; i < enemyTankssize; i++) {
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            enemyTank.setDirection(2);
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认是黑色
        //画出我们的坦克
        drawtank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        //画出坦克射击的子弹
        if (hero.shot != null && hero.shot.shotlive == true) {
            g.fillOval(hero.shot.x,hero.shot.y,5,5);
        }

        //画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            drawtank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 0);
        }
    }
    //画坦克方法

    /**
     * x 坦克的横坐标
     * y 坦克的纵坐标
     * g 画笔
     * direction 方向
     * type 坦克类型
     */
    public void drawtank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1://ours tanke
                g.setColor(Color.red);
                break;
        }
        //绘制坦克的方向
        //0表示向上，1表示向右，2表示向下，3表示像左
        switch (direction) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false);//坦克的左轮
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克的右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克的身体
                g.fillOval(x + 10, y + 20, 20, 20);//坦克的盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//炮筒
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);//坦克的左轮
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克的右轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克的身体
                g.fillOval(x + 20, y + 10, 20, 20);//坦克的盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//炮筒
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);//坦克的左轮
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克的右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克的身体
                g.fillOval(x + 10, y + 20, 20, 20);//坦克的盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//炮筒
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);//坦克的左轮
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克的右轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克的身体
                g.fillOval(x + 20, y + 10, 20, 20);//坦克的盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//炮筒
                break;
            default:
                System.out.println("暂时不处理");
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            hero.moveup();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(1);
            hero.moveright();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            hero.movedown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            hero.moveleft();
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            hero.shotEnemyTank();
            System.out.println("用户按下了j,开始射击");
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100毫秒重绘制区域，实现子弹移动
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
