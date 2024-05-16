package model;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.example.compendiumofmateriamedica.R;

import java.io.InputStream;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;

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

        // Create custom RequestOptions
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Optional: Disable disk caching
                .timeout(30000); // Set timeout in milliseconds (e.g., 30 seconds)

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

    // Check robot avatar url validation
    public static boolean isValidRoboHashURL(String url) {
        // Define the regex pattern to match the RoboHash URL
        String regex = "https://robohash\\.org/.+";
        // Compile the regex pattern into a Pattern object
        Pattern pattern = Pattern.compile(regex);
        // Create a Matcher object to match the input URL against the pattern
        Matcher matcher = pattern.matcher(url);
        // Return true if the URL matches the pattern, false otherwise
        return matcher.matches();
    }


    // Check username validation
    // Here, we assume that valid characters include letters, digits, and underscores
    public static boolean isValidUsername(String username) {
        // Check if username is not empty
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        // Check if username contains only valid characters
        String regex = "^[a-zA-Z0-9_]+$";
        if (!username.matches(regex)) {
            return false;
        }
        // Username is valid
        return true;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+[]{}|;:,.<>?";

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();

        // generate random lenth: min 1 to max 20
        int max=20;
        int min=1;
        int length=random.nextInt(max - min + 1) + min;

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }


}
