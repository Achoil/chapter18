package com.chenzhilei.tankgame04;

/**
 * @auther chenzhilei
 * @Date 2022/11/18
 */
public class Boom {
    int x,y;
    int life = 18;
    boolean islive  = true;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifedowm(){
        if(life >0){
            life--;
        }else{
            islive =false;
        }
    }
}
