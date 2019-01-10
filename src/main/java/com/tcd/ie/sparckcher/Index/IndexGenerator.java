/**
 * 
 */
package com.tcd.ie.sparckcher.Index;

import java.nio.file.Path;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import java.nio.file.Paths;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import java.io.IOException;
import org.apache.lucene.document.StringField;
import com.tcd.ie.sparckcher.customAnalyzer.CustomAnalyzer;
import com.tcd.ie.sparckcher.Pojo.ContentVO;
import org.apache.lucene.store.Directory;
import java.util.ArrayList;
import java.util.Set;

import com.tcd.ie.sparckcher.ProcessFiles.ProcessNewsFiles;
import com.tcd.ie.sparckcher.constants.Constants;

/**
 * @author Sparckcher
 *
 */
public class IndexGenerator {

	public static void executeIndex() throws IOException {
		Set<Path> paths = ProcessNewsFiles.getFilesPaths();

		IndexWriterConfig config = new IndexWriterConfig(new CustomAnalyzer());
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		Directory dir = FSDirectory.open(Paths.get("resources/").resolve("indexes/"));
		IndexWriter writer = new IndexWriter(dir, config);
		//ArrayList<ContentVO> contentList=null;
		paths.forEach(path->{try {
			ProcessNewsFiles.parseNewsReports(path).forEach(eachReport->{
				Document doc = new Document();
				doc.add(new TextField(Constants.WORDS, eachReport.getContent(), Field.Store.NO));
				doc.add(new StringField(Constants.NO, eachReport.getId(), Field.Store.YES));
				try {
					writer.addDocument(doc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}} );
		
		/*for (Path path : paths) {
			contentList = ProcessNewsFiles.parseNewsReports(path);

			for (ContentVO report : contentList) {
				Document doc = new Document();
				doc.add(new TextField(Constants.WORDS, report.getContent(), Field.Store.NO));
				doc.add(new StringField(Constants.NO, report.getId(), Field.Store.YES));
				writer.addDocument(doc);
			}
		}*/
		writer.close();
	}
}
