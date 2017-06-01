package main;

import Classes.Timer;
import Classes.TimerHandler;

/**
 * Created by bodor on 2017.06.01..
 */
public class Main {
    public static void main(String[] args) {
        new Thread(new TimerHandler()).start();
    }
}
