package foodbank.donor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDonatedFoodItem is a Querydsl query type for DonatedFoodItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDonatedFoodItem extends EntityPathBase<DonatedFoodItem> {

    private static final long serialVersionUID = 1526240695L;

    public static final QDonatedFoodItem donatedFoodItem = new QDonatedFoodItem("donatedFoodItem");

    public final foodbank.inventory.entity.QFoodItem _super = new foodbank.inventory.entity.QFoodItem(this);

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

    public QDonatedFoodItem(String variable) {
        super(DonatedFoodItem.class, forVariable(variable));
    }

    public QDonatedFoodItem(Path<? extends DonatedFoodItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDonatedFoodItem(PathMetadata metadata) {
        super(DonatedFoodItem.class, metadata);
    }

}

