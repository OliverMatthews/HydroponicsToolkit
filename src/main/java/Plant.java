import java.time.LocalDate;

/**
 * Represents a single plant in the hydroponics setup.
 *
 * @since 0.1.0
 */
public class Plant {

    /**
     * Unique identifying number for the plant.
     */
    private int uid;
    /**
     * Type of plant, e.g. 'Lettuce'.
     */
    private String type;
    /**
     * The date on which the plant was planted.
     */
    private LocalDate datePlanted;
    /**
     * Estimated date on which the plant will be ready to be harvested.
     */
    private LocalDate estimatedHarvestDate;

    /**
     * Default constructor for new plant objects, used when adding plants to the hydroponics system.
     * @param type Type of plant, e.g. 'Lettuce'.
     * @param datePlanted Date on which the plant was planted.
     */
    public Plant(String type, LocalDate datePlanted) {
        this.type = type;
        this.datePlanted = datePlanted;
    }

    /**
     * Database constructor for instantiating a plant using details read back from the database.
     * @param uid Unique identifying number for the plant.
     * @param type Type of plant, e.g. 'Lettuce'.
     * @param datePlanted Date on which the plant was planted.
     * @param estimatedHarvestDate Estimated date on which the plant will be ready to be harvested.
     */
    public Plant(int uid, String type, LocalDate datePlanted, LocalDate estimatedHarvestDate) {
        this.uid = uid;
        this.type = type;
        this.datePlanted = datePlanted;
        this.estimatedHarvestDate = estimatedHarvestDate;
    }

    /**
     * Gets the UID of the plant.
     * @return UID of the plant.
     */
    public int getUid() {
        return uid;
    }

    /**
     * Gets the type of the plant.
     * @return Type of plant, e.g. 'Lettuce'.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the date on which the plant was planted.
     * @return Date on which the plant was planted.
     */
    public LocalDate getDatePlanted() {
        return datePlanted;
    }

    /**
     * Gets the estimated date the plant will be ready for harvest.
     * @return Estimated date the plant will be ready for harvest.
     */
    public LocalDate getEstimatedHarvestDate() {
        return estimatedHarvestDate;
    }

    /**
     * Sets the UID number of the plant.
     * @param uid UID number of the plant.
     */
    private void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * Sets the estimated date the plant will be ready for harvest.
     * @param estimatedHarvestDate Estimated date the plant will be ready for harvest.
     */
    private void setEstimatedHarvestDate(LocalDate estimatedHarvestDate) {
        this.estimatedHarvestDate = estimatedHarvestDate;
    }
}
