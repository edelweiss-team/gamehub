package model.bean;

import java.util.ArrayList;
import jep.Interpreter;
import jep.JepConfig;
import jep.JepException;
import jep.SharedInterpreter;

public class ProvaJep {
    public static void main(String[] args) throws JepException {
        try (Interpreter interp = new SharedInterpreter()) {
            JepConfig jep = new JepConfig();
            jep.addIncludePaths("./");
            interp.close();
            Interpreter interp2 = jep.createSubInterpreter();
            /*interp.exec("from java.lang import System");
            interp.exec("import sklearn as skl");
            interp.exec("import pandas as pd");
            interp.exec("import numpy as np");
            interp.exec("import os");*/
            interp2.exec("import sys");
            interp2.exec("print('ciaoooooooo')");
            interp2.exec("print(sys.path)");
            interp2.exec("import gigginoPy");
            /*interp.exec("s = os.getcwd()");
            interp.exec("System.out.println(s)");
            interp.exec("print(s)");
            interp.exec("print(s[1:-1])");
            //Object o2 = interp.getValue("prova()");*/
            interp2.exec("x = ['giggino']");
            ArrayList<String> o = interp2.getValue("gigginoPy.prova()", ArrayList.class);
            System.out.println("\n\n" + o + ", " + o.getClass());
        }
    }
}
