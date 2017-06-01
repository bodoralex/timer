package Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by bodor on 2017.06.01..
 */
public class TimerHandler implements Runnable {
    private Map<Timer, Thread> timers = new HashMap<>();

    private Timer getTimerByName(String name) {
        for (Map.Entry<Timer, Thread> mapEntry : timers.entrySet()) {
            if (mapEntry.getKey().getName().equalsIgnoreCase(name)) return mapEntry.getKey();
        }
        System.err.println("There is no timer with this name");
        return null;
    }

    private void createTimer(String name) {
        Timer timer = new Timer(name);
        timers.put(timer, null);
    }

    private void check() {
        for (Map.Entry<Timer, Thread> timerThreadEntry : timers.entrySet()) {
            System.out.println(timerThreadEntry.getKey() + (timerThreadEntry.getKey().isRunning() ? " id: " + timerThreadEntry.getValue().getId() : ""));
        }
    }

    private void check(String name) {
        Timer timer = getTimerByName(name);
        if (timer == null) return;
        System.out.println(timer + (timer.isRunning() ? " id: " + timers.get(timer).getId() : ""));
    }

    private void startTimer(String name) {
        Timer timer = getTimerByName(name);
        if (timer == null) return;
        if(timer.isRunning()){
            System.out.println("Timer is aalready running");
            return;
        }
        Thread timerThread = new Thread(timer);
        timers.put(timer, timerThread);
        timerThread.start();
    }

    private void pauseTimer(String name) {
        Timer timer = getTimerByName(name);
        if (timer == null) return;
        if(!timer.isRunning()){
            System.out.println("Timer is already stopped");
            return;
        }
        timers.get(timer).interrupt();
        timers.put(timer, null);
    }

    private void deleteTimer(String name) {
        Timer timer = getTimerByName(name);
        if (timer == null) return;
        timers.remove(timer);
        System.out.println("Timer deleted");
    }
    private void stopAllTheTimers(){
        for (Thread thread : timers.values()) {
            if(thread == null) continue;
            thread.interrupt();
        }
    }
    private void exit(){
        stopAllTheTimers();
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        System.out.println("Hello in the multithreaded timer!\n");
        System.out.println("Create a timer: create 'timer_name'");
        System.out.println("Check the timers: check");
        System.out.println("Check a specified timer: check 'timer_name'");
        System.out.println("Start/resume a timer: start 'timer_name'");
        System.out.println("Pause a timer: pause 'timer_name'");
        System.out.println("Delete a timer: del 'timer_name'");
        System.out.println("\nExit application: exit");

        Scanner scanner = new Scanner(System.in);
        while (true) {

            if(Thread.currentThread().isInterrupted()) return; //bye

            if (scanner.hasNext()) {
                String input = scanner.nextLine();
                functionChooser(input);
            }
        }
    }

    private void functionChooser(String input) {
        //System.out.println(input);
        String[] splitted = input.toLowerCase().split(" ");
        if (splitted.length == 1) {
            String oneWordStatement = splitted[0];
            switch (oneWordStatement) {
                case "check":
                    check();
                    break;
                case "exit":
                    exit();
                default:
                    System.err.println("Invalid statement");
            }
        } else if (splitted.length == 2) {
            String statement = splitted[0];
            String name = splitted[1];
            switch (statement) {
                case "check":
                    check(name);
                    break;
                case "create":
                    createTimer(name);
                    break;
                case "start":
                    startTimer(name);
                    break;
                case "pause":
                    pauseTimer(name);
                    break;
                case "del":
                    deleteTimer(name);
                    break;
                default:
                    System.err.println("Invalid statement");
            }
        } else {
            System.err.println("Invalid statement");
        }
    }
}
