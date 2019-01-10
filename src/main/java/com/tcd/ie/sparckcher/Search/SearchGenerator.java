/**
 * 
 */
package com.tcd.ie.sparckcher.Search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;

import com.tcd.ie.sparckcher.Pojo.TopicVO;
import com.tcd.ie.sparckcher.customAnalyzer.CustomAnalyzer;

/**
 * @author Sparckcher
 *
 */
public class SearchGenerator {
	private static Analyzer analyzer = new EnglishAnalyzer();
	private static IndexSearcher indexSearcher;
	private static Similarity similarity = new BM25Similarity();

	public static IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}

	public static void executeSearch() throws IOException {
			DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("resources/").resolve("indexes/")));
			indexSearcher = new IndexSearcher(reader);
			indexSearcher.setSimilarity(similarity);
	}

	public static ScoreDoc[] search(TopicVO topic, int topHitsCount) throws IOException, ParseException {
		Query query = generateQuery(topic);

			return indexSearcher.search(query, topHitsCount).scoreDocs;

	}

	private static Query generateQuery(TopicVO topic) throws ParseException {
		String fields[] = new String[] { "content" };
		QueryParser parser = new MultiFieldQueryParser(fields, analyzer);

			Query titleQuery = parser.parse(QueryParser.escape(topic.getTitle()));
			titleQuery = new BoostQuery(titleQuery, 4.2f);
			Query descriptionQuery = parser.parse(QueryParser.escape(topic.getDescription()));
			descriptionQuery = new BoostQuery(descriptionQuery, 1.8f);
	        StringBuffer stg = new StringBuffer();
	        Stream<String> stream1 = Arrays.stream(topic.getNarrative().split("[\\.;]"));
	        stream1.forEach(eachBreak->{if(!eachBreak.contains("not relevant"))
	        	{  stg.append(eachBreak).append(" "); }});
			Query narrativeQuery = parser.parse(QueryParser.escape("a" + stg.toString()));

			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			builder.add(titleQuery, BooleanClause.Occur.MUST);
			builder.add(descriptionQuery, BooleanClause.Occur.MUST);
			builder.add(narrativeQuery, BooleanClause.Occur.MUST);

			return builder.build();

	}
}
