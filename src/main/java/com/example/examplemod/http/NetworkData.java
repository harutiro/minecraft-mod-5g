package com.example.examplemod.http;

public class NetworkData {
    long time;
    int ping;
    int down;
    int up;

    public NetworkData(long time, int ping, int down, int up) {
        this.time = time;
        this.ping = ping;
        this.down = down;
        this.up = up;
    }
}
