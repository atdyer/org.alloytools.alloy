package edu.mit.csail.sdg.alloy4whole;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.translator.A4Options;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class AtomCLI {

    public static void main(String[] args) throws Err {

        AtomCLI CLI = new AtomCLI();

    }

    private A4Reporter reporter;
    private A4Options options;
    private Module module;

    // CLI commands:
    // - q: quit

    private AtomCLI () {

        initialize_defaults();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean wait_for_next_input = true;

        while (wait_for_next_input) {

            try {

                String input = reader.readLine();

                if (input.length() > 0) {

                    char command = input.charAt(0);

                    switch (command) {

                        // Get commands
                        case 'c':
                            break;

                        // Execute command
                        case 'e':
                            break;

                        // Set model
                        case 'm':
                            break;

                        // Quit
                        case 'q':
                            wait_for_next_input = false;
                            break;

                        default:
                            System.out.println("Unknown command.");
                            break;

                    }

                }

            }

            catch (IOException e) {

                System.out.println("Error");

            }


        }

    }

    private void initialize_defaults () {

        reporter = new A4Reporter();
        options = new A4Options();
        options.solver = A4Options.SatSolver.SAT4J;

    }

}
