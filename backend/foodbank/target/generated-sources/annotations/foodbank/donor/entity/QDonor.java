package foodbank.donor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDonor is a Querydsl query type for Donor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDonor extends EntityPathBase<Donor> {

    private static final long serialVersionUID = -210018857L;

    public static final QDonor donor = new QDonor("donor");

    public final StringPath address = createString("address");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final ListPath<NonperishableDonation, QNonperishableDonation> nonperishableDonations = this.<NonperishableDonation, QNonperishableDonation>createList("nonperishableDonations", NonperishableDonation.class, QNonperishableDonation.class, PathInits.DIRECT2);

    public final ListPath<PerishableDonation, QPerishableDonation> perishableDonations = this.<PerishableDonation, QPerishableDonation>createList("perishableDonations", PerishableDonation.class, QPerishableDonation.class, PathInits.DIRECT2);

    public QDonor(String variable) {
        super(Donor.class, forVariable(variable));
    }

    public QDonor(Path<? extends Donor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDonor(PathMetadata metadata) {
        super(Donor.class, metadata);
    }

}

