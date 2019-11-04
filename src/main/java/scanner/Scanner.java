package scanner;

import scanner.generator.minimization.DFAMinimization;
import scanner.generator.subset.SubsetConstruction;
import scanner.generator.thompsons.ThompsonsConstruction;
import scanner.language.Regex;
import scanner.model.automata.DFA;
import scanner.model.automata.NDFA;
import scanner.model.automata.PartitionDFA;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Scanner {
    private static final String LANGUAGE_DEFINITION_FILENAME = "language.txt";

    public static void scan(String filename) {
        Set<Regex> regexEntries = readInLanguageDefinitionEntries();
        if(regexEntries != null) {
            generateScanner(regexEntries);
        }
    }

    private static Set<Regex> readInLanguageDefinitionEntries() {
        try (BufferedReader br = new BufferedReader(getInputStreamReader(LANGUAGE_DEFINITION_FILENAME))){
            return processFile(br);
        }
        catch (IOException e) {
            System.out.println("Error: Could not process language definition file");
            return null;
        }
    }

    private  static InputStreamReader getInputStreamReader(String filename) throws IOException {
        return new InputStreamReader(
               new FileInputStream(
               new File(filename)));
    }

    private  static Set<Regex>  processFile(BufferedReader br) throws IOException {
        // Initialize spec
        Set<Regex> regexEntries = new HashSet<>();
        String line;
        while ((line = br.readLine()) != null)
            regexEntries.add(new Regex(line.trim()));
        return regexEntries;
    }

    private static void generateScanner(Set<Regex> entries) {
        NDFA ndfa = new ThompsonsConstruction().apply(entries);
        System.out.println(ndfa);
        PartitionDFA pdfa = new SubsetConstruction().apply(ndfa);
        System.out.println(pdfa);
        DFA minimizedDFA = new DFAMinimization().apply(pdfa);
        System.out.println(minimizedDFA);
    }
}
