package logic;

import java.io.File;

/**
 * Created by leonard on 22.01.17.
 */
public class Controller {
	private static Controller ourInstance = new Controller();

	public static Controller getInstance() {
		return ourInstance;
	}

	private Controller() {
	}

	public boolean startAnalyzingDirectory(final File directory){
		return AnalysisManager.getInstance().analyzeDir(directory);
	}

	public static int getFileCount(final File directory) {
		return AnalysisManager.getInstance().getFiles(directory).size();
	}

	public static void main(String args[]) {
		try {
			Controller.testController();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testController() {
		File directory = new File("/home");

			if(Controller.getInstance().startAnalyzingDirectory(directory)){
				System.out.println("started Analysis on " + directory.getAbsolutePath());
			} else {
				System.err.println("failed to start Analysis on " + directory.getAbsolutePath());
			}
			if(Controller.getInstance().startAnalyzingDirectory(directory)){
				System.out.println("started Analysis on " + directory.getAbsolutePath());
			} else {
				System.err.println("failed to start Analysis on " + directory.getAbsolutePath());
			}

		AnalysisManager.getInstance().pauseAnalysis(directory);
		System.out.println(AnalysisManager.getInstance().continueAnalysis(directory));
		try {
			Controller.getInstance().startAnalyzingDirectory(new File("/home"));
		} catch (Exception e) {
			AnalysisManager.getInstance().abortAnalyzing(directory);
			try {
				Controller.getInstance().startAnalyzingDirectory(new File("/home"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		while (AnalysisManager.getInstance().isAnalyzing(directory)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getFileCount(directory) + " files found; has finished: " + AnalysisManager.getInstance().hasFinished(directory));
		}

		System.out.println(getFileCount(directory) + " files found;  has finished: " + AnalysisManager.getInstance().hasFinished(directory));
	}
}
