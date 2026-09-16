package fein;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/** Represents one user or Fein message in the chat history. */
public class DialogBox extends HBox {
    private static final String DIALOG_BOX_FXML = "/view/DialogBox.fxml";

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    /** Creates a dialog box containing the supplied message and avatar. */
    public DialogBox(String text, Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DIALOG_BOX_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load the dialog box layout", exception);
        }

        // These controls are required by DialogBox.fxml for every message to be displayed.
        assert dialog != null && displayPicture != null : "DialogBox.fxml must inject its controls";
        dialog.setText(text);
        displayPicture.setImage(image);
        if (image == null) {
            displayPicture.setManaged(false);
            displayPicture.setVisible(false);
        }
    }

    /** Returns a dialog box aligned as a user message. */
    public static DialogBox getUserDialog(String text, Image image) {
        return new DialogBox(text, image);
    }

    /** Returns a dialog box aligned as a Fein response. */
    public static DialogBox getFeinDialog(String text, Image image, String commandType) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.flip();
        dialogBox.changeDialogStyle(commandType);
        return dialogBox;
    }

    /** Flips the dialog box so that Fein's avatar appears on the left. */
    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(children);
        getChildren().setAll(children);
        dialog.getStyleClass().add("reply-label");
    }

    /** Applies a colour to Fein's response based on the command that produced it. */
    private void changeDialogStyle(String commandType) {
        // Command types are assigned by Fein before a response reaches the UI.
        assert commandType != null : "A response must have a command type for styling";
        if (commandType.equals("add")) {
            dialog.getStyleClass().add("add-label");
        } else if (commandType.equals("mark") || commandType.equals("unmark")) {
            dialog.getStyleClass().add("marked-label");
        } else if (commandType.equals("delete")) {
            dialog.getStyleClass().add("delete-label");
        } else if (commandType.equals("error")) {
            dialog.getStyleClass().add("error-label");
        }
    }
}
