package main;

import analyzer.level1.BodyAnalyzer;
import de.unifreiburg.cs.proglang.jgs.instrumentation.*;
import soot.*;
import util.logging.DebugCSVHandler;
import util.logging.SOutHandler;
import util.parser.ArgParser;
import util.parser.ArgumentContainer;
import de.unifreiburg.cs.proglang.jgs.typing.FixedTypings;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static soot.SootClass.SIGNATURES;


/**
 * This is the Main Entry for the Dynamic Analyzer.
 * It has some useful Methods, that are used in the first place,
 * to set up things  to run the Dynamic Analyzer.
 *
 * It provides also a main Entry to run it as a program.
 * @author Regina Koenig, Nicolas Müller, Karsten Fix
 */
public class Main {

	// <editor-fold desc="Logger Setup">
	/** The logger, that prints log messages. */
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	/** Flag for the logger setup, only to assure, that it does not call
	 * the logger setup more then required. */
	private static boolean loggerSetup = false;
	
	/**
	 * Sets up the logger. Thereby it removes the standard Handlers and replaces
	 * them by our own defined Handlers and Formatter.
	 */
	public static void setupLogger() {
		if (loggerSetup) return;
		// Setting up the loggers of the different levels.
		// The logger for the instrumentation, is the Package Name of the BodyAnalyser.
		Logger l1 = Logger.getLogger("");
		l1.setLevel(Level.ALL);

		// Avoiding passing the Messages more up. Abd removing all standard
		// Handlers, such that the Messages only appearing, where we want.
		// l1.setUseParentHandlers(false);
		for (Handler h : l1.getHandlers()) l1.removeHandler(h);

		// Adding all Handlers, that we want. There so is the Debug Handler
		// and a console Handler
		SOutHandler h = new SOutHandler();
		//try {
			//l1.addHandler(new DebugCSVHandler());
			l1.addHandler(h);
		//} catch (IOException e) { e.printStackTrace(); }

		// for the Console Handler it could be decided which Level
		// should be used.
		h.setLevel(Level.INFO);
		loggerSetup = true;
	}
	// </editor-fold>

    /**
	 * The entry point for compilation and instrumentation (that is, adding the appropriate
	 * code to check for information leak). Use appropriate arguments to indicate
	 * which test will be compiled, and what the output format should be.
	 *
     * To see which command line args to use, go to the parser in util.parser, or run the main (this one) without any
     * arguments, which'll print the help.
	 *
	 * @param args Commandline-Args for analysis
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		ArgumentContainer sootOptionsContainer = ArgParser.getSootOptions(args);
		MethodTypings methodTypings;
		if (sootOptionsContainer.usePublicTyping()) {
			methodTypings = FixedTypings.allPublic();
		} else {
			methodTypings = FixedTypings.allDynamic();
		}
		execute(args, FixedTypings.allDynamic(), NoCasts.apply());
	}




	public static void execute(String[] args,
								MethodTypings m,
							    Casts c) throws UnsupportedEncodingException {
	    doSootSetup(args);
		executeWithoutSootSetup(args, m, c);
	}

	// TODO: move to another package (or even project) as this kind of setup is used by the whole application, not only DA
    public static void doSootSetup(String[] args) throws UnsupportedEncodingException {

        ArgumentContainer sootOptionsContainer = ArgParser.getSootOptions(args);

        setupLogger();

        String javaHome = System.getProperty("java.home");    //gets the path to java home, here: "/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/jre"


        if (javaHome == null) {
            throw new IllegalStateException("System property `java.home' is undefined");
        }

        // Setting the soot classpath

        String classPath = String.join(File.pathSeparator,
									   Arrays.asList(Scene.v().getSootClassPath(),
													 ".",
													 new File(javaHome, "lib/jce.jar").toString(),
													 new File(javaHome, "lib/rt.jar").toString()));

        // Adding the arguments given by the user via the -p flag. See util.parser.ArgParser
        String extraCpDirs = String.join(File.pathSeparator, sootOptionsContainer.getAddDirsToClasspath());
        String extraCpClasses = String.join(File.pathSeparator, sootOptionsContainer.getAddClassesToClasspath());
        classPath = String.join(File.pathSeparator, extraCpDirs, classPath, extraCpClasses);

        // Add the current classpath to soot classpath
		// TODO: this is only a quick hack for testing. We should figure out precisely how the soot classpath should look.
		List<String> cpath = new ArrayList<>();
        // TODO: Lu: I'm also not sure what a "ContextClassLoader" is.
		// (pasted from https://stackoverflow.com/questions/11613988/how-to-get-classpath-from-classloader)
        ClassLoader cxClassloader = Thread.currentThread().getContextClassLoader();
        if (cxClassloader instanceof URLClassLoader) {
			for (URL url : Arrays.asList(((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs())) {
				cpath.add(URLDecoder.decode(url.getPath(), "UTF-8"));
			}
		} else {
        	// throw new RuntimeException("Cannot get URLs needed for the soot classpath from current contextclassloader");
			logger.warning("Cannot get URLs needed for the soot classpath from current contextclassloader.\n "
										 + "(Ignore this warning when running under sbt).");
		}

		Scene.v().setSootClassPath(String.join(File.pathSeparator, cpath) + File.pathSeparator + classPath);

		logger.info("Soot classpath: " + Scene.v().getSootClassPath());

        // those are needed because of soot-magic i guess
        Scene.v().addBasicClass("analyzer.level2.HandleStmt", SIGNATURES);
        Scene.v().addBasicClass("analyzer.level2.SecurityLevel", SIGNATURES);
    }


	/**
     * Method which configures and executes soot.main.Main.
     * @param args This arguments are delivered by main.Main.main.
     */
	public static <L> void executeWithoutSootSetup(String[] args,
												   MethodTypings<L> m,
												   Casts<L> c) {

		ArgumentContainer sootOptionsContainer = ArgParser.getSootOptions(args);
        LinkedList<String> sootOptions = new LinkedList<>(Arrays.asList(
                sootOptionsContainer.getMainclass(),                    // adds the mainclass file
                //"-main-class", sootOptionsContainer.getMainclass(),     // specifies which file should be the mainclass
                "-f", sootOptionsContainer.getOutputFormat(),           // sets output format
                "--d", sootOptionsContainer.getOutputFolderAbsolutePath()
				));         // sets output folder
		for (String s : sootOptionsContainer.getAdditionalFiles()) {
		    sootOptions.add(s);
		    // add further files to be instrumented (-f flag)
        }

        // ====== Create / load fake static analysis results ======
		MethodTypings<L> methodTypings = m;
		Casts<L> casts = c;


        BodyAnalyzer<L> bodyAnalyzer = new BodyAnalyzer<>(methodTypings, casts);

		PackManager.v().getPack("jtp").add(new Transform("jtp.analyzer", bodyAnalyzer));
        BodyAnalyzer<L> bodyAnalyzerNew = new BodyAnalyzer<>(methodTypings, casts);
		//PackManager.v().getPack("jtp").add(new Transform("jtp.analyzer", bodyAnalyzerNew));

		soot.Main.main(sootOptions.toArray(new String[sootOptions.size()]));


		// for multiple runs, soot needs to be reset, which is done in the following line
		G.reset();

		// open question:
		// was ist der empfohlene weg, exceptions zu werfen aus einer analyse heraus.
		// unsere situation: Rufen main.Main in unit tests auf, wewnn wir einmal expcept werfen, bricht
		// alles ab, obwohl wir resetten.

	}


}
