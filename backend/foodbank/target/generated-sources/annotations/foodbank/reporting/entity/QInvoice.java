package foodbank.reporting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvoice is a Querydsl query type for Invoice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInvoice extends EntityPathBase<Invoice> {

    private static final long serialVersionUID = -2114965610L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvoice invoice = new QInvoice("invoice");

    public final foodbank.beneficiary.entity.QBeneficiary billingOrganization;

    public final StringPath comments = createString("comments");

    public final StringPath dateMonth = createString("dateMonth");

    public final StringPath deliveryDate = createString("deliveryDate");

    public final BooleanPath deliveryStatus = createBoolean("deliveryStatus");

    public final StringPath deliveryTime = createString("deliveryTime");

    public final StringPath generationDate = createString("generationDate");

    public final BooleanPath generationStatus = createBoolean("generationStatus");

    public final StringPath id = createString("id");

    public final StringPath issuedBy = createString("issuedBy");

    public final ListPath<foodbank.packing.entity.PackedFoodItem, foodbank.packing.entity.QPackedFoodItem> packedItems = this.<foodbank.packing.entity.PackedFoodItem, foodbank.packing.entity.QPackedFoodItem>createList("packedItems", foodbank.packing.entity.PackedFoodItem.class, foodbank.packing.entity.QPackedFoodItem.class, PathInits.DIRECT2);

    public final foodbank.beneficiary.entity.QBeneficiary receivingOrganization;

    public QInvoice(String variable) {
        this(Invoice.class, forVariable(variable), INITS);
    }

    public QInvoice(Path<? extends Invoice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvoice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvoice(PathMetadata metadata, PathInits inits) {
        this(Invoice.class, metadata, inits);
    }

    public QInvoice(Class<? extends Invoice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.billingOrganization = inits.isInitialized("billingOrganization") ? new foodbank.beneficiary.entity.QBeneficiary(forProperty("billingOrganization"), inits.get("billingOrganization")) : null;
        this.receivingOrganization = inits.isInitialized("receivingOrganization") ? new foodbank.beneficiary.entity.QBeneficiary(forProperty("receivingOrganization"), inits.get("receivingOrganization")) : null;
    }

}

