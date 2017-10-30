package foodbank.request.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRequest is a Querydsl query type for Request
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRequest extends EntityPathBase<Request> {

    private static final long serialVersionUID = 870918519L;

    public static final QRequest request = new QRequest("request");

    public final StringPath beneficiary = createString("beneficiary");

    public final StringPath description = createString("description");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> qty = createNumber("qty", Integer.class);

    public QRequest(String variable) {
        super(Request.class, forVariable(variable));
    }

    public QRequest(Path<? extends Request> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRequest(PathMetadata metadata) {
        super(Request.class, metadata);
    }

}

