package com.tcd.ie.sparckcher.application;

import com.tcd.ie.sparckcher.Index.IndexGenerator;
import com.tcd.ie.sparckcher.Search.SearchGenerator;
import com.tcd.ie.sparckcher.results.Results;

public class ExecuteApplication {

	public static void main(String[] args) throws Exception {
		System.out.println("Executing parsing and indexing....Please be patient :)");
			IndexGenerator.executeIndex();
		System.out.println("Super...parsing and indexing done! Cheers :)");
			SearchGenerator.executeSearch();
		System.out.println("Executing Searching....Please be patient :)");
			Results.generateResults();
		System.out.println("Super...Searching done and results generated! Cheers :)");
	}
}
