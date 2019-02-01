package codehealthy.com.stoicly.UI.author;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AuthorViewModelFactory implements ViewModelProvider.Factory {
    //    view model needs application context.
//    passing author id as parameter
    private Application application;
    private int         param;


     AuthorViewModelFactory(Application application, int param) {
        this.application = application;
        this.param = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthorViewModel(application, param);
    }
}
