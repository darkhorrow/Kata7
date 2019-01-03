package watch2;

import static java.lang.Math.PI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Watch {
    private double hours = Math.PI/2;
    private double minutes = Math.PI/2;
    private double seconds = Math.PI/2;
    private final Timer timer;
    private final List<Observer> observers = new ArrayList<>();
    private double SecondStep = 2 * PI / 60;
    private double MinuteStep = SecondStep / 60;
    private double HourStep = MinuteStep / 12;

    public Watch() {
        Calendar calendar = new GregorianCalendar();
        double sysHours = calendar.get(Calendar.HOUR_OF_DAY);
        double sysMinutes = calendar.get(Calendar.MINUTE);
        double sysSeconds = calendar.get(Calendar.SECOND);
        hours=normalize(hours-((sysHours%12)*60*60+sysMinutes*60+sysSeconds)*HourStep);
        minutes=normalize(minutes-(sysMinutes*60+sysSeconds)*MinuteStep);
        seconds=normalize(seconds-sysSeconds*SecondStep);
        timer = new Timer();
        timer.schedule(timerTask(), 0, 1000);
    }
    
    public double getHours() {
        return hours;
    }

    public double getMinutes() {
        return minutes;
    }

    public double getSeconds() {
        return seconds;
    }
    
    

    private TimerTask timerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                step();
                updateObservers();
            }

        };
    }
    
    private void step() {
        Calendar calendar = new GregorianCalendar();
        double sysHours = calendar.get(Calendar.HOUR_OF_DAY);
        double sysMinutes = calendar.get(Calendar.MINUTE);
        double sysSeconds = calendar.get(Calendar.SECOND);
        hours=normalize(Math.PI/2-((sysHours%12)*60*60+sysMinutes*60+sysSeconds)*HourStep);
        minutes=normalize(Math.PI/2-(sysMinutes*60+sysSeconds)*MinuteStep);
        seconds=normalize(Math.PI/2-sysSeconds*SecondStep);
    }
    
    public void add(Observer observer) {
        observers.add(observer);
    }
    
    private void updateObservers() {
        for (Observer observer : observers) 
            observer.update();
    }

    private double normalize(double v) {
        return v % (2 * PI);
    }

    public interface Observer {

        public void update();

    }

    
}
