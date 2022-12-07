package com.chenzhilei.tankgame03;

import javax.swing.*;

public class CzlTankGame02 extends JFrame {
    MyPanel myPanel = null;

    public static void main(String[] args) {
        CzlTankGame02 czlTankGame01 = new CzlTankGame02();
    }

    public CzlTankGame02() {
        myPanel =new MyPanel();
        new Thread(myPanel).start();
        this.add(myPanel);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(myPanel);
    }
}
