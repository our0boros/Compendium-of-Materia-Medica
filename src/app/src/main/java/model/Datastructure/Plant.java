package model.Datastructure;

import androidx.annotation.NonNull;

/**
 * @author: Haochen Gong
 * @description: Plant类
 **/
public class Plant implements Comparable<Plant>{
    private final int id;
    private final String commonName;
    private final String slug;
    private final String scientificName;
    private final String image;
    private final String genus;
    private final String family;
    private final String description;

    public Plant(int id, String commonName, String slug, String scientificName, String image, String genus, String family, String description){
        this.id = id;
        this.commonName = commonName;
        this.slug = slug;
        this.scientificName = scientificName;
        this.image = image;
        this.genus = genus;
        this.family = family;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getSlug() {
        return slug;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getImage() {
        return image;
    }

    public String getGenus() {
        return genus;
    }

    public String getFamily() {
        return family;
    }

    public String getDescription() {
        return description;
    }

    public Object getByType(PlantTreeManager.PlantInfoType type) {
        switch (type) {
            case ID: return getId();
            case COMMON_NAME: return getCommonName();
            case SLUG: return getSlug();
            case SCIENTIFIC_NAME: return getScientificName();
            case GENUS: return getGenus();
            case FAMILY: return getFamily();
            default: return null;
        }
    }

    @Override
    public int compareTo(Plant plant) {
        return this.id - plant.id;
    }

    @NonNull
    @Override
    public String toString() {
        return "{PlantID: " + id + ", "
                + "CommonName: " + commonName + ", "
                + "Slug: " + slug + ", "
                + "ScientificName: " + scientificName + ", "
                + "ImageUrl: " + image + ", "
                + "Genus: " + genus + ", "
                + "Family: " + family + ", "
                + "Description: " + description + "}";
    }
}