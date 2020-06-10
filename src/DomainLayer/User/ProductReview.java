package DomainLayer.User;

import DomainLayer.User.Subscriber;

import javax.persistence.*;
import java.lang.annotation.Target;

//persistence
@Entity
@Table(name = "products_reviews")
public class ProductReview {
    @Column
    String review_data = "";
    @Column
    int rank;
    @OneToOne(targetEntity = Subscriber.class,cascade = CascadeType.ALL)
    Subscriber subscriber;


    public ProductReview(Subscriber subscriber,int rank,String review_data)    {
        this.review_data = review_data;
        this.rank = rank;
        this.subscriber = subscriber;
    }

    public ProductReview() {
    }

    public String getReview_data() {
        return review_data;
    }

    public int getRank() {
        return rank;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
