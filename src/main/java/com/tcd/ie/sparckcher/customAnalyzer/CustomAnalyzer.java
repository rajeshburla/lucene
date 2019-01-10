/**
 * 
 */
package com.tcd.ie.sparckcher.customAnalyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;

/**
 * @author Sparckcher
 *
 */
public class CustomAnalyzer extends Analyzer{

	@Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new ClassicTokenizer();
        TokenStream tokenStream = new LowerCaseFilter(source);
        tokenStream = new EnglishPossessiveFilter(tokenStream);
        tokenStream = new EnglishMinimalStemFilter(tokenStream);
        tokenStream = new KStemFilter(tokenStream);
        FileInputStream fs = null;
		try {
			fs = new FileInputStream("resources/stopwords.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                     
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));                
        String line = "";                                                                 
        Set<String> stopList=new HashSet<>();                                     
        try {
			while ((line = br.readLine()) != null) { 
				stopList.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                     
        tokenStream = new StopFilter(tokenStream, CharArraySet.copy(stopList));
        tokenStream = new PorterStemFilter(tokenStream);
        return new TokenStreamComponents(source, tokenStream);
    }
}