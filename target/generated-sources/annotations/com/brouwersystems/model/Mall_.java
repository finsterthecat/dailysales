package com.brouwersystems.model;

import com.brouwersystems.model.Store;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-02-20T14:06:31")
@StaticMetamodel(Mall.class)
public class Mall_ { 

    public static volatile SingularAttribute<Mall, Long> id;
    public static volatile SingularAttribute<Mall, String> name;
    public static volatile CollectionAttribute<Mall, Store> stores;

}