package libraries.thealtening.api.retriever;

import libraries.thealtening.api.TheAlteningException;
import libraries.thealtening.api.response.Account;
import libraries.thealtening.api.response.License;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class AsynchronousDataRetriever extends BasicDataRetriever {

    public AsynchronousDataRetriever(String apiKey) {
        super(apiKey);
    }

    public CompletableFuture<License> getLicenseDataAsync() {
        return completeTask(BasicDataRetriever::getLicence);
    }

    public CompletableFuture<Account> getAccountDataAsync() {
        return completeTask(BasicDataRetriever::getAccount);
    }

    public CompletableFuture<Boolean> isPrivateAsync(String token) {
        return completeTask(dr -> dr.isPrivate(token));
    }

    public CompletableFuture<Boolean> isFavoriteAsync(String token) {
        return completeTask(dr -> dr.isFavorite(token));
    }

    private <T> CompletableFuture<T> completeTask(Function<BasicDataRetriever, T> function) {
        CompletableFuture<T> returnValue = new CompletableFuture<>();
        try {
            returnValue.complete(function.apply(this));
        } catch (TheAlteningException exception) {
            returnValue.completeExceptionally(exception);
        }

        return returnValue;
    }
}