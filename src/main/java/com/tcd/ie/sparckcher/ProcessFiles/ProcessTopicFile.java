/**
 * 
 */
package com.tcd.ie.sparckcher.ProcessFiles;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tcd.ie.sparckcher.Pojo.TopicVO;

/**
 * @author Sparckcher
 *
 */
public class ProcessTopicFile {

	public static ArrayList<TopicVO> readTopic() throws Exception {
		ArrayList<TopicVO> listTop = new ArrayList<>();

			Matcher matcher = Pattern.compile("<top>.*?<num>" + ".*?(\\d+).*?<title>" + "\\s*(.*?)\\s*<desc>" + ".*?:\\s*(.*?)\\s*<narr>"
					+ ".*?:\\s*(.*?)\\s*</top>").matcher(String.join(" ", Files.readAllLines(Paths.get("resources/").resolve("topics/topics.401-450"))));
			TopicVO topicVO =null;
				
			while (matcher.find()) {
				if (matcher.groupCount() != 4) {
					throw new Exception("Invalid format");
				}
				topicVO = new TopicVO();
				topicVO.setId(Integer.valueOf(matcher.group(1)));
				topicVO.setTitle(matcher.group(2));
				topicVO.setDescription(matcher.group(3));
				topicVO.setNarrative(matcher.group(4));
				listTop.add(topicVO);
			}

		return listTop;
	}
}
