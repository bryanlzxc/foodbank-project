package foodbank.beneficiary.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBeneficiary is a Querydsl query type for Beneficiary
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBeneficiary extends EntityPathBase<Beneficiary> {

    private static final long serialVersionUID = 29323191L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBeneficiary beneficiary = new QBeneficiary("beneficiary");

    public final NumberPath<Long> acraRegistrationNumber = createNumber("acraRegistrationNumber", Long.class);

    public final StringPath address = createString("address");

    public final NumberPath<Long> membershipNumber = createNumber("membershipNumber", Long.class);

    public final StringPath memberType = createString("memberType");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> numBeneficiary = createNumber("numBeneficiary", Integer.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath sector = createString("sector");

    public final foodbank.user.entity.QUser user;

    public QBeneficiary(String variable) {
        this(Beneficiary.class, forVariable(variable), INITS);
    }

    public QBeneficiary(Path<? extends Beneficiary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBeneficiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBeneficiary(PathMetadata metadata, PathInits inits) {
        this(Beneficiary.class, metadata, inits);
    }

    public QBeneficiary(Class<? extends Beneficiary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new foodbank.user.entity.QUser(forProperty("user")) : null;
    }

}

