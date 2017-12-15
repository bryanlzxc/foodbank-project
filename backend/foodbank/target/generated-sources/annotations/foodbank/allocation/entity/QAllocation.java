package foodbank.allocation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAllocation is a Querydsl query type for Allocation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAllocation extends EntityPathBase<Allocation> {

    private static final long serialVersionUID = 449388593L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAllocation allocation = new QAllocation("allocation");

    public final ListPath<foodbank.inventory.entity.FoodItem, foodbank.inventory.entity.QFoodItem> allocatedItems = this.<foodbank.inventory.entity.FoodItem, foodbank.inventory.entity.QFoodItem>createList("allocatedItems", foodbank.inventory.entity.FoodItem.class, foodbank.inventory.entity.QFoodItem.class, PathInits.DIRECT2);

    public final foodbank.beneficiary.entity.QBeneficiary beneficiary;

    public final StringPath id = createString("id");

    public QAllocation(String variable) {
        this(Allocation.class, forVariable(variable), INITS);
    }

    public QAllocation(Path<? extends Allocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAllocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAllocation(PathMetadata metadata, PathInits inits) {
        this(Allocation.class, metadata, inits);
    }

    public QAllocation(Class<? extends Allocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.beneficiary = inits.isInitialized("beneficiary") ? new foodbank.beneficiary.entity.QBeneficiary(forProperty("beneficiary"), inits.get("beneficiary")) : null;
    }

}

