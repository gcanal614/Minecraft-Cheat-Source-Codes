package libraries.thealtening.api;

import libraries.thealtening.api.retriever.AsynchronousDataRetriever;
import libraries.thealtening.api.retriever.BasicDataRetriever;

public final class TheAltening {

    public static BasicDataRetriever newBasicRetriever(String apiKey) {
        return new BasicDataRetriever(apiKey);
    }

    public static AsynchronousDataRetriever newAsyncRetriever(String apiKey) {
        return new AsynchronousDataRetriever(apiKey);
    }
}
