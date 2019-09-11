package Launcher.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class CrashReporter
{
    private File dir;

    private String name;

    public CrashReporter(String name, File dir)
    {
        this.name = name;
        this.dir = dir;
    }

    public void catchError(Exception e, String message)
    {
        LogUtil.err("ex-caught");

        System.out.println(makeCrashReport(name, e));

        String msg;

        try
        {
            File report = writeError(e);
            msg = "\nThe crash report is in : " + report.getAbsolutePath() + "";
        }
        catch (IOException e2)
        {
            LogUtil.err("report-error");
            e.printStackTrace();
            msg = "\nAnd unable to write the crash report :( : " + e2;
        }

        JOptionPane.showMessageDialog(null, message + "\n" + e + "\n" + msg, "Error", JOptionPane.ERROR_MESSAGE);

        System.exit(1);
    }

    public File writeError(Exception e) throws IOException
    {
        File file;
        int number = 0;
        while ((file = new File(dir, "crash-" + number + ".txt")).exists())
            number++;

        LogUtil.info("writing-crash", file.getAbsolutePath());

        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);

        fw.write(makeCrashReport(name, e));

        fw.close();

        return file;
    }

    public File getDir()
    {
        return dir;
    }

    public void setDir(File dir)
    {
        this.dir = dir;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static String makeCrashReport(String projectName, Exception e)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String report = "# " + projectName + " Crash Report\n\r" +
                        "#\n\r" +
                        "# At : " + dateFormat.format(date) + "\n\r" +
                        "#\n\r" +
                        "# Exception : " + e.getClass().getSimpleName() + "\n\r";

        report += "\n\r# " + e.toString();

        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement element : stackTrace)
            report += "\n\r#     " + element;

        Throwable cause = e.getCause();
        if (cause != null)
        {
            report += "\n\r# Caused by: " + cause.toString();

            StackTraceElement[] causeStackTrace = cause.getStackTrace();
            for (StackTraceElement element : causeStackTrace)
                report += "\n\r#     " + element;
        }

        return report;
    }
}
