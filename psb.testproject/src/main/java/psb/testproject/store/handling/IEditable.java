package psb.testproject.store.handling;

import java.io.File;
import java.util.Scanner;

public interface IEditable {
    void editName(Scanner in);
    void editPrice(Scanner in);
    void editWeight(Scanner in);
}
