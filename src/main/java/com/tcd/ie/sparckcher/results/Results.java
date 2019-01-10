/**
 * 
 */
package com.tcd.ie.sparckcher.results;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;

import com.tcd.ie.sparckcher.Pojo.TopicVO;
import com.tcd.ie.sparckcher.ProcessFiles.ProcessTopicFile;
import com.tcd.ie.sparckcher.Search.SearchGenerator;

/**
 * @author Sparckcher
 *
 */
public class Results {

	public static void generateResults() throws Exception {
		FileWriter fileWriter = new FileWriter(Paths.get("resources/").resolve("results.txt").toFile());
		ArrayList<TopicVO> listTop = ProcessTopicFile.readTopic();
		BufferedWriter writer = new BufferedWriter(fileWriter);
		listTop.forEach(eachTopic -> {
			try {
				final int[] count = { 1 };
				Arrays.stream(SearchGenerator.search(eachTopic, 1000)).forEach(eachHit -> {
					Document doc = null;
					try {
						doc = SearchGenerator.getIndexSearcher().doc(eachHit.doc);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String line = String.format("%d Q0 %s %d %f STANDARD\n", eachTopic.getId(), doc.get("id"),
							(count[0]++), eachHit.score);
					try {
						writer.write(line);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		});

		writer.close();
	}
}
