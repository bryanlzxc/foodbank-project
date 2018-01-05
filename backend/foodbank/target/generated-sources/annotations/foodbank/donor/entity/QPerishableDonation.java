package foodbank.donor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPerishableDonation is a Querydsl query type for PerishableDonation
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPerishableDonation extends BeanPath<PerishableDonation> {

    private static final long serialVersionUID = 320140284L;

    public static final QPerishableDonation perishableDonation = new QPerishableDonation("perishableDonation");

    public final StringPath donationDate = createString("donationDate");

    public final ArrayPath<String[], String> donationDetails = createArray("donationDetails", String[].class);

    public QPerishableDonation(String variable) {
        super(PerishableDonation.class, forVariable(variable));
    }

    public QPerishableDonation(Path<? extends PerishableDonation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerishableDonation(PathMetadata metadata) {
        super(PerishableDonation.class, metadata);
    }

}

