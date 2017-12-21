package foodbank.admin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminSettings is a Querydsl query type for AdminSettings
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdminSettings extends EntityPathBase<AdminSettings> {

    private static final long serialVersionUID = -1125574214L;

    public static final QAdminSettings adminSettings = new QAdminSettings("adminSettings");

    public final NumberPath<Double> decayRate = createNumber("decayRate", Double.class);

    public final StringPath id = createString("id");

    public final NumberPath<Double> multiplierRate = createNumber("multiplierRate", Double.class);

    public final StringPath windowEndDateTime = createString("windowEndDateTime");

    public final StringPath windowStartDateTime = createString("windowStartDateTime");

    public final EnumPath<AdminSettings.WindowStatus> windowStatus = createEnum("windowStatus", AdminSettings.WindowStatus.class);

    public QAdminSettings(String variable) {
        super(AdminSettings.class, forVariable(variable));
    }

    public QAdminSettings(Path<? extends AdminSettings> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminSettings(PathMetadata metadata) {
        super(AdminSettings.class, metadata);
    }

}

