package Classes;

/**
 * Created by bodor on 2017.06.01..
 */
public class Timer implements Runnable {
    private double runTime;
    private String name;
    private boolean running;

    public Timer(String name) {
        this.name = name;
        this.runTime = 0;
        System.out.println(this);
    }

    @Override
    public void run() {
        running = true;
        System.out.println(statusMessage());
        while (running) {
            try {
                Thread.sleep(10);
                runTime += 0.01;
            } catch (InterruptedException e) {
                running = false;
                System.out.println(name +" paused");
            }
        }
    }

    private String statusMessage() {
        String message = null;
        if (runTime == 0) {
            message = name + "  started";
        } else{
            message = String.format("%s is resumed from %.3g seconds", name, runTime);
        }
        return message;
    }

    @Override
    public String toString() {
        return String.format("%s = run: %s,  time: %.3g", name, running, runTime);
    }

    public boolean isRunning() {
        return running;
    }


    public void setRunning(boolean running) {
        this.running = running;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
