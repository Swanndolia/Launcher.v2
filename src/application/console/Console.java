package application.console;

import java.awt.EventQueue;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;

public class Console extends OutputStream
{

    private byte[] oneByte;
    private Appender appender;

    public Console(JTextArea txtara) {
        this(txtara, 1000);
    }

    public Console(JTextArea txtara, int maxlin) {
        if (maxlin < 1) {
            throw new IllegalArgumentException("TextAreaOutputStream maximum lines must be positive (value=" + maxlin + ")");
        }
        this.oneByte = new byte[1];
        this.appender = new Appender(txtara, maxlin);
    }

    public synchronized void clear() {
        if (this.appender != null) {
            this.appender.clear();
        }
    }

    @Override
    public synchronized void close() {
        this.appender = null;
    }

    @Override
    public synchronized void flush() {
    }

    @Override
    public synchronized void write(int val) {
        this.oneByte[0] = (byte)val;
        this.write(this.oneByte, 0, 1);
    }

    @Override
    public synchronized void write(byte[] ba) {
        this.write(ba, 0, ba.length);
    }

    @Override
    public synchronized void write(byte[] ba, int str, int len) {
        if (this.appender != null) {
            this.appender.append(Console.bytesToString(ba, str, len));
        }
    }

    private static String bytesToString(byte[] ba, int str, int len) {
        try {
            return new String(ba, str, len, "UTF-8");
        }
        catch (UnsupportedEncodingException thr) {
            return new String(ba, str, len);
        }
    }

    static class Appender
    implements Runnable {
        private final JTextArea textArea;
        private final int maxLines;
        private final LinkedList<Integer> lengths;
        private final List<String> values;
        private int curLength;
        private boolean clear;
        private boolean queue;
        private static final String EOL1 = "\n";
        private static final String EOL2 = System.getProperty("line.separator", "\n");

        @SuppressWarnings({"rawtypes", "unchecked"})
        Appender(JTextArea txtara, int maxlin) {
            this.textArea = txtara;
            this.maxLines = maxlin;
            this.lengths = new LinkedList();
            this.values = new ArrayList<String>();
            this.curLength = 0;
            this.clear = false;
            this.queue = true;
        }

        synchronized void append(String val) {
            this.values.add(val);
            if (this.queue) {
                this.queue = false;
                EventQueue.invokeLater(this);
            }
        }

        synchronized void clear() {
            this.clear = true;
            this.curLength = 0;
            this.lengths.clear();
            this.values.clear();
            if (this.queue) {
                this.queue = false;
                EventQueue.invokeLater(this);
            }
        }

        @Override
        public synchronized void run() {
            if (this.clear) {
                this.textArea.setText("");
            }
            for (String val : this.values) {
                this.curLength += val.length();
                if (val.endsWith(EOL1) || val.endsWith(EOL2)) {
                    if (this.lengths.size() >= this.maxLines) {
                        this.textArea.replaceRange("", 0, this.lengths.removeFirst());
                    }
                    this.lengths.addLast(this.curLength);
                    this.curLength = 0;
                }
                this.textArea.append(val);
            }
            this.values.clear();
            this.clear = false;
            this.queue = true;
        }
    }

}