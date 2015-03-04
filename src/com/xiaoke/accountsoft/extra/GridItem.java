package com.xiaoke.accountsoft.extra;

public class GridItem {

	private String title;
	private int imageId;
	
	public GridItem() {
		super();
	}
	public GridItem(String title, int imageId){
		super();
		this.title = title;
		this.imageId = imageId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId){
		this.imageId = imageId;
	}
}
