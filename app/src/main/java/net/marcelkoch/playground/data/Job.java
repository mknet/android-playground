package net.marcelkoch.playground.data;

/**
 * Created by marcel on 28.04.15.
 */
public class Job {

    public int id;
    public String address;
    public long latitude;
    public long longitude;
    public String message;

    @Override
    public String toString() {
        return message;
    }
}
