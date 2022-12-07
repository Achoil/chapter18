package com.chenzhilei.tankgame04;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;
    //定义vector存放敌方坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankssize = 3;
    //定义vector存放炸弹,当子弹击中坦克，加入一个Boom对象
    Vector<Boom> booms = new Vector<>();
    //定义三张图片，用于显示爆炸
    Image img01 = null;
    Image img02 = null;
    Image img03 = null;

    public MyPanel() {
        //初始化自己的坦克
        hero = new Hero(4+00, 100);
        //初始化敌人坦克
        for (int i = 0; i < enemyTankssize; i++) {
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            //设置方向
            enemyTank.setDirection(2);
            //启动地方tank线程
            new Thread(enemyTank).start();
            //给敌方坦克加入一颗子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
            enemyTank.shots.add(shot);
            new Thread(shot).start();
            enemyTanks.add(enemyTank);
        }
        //初始化图片对象
        img01 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        img02 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        img03 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认是黑色
        //画出我们的坦克
        if (hero.islive && hero != null) {
            drawtank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        }
        //画出我方坦克射击的子弹
//        if (hero.shot != null && hero.shot.shotlive == true) {
//            g.fillOval(hero.shot.x, hero.shot.y, 5, 5);
//        }
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.shotlive == true) {
                g.fillOval(shot.x, shot.y, 5, 5);
            } else {
                hero.shots.remove(shot);
            }
        }
        //如果booms集合中有炸弹，就画出爆炸效果
        for (int i = 0; i < booms.size(); i++) {
            //取出炸弹
            Boom boom = booms.get(i);
            //根据当前坦克的life值去画出相应的图片
            if (boom.life > 12) {
                g.drawImage(img01, boom.x, boom.y, 60, 60, this);
            } else if (boom.life > 6) {
                g.drawImage(img02, boom.x, boom.y, 60, 60, this);
            } else {
                g.drawImage(img03, boom.x, boom.y, 60, 60, this);
            }
            //让炸弹的生命值减少
            boom.lifedowm();
            if (boom.life == 0) {
                booms.remove(boom);
            }
        }


        //画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.islive) {
                drawtank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 0);
                //画出敌方坦克射出的子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.shotlive) {
                        g.fillOval(shot.x, shot.y, 3, 3);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
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
                g.setColor(Color.yellow);
                break;
        }
        //绘制坦克的方向
        //0表示向上，1表示向右，2表示向下，3表示向左
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

    //我方坦克被击中
    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (hero.islive && shot.shotlive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    //坦克发射多颗子弹，将集合中的所有子弹都 取出与敌方tank比较
    public void hitenemyTank() {
        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);

            if (shot != null && shot.shotlive) {//判断我方子弹是否存活
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
            this.repaint();
        }
    }

    //判断我方子弹什么时候击中敌方tank
    public void hitTank(Shot s, Tank enemyTank) {
        switch (enemyTank.getDirection()) {
            case 0://TANK UP
            case 2://TANK dowm
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40 &&
                        s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.shotlive = false;
                    enemyTank.islive = false;
                    //子弹击中坦克后，将坦克从集合中移除
                    enemyTanks.remove(enemyTank);
                    //创建一个Boom对象，加入到booms集合中
                    Boom boom = new Boom(enemyTank.getX(), enemyTank.getY());
                    booms.add(boom);
                }
            case 1://left
            case 3://right
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60 &&
                        s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.shotlive = false;
                    enemyTank.islive = false;
                    enemyTanks.remove(enemyTank);

                    Boom boom = new Boom(enemyTank.getX(), enemyTank.getY());
                    booms.add(boom);
                }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //控制我方坦克移动
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            if (hero.getY() > 0) {
                hero.moveup();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveright();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            if (hero.getY() + 60 < 750) {
                hero.movedown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            if (hero.getX() > 0) {
                hero.moveleft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
//            if (hero.shot == null ||!hero.shot.shotlive) {
            hero.shotEnemyTank();
//            }
            System.out.println("用户按下了j,开始射击");
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100毫秒重绘制区域，实现子弹移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中tank
//            if (hero.shot != null && hero.shot.shotlive) {//判断我方子弹是否存活
//                for (int i = 0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    hitTank(hero.shot, enemyTank);
//                }
//            }
            hitenemyTank();
            hitHero();
            this.repaint();
        }
    }
}
