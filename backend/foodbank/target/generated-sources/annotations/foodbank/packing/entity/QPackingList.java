package foodbank.packing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPackingList is a Querydsl query type for PackingList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPackingList extends EntityPathBase<PackingList> {

    private static final long serialVersionUID = 1512493941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPackingList packingList = new QPackingList("packingList");

    public final foodbank.beneficiary.entity.QBeneficiary beneficiary;

    public final StringPath id = createString("id");

    public final ListPath<PackedFoodItem, QPackedFoodItem> packedItems = this.<PackedFoodItem, QPackedFoodItem>createList("packedItems", PackedFoodItem.class, QPackedFoodItem.class, PathInits.DIRECT2);

    public final BooleanPath packingStatus = createBoolean("packingStatus");

    public QPackingList(String variable) {
        this(PackingList.class, forVariable(variable), INITS);
    }

    public QPackingList(Path<? extends PackingList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPackingList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPackingList(PathMetadata metadata, PathInits inits) {
        this(PackingList.class, metadata, inits);
    }

    public QPackingList(Class<? extends PackingList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.beneficiary = inits.isInitialized("beneficiary") ? new foodbank.beneficiary.entity.QBeneficiary(forProperty("beneficiary"), inits.get("beneficiary")) : null;
    }

}

