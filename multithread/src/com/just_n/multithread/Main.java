package com.just_n.multithread;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        Duration d = Duration.ofHours(5);
        System.out.println(d.plusHours(3));
        System.out.println(d.abs());
    }


}
