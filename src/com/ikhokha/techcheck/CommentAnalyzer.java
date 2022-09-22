package com.ikhokha.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommentAnalyzer {
	private File _file;

	public CommentAnalyzer(File file) {
		_file = file;
	}

	public Callable<Metrics> analyze = () -> {
		var report = new Metrics();
		try {
			// read the file
			BufferedReader reader = new BufferedReader(new FileReader(_file));

			// get lines in file, converting stream to strings
			List<String> lines = reader.lines().collect(Collectors.toList());
			// get the number of lines in the file

			// get the number of lines that contain a question mark

			// match all urls and assign to spam count

			// get the number of lines that are less than 10 characters

			Shaker(report, lines);
			Mover(report, lines);
			Shorter(report, lines);
			Questions(report, lines);
			Spam(report, lines);

			System.out.println("File processed: " + _file.getName());

		} catch (FileNotFoundException e) {

			System.out.println(e.getMessage());
		}
		return report;
	};

	private void Spam(Metrics report, List<String> lines)

	{
		report.Spam = lines.stream().filter(s -> Pattern.compile(
				Constants.regex,
				Pattern.CASE_INSENSITIVE).matcher(s).find()).count();
	}

	private void Questions(Metrics report, List<String> lines) {
		report.Questions = lines.stream().filter(s -> s.contains("?")).count();
	}

	private void Shorter(Metrics report, List<String> lines) {
		report.Shorter = lines.stream().filter(s -> s.length() < 15).count();

	}

	private void Mover(Metrics report, List<String> lines) {
		report.Movers = lines.stream().filter(s -> s.toLowerCase().contains("mover")).count();
	}

	private void Shaker(Metrics report, List<String> lines) {
		report.Shakers = lines.stream().filter(s -> s.toLowerCase().contains("shaker")).count();
	}

}
