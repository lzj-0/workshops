

public class PhoneApp {
    private String app;
    private String category;
    private Float rating;

    public PhoneApp(String app, String category, Float rating) {
        this.app = app;
        this.category = category;
        this.rating = rating;
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getRating() {
        return this.rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "App: %s\ncategory: %s\nrating: %f\n\n".formatted(this.getApp(), this.getCategory(), this.getRating());
    }
    
    
}
