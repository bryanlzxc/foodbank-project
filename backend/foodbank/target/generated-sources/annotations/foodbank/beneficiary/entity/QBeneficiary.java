package foodbank.beneficiary.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBeneficiary is a Querydsl query type for Beneficiary
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBeneficiary extends EntityPathBase<Beneficiary> {

    private static final long serialVersionUID = 29323191L;

    public static final QBeneficiary beneficiary = new QBeneficiary("beneficiary");

    public final StringPath address = createString("address");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> numBeneficiary = createNumber("numBeneficiary", Integer.class);

    public final NumberPath<Integer> numbeneficiary = createNumber("numbeneficiary", Integer.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath sector = createString("sector");

    public QBeneficiary(String variable) {
        super(Beneficiary.class, forVariable(variable));
    }

    public QBeneficiary(Path<? extends Beneficiary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBeneficiary(PathMetadata metadata) {
        super(Beneficiary.class, metadata);
    }

}

