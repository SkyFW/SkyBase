package org.skyfw.base.test;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.concurrent.TimeUnit;

public class TTUI_Tester {

    public static void doTest(){
        new Thread(){

            @Override
            public void run() {
                super.run();

                //Demo1.main(args);
                try {
                    // Setup terminal and screen layers
                    Terminal terminal = new DefaultTerminalFactory().createTerminal();
                    Screen screen = new TerminalScreen(terminal);
                    screen.startScreen();

                    // Create panel to hold components
                    Panel panel = new Panel();
                    panel.setLayoutManager(new GridLayout(2));

                    panel.addComponent(new Label("Forename"));
                    panel.addComponent(new TextBox());

                    panel.addComponent(new Label("Surname"));
                    panel.addComponent(new TextBox());

                    panel.addComponent(new EmptySpace(new TerminalSize(0, 0))); // Empty space underneath labels
                    panel.addComponent(new Button("Submit"));

                    // Create window to hold the panel
                    BasicWindow window = new BasicWindow();
                    window.setComponent(panel);

                    // Create gui and start gui
                    MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
                    gui.addWindowAndWait(window);
                }catch (Exception e){}

            }

        }.run();

        /*try {
            TApplication.BackendType backendType = TApplication.BackendType.SWING;
            DemoApplication app = new DemoApplication(backendType);
            (new thread(app)).start();
        } catch (exception e) {
            e.printStackTrace();
        }*/


        try {
            TimeUnit.SECONDS.sleep(1000);
        }catch (Exception e){

        }

    }

}
