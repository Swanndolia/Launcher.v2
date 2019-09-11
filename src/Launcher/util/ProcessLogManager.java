package Launcher.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessLogManager extends Thread
{

    private boolean print = true;

    private BufferedReader reader;

    private File toWrite;

    private BufferedWriter writer;

    public ProcessLogManager(InputStream input)
    {
        this(input, null);
    }

    public ProcessLogManager(InputStream input, File toWrite)
    {
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.toWrite = toWrite;

        if (toWrite != null)
            try
            {
                this.writer = new BufferedWriter(new FileWriter(toWrite));
            }
            catch (IOException e)
            {
                LogUtil.err("log-err", e.toString());
            }
    }

    @Override
    public void run()
    {
        String line;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                if (print)
                    System.out.println(line);

                if (writer != null)
                    try
                    {
                        writer.write(line + "\n");
                    }
                    catch (IOException e)
                    {
                        LogUtil.err("log-err", e.toString());
                    }
            }
        }
        catch (IOException e)
        {
            LogUtil.err("log-end", e.toString());

            this.interrupt();
        }

        if (writer != null)
            try
            {
                writer.close();
            }
            catch (IOException ignored)
            {
            }
    }

    public boolean isPrint()
    {
        return print;
    }

    public void setPrint(boolean print)
    {
        this.print = print;
    }

    public File getToWrite()
    {
        return toWrite;
    }

    public void setToWrite(File toWrite)
    {
        this.toWrite = toWrite;
    }
}
