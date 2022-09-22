package com.ikhokha.techcheck;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	private static final Metrics metric = new Metrics();
	private static final ArrayList<Metrics> reports = new ArrayList<>();
	private static final ArrayList<Metrics> metricreports = reports;


	public static void main(String[] args) {
		try {
			// get files from docs folder
			String pathname = "docs";
			File[] files = new File(pathname).listFiles((file, s) -> {
				String suffix = ".txt";
				return s.endsWith(suffix);

			});
			// create a thread pool
			ExecutorService executor = Executors.newFixedThreadPool(5);
			// create a list to hold the Future object associated with Callable
			// create stack of future to store all file analysis report
			Stack<Future<Metrics>> futures = new Stack<>();
			// iterate over file, analysing each
			if (files != null) {
				for (File file : files) {
					// create CommentAnalyzer object
					CommentAnalyzer analyzer = new CommentAnalyzer(file);
					// submit Callable tasks to be executed by thread pool
					Future<Metrics> future = executor.submit(analyzer.analyze);
					// add Future to the list, we can get return value using Future
					futures.push(future);
				}
			}
			if (files != null) {
				for (File file : files) {
					// create CommentAnalyzer object
					var c = new CommentAnalyzer(file);
					// submit Callable tasks to be executed by thread pool
					futures(executor, futures, c);

				}
			}
			// Process the future reports while waiting for the threads to finish
			while (!futures.empty()) {
				// pop the future
				var result = futures.pop();
				// get the future report
				var comment = result.get();
				// add comment to report
				reports.add(comment);
			}
			executor.shutdown();
			// process the reports
			printReportResult();
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Thread execution error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void futures(ExecutorService executor, Stack<Future<Metrics>> futures,
								CommentAnalyzer c) {
		futures.push(executor.submit(c.analyze));
	}

	/**
	 * Consolidates and prints a report of each analysis
	 */
	private static void printReportResult() {
		// create a map to hold the report results
		for (int i = 0; i < Main.metricreports.size(); i++) {

			Metrics.Spam(Main.metricreports, metric, i);
			Metrics.Question(Main.metricreports, metric, i);
			Metrics.Movers(Main.metricreports, metric, i);
			Metrics.Shakers(Main.metricreports, metric, i);
			Metrics.Shorter(Main.metricreports, metric, i);
		}
		System.out.println("===================================");

		System.out.printf(Constants.SPAM, metric.Spam);
		System.out.printf(Constants.QUESTION, metric.Questions);
		System.out.printf(Constants.MOVER, metric.Movers);
		System.out.printf(Constants.SHAKERS, metric.Shakers);
		System.out.printf(Constants.SHORTER_THAN_15, metric.Shorter);

		System.out.println("===================================");
	}

}
