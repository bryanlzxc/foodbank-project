package foodbank.history.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequestHistory is a Querydsl query type for RequestHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRequestHistory extends EntityPathBase<RequestHistory> {

    private static final long serialVersionUID = 1880260450L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequestHistory requestHistory = new QRequestHistory("requestHistory");

    public final NumberPath<Integer> allocatedQuantity = createNumber("allocatedQuantity", Integer.class);

    public final foodbank.beneficiary.entity.QBeneficiary beneficiary;

    public final StringPath category = createString("category");

    public final StringPath classification = createString("classification");

    public final StringPath description = createString("description");

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> requestCreationDate = createDateTime("requestCreationDate", java.util.Date.class);

    public final NumberPath<Integer> requestedQuantity = createNumber("requestedQuantity", Integer.class);

    public final StringPath username = createString("username");

    public QRequestHistory(String variable) {
        this(RequestHistory.class, forVariable(variable), INITS);
    }

    public QRequestHistory(Path<? extends RequestHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequestHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequestHistory(PathMetadata metadata, PathInits inits) {
        this(RequestHistory.class, metadata, inits);
    }

    public QRequestHistory(Class<? extends RequestHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.beneficiary = inits.isInitialized("beneficiary") ? new foodbank.beneficiary.entity.QBeneficiary(forProperty("beneficiary"), inits.get("beneficiary")) : null;
    }

}

