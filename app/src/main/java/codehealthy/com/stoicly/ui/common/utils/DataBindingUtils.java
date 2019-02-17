package codehealthy.com.stoicly.ui.common.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

public class DataBindingUtils {


    @BindingAdapter("imageFromDrawablePath")
    public static void setImageFromDrawablePath(ImageView view, String imageUrl) {
        if (view != null & imageUrl != null) {
            Context context = view.getContext();
            int resId = context.getResources().getIdentifier(imageUrl, "drawable", context.getPackageName());
            view.setImageResource(resId);
        }
    }

}
