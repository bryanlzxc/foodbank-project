package foodbank.donor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDonation is a Querydsl query type for Donation
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QDonation extends BeanPath<Donation> {

    private static final long serialVersionUID = 1082816129L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDonation donation = new QDonation("donation");

    public final StringPath category = createString("category");

    public final StringPath classification = createString("classification");

    public final foodbank.inventory.entity.QFoodItem foodItem;

    public QDonation(String variable) {
        this(Donation.class, forVariable(variable), INITS);
    }

    public QDonation(Path<? extends Donation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDonation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDonation(PathMetadata metadata, PathInits inits) {
        this(Donation.class, metadata, inits);
    }

    public QDonation(Class<? extends Donation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodItem = inits.isInitialized("foodItem") ? new foodbank.inventory.entity.QFoodItem(forProperty("foodItem")) : null;
    }

}

