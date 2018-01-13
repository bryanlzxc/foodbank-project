package foodbank.donor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNonperishableDonation is a Querydsl query type for NonperishableDonation
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QNonperishableDonation extends BeanPath<NonperishableDonation> {

    private static final long serialVersionUID = -1505549621L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNonperishableDonation nonperishableDonation = new QNonperishableDonation("nonperishableDonation");

    public final foodbank.inventory.entity.QFoodItem donatedFoodItem;

    public final StringPath donationDate = createString("donationDate");

    public QNonperishableDonation(String variable) {
        this(NonperishableDonation.class, forVariable(variable), INITS);
    }

    public QNonperishableDonation(Path<? extends NonperishableDonation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNonperishableDonation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNonperishableDonation(PathMetadata metadata, PathInits inits) {
        this(NonperishableDonation.class, metadata, inits);
    }

    public QNonperishableDonation(Class<? extends NonperishableDonation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.donatedFoodItem = inits.isInitialized("donatedFoodItem") ? new foodbank.inventory.entity.QFoodItem(forProperty("donatedFoodItem")) : null;
    }

}

