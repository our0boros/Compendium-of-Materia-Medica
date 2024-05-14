package model;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.compendiumofmateriamedica.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilsApp {

    private UtilsApp() {
        // Private constructor to prevent instantiation
    }

    // Utils methods for app
    // Convert dp to px
    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    // Load image from URL using Glide
    public static void loadImageFromURL(Context context, String url, ImageView imageView, String imageType) {
        int errorImage;

        switch (imageType) {
            case "Avatar":
                errorImage = R.drawable.unknown_user;
                break;
            case "Photo":
                errorImage = R.drawable.load_image_fail;
                break;
            default:
                errorImage = R.drawable.load_image_fail;
                break;
        }

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loading_image) // Placeholder image while loading
                .error(errorImage) // Error image if loading fails
                .into(imageView);
    }

    // Format timestamp
    public static String formatTimestamp(String timestamp) {
        LocalDateTime dateTime = LocalDateTime.parse(timestamp);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter;

        if (dateTime.getYear() == currentDate.getYear()) {
            if (dateTime.toLocalDate().equals(currentDate)) {
                // Today's date, format as "Today HH:mm"
                formatter = DateTimeFormatter.ofPattern("'Today' HH:mm");
            } else {
                // Other days in the current year, format as "MM-dd HH:mm"
                formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            }
        } else {
            // Not in the current year, format as "yyyy-MM-dd"
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        return dateTime.format(formatter);
    }

    // Other utility methods can be added here
}
