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

    public final foodbank.allocation.entity.QAllocation allocation;

    public final StringPath id = createString("id");

    public final ListPath<foodbank.inventory.entity.FoodItem, foodbank.inventory.entity.QFoodItem> packedItems = this.<foodbank.inventory.entity.FoodItem, foodbank.inventory.entity.QFoodItem>createList("packedItems", foodbank.inventory.entity.FoodItem.class, foodbank.inventory.entity.QFoodItem.class, PathInits.DIRECT2);

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
        this.allocation = inits.isInitialized("allocation") ? new foodbank.allocation.entity.QAllocation(forProperty("allocation"), inits.get("allocation")) : null;
    }

}

