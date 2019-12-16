package org.spring.banner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
public class CreateFolder {
	@Autowired
	private Environment applicationProprty;

	@GetMapping(value = "/createFolder")
	public List<String> createFolder(
			@RequestParam(name = "fromDate") @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate) {
		String mainDir = applicationProprty.getProperty("file.folder-dir");

		return createAndCheckDirectory(mainDir, firstDate);
	}

	private List<String> createAndCheckDirectory(String mainDir, LocalDate firstDate) {

		File dir1 = new File(mainDir + "//");

		boolean successful = dir1.mkdirs();
		LocalDate date = firstDate;
		LocalDate dat = LocalDate.now();
		if (successful) {

			return getFile(mainDir, firstDate, dat);
		} else {
			// something failed trying to create the directories
			return getFile(mainDir, firstDate, dat);
		}
	}

	public List<String> getFile(String mainDir, LocalDate firstDate, LocalDate dat) {// create multiple directories at
																						// one time
		File dir = null;
		List<String> fileList = new ArrayList<String>();

		LocalDate date = firstDate;
		for (int i = 0; i < ChronoUnit.DAYS.between(firstDate, dat); i++) {
			if (date.getDayOfWeek().getValue() != DayOfWeek.SUNDAY.getValue()) {
				dir = new File(mainDir + "//" + date);
				dir.mkdirs();
				// created the directories successfully
				String directory = dir.getAbsolutePath() + "\\";
				genFile(directory);
				fileList.add(directory);
			}

			date = LocalDate.parse(date.toString()).plusDays(1);

		}
		return fileList;

	}

	public void genFile(String directory) {
		List<String> lines = Arrays.asList("PROVIDE DESCRIPTION: ->  ");
		try {
			Files.write(Paths.get(directory + "REDEM.txt"), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
