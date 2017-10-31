package foodbank.request.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequest is a Querydsl query type for Request
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRequest extends EntityPathBase<Request> {

    private static final long serialVersionUID = 870918519L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequest request = new QRequest("request");

    public final foodbank.beneficiary.entity.QBeneficiary beneficiary;

    public final StringPath beneficiaryName = createString("beneficiaryName");

    public final foodbank.inventory.entity.QFoodItem foodItem;

    public final StringPath foodItemDescription = createString("foodItemDescription");

    public final NumberPath<Integer> foodItemQuantity = createNumber("foodItemQuantity", Integer.class);

    public final StringPath id = createString("id");

    public final StringPath requestCreationDate = createString("requestCreationDate");

    public QRequest(String variable) {
        this(Request.class, forVariable(variable), INITS);
    }

    public QRequest(Path<? extends Request> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequest(PathMetadata metadata, PathInits inits) {
        this(Request.class, metadata, inits);
    }

    public QRequest(Class<? extends Request> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.beneficiary = inits.isInitialized("beneficiary") ? new foodbank.beneficiary.entity.QBeneficiary(forProperty("beneficiary")) : null;
        this.foodItem = inits.isInitialized("foodItem") ? new foodbank.inventory.entity.QFoodItem(forProperty("foodItem")) : null;
    }

}

