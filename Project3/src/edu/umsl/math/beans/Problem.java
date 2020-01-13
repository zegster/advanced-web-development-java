package edu.umsl.math.beans;

public class Problem 
{
	private int pid;
	private String content;
	private int category_id;
	
	
	public int getPid() 
	{
		return pid;
	}
	public void setPid(int pid) 
	{
		this.pid = pid;
	}
	
	
	public String getContent() 
	{
		return content;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	
	
	public int getCategory_id() 
	{
		return category_id;
	}
	public void setCategory_id(int category_id) 
	{
		this.category_id = category_id;
	}
}
