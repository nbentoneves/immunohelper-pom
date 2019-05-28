package org.opensource.immunometabolism.tools;

import javafx.scene.control.TextArea;
import org.springframework.stereotype.Component;


@Component
public class ConsoleComponentImpl implements NotifyLog {

    private TextArea consoleInfo;

    @Override
    public void update(String log) {
        consoleInfo.appendText(log + "\n");
    }

    public void setConsoleInfo(TextArea consoleInfo) {
        this.consoleInfo = consoleInfo;
    }
}
