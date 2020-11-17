package einfuehrung;

import javafx.scene.control.TextField;

public class Controller {

    public TextField vornameTF;
    public TextField nachnameTF;

    public void clear() {
        vornameTF.clear();
        nachnameTF.clear();
    }

    public void submit() {
        String vorname = vornameTF.getText().trim(), nachname = nachnameTF.getText().trim();
        if (!vorname.isEmpty() && !nachname.isEmpty()) {
            System.out.printf("Name: %s %s%n", vorname, nachname);
            clear();
        }
    }
}
