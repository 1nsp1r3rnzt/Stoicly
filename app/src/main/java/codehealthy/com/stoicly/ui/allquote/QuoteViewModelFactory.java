package codehealthy.com.stoicly.ui.allquote;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class QuoteViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public QuoteViewModelFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(QuoteViewModel.class)) {
            return (T) new QuoteViewModel(application);
        }
        throw new IllegalArgumentException("Unknown Model Class");
    }


}
