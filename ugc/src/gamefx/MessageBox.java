/*
 *	Java Institute For Advanced Technology
 *	Final Project
 *	
 *	*************************************
 *
 *	The MIT License
 *
 *	Copyright © 2017 CYCLOTECH.
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *	THE SOFTWARE.
 */
package gamefx;

import java.awt.Toolkit;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * MessageBox version 1.17.07.27
 *
 * This is a developed version of javafx.scene.control.Alert;
 *
 * @author Roshana Pitigala
 */
public class MessageBox {

    /**
     * Default alert types of javafx.scene.control.Alert.AlertType .
     */
    public static enum MessageType {
        NONE, INFORMATION, WARNING, CONFIRMATION, ERROR;
    }

    /**
     * Default button types of javafx.scene.control.ButtonType .
     */
    public static final String OK_BUTTON = "OK_BUTTON",
            CANCEL_BUTTON = "CANCEL_BUTTON",
            APPLY_BUTTON = "APPLY_BUTTON",
            FINISH_BUTTON = "FINISH_BUTTON",
            NEXT_BUTTON = "NEXT_BUTTON",
            NO_BUTTON = "NO_BUTTON",
            PREVIOUS_BUTTON = "PREVIOUS_BUTTON",
            YES_BUTTON = "YES_BUTTON";

    /**
     * Displays a Information Alert with the Header as Title.
     *
     * @param title Message Title.
     * @param messageBody Message Body.
     */
    public static void showInformationMessage(String title, String messageBody) {
        // <editor-fold defaultstate="collapsed" desc="showInformationMessage">
        showMessage(MessageType.INFORMATION, title, null, messageBody, null, false);
        // </editor-fold>
    }

    /**
     * Displays a Information Alert with the Header.
     *
     * @param title Message Title.
     * @param messageHeader Message Header.
     * @param messageBody Message Body.
     * @deprecated Since the title is now set as message header, this is not
     * recommended to use.
     */
    @Deprecated
    public static void showInformationMessage(String title, String messageHeader, String messageBody) {
        // <editor-fold defaultstate="collapsed" desc="showInformationMessage">
        showMessage(MessageType.INFORMATION, title, messageHeader, messageBody, null, false);
        // </editor-fold>
    }

    /**
     * Displays a Confirmation Alert with the Header as Title.
     *
     * @param title Message Title.
     * @param messageBody Message Body.
     * @param messageButtons Define the names of the message buttons. Make sure
     * the names are unique.
     * @return Returns the name of the clicked button. Use equals(Object
     * anObject) method to verify.
     */
    public static String showConfirmationMessage(String title, String messageBody, String messageButtons[]) {
        // <editor-fold defaultstate="collapsed" desc="showConfirmationMessage">
        return showMessage(MessageType.CONFIRMATION, title, null, messageBody, messageButtons, false);
        // </editor-fold>
    }

    /**
     * Displays a Confirmation Alert with the Header.
     *
     * @param title Message Title.
     * @param messageHeader Message Header.
     * @param messageBody Message Body.
     * @param messageButtons Define the names of the message buttons. Make sure
     * the names are unique.
     * @return Returns the name of the clicked button. Use equals(Object
     * anObject) method to verify.
     */
    @Deprecated
    public static String showConfirmationMessage(String title, String messageHeader, String messageBody, String messageButtons[]) {
        // <editor-fold defaultstate="collapsed" desc="showConfirmationMessage">
        return showMessage(MessageType.CONFIRMATION, title, messageHeader, messageBody, messageButtons, false);
        // </editor-fold>
    }

    /**
     * Displays a Error Alert with the Header as Title.
     *
     * @param title Message Title.
     * @param messageBody Message Body.
     */
    public static void showErrorMessage(String title, String messageBody) {
        // <editor-fold defaultstate="collapsed" desc="showErrorMessage">
        showMessage(MessageType.ERROR, title, null, messageBody, null, false);
        // </editor-fold>
    }

    /**
     * Displays a Error Alert with the Header.
     *
     * @param title Message Title.
     * @param messageHeader Message Header.
     * @param messageBody Message Body.
     * @deprecated Since the title is now set as message header, this is not
     * recommended to use.
     */
    @Deprecated
    public static void showErrorMessage(String title, String messageHeader, String messageBody) {
        // <editor-fold defaultstate="collapsed" desc="showErrorMessage">
        showMessage(MessageType.ERROR, title, messageHeader, messageBody, null, false);
        // </editor-fold>
    }

    /**
     * Displays a Warning Alert with the Header as Title.
     *
     * @param title Message Title.
     * @param messageBody Message Body.
     */
    public static void showWarningMessage(String title, String messageBody) {
        // <editor-fold defaultstate="collapsed" desc="showWarningMessage">
        showMessage(MessageType.WARNING, title, null, messageBody, null, false);
        // </editor-fold>
    }

    /**
     * Displays a Warning Alert with the Header.
     *
     * @param title Message Title.
     * @param messageHeader Message Header.
     * @param messageBody Message Body.
     * @deprecated Since the title is now set as message header, this is not
     * recommended to use.
     */
    @Deprecated
    public static void showWarningMessage(String title, String messageHeader, String messageBody) {
        // <editor-fold defaultstate="collapsed" desc="showWarningMessage">
        showMessage(MessageType.WARNING, title, messageHeader, messageBody, null, false);
        // </editor-fold>
    }

    /**
     * Displays a Customized Alert.
     *
     * @param type Message type.
     * @param title Message Title.
     * @param messageHeader Message Header. Title will be used if null.
     * @param messageBody Message Body.
     * @param messageButtons Define the names of the message buttons. Make sure
     * the names are unique. Default buttons are given as constant variables.
     * Example: MessageBox.OK_BUTTON;
     * @param beepTone if true, the system default beep sound will be played
     * when shown.
     * @return Returns the name of the clicked button. Use equals(Object
     * anObject) method to verify.
     */
    public static String showMessage(MessageType type, String title, String messageHeader, String messageBody, String messageButtons[], boolean beepTone) {
        // <editor-fold defaultstate="collapsed" desc="showMessage">
        Alert mb = new Alert(AlertType.NONE);
        ButtonType Buttons[] = null;
        switch (type) {
            case INFORMATION:
                mb.setAlertType(AlertType.INFORMATION);
                break;
            case WARNING:
                mb.setAlertType(AlertType.WARNING);
                break;
            case CONFIRMATION:
                mb.setAlertType(AlertType.CONFIRMATION);
                break;
            case ERROR:
                mb.setAlertType(AlertType.ERROR);
                break;
            default:
                mb.setAlertType(AlertType.NONE);
        }
        if (messageHeader != null) {
            mb.setHeaderText(messageHeader);
        } else {
            mb.setHeaderText(title);
        }
        mb.setContentText(messageBody);
        mb.setTitle(title);
        mb.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = mb.getDialogPane();
        dialogPane.getStylesheets().add(MessageBox.class.getResource("MessageBox.css").toExternalForm());
        dialogPane.getStyleClass().add("messageBox");

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.toFront();

        if (messageButtons != null) {
            Buttons = new ButtonType[messageButtons.length];
            for (int x = 0; x < messageButtons.length; x++) {
                if (messageButtons[x].equals(MessageBox.APPLY_BUTTON)) {
                    Buttons[x] = ButtonType.APPLY;
                } else if (messageButtons[x].equals(MessageBox.CANCEL_BUTTON)) {
                    Buttons[x] = ButtonType.CANCEL;
                } else if (messageButtons[x].equals(MessageBox.FINISH_BUTTON)) {
                    Buttons[x] = ButtonType.FINISH;
                } else if (messageButtons[x].equals(MessageBox.NEXT_BUTTON)) {
                    Buttons[x] = ButtonType.NEXT;
                } else if (messageButtons[x].equals(MessageBox.NO_BUTTON)) {
                    Buttons[x] = ButtonType.NO;
                } else if (messageButtons[x].equals(MessageBox.OK_BUTTON)) {
                    Buttons[x] = ButtonType.OK;
                } else if (messageButtons[x].equals(MessageBox.PREVIOUS_BUTTON)) {
                    Buttons[x] = ButtonType.PREVIOUS;
                } else if (messageButtons[x].equals(MessageBox.YES_BUTTON)) {
                    Buttons[x] = ButtonType.YES;
                } else if (messageButtons[x] != null) {
                    Buttons[x] = new ButtonType(messageButtons[x]);
                } else {
                    Buttons[x] = new ButtonType("");
                }
            }
            mb.getButtonTypes().setAll(Buttons);

            if (beepTone) {
                Toolkit.getDefaultToolkit().beep();
            }

            Optional<ButtonType> result = mb.showAndWait();

            for (int x = 0; x < Buttons.length; x++) {
                if (result.get() == Buttons[x]) {
                    return messageButtons[x];
                }
            }
        } else {
            if (beepTone) {
                Toolkit.getDefaultToolkit().beep();
            }

            mb.showAndWait();
        }

        return null;
        //</editor-fold>
    }

}
