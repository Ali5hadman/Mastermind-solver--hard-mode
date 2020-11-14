package com;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class Main {

    static int randomnmber() {
        Random rand = new Random(); //instance of random class
        int upperbound = 8;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);
        double double_random = rand.nextDouble();
        float float_random = rand.nextFloat();

//        System.out.println("Random integer value from 0 to" + (upperbound-1) + " : "+ int_random);
//        System.out.println("Random float value between 0.0 and 1.0 : "+float_random);
//        System.out.println("Random double value between 0.0 and 1.0 : "+double_random);

        return int_random + 1;
    }


    static ArrayList<Integer> getcode(ArrayList<Integer> code) {

        for (int i = 0; i < 5; i++) {
            code.add(randomnmber());
        }

        return code;
    }


    static ArrayList<ArrayList<Integer>> getsolutions(ArrayList<ArrayList<Integer>> solutions) {


        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                for (int z = 1; z < 9; z++) {
                    for (int q = 1; q < 9; q++) {
                        for (int t = 1; t < 9; t++) {


                            ArrayList<Integer> inside = new ArrayList<>();

                            inside.add(x);
                            inside.add(y);
                            inside.add(z);
                            inside.add(q);
                            inside.add(t);

                            solutions.add(inside);

                        }
                    }
                }
            }

        }

        return solutions;

    }

    static ArrayList<String> checkcode(ArrayList<Integer> guess, ArrayList<Integer> code) {

        ArrayList<String> result = new ArrayList<String>();
        for (int g = 0; g < guess.size(); g++) {
            if (guess.get(g) == code.get(g)) {
                result.add("B");
                guess.set(g, guess.get(g) * (-1));
                code.set(g, code.get(g) * (-1));
            }
        }

        for (int c = 0; c < guess.size(); c++) {
            if (guess.get(c) > 0) {
                for (int j = 0; j < code.size(); j++) {
                    if (guess.get(c) == code.get(j)) {
                        result.add("W");
                        code.set(j, code.get(j) * (-1));
                        break;
                    }

                }

            }
        }
        for (int c = 0; c < guess.size(); c++) {
            if (guess.get(c) < 0) {
                guess.set(c, guess.get(c) * (-1));
            }
            if (code.get(c) < 0) {
                code.set(c, code.get(c) * (-1));
            }
        }

        return result;
    }

    static ArrayList<Integer> getnextguess(ArrayList<ArrayList<Integer>> allsol, ArrayList<ArrayList<Integer>> possol) {

        ArrayList<Integer> nextguess = new ArrayList<>();
        ArrayList<Integer> score = new ArrayList<>();
        ArrayList<Integer> maxes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> maxsol = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> possnextgues = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<String>> allresult = new ArrayList<ArrayList<String>>();



        for (int i = 0; i < allsol.size(); i++) {
            for (int j = 0; j < possol.size(); j++) {
                ArrayList<String> result = new ArrayList<String>();
                result = checkcode(allsol.get(i), possol.get(j));


                if (!allresult.contains(result)) {
                    allresult.add(result);
                    score.add(1);
                } else {
                    score.set(allresult.indexOf(result), (score.get(allresult.indexOf(result)) + 1));
                }
            }

            int max = 0;
            for (int s : score) {
                if (max < s) {
                    max = s;
                }
            }

            maxsol.add(allsol.get(i));
            maxes.add(max);

            score.clear();
            allresult.clear();
            //System.out.println(i + " out of 32768");

        }

    


        int min = 1000000;
        for (int m = 0; m < maxes.size(); m++) {
            if (maxes.get(m) < min) {
                min = maxes.get(m);
            }
        }

        for (int s = 0; s < maxes.size(); s++) {
            if (maxes.get(s) == min) {
                possnextgues.add(maxsol.get(s));
            }
        }

        nextguess = possnextgues.get(0);

        for (int p = 0; p < possnextgues.size(); p++) {

            if (possol.contains(possnextgues.get(p))) {
                nextguess = possnextgues.get(p);
                break;
            }

        }


        return nextguess;
    }


    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        ArrayList<Integer> code = new ArrayList<Integer>();
        code = getcode(code);

//        code.add(7);
//        code.add(1);
//        code.add(2);
//        code.add(7);

        System.out.println("Secret Code = " + code);

        ArrayList<ArrayList<Integer>> possiblesolutions = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> allsolutions = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> wantedresult = new ArrayList<String>();
        ArrayList<String> tempresult = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> toberemoved = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> guess = new ArrayList<Integer>();

        allsolutions = getsolutions(allsolutions);
        possiblesolutions = allsolutions;


        wantedresult.add("B");
        wantedresult.add("B");
        wantedresult.add("B");
        wantedresult.add("B");
        wantedresult.add("B");


// initial guess
//        guess.add(1);
//        guess.add(1);
//        guess.add(2);
//        guess.add(2);


//        guess = getnextguess(allsolutions, possiblesolutions);

        guess.add(1);
        guess.add(1);
        guess.add(2);
        guess.add(3);
        guess.add(4);


        Boolean won = false;


        int c = 0;
        while (!won) {
            c++;
            System.out.println("Turn " + c + " ================================");

            //System.out.println("Code = " + code);
            System.out.println("Guess = " + guess);

            result = checkcode(guess, code);
            System.out.println("result = " + result);
            for (int i = 0; i < possiblesolutions.size(); i++) {

                tempresult = checkcode(possiblesolutions.get(i), guess);
                if (!tempresult.equals(result)) {
                    toberemoved.add(possiblesolutions.get(i));


                }

            }


            for (int i = 0; i < toberemoved.size(); i++) {
                possiblesolutions.remove(toberemoved.get(i));
            }
            //System.out.println("lengh of pos sol =  " + possiblesolutions.size());

            if (result.equals(wantedresult)) {
                won = true;
                System.out.println("WON!");
            } else {
                guess = getnextguess(allsolutions, possiblesolutions);
            }

        }

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        //System.out.println("it took " + totalTime* 1/1000    + " Seconds" );
    }
}


