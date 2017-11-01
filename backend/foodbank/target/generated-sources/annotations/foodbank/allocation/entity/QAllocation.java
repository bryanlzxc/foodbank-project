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

    public static final QAllocation allocation = new QAllocation("allocation");

    public final StringPath id = createString("id");

    public final ListPath<AllocationFoodItem, QAllocationFoodItem> list = this.<AllocationFoodItem, QAllocationFoodItem>createList("list", AllocationFoodItem.class, QAllocationFoodItem.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QAllocation(String variable) {
        super(Allocation.class, forVariable(variable));
    }

    public QAllocation(Path<? extends Allocation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAllocation(PathMetadata metadata) {
        super(Allocation.class, metadata);
    }

}

