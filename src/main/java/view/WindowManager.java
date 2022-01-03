package view;

import view.form.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Map;

public class WindowManager {

    private static final int WIDTH = 1280, HEIGHT = 680;
    private static final String WINDOW_TITLE = "Test Automatic Creation - 2.0.0";
    private static final JFrame window = new JFrame(WINDOW_TITLE);

    public static void launchWindow() {
        createWindow();
        window.setVisible(true);
    }

    private static void createWindow() {
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setContentPane(createWindowContentPane(window.getContentPane()));
        window.setLocationRelativeTo(null);
        window.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
                        threads.forEach(
                                (thread, stackTraceElements) -> {
                                    System.out.println("\n\n");
                                    System.out.println(thread);
                                    System.out.println(Arrays.toString(stackTraceElements));
                                }
                        );
                        super.windowClosing(e);
                    }
                }
        );
    }

    private static Container createWindowContentPane(Container windowContentPane) {
        windowContentPane.setBackground(Color.DARK_GRAY);
        populateWindowContentPane(windowContentPane);
        return windowContentPane;
    }

    private static void populateWindowContentPane(Container windowContentPane) {
        Form form = new Form();
        form.createFormView(windowContentPane);
    }

}
