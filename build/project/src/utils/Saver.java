package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

public class Saver {
	
	private File file;
	private Properties properties;
    public Saver(File fileName)
    {
        file = fileName;
        properties = new Properties();
        if (file.exists())
            load();
	}
	
    public void set(String key, String value)
	{
	    properties.setProperty(key, value);
	    save();
	}

	public String get(String key)
	{
	    return properties.getProperty(key);
	}
	   
	public String get(String key, String def)
	{
	    String value = properties.getProperty(key);
	    return value == null ? def : value;
	}

	public void save()
	{
	    try
	    {
	        properties.store(new BufferedWriter(new FileWriter(file)), "Generated by the launcher, DO NOT EDIT IF YOU DON'T KNOW WHAT YOU DO");
	    }
	    catch (Throwable t)
	    {
	        //ERROR TO LOG
	    }
	}

	public void load()
	{
	    try
	    {
	        properties.load(new FileInputStream(file));
	    }
	    catch (Throwable t)
	    {
	    	//ERROR TO LOG
	    }
	}
}
