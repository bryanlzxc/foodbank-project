package foodbank.inventory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassification is a Querydsl query type for Classification
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QClassification extends BeanPath<Classification> {

    private static final long serialVersionUID = 826461195L;

    public static final QClassification classification1 = new QClassification("classification1");

    public final StringPath classification = createString("classification");

    public final ListPath<FoodItem, QFoodItem> foodItems = this.<FoodItem, QFoodItem>createList("foodItems", FoodItem.class, QFoodItem.class, PathInits.DIRECT2);

    public QClassification(String variable) {
        super(Classification.class, forVariable(variable));
    }

    public QClassification(Path<? extends Classification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassification(PathMetadata metadata) {
        super(Classification.class, metadata);
    }

}

