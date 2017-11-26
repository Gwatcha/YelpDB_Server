package RecordClasses;

public class Review {
    private String type;
    private String business_id;
    private RestaurantVotes votes;
    private String review_id;
    private String text;
    private Integer stars;
    private String user_id;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public RestaurantVotes getVotes() {
        return votes;
    }

    public void setVotes(RestaurantVotes votes) {
        this.votes = votes;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public class RestaurantVotes {
        Integer cool;
        Integer useful;
        Integer funny;

        public Integer getCool() {
            return cool;
        }

        public void setCool(Integer cool) {
            this.cool = cool;
        }

        public Integer getUseful() {
            return useful;
        }

        public void setUseful(Integer useful) {
            this.useful = useful;
        }

        public Integer getFunny() {
            return funny;
        }

        public void setFunny(Integer funny) {
            this.funny = funny;
        }
    }
}
