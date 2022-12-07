package com.chenzhilei.tankgame04;

import javax.swing.*;

public class CzlTankGame04 extends JFrame {
    MyPanel myPanel = null;

    public static void main(String[] args) {
        CzlTankGame04 czlTankGame01 = new CzlTankGame04();
    }

    public CzlTankGame04() {
        myPanel =new MyPanel();
        new Thread(myPanel).start();
        this.add(myPanel);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(myPanel);
    }
}
