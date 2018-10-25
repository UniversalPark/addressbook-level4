package seedu.address.ui.exceptions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FilePathToUrl;
import seedu.address.ui.UiPart;

/**
 * Abstract class that loads Html pages, handles IOException when page cannot be read, and has ability to queue
 * Javascript calls for running after the page loads completely.
 * Override the webView getter when extending this abstract class.
 */
public abstract class BrowserRelatedUiPart<T> extends UiPart<T> {
    private static final int MAX_QUEUE_SIZE = 15;

    protected ArrayList<String> queuedJavascript = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    public BrowserRelatedUiPart(String FXML) {
        super(FXML);
        init();
    }

    public BrowserRelatedUiPart(String FXML, T root) {
        super(FXML, root);
        init();
    }

    private void init() {
        WebView webView = getWebView();
        assert(webView != null);
        webView.getEngine().setJavaScriptEnabled(true);
        webView.getEngine().getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

                    if (newState == Worker.State.SUCCEEDED) {
                        runQueuedJavascript();
                    }

                }
            });
    }

    protected WebView getWebView() {
        return null;
    }

    private void runQueuedJavascript() {
        WebView webView = getWebView();
        if (queuedJavascript.size() > MAX_QUEUE_SIZE) {
            logger.warning(String.format("Javascript queue size exceeds %1s", MAX_QUEUE_SIZE));
            queuedJavascript.clear();
        }
        while (!queuedJavascript.isEmpty()) {
            webView.getEngine().executeScript(queuedJavascript.remove(0));
        }
    }

    public void loadPage(FilePathToUrl filePathToUrl) {
        WebView webView = getWebView();
        try {
            String url = filePathToUrl.filePathToUrlString();
            Platform.runLater(() -> webView.getEngine().load(url));
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
    }

}
