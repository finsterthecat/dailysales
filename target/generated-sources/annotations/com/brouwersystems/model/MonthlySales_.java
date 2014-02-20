package com.brouwersystems.model;

import com.brouwersystems.model.Store;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-02-20T14:06:31")
@StaticMetamodel(MonthlySales.class)
public class MonthlySales_ { 

    public static volatile SingularAttribute<MonthlySales, Long> id;
    public static volatile SingularAttribute<MonthlySales, Date> salesDate;
    public static volatile SingularAttribute<MonthlySales, Store> store;
    public static volatile SingularAttribute<MonthlySales, BigDecimal> costAmt;
    public static volatile SingularAttribute<MonthlySales, BigDecimal> salesAmt;

}