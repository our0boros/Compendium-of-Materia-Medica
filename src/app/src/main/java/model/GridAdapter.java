package model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private Context context;
    private ArrayList<Integer> data;
    private PlantTreeManager plantTreeManager;
    private PostTreeManager postTreeManager;

    public GridAdapter(Context context, ArrayList<Integer> data) throws JSONException, IOException {
        this.context = context;
        this.data = data;
        plantTreeManager = new PlantTreeManager((RBTree<Plant>) GeneratorFactory.tree(this.context, DataType.PLANT, R.raw.plants));
        postTreeManager = new PostTreeManager((RBTree<Post>) GeneratorFactory.tree(this.context, DataType.POST, R.raw.posts));
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_item, parent, false);
        return new GridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        String plantURL = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID, String.valueOf(data.get(position))).get(0).getValue().getImage();
        MainActivity.loadImageFromURL(this.context, plantURL, holder.plantImage, "Photo");
        String plantName = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID, String.valueOf(data.get(position))).get(0).getValue().getCommonName();
        String plantFamily = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID, String.valueOf(data.get(position))).get(0).getValue().getFamily();
        holder.plantHeading.setText(plantName);
        holder.plantSubheading.setText(plantFamily);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        public ImageView plantImage;
        public TextView plantHeading;
        public TextView plantSubheading;


        public GridViewHolder(View itemView) {
            super(itemView);
            plantImage = itemView.findViewById(R.id.plantImage);
            plantHeading = itemView.findViewById(R.id.plantHeading);
            plantSubheading = itemView.findViewById(R.id.plantSubHeading);
        }
    }
}
