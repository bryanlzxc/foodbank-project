package foodbank.allocation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAllocatedFoodItems is a Querydsl query type for AllocatedFoodItems
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAllocatedFoodItems extends BeanPath<AllocatedFoodItems> {

    private static final long serialVersionUID = -592700596L;

    public static final QAllocatedFoodItems allocatedFoodItems = new QAllocatedFoodItems("allocatedFoodItems");

    public final NumberPath<Integer> allocatedQuantity = createNumber("allocatedQuantity", Integer.class);

    public final StringPath category = createString("category");

    public final StringPath classification = createString("classification");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> inventoryQuantity = createNumber("inventoryQuantity", Integer.class);

    public final NumberPath<Integer> requestedQuantity = createNumber("requestedQuantity", Integer.class);

    public QAllocatedFoodItems(String variable) {
        super(AllocatedFoodItems.class, forVariable(variable));
    }

    public QAllocatedFoodItems(Path<? extends AllocatedFoodItems> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAllocatedFoodItems(PathMetadata metadata) {
        super(AllocatedFoodItems.class, metadata);
    }

}

