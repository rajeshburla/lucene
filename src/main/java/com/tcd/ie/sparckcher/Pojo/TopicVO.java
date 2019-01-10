/**
 * 
 */
package com.tcd.ie.sparckcher.Pojo;

/**
 * @author Sparckcher
 *
 */
public class TopicVO {

	static final String DESCRIPTION = "description";
    static final String TITLE = "title";
    static final String NARRATIVE = "narrative";
    static final String ID = "id";
	private int id;
    private String description;
    private String title;
    private String narrative;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the narrative
	 */
	public String getNarrative() {
		return narrative;
	}
	/**
	 * @param narrative the narrative to set
	 */
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	
/*	public TopicVO(int id, String title, String author, String narrative) {
        this.id = id;
        this.title = title;
        this.description = author;
        this.narrative = narrative;
    }*/
	/*public StringBuffer relNarrative() {
        String[] breaks = narrative.split("[\\.;]");
        StringBuffer stg = new StringBuffer();
        for (String eachBreak: breaks) {
            if (!eachBreak.contains("not relevant")) {
                stg.append(eachBreak).append(" ");
            }
        }
        return stg;
    }*/
}
