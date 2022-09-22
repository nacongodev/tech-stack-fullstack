package com.ikhokha.techcheck;

import java.util.ArrayList;

public class Metrics {

    public long Movers;
    public long Shakers;
    public long Shorter;
    public long Questions;
    public long Spam;

    static void Shorter(ArrayList<Metrics> reports, Metrics r, int i) {

        r.Shorter += reports.get(i).Shorter;
    }

    static void Shakers(ArrayList<Metrics> reports, Metrics r, int i) {
        r.Shakers += reports.get(i).Shakers;
    }

    static void Movers(ArrayList<Metrics> reports, Metrics r, int i) {
        r.Movers += reports.get(i).Movers;
    }

    static void Question(ArrayList<Metrics> reports, Metrics r, int i) {
        r.Questions += reports.get(i).Questions;
    }

    static void Spam(ArrayList<Metrics> reports, Metrics r, int i) {
        r.Spam += reports.get(i).Spam;
    }


}
