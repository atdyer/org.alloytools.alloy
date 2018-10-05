package edu.mit.csail.sdg.alloy4whole;

import edu.mit.csail.sdg.alloy4.Err;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class AtomCLI {

    public static void main(String[] args) throws Err {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String test = reader.readLine();
            System.out.println(test);
        }
        catch (IOException e) {
            System.out.println("Error");
        }

    }

}
