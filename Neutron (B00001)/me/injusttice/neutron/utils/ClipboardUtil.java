package me.injusttice.neutron.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtil {
    private ClipboardUtil() {
    }

    public static void setClipboardContents(final String data) {
        final StringSelection selection = new StringSelection(data);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static String getClipboardContents() {
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        try {
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            // TODO :: Error log
            return null;
        }
    }
}
