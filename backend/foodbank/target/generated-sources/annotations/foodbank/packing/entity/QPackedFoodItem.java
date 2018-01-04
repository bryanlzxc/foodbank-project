package foodbank.packing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPackedFoodItem is a Querydsl query type for PackedFoodItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPackedFoodItem extends EntityPathBase<PackedFoodItem> {

    private static final long serialVersionUID = 1362270811L;

    public static final QPackedFoodItem packedFoodItem = new QPackedFoodItem("packedFoodItem");

    public final foodbank.inventory.entity.QFoodItem _super = new foodbank.inventory.entity.QFoodItem(this);

    public final NumberPath<Integer> allocatedQuantity = createNumber("allocatedQuantity", Integer.class);

    //inherited
    public final StringPath category = _super.category;

    //inherited
    public final StringPath classification = _super.classification;

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final NumberPath<Integer> quantity = _super.quantity;

    public QPackedFoodItem(String variable) {
        super(PackedFoodItem.class, forVariable(variable));
    }

    public QPackedFoodItem(Path<? extends PackedFoodItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPackedFoodItem(PathMetadata metadata) {
        super(PackedFoodItem.class, metadata);
    }

}

