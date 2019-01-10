/**
 * 
 */
package com.tcd.ie.sparckcher.ProcessFiles;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tcd.ie.sparckcher.Pojo.ContentVO;
import com.tcd.ie.sparckcher.constants.Constants;

/**
 * @author Sparckcher
 *
 */
public class ProcessNewsFiles {

	static Set<Path> dirLocations = null;

	public static ArrayList<ContentVO> parseNewsReports(Path path) throws IOException {
		ArrayList<ContentVO> contentVO = new ArrayList<>();

		Document document = Jsoup.parse(path.toFile(), StandardCharsets.UTF_8.name());
		Elements elements = document.select(Constants.DOC);
		ContentVO vo = null;
		for (Element eachElement : elements) {
			vo = new ContentVO();
			vo.setId(eachElement.getElementsByTag(Constants.DOC_NO).text());
			vo.setContent(eachElement.getElementsByTag(Constants.TEXT).text());
			contentVO.add(vo);
		}

		return contentVO;
	}

	public static Set<Path> getFilesPaths() {

		File[] subFiles = null;
		Stack<File> files = new Stack<>();
		Arrays.asList("fbis", "fr94", "ft", "latimes")
				.forEach(item -> files.push(Paths.get("resources/documents").resolve(item).toFile()));
		dirLocations = new HashSet<>();
		while (!files.isEmpty()) {

			File eachFile = files.pop();
			if (eachFile.isFile()) {
				if (Arrays.asList("fb", "fr", "ft", "la").contains(
						eachFile.getName().substring(0, Arrays.asList("fb", "fr", "ft", "la").get(0).length()))) {
					dirLocations.add(eachFile.toPath());
				}
			} else {
				subFiles = eachFile.listFiles();
				if (subFiles == null) {
					System.exit(1);
				}

				for (File item : subFiles) {
					files.push(item);
				}
			}
		}

		return dirLocations;
	}
}
