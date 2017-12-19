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

    public final ListPath<Donation, QDonation> donations = this.<Donation, QDonation>createList("donations", Donation.class, QDonation.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

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

