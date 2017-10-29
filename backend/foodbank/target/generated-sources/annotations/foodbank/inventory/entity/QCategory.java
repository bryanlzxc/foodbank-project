package foodbank.inventory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -1455057693L;

    public static final QCategory category1 = new QCategory("category1");

    public final StringPath category = createString("category");

    public final ListPath<Classification, QClassification> classification = this.<Classification, QClassification>createList("classification", Classification.class, QClassification.class, PathInits.DIRECT2);

    public final ListPath<Classification, QClassification> classifications = this.<Classification, QClassification>createList("classifications", Classification.class, QClassification.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

