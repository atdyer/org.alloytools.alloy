package edu.mit.csail.sdg.alloy4whole;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.ConstList;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4viz.VizGUI;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;

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
    private Command command;
    private A4Solution solution;

    // CLI commands:
    // - c: list commands
    // - d: display last solution
    // - e: execute a command
    // - m: set the model
    // - n: next solution
    // - q: quit

    private AtomCLI () {

        initialize_defaults();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean wait_for_next_input = true;

        System.out.println("CLI READY");

        while (wait_for_next_input) {

            try {

                String input = reader.readLine();
                String[] tokens = input.split("\\s+");

                if (input.length() > 0) {

                    char cmd = input.charAt(0);

                    switch (cmd) {

                        // Get commands
                        case 'c':
                            list_commands();
                            break;

                        // Display last solution
                        case 'd':
                            display_solution();
                            break;

                        // Execute command
                        case 'e':
                            if (tokens.length > 1) {
                                int index = Integer.parseInt(tokens[1]);
                                execute_command(index);
                            }
                            break;

                        // Set model
                        case 'm':
                            if (tokens.length > 1) {
                                set_model(tokens[1]);
                            }
                            break;

                        case 'n':
                            next_solution();
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

                System.out.println("CLI READY");

            }

            catch (IOException e) {

                System.out.println("Error");

            }


        }

    }

    private void display_solution () {

        if (solution != null) {

            solution.writeXML("CLI.xml");
            VizGUI viz = new VizGUI(false, "CLI.xml", null);
            viz.loadXML("CLI.xml", true);

        }

    }

    private void execute_command (int command_index) {

        if (module != null) {

            ConstList<Command> commands = module.getAllCommands();

            if (command_index >= 0 && command_index < commands.size()) {

                command = commands.get(command_index);

                solution = TranslateAlloyToKodkod.execute_command(
                        reporter,
                        module.getAllReachableSigs(),
                        command,
                        options
                );

                System.out.println('r');
                System.out.println(Integer.toString(command_index) + ":" + command.toString());
                System.out.println(solution);

            }

        }

    }

    private void list_commands () {

        if (module != null) {

            System.out.println('c');
            ConstList<Command> commands = module.getAllCommands();
            for (int i=0; i<commands.size(); ++i) {
                System.out.println(i + ":" + commands.get(i));
            }

        }

    }

    private void next_solution () {

        if (command != null && solution != null) {

            solution = solution.next();

            System.out.println('r');
            System.out.println(command);
            System.out.println(solution);

        }

    }

    private void set_model (String file) {

        module = CompUtil.parseEverything_fromFile(reporter, null, file);
        command = null;
        solution = null;

        System.out.println('m');
        System.out.println(file);

    }

    private void initialize_defaults () {

        reporter = new A4Reporter();
        options = new A4Options();
        options.solver = A4Options.SatSolver.SAT4J;

    }

}
