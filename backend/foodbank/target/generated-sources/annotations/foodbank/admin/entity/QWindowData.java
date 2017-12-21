package foodbank.admin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWindowData is a Querydsl query type for WindowData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWindowData extends EntityPathBase<WindowData> {

    private static final long serialVersionUID = 790258578L;

    public static final QWindowData windowData = new QWindowData("windowData");

    public final QAdminSettings _super = new QAdminSettings(this);

    //inherited
    public final NumberPath<Double> decayRate = _super.decayRate;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final NumberPath<Double> multiplierRate = _super.multiplierRate;

    //inherited
    public final StringPath windowEndDateTime = _super.windowEndDateTime;

    //inherited
    public final StringPath windowStartDateTime = _super.windowStartDateTime;

    //inherited
    public final EnumPath<AdminSettings.WindowStatus> windowStatus = _super.windowStatus;

    public QWindowData(String variable) {
        super(WindowData.class, forVariable(variable));
    }

    public QWindowData(Path<? extends WindowData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWindowData(PathMetadata metadata) {
        super(WindowData.class, metadata);
    }

}

