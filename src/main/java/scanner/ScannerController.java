package scanner;

import scanner.generator.minimization.DFAMinimization;
import scanner.generator.subset.SubsetConstruction;
import scanner.generator.thompsons.ThompsonsConstruction;
import scanner.language.Regex;
import scanner.model.FiniteAutomaton;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ScannerController {
    public static void startScan(String filename) {
        Set<Regex> entries = getLanguageDefinitionEntries("language.txt");
        generateScanner(entries);
    }

    private  static Set<Regex> getLanguageDefinitionEntries(String filename) {
        try {
            File file = new File(filename);
            InputStream ioStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(ioStream);
            BufferedReader br = new BufferedReader(reader);
            return processEntries(br);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private  static Set<Regex>  processEntries(BufferedReader br) {
        Set<Regex> specifiers = new HashSet<>();

        try {
            String line;

            while ((line = br.readLine()) != null) {
                specifiers.add(new Regex(line.trim()));
            }

        } catch (IOException e) {
            System.out.println("Exception while reading input " + e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing stream: " + e);
            }
        }
        return specifiers;
    }

    private static void generateScanner(Set<Regex> entries) {
        FiniteAutomaton ndfa = new ThompsonsConstruction().apply(entries);
        System.out.println(ndfa);

        FiniteAutomaton DFA = new SubsetConstruction().apply(ndfa);
        System.out.println(DFA);

        FiniteAutomaton minimizedDFA = new DFAMinimization().apply(DFA);

        System.out.println(minimizedDFA);
    }
}
