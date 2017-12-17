package foodbank.inventory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFoodItem is a Querydsl query type for FoodItem
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QFoodItem extends BeanPath<FoodItem> {

    private static final long serialVersionUID = -1126402954L;

    public static final QFoodItem foodItem = new QFoodItem("foodItem");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QFoodItem(String variable) {
        super(FoodItem.class, forVariable(variable));
    }

    public QFoodItem(Path<? extends FoodItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFoodItem(PathMetadata metadata) {
        super(FoodItem.class, metadata);
    }

}
