package foodbank.allocation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAllocationFoodItem is a Querydsl query type for AllocationFoodItem
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAllocationFoodItem extends BeanPath<AllocationFoodItem> {

    private static final long serialVersionUID = 1261854690L;

    public static final QAllocationFoodItem allocationFoodItem = new QAllocationFoodItem("allocationFoodItem");

    public final NumberPath<Integer> al_qty = createNumber("al_qty", Integer.class);

    public final StringPath category = createString("category");

    public final StringPath classification = createString("classification");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> req_qty = createNumber("req_qty", Integer.class);

    public QAllocationFoodItem(String variable) {
        super(AllocationFoodItem.class, forVariable(variable));
    }

    public QAllocationFoodItem(Path<? extends AllocationFoodItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAllocationFoodItem(PathMetadata metadata) {
        super(AllocationFoodItem.class, metadata);
    }

}

