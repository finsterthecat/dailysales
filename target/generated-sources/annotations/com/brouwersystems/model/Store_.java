package com.brouwersystems.model;

import com.brouwersystems.model.Mall;
import com.brouwersystems.model.MonthlySales;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-02-20T14:06:31")
@StaticMetamodel(Store.class)
public class Store_ { 

    public static volatile SingularAttribute<Store, Long> id;
    public static volatile CollectionAttribute<Store, MonthlySales> monthlySales;
    public static volatile SingularAttribute<Store, String> name;
    public static volatile SingularAttribute<Store, Mall> mall;

}